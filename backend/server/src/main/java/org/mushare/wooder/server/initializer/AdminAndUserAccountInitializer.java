package org.mushare.wooder.server.initializer;

import java.util.Collections;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mushare.wooder.server.service.SecurityService;
import org.mushare.wooder.server.service.UserService;
import org.mushare.wooder.spec.request.UserRequest;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class AdminAndUserAccountInitializer implements InitializingBean {

  private UserService userService;
  private SecurityService securityService;

  @Override
  public void afterPropertiesSet() {
    securityService.initializeAuthorities();

    userService.createUser(UserRequest.builder().username("admin").password("password")
        .authorities(Collections.singletonList("ADMIN")).email("admin@admin.com").build());
    log.info("Initialized admin account");

  }
}
