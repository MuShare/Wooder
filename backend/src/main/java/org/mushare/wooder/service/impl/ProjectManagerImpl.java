package org.mushare.wooder.service.impl;

import org.mushare.common.util.Debug;
import org.mushare.wooder.bean.ProjectBean;
import org.mushare.wooder.domain.Group;
import org.mushare.wooder.domain.Member;
import org.mushare.wooder.domain.Project;
import org.mushare.wooder.service.ProjectManager;
import org.mushare.wooder.service.common.BaseManager;
import org.mushare.wooder.service.common.Result;
import org.mushare.wooder.service.common.ResultCode;
import org.mushare.wooder.service.common.ResultList;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @Override
    public Result<ProjectBean> projectInfo(String projectId, String memberId) {
        return authProject(projectId, memberId, project -> {
            return Result.data(new ProjectBean(project));
        });
    }

    @Override
    public ResultList<ProjectBean> getProjectsByGroupId(String groupId) {
        Optional<Group> group = groupDao.findById(groupId);
        if (!group.isPresent()) {
            Debug.error("Cannot find a group with this group id " + groupId);
            return ResultList.error(ResultCode.GroupIdError);
        }
        List<ProjectBean> projectBeans = projectDao.findByGroupOrderByCreatedAt(group.get())
                .stream().map(project -> new ProjectBean(project)).collect(Collectors.toList());
        return ResultList.data(projectBeans);
    }

    @Override
    public ResultList<ProjectBean> getProjectsByMemberId(String memberId) {
        Optional<Member> member = memberDao.findById(memberId);
        if (!member.isPresent()) {
            return ResultList.error(ResultCode.MemberIdError);
        }
        List<ProjectBean> projectBeans = projectDao.findByGroupOrderByCreatedAt(member.get().getGroup())
                .stream().map(project -> new ProjectBean(project)).collect(Collectors.toList());
        return ResultList.data(projectBeans);
    }

}
