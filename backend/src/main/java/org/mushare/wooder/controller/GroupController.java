package org.mushare.wooder.controller;

import org.mushare.wooder.controller.common.BaseController;
import org.mushare.wooder.controller.common.ErrorCode;
import org.mushare.wooder.controller.common.Response;
import org.mushare.wooder.service.common.Result;
import org.mushare.wooder.service.common.ResultCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/web/group")
public class GroupController extends BaseController {

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity createGroup(@RequestParam String email, @RequestParam String name, @RequestParam String password) {
        Result result = groupManager.create(email, name, password);
        if (result.hasError()) {
            return result.errorMapping(Collections.singletonMap(ResultCode.GroupEmailRegistered, ErrorCode.GroupExist));
        }
        return Response.success().build();
    }

}
