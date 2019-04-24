package org.mushare.wooder.service;

import org.mushare.wooder.bean.ProjectBean;
import org.mushare.wooder.service.common.Result;
import org.mushare.wooder.service.common.ResultList;

public interface ProjectManager {

    Result<ProjectBean> add(String name, String groupId);

    ResultList<ProjectBean> getProjectsByGroupId(String groupId);

}
