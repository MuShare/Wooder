package org.mushare.wooder.server.service;

import lombok.AllArgsConstructor;
import org.mushare.wooder.server.repository.ProjectDto;
import org.mushare.wooder.server.repository.TextFolderRepository;
import org.mushare.wooder.spec.response.ProjectInfoResponse;
import org.mushare.wooder.spec.response.TextFolderListResponse.TextFolderListItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Transactional
public class TextFolderService {

  private TextFolderRepository textFolderRepository;

  public TextFolderListItem getTextFolderInfo(long textFolderId) {
    return textFolderRepository.findById(textFolderId)
        .map(textFolderDto -> TextFolderListItem.builder().createTime(textFolderDto.getCreatedAt())
            .updateTime(textFolderDto.getUpdatedAt()).name(textFolderDto.getName())
            .projectInfoResponse(toProjectInfoResponse(textFolderDto.getProjectDto())).build())
        .orElse(TextFolderListItem.builder().build());
  }

  private ProjectInfoResponse toProjectInfoResponse(ProjectDto projectDto) {
    return ProjectInfoResponse.builder().name(projectDto.getName()).id(projectDto.getId())
        .description(projectDto.getDescription()).build();
  }
}
