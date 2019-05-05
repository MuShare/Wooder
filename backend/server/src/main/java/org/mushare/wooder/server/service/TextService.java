package org.mushare.wooder.server.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mushare.wooder.server.repository.LanguageRepository;
import org.mushare.wooder.server.repository.TextContentDto;
import org.mushare.wooder.server.repository.TextContentRepository;
import org.mushare.wooder.server.repository.TextDto;
import org.mushare.wooder.server.repository.TextFolderDto;
import org.mushare.wooder.server.repository.TextRepository;
import org.mushare.wooder.spec.request.TextRequest;
import org.mushare.wooder.spec.request.TextRequest.TextContentRequest;
import org.mushare.wooder.spec.response.LanguageInfoResponse;
import org.mushare.wooder.spec.response.OperationResponse;
import org.mushare.wooder.spec.response.TextListResponse;
import org.mushare.wooder.spec.response.TextResponse;
import org.mushare.wooder.spec.response.TextResponse.TextContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Transactional
public class TextService {

  TextRepository textRepository;
  TextContentRepository textContentRepository;
  EntityManager entityManager;
  LanguageRepository languageRepository;

  public TextResponse getTextByTextId(long textId) {
    return textRepository.findById(textId)
        .map(this::toTextResponse).orElse(TextResponse.builder().build());
  }

  public TextListResponse getTextByTextfolderId(long textFolderId, int pageNumber, int pageSize) {
    Page<TextDto> textDtos = textRepository
        .findByTextFolderDtoId(textFolderId, PageRequest.of(pageNumber, pageSize));
    return TextListResponse
        .builder()
        .totalCount(textDtos.getTotalElements())
        .textResponses(textDtos.get().map(this::toTextResponse)
            .collect(Collectors.toList()))
        .build();
  }

  private TextResponse toTextResponse(TextDto textDto) {
    return TextResponse
        .builder()
        .id(textDto.getId())
        .identifier(textDto.getIdentifier())
        .name(textDto.getName())
        .platforms(getPlatforms(textDto))
        .textContents(textContentRepository
            .findByTextIdOrderByLanguageDto(textDto.getId()).stream()
            .map(textContentDto -> TextContent.builder()
                .createTime(textContentDto.getCreateTime())
                .updateTime(textContentDto.getUpdateTime())
                .id(textContentDto.getId())
                .languageInfoResponse(LanguageInfoResponse.builder()
                    .identifier(textContentDto.getLanguageDto().getIdentifier())
                    .name(textContentDto.getLanguageDto().getName())
                    .build())
                .string(textContentDto.getString())
                .build()
            ).collect(Collectors.toList())
        )
        .build();
  }

  public OperationResponse editText(TextRequest textRequest) {
    return textRepository.findById(textRequest.getId())
        .map(textDto -> {
          textDto.setAndroid(textRequest.getPlatforms().contains("android"));
          textDto.setIos(textRequest.getPlatforms().contains("ios"));
          textDto.setIdentifier(textRequest.getIdentifier());
          textDto.setName(textRequest.getName());
          textDto.setUpdateTime(System.currentTimeMillis());
          textRepository.save(textDto);
          Map<Long, String> contents = textRequest.getTextContentRequests().stream()
              .collect(Collectors.toMap(
                  TextContentRequest::getId, TextContentRequest::getString));
          textContentRepository.findByTextIdOrderByLanguageDto(textRequest.getId()).stream()
              .filter(textContentDto -> contents.containsKey(textContentDto.getId()))
              .filter(textContentDto -> contents.get(textContentDto.getId()) != null && !contents
                  .get(textContentDto.getId()).equals(textContentDto.getString()))
              .forEach(textContentDto -> {
                textContentDto.setString(contents.get(textContentDto.getId()));
                textContentDto.setUpdateTime(System.currentTimeMillis());
              });
          return OperationResponse.builder().succeed(true).build();
        }).orElse(OperationResponse.builder().succeed(false).build());
  }

  public OperationResponse createText(String identifier, long textFolderId) {
    TextDto textDto = TextDto.builder()
        .createTime(System.currentTimeMillis())
        .updateTime(System.currentTimeMillis())
        .identifier(identifier)
        .name("")
        .android(true)
        .ios(true)
        .textFolderDto(entityManager.getReference(TextFolderDto.class, textFolderId))
        .build();
    textRepository.save(textDto);
    List<TextContentDto> textContentDtos = languageRepository
        .findByProjectDtoId(textDto.getTextFolderDto().getProjectDto().getId(),
            PageRequest.of(0, Integer.MAX_VALUE)).stream()
        .map(languageDto -> TextContentDto.builder()
            .createTime(System.currentTimeMillis())
            .updateTime(System.currentTimeMillis())
            .languageDto(languageDto)
            .string("")
            .text(textDto)
            .build())
        .collect(Collectors.toList());
    textContentRepository.saveAll(textContentDtos);
    return OperationResponse.builder().succeed(true).build();
  }

  private List<String> getPlatforms(TextDto textDto) {
    List<String> platforms = new ArrayList<>();
    if (textDto.getAndroid()) {
      platforms.add("android");
    }
    if (textDto.getIos()) {
      platforms.add("ios");
    }
    return platforms;
  }
}
