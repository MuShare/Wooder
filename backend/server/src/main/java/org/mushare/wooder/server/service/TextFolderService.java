package org.mushare.wooder.server.service;

import javax.persistence.EntityManager;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mushare.wooder.server.repository.ProjectDto;
import org.mushare.wooder.server.repository.TextFolderDto;
import org.mushare.wooder.server.repository.TextFolderRepository;
import org.mushare.wooder.spec.response.OperationResponse;
import org.mushare.wooder.spec.response.ProjectInfoResponse;
import org.mushare.wooder.spec.response.TextFolderListResponse.TextFolderListItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Transactional
public class TextFolderService {

  private EntityManager entityManager;
  private TextFolderRepository textFolderRepository;

  public OperationResponse create(String name, long projectId) {
    textFolderRepository.save(TextFolderDto.builder()
        .createdAt(System.currentTimeMillis())
        .updatedAt(System.currentTimeMillis())
        .name(name)
        .projectDto(entityManager.getReference(ProjectDto.class, projectId))
        .build());
    return OperationResponse.builder().succeed(true).build();
  }

  public TextFolderListItem getTextFolderInfo(long textFolderId) {
    return textFolderRepository.findById(textFolderId)
        .map(textFolderDto -> TextFolderListItem.builder()
            .createTime(textFolderDto.getCreatedAt())
            .updateTime(textFolderDto.getUpdatedAt())
            .name(textFolderDto.getName())
            .projectInfoResponse(toProjectInfoResponse(textFolderDto.getProjectDto()))
            .build())
        .orElse(TextFolderListItem.builder().build());
  }

  private ProjectInfoResponse toProjectInfoResponse(ProjectDto projectDto) {
    return ProjectInfoResponse.builder()
        .name(projectDto.getName())
        .id(projectDto.getId())
        .description(projectDto.getDescription())
        .build();
  }
}
