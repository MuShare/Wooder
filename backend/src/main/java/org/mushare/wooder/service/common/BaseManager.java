package org.mushare.wooder.service.common;

import org.mushare.wooder.dao.*;
import org.mushare.wooder.domain.Member;
import org.mushare.wooder.domain.Project;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

public class BaseManager {

    @Autowired
    protected GroupDao groupDao;

    @Autowired
    protected ProjectDao projectDao;

    @Autowired
    protected MemberDao memberDao;

    @Autowired
    protected TextFolderDao textFolderDao;

    @Autowired
    protected LanguageDao languageDao;

    protected String uuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    protected Result authProject(String projectId, String memberId, Function<Project, Result> authed) {
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
