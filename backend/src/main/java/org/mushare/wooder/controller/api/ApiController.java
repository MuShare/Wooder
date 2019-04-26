package org.mushare.wooder.controller.api;

import org.mushare.wooder.controller.common.BaseController;
import org.mushare.wooder.controller.common.Response;
import org.mushare.wooder.service.common.Result;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = "/api")
public class ApiController extends BaseController {

    @RequestMapping(value = "/xcode/text/{projectId}", method = RequestMethod.GET)
    public Map<String, String> pullXcodeText(@PathVariable String projectId) {
        Result<Map<String, String>> result = projectManager.xcodeText(projectId);
        return result.getData();
    }

}
