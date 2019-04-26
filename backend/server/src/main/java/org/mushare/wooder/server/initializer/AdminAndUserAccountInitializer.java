package org.mushare.wooder.server.initializer;

import java.util.Collections;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mushare.wooder.server.repository.GroupDto;
import org.mushare.wooder.server.service.GroupService;
import org.mushare.wooder.server.service.SecurityService;
import org.mushare.wooder.server.service.UserService;
import org.mushare.wooder.spec.request.GroupRequest;
import org.mushare.wooder.spec.request.UserRequest;
import org.mushare.wooder.spec.response.OperationResponse;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class AdminAndUserAccountInitializer implements InitializingBean {

  private UserService userService;
  private SecurityService securityService;
  private GroupService groupService;

  @Override
  public void afterPropertiesSet() throws Exception {
    securityService.initializeAuthorities();
    OperationResponse operationResponse = groupService.addGroup(GroupRequest.builder()
        .name("admin")
        .email("admin@amdin.com")
        .password("password")
        .build());
    if (operationResponse.isSucceed()) {
      userService.createUser(UserRequest
          .builder()
          .username("admin")
          .password("password").authorities(
              Collections.singletonList("ADMIN"))
          .email("admin@admin.com")
          .build(), (GroupDto) operationResponse.getObject());
      log.info("Initialized admin account");
    } else {
      throw new Exception("Cannot init admin account");
    }
  }
}
