package org.mushare.wooder.controller;

import com.google.common.collect.ImmutableMap;
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

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/web/member")
public class MemberController extends BaseController {

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity addMember(@RequestParam String email, @RequestParam String name, @RequestParam String password, HttpServletRequest request) {
        return authGroup(request, groupId -> {
            Result result = memberManager.add(email, name, password, groupId);
            if (result.hasError()) {
                return result.errorMapping(ImmutableMap.of(
                        ResultCode.GroupIdError, ErrorCode.GroupIdNotExist,
                        ResultCode.MemberEmailRegistered, ErrorCode.MemberExist
                ));
            }
            return Response.success().build();
        });
    }

}
