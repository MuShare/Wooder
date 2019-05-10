package org.mushare.wooder.server.service;

import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mushare.wooder.server.repository.AuthorityRepository;
import org.mushare.wooder.server.repository.GroupDto;
import org.mushare.wooder.server.repository.GroupRepository;
import org.mushare.wooder.server.repository.LanguageDto;
import org.mushare.wooder.server.repository.LanguageRepository;
import org.mushare.wooder.server.repository.ProjectAuthorityDto;
import org.mushare.wooder.server.repository.ProjectAuthorityRepository;
import org.mushare.wooder.server.repository.ProjectDto;
import org.mushare.wooder.server.repository.ProjectRepository;
import org.mushare.wooder.server.repository.TextFolderDto;
import org.mushare.wooder.server.repository.TextFolderRepository;
import org.mushare.wooder.server.repository.UserDto;
import org.mushare.wooder.server.security.SecurityConstants.Authority;
import org.mushare.wooder.spec.request.ProjectRequest;
import org.mushare.wooder.spec.response.LanguageInfoResponse;
import org.mushare.wooder.spec.response.LanguageListResponse;
import org.mushare.wooder.spec.response.OperationResponse;
import org.mushare.wooder.spec.response.ProjectInfoListResponse;
import org.mushare.wooder.spec.response.ProjectInfoResponse;
import org.mushare.wooder.spec.response.TextFolderListResponse;
import org.mushare.wooder.spec.response.TextFolderListResponse.TextFolderListItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Transactional
public class ProjectService {

  private ProjectRepository projectRepository;
  private EntityManager entityManager;
  private SecurityService securityService;
  private TextFolderRepository textFolderRepository;
  private LanguageRepository languageRepository;
  private AuthorityRepository authorityRepository;
  private ProjectAuthorityRepository projectAuthorityRepository;
  private GroupRepository groupRepository;

  public OperationResponse createProject(ProjectRequest projectRequest) throws Exception {
    System.out.println(projectRequest);
    ProjectDto createdProjectDto = projectRepository
        .save(ProjectDto.builder().createdAt(System.currentTimeMillis())
            .updatedAt(System.currentTimeMillis()).description(projectRequest.getDescription())
            .groupDto(groupRepository.existsById(projectRequest.getGroupId()) ? entityManager
                .getReference(GroupDto.class, projectRequest.getGroupId()) : null)
            .name(projectRequest.getName())
            .description(projectRequest.getDescription())
            .createdByUserDto(
                entityManager.getReference(UserDto.class, securityService.getCurrentUserId()))
            .build());
    projectRepository.save(createdProjectDto);
    ProjectAuthorityDto projectAuthorityDto = ProjectAuthorityDto.builder()
        .authorityDto(authorityRepository.findByAuthority(Authority.PROJECT_ADMIN))
        .projectDto(createdProjectDto).userDto(securityService.getCurrentUser())
        .createTime(System.currentTimeMillis()).updateTime(System.currentTimeMillis()).build();
    projectAuthorityRepository.save(projectAuthorityDto);
    return OperationResponse.builder().succeed(true).object(createdProjectDto).build();
  }

  public ProjectInfoResponse projectInfo(long projectId) {
    return projectRepository.findById(projectId)
        .map(projectDto -> ProjectInfoResponse.builder().createTime(projectDto.getCreatedAt())
            .createdByUserId(projectDto.getCreatedByUserDto().getId())
            .createdByUserName(projectDto.getCreatedByUserDto().getUsername())
            .groupId(projectDto.getGroupDto() == null ? -1 : projectDto.getGroupDto().getId())
            .groupName(projectDto.getGroupDto() == null ? null : projectDto.getGroupDto().getName())
            .description(projectDto.getDescription())
            .name(projectDto.getName())
            .id(projectDto.getId())
            .build())
        .orElse(ProjectInfoResponse.builder().build());
  }

  public ProjectInfoListResponse projectInfoList(int pageNumber, int pageSize) {
    try {
      Page<ProjectAuthorityDto> projectAuthorityDtos = projectAuthorityRepository
          .findByUserDtoId(securityService.getCurrentUserId(),
              PageRequest.of(pageNumber, pageSize));
      return ProjectInfoListResponse.builder().totalCount(projectAuthorityDtos.getTotalElements())
          .projectInfoResponses(projectAuthorityDtos.get().map(projectAuthorityDto -> {
            ProjectDto projectDto = projectAuthorityDto.getProjectDto();
            return ProjectInfoResponse.builder().name(projectDto.getName())
                .description(projectDto.getDescription()).createTime(projectDto.getCreatedAt())
                .id(projectDto.getId()).createdByUserId(projectDto.getCreatedByUserDto().getId())
                .createdByUserName(projectDto.getCreatedByUserDto().getUsername())
                .groupId(projectDto.getGroupDto() == null ? -1 : projectDto.getGroupDto().getId())
                .groupName(
                    projectDto.getGroupDto() == null ? null : projectDto.getGroupDto().getName())
                .build();
          }).collect(Collectors.toList())).build();
    } catch (Exception ex) {
      log.error("failed to get project list", ex);
      return ProjectInfoListResponse.builder().build();
    }
  }

  public OperationResponse createTextFolder(long projectId, String textFolderName) {
    ProjectDto projectDto = entityManager.getReference(ProjectDto.class, projectId);
    textFolderRepository.save(TextFolderDto.builder().createdAt(System.currentTimeMillis())
        .updatedAt(System.currentTimeMillis()).name(textFolderName).projectDto(projectDto).build());
    return OperationResponse.builder().succeed(true).build();
  }

  public TextFolderListResponse getTextFolderList(long projectId, int pageNumber, int pageSize) {
    Page<TextFolderDto> textFolderDtos = textFolderRepository.findByProjectDtoId(projectId,
        PageRequest.of(pageNumber, pageSize));
    return TextFolderListResponse.builder().totalCount(textFolderDtos.getTotalElements())
        .textFolderListItems(textFolderDtos.get()
            .map(textFolderDto -> TextFolderListItem.builder()
                .createTime(textFolderDto.getCreatedAt())
                .updateTime(textFolderDto.getUpdatedAt())
                .name(textFolderDto.getName())
                .id(textFolderDto.getId())
                .build())
            .collect(Collectors.toList()))
        .build();
  }

  public OperationResponse createLanguage(long projectId, String identifier, String name) {
    ProjectDto projectDto = entityManager.getReference(ProjectDto.class, projectId);
    languageRepository
        .save(LanguageDto.builder().createdAt(System.currentTimeMillis()).identifier(identifier)
            .name(name).projectDto(projectDto).updatedAt(System.currentTimeMillis()).build());
    return OperationResponse.builder().succeed(true).build();
  }

  public LanguageListResponse getLanguageList(long projectId, int pageNumber, int pageSize) {
    Page<LanguageDto> languageDtos = languageRepository.findByProjectDtoId(projectId,
        PageRequest.of(pageNumber, pageSize));
    return LanguageListResponse.builder().totalCount(languageDtos.getTotalElements())
        .languageListItems(languageDtos.get()
            .map(
                languageDto -> LanguageInfoResponse.builder().createTime(languageDto.getCreatedAt())
                    .updateTime(languageDto.getUpdatedAt()).id(languageDto.getId())
                    .identifier(languageDto.getIdentifier()).id(languageDto.getId())
                    .name(languageDto.getName())
                    .build())
            .collect(Collectors.toList()))
        .build();
  }
}
