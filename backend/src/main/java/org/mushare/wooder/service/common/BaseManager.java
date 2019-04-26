package org.mushare.wooder.service.common;

import org.mushare.wooder.dao.*;
import org.mushare.wooder.domain.Member;
import org.mushare.wooder.domain.Project;
import org.mushare.wooder.domain.Text;
import org.mushare.wooder.domain.TextFolder;
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
    protected TextDao textDao;

    @Autowired
    protected TextContentDao textContentDao;

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

    protected <T> Result<T> authTextFolder(String textfolderId, String memberId, Function<TextFolder, Result<T>> authed) {
        Optional<TextFolder> textFolder = textFolderDao.findById(textfolderId);
        if (!textFolder.isPresent()) {
            return Result.error(ResultCode.TextFolderIdError);
        }
        Optional<Member> member = memberDao.findById(memberId);
        if (!member.isPresent()) {
            return Result.error(ResultCode.MemberIdError);
        }
        Project project = textFolder.get().getProject();
        if (!projectDao.findByGroupOrderByCreatedAt(member.get().getGroup()).contains(project)) {
            return Result.error(ResultCode.NoPrivilege);
        }
        return authed.apply(textFolder.get());
    }

    protected <T> Result<T> authText(String textId, String memberId, Function<Text, Result<T>> authed) {
        Optional<Text> text = textDao.findById(textId);
        if (!text.isPresent()) {
            return Result.error(ResultCode.TextIdError);
        }
        Optional<Member> member = memberDao.findById(memberId);
        if (!member.isPresent()) {
            return Result.error(ResultCode.MemberIdError);
        }
        Project project = text.get().getFolder().getProject();
        if (!projectDao.findByGroupOrderByCreatedAt(member.get().getGroup()).contains(project)) {
            return Result.error(ResultCode.NoPrivilege);
        }
        return authed.apply(text.get());
    }

}
