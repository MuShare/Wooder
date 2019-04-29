package org.mushare.wooder.server.controller;

import lombok.AllArgsConstructor;
import org.mushare.wooder.server.service.UserService;
import org.mushare.wooder.spec.Endpoints;
import org.mushare.wooder.spec.request.UserRequest;
import org.mushare.wooder.spec.response.OperationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {

  private UserService userService;

  @RequestMapping(value = Endpoints.USER_ADD, method = RequestMethod.PUT)
  public OperationResponse createUser(@RequestBody UserRequest userRequest) {
    return userService.createUser(userRequest);
  }
}
