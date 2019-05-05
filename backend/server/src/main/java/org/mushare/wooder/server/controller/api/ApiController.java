package org.mushare.wooder.server.controller.api;

import java.util.Map;
import lombok.AllArgsConstructor;
import org.mushare.wooder.server.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ApiController {

  private ProjectService projectService;

  @RequestMapping(value = "/xcode/text/{projectId}", method = RequestMethod.GET)
  public Map<String, String> pullXcodeText(@PathVariable String projectId) {
    return null;
  }
}
