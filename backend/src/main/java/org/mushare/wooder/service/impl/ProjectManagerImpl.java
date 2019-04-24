package org.mushare.wooder.service.impl;

import org.mushare.common.util.Debug;
import org.mushare.wooder.bean.ProjectBean;
import org.mushare.wooder.domain.Group;
import org.mushare.wooder.domain.Project;
import org.mushare.wooder.service.ProjectManager;
import org.mushare.wooder.service.common.BaseManager;
import org.mushare.wooder.service.common.Result;
import org.mushare.wooder.service.common.ResultCode;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProjectManagerImpl extends BaseManager implements ProjectManager {

    @Override
    public Result<ProjectBean> add(String name, String groupId) {
        Optional<Group> group = groupDao.findById(groupId);
        if (!group.isPresent()) {
            Debug.error("Cannot find a group with this group id " + groupId);
            return Result.error(ResultCode.GroupIdError);
        }
        Project project = new Project();
        project.setCreatedAt(System.currentTimeMillis());
        project.setUpdatedAt(project.getCreatedAt());
        project.setName(name);
        project.setGroup(group.get());
        projectDao.save(project);
        return Result.data(new ProjectBean(project));
    }

}
