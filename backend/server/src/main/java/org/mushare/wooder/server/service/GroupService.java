package org.mushare.wooder.server.service;

import javax.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mushare.wooder.server.repository.GroupDto;
import org.mushare.wooder.server.repository.GroupRepository;
import org.mushare.wooder.spec.request.GroupRequest;
import org.mushare.wooder.spec.response.GroupInfoResponse;
import org.mushare.wooder.spec.response.OperationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class GroupService {

  private GroupRepository groupRepository;
  private SecurityService securityService;

  public OperationResponse createGroup(GroupRequest groupRequest) {
    try {
      GroupDto groupDto = groupRepository.save(GroupDto.builder().name(groupRequest.getName())
          .description(groupRequest.getDescription())
          .createdByUserDto(securityService.getCurrentUser())
          .createdAt(System.currentTimeMillis()).updatedAt(System.currentTimeMillis()).build());
      return OperationResponse.builder().object(groupDto).succeed(true).build();
    } catch (Exception ex) {
      log.error("failed to create group", ex);
      return OperationResponse.builder().succeed(false).message(ex.getMessage()).build();
    }
  }

  @Transactional
  public GroupInfoResponse getGroupInfo(long groupId) {
    return groupRepository.findById(groupId)
        .map(groupDto -> GroupInfoResponse.builder().id(groupDto.getId())
            .description(groupDto.getDescription())
            .name(groupDto.getName()).createdTime(groupDto.getCreatedAt())
            .updatedTime(groupDto.getUpdatedAt())
            .createdUserId(groupDto.getCreatedByUserDto().getId())
            .createdUsername(groupDto.getCreatedByUserDto().getUsername()).build())
        .orElse(GroupInfoResponse.builder().build());
  }

}
