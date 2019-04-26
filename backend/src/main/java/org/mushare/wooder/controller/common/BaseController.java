package org.mushare.wooder.controller.common;

import org.mushare.wooder.bean.GroupBean;
import org.mushare.wooder.bean.MemberBean;
import org.mushare.wooder.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.function.Function;

public class BaseController {

    private final static String GroupIdFlag = "org.mushare.wooder.controller.BaseController.GroupIdFlag";
    private final static String MemberIdFlag = "org.mushare.wooder.controller.BaseController.MemberIdFlag";

    @Autowired
    protected GroupManager groupManager;

    @Autowired
    protected ProjectManager projectManager;

    @Autowired
    protected MemberManager memberManager;

    @Autowired
    protected TextFolderManager textFolderManager;

    @Autowired
    protected TextManager textManager;

    @Autowired
    protected LanguageManager languageManager;

    protected String token(HttpServletRequest request) {
        return request.getHeader("token");
    }

    /**
     * Get device remote IP by HttpServletRequest.
     *
     * @param request
     * @return
     */
    protected static String getRemoteIP(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    protected void groupLogin(HttpServletRequest request, GroupBean groupBean) {
        request.getSession().setAttribute(GroupIdFlag, groupBean.getId());
    }

    protected ResponseEntity authGroup(HttpServletRequest request, Function<String, ResponseEntity> authed) {
        String groupId = (String) request.getSession().getAttribute(GroupIdFlag);
        if (groupId == null) {
            return Response.badRequest(ErrorCode.ErrorNoSession).build();
        }
        return authed.apply(groupId);
    }

    protected void memberLogin(HttpServletRequest request, MemberBean memberBean) {
        request.getSession().setAttribute(MemberIdFlag, memberBean.getId());
    }

    protected ResponseEntity authMember(HttpServletRequest request, Function<String, ResponseEntity> authed) {
        String memberId = (String) request.getSession().getAttribute(MemberIdFlag);
        if (memberId == null) {
            return Response.badRequest(ErrorCode.ErrorNoSession).build();
        }
        return authed.apply(memberId);
    }

    protected ResponseEntity authGroupOrMember(HttpServletRequest request,
                                               Function<String, ResponseEntity> groupAuthed,
                                               Function<String, ResponseEntity> memberAuthed) {
        String groupId = (String) request.getSession().getAttribute(GroupIdFlag);
        if (groupId != null) {
            return groupAuthed.apply(groupId);
        }
        String memberId = (String) request.getSession().getAttribute(MemberIdFlag);
        if (memberId != null) {
            return memberAuthed.apply(memberId);
        }
        return Response.badRequest(ErrorCode.ErrorNoSession).build();
    }

}
