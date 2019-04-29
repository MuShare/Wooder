package org.mushare.wooder.server.controller;

import lombok.AllArgsConstructor;
import org.mushare.wooder.server.service.ProjectService;
import org.mushare.wooder.spec.Endpoints;
import org.mushare.wooder.spec.request.ProjectRequest;
import org.mushare.wooder.spec.response.LanguageListResponse;
import org.mushare.wooder.spec.response.OperationResponse;
import org.mushare.wooder.spec.response.ProjectInfoListResponse;
import org.mushare.wooder.spec.response.ProjectInfoResponse;
import org.mushare.wooder.spec.response.TextFolderListResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ProjectController {

  private ProjectService projectService;

  @RequestMapping(value = Endpoints.PROJECT_ADD, method = RequestMethod.PUT)
  public OperationResponse createProject(@RequestBody ProjectRequest projectRequest) {
    return projectService.createProject(projectRequest);
  }

  @RequestMapping(value = Endpoints.PROJECT + "/{projectId}", method = RequestMethod.GET)
  public ProjectInfoResponse getProjectInfo(@PathVariable long projectId) {
    return projectService.projectInfo(projectId);
  }

  @RequestMapping(value = Endpoints.PROJECT_LIST, method = RequestMethod.GET)
  public ProjectInfoListResponse getProjectListByGroup(@RequestParam long groupId,
      @RequestParam int pageNumber, @RequestParam int pageSize) {
    return projectService.projectInfoList(groupId, pageNumber, pageSize);
  }

  @RequestMapping(value = Endpoints.PROJECT
      + "/{projectId}/textfolder/add", method = RequestMethod.POST)
  public OperationResponse createFolder(@PathVariable String projectId, @RequestParam String name) {
    return null;
  }

  @RequestMapping(value = Endpoints.PROJECT
      + "/{projectId}/textfolder/list", method = RequestMethod.GET)
  public TextFolderListResponse createLanguage(@PathVariable long projectId,
      @RequestParam int pageNumber, @RequestParam int pageSize) {
    return projectService.getTextFolderList(projectId, pageNumber, pageSize);
  }

  @RequestMapping(value = Endpoints.PROJECT
      + "/{projectId}/language/add", method = RequestMethod.POST)
  public OperationResponse createLanguage(@PathVariable long projectId,
      @RequestParam String identifier, @RequestParam String name) {
    return projectService.createLanguage(projectId, identifier, name);
  }

  @RequestMapping(value = Endpoints.PROJECT
      + "/{projectId}/language/list", method = RequestMethod.GET)
  public LanguageListResponse getLanguages(@PathVariable long projectId,
      @RequestParam int pageNumber, @RequestParam int pageSize) {
    return projectService.getLanguageList(projectId, pageNumber, pageSize);
  }
}
