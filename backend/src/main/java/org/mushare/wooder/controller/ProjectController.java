package org.mushare.wooder.controller;

import com.google.common.collect.ImmutableMap;
import org.mushare.wooder.bean.ProjectBean;
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
@RequestMapping("/web/project")
public class ProjectController extends BaseController {

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity addProject(@RequestParam String name, HttpServletRequest request) {
        return authGroup(request, groupId -> {
            Result<ProjectBean> result = projectManager.add(name, groupId);
            if (result.hasError()) {
                return result.errorMapping(ImmutableMap.of(
                        ResultCode.GroupIdError, ErrorCode.GroupIdNotExist
                ));
            }
            return Response.ok()
                    .append("project", result.getData())
                    .build();
        });
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseEntity getProjects(HttpServletRequest request) {
        return authGroupOrMember(request, groupId -> {
            ResultList<ProjectBean> result = projectManager.getProjectsByGroupId(groupId);
            if (result.hasError()) {
                return result.errorMapping(ImmutableMap.of(
                        ResultCode.GroupIdError, ErrorCode.GroupIdNotExist
                ));
            }
            return Response.ok()
                    .append("projects", result.getData())
                    .build();
        }, memberId -> {
            ResultList<ProjectBean> result = projectManager.getProjectsByMemberId(memberId);
            if (result.hasError()) {
                return result.errorMapping(ImmutableMap.of(
                        ResultCode.MemberIdError, ErrorCode.MemberIdNotExist
                ));
            }
            return Response.ok()
                    .append("projects", result.getData())
                    .build();
        });
    }

}
