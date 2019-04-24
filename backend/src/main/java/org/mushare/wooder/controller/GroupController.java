package org.mushare.wooder.controller;

import com.google.common.collect.ImmutableMap;
import org.mushare.wooder.bean.GroupBean;
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

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity loginWithEmail(@RequestParam String email, @RequestParam String password, HttpServletRequest request) {
        Result<GroupBean> result = groupManager.login(email, password);
        if (result.hasError()) {
            return result.errorMapping(ImmutableMap.of(
                    ResultCode.GrouprEmailNotRegistered, ErrorCode.GroupNotExist,
                    ResultCode.GroupPasswordWrong, ErrorCode.GroupPasswordWrong
            ));
        }
        GroupBean groupBean = result.getData();
        request.getSession().setAttribute(GroupIdFlag, groupBean.getId());
        return Response.success()
                .append("groupId", groupBean)
                .build();
    }

}
