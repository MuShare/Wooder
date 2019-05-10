package org.mushare.wooder.server.controller.web;

import lombok.AllArgsConstructor;
import org.mushare.wooder.server.service.GroupService;
import org.mushare.wooder.spec.Endpoints;
import org.mushare.wooder.spec.request.GroupRequest;
import org.mushare.wooder.spec.response.GroupInfoResponse;
import org.mushare.wooder.spec.response.GroupUserListResponse;
import org.mushare.wooder.spec.response.OperationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class GroupController {

  private GroupService groupService;

  @RequestMapping(value = Endpoints.GROUP_CREATE, method = RequestMethod.PUT)
  public OperationResponse createGroup(GroupRequest groupRequest) {
    return groupService.createGroup(groupRequest);
  }

  @RequestMapping(value = Endpoints.GROUP_INFO, method = RequestMethod.GET)
  public GroupInfoResponse getGroupInfo(@RequestParam long groupId) {
    return groupService.getGroupInfo(groupId);
  }

  @RequestMapping(value = Endpoints.USER_LIST, method = RequestMethod.GET)
  public GroupUserListResponse getGroupUserList(@RequestParam long groupId,
      @RequestParam int pageSize, @RequestParam int pageNumber) {

    return null;
  }
}
