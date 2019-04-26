package org.mushare.wooder.controller.web;

import com.google.common.collect.ImmutableMap;
import org.mushare.wooder.bean.MemberBean;
import org.mushare.wooder.controller.common.BaseController;
import org.mushare.wooder.controller.common.ErrorCode;
import org.mushare.wooder.controller.common.Response;
import org.mushare.wooder.service.common.Result;
import org.mushare.wooder.service.common.ResultCode;
import org.mushare.wooder.service.common.ResultList;
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

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseEntity getMembersOfGroup(HttpServletRequest request) {
        return authGroup(request, groupId -> {
            ResultList result = memberManager.getMembersByGroupId(groupId);
            if (result.hasError()) {
                return result.errorMapping(ImmutableMap.of(
                        ResultCode.GroupIdError, ErrorCode.GroupIdNotExist
                ));
            }
            return Response.ok()
                    .append("members", result.getData())
                    .build();
        });
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity loginWithEmail(@RequestParam String email, @RequestParam String password, HttpServletRequest request) {
        Result<MemberBean> result = memberManager.login(email, password);
        if (result.hasError()) {
            return result.errorMapping(ImmutableMap.of(
                    ResultCode.MemberEmailNotRegistered, ErrorCode.MemberNotExist,
                    ResultCode.MemberPasswordWrong, ErrorCode.MemberPasswordWrong
            ));
        }
        memberLogin(request, result.getData());
        return Response.success().build();
    }

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public ResponseEntity getMemberInfo(HttpServletRequest request) {
        return authMember(request, memberId -> {
            Result<MemberBean> result = memberManager.memberInfo(memberId);
            if (result.hasError()) {
                return result.errorMapping(ImmutableMap.of(
                        ResultCode.MemberIdError, ErrorCode.MemberIdNotExist
                ));
            }
            return Response.ok()
                    .append("member", result.getData())
                    .build();
        });

    }

}
