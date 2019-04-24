package org.mushare.wooder.controller.common;

import org.mushare.wooder.service.GroupManager;
import org.mushare.wooder.service.ProjectManager;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

public class BaseController {

    protected final static String GroupIdFlag = "org.mushare.wooder.controller.GrouperController.GroupIdFlag";

    @Autowired
    protected GroupManager groupManager;

    @Autowired
    protected ProjectManager projectManager;

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

}
