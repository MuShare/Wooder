package org.mushare.wooder.service.impl;

import org.mushare.wooder.bean.TextFolderBean;
import org.mushare.wooder.domain.Member;
import org.mushare.wooder.domain.Project;
import org.mushare.wooder.domain.TextFolder;
import org.mushare.wooder.service.TextFolderManager;
import org.mushare.wooder.service.common.BaseManager;
import org.mushare.wooder.service.common.Result;
import org.mushare.wooder.service.common.ResultCode;
import org.mushare.wooder.service.common.ResultList;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class TextFolderManagerImpl extends BaseManager implements TextFolderManager {

    @Override
    public Result add(String name, String projectId, String memberId) {
        return authProject(projectId, memberId, project -> {
            TextFolder textFolder = new TextFolder();
            textFolder.setCreatedAt(System.currentTimeMillis());
            textFolder.setUpdatedAt(textFolder.getCreatedAt());
            textFolder.setName(name);
            textFolder.setProject(project);
            textFolderDao.save(textFolder);
            return Result.success();
        });
    }

    @Override
    public ResultList<TextFolderBean> getFoldersByProjectId(String projectId, String memberId) {
        return (ResultList<TextFolderBean>) authProject(projectId, memberId, project -> {
            return ResultList.data(textFolderDao.findByProjectOrderByName(project).stream().map(textFolder -> {
                return new TextFolderBean(textFolder);
            }).collect(Collectors.toList()));
        });
    }

    private Result authProject(String projectId, String memberId, Function<Project, Result> authed) {
        Optional<Project> project = projectDao.findById(projectId);
        if (!project.isPresent()) {
            return Result.error(ResultCode.ProjectIdError);
        }
        Optional<Member> member = memberDao.findById(memberId);
        if (!member.isPresent()) {
            return Result.error(ResultCode.MemberIdError);
        }
        if (!projectDao.findByGroupOrderByCreatedAt(member.get().getGroup()).contains(project.get())) {
            return Result.error(ResultCode.NoPrivilege);
        }
        return authed.apply(project.get());
    }

}
