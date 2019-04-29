package org.mushare.wooder.server.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mushare.wooder.server.repository.TextContentRepository;
import org.mushare.wooder.server.repository.TextDto;
import org.mushare.wooder.server.repository.TextRepository;
import org.mushare.wooder.spec.response.LanguageInfoResponse;
import org.mushare.wooder.spec.response.TextResponse;
import org.mushare.wooder.spec.response.TextResponse.TextContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Transactional
public class TextService {

  TextRepository textRepository;
  TextContentRepository textContentRepository;

  public TextResponse getText(long textId) {
    return textRepository.findById(textId)
        .map(textDto ->
            TextResponse
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
                .build()
        ).orElse(TextResponse.builder().build());
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
