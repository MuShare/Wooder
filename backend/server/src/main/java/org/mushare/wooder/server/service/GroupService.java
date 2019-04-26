package org.mushare.wooder.server.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mushare.wooder.server.repository.GroupDto;
import org.mushare.wooder.server.repository.GroupRepository;
import org.mushare.wooder.spec.request.GroupRequest;
import org.mushare.wooder.spec.response.OperationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class GroupService {

  private GroupRepository groupRepository;
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  public OperationResponse addGroup(GroupRequest groupRequest) {
    try {
      GroupDto groupDto = groupRepository.save(GroupDto.builder()
          .email(groupRequest.getEmail())
          .name(groupRequest.getName())
          .password(bCryptPasswordEncoder.encode(groupRequest.getPassword()))
          .createdAt(System.currentTimeMillis())
          .updatedAt(System.currentTimeMillis())
          .build()
      );
      return OperationResponse.builder()
          .object(groupDto)
          .succeed(true)
          .build();
    } catch (Exception ex) {
      log.error("failed to create group", ex);
      return OperationResponse.builder()
          .succeed(false)
          .message(ex.getMessage())
          .build();
    }
  }
}
