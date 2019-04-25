package org.mushare.wooder.service.common;

import org.mushare.wooder.dao.GroupDao;
import org.mushare.wooder.dao.MemberDao;
import org.mushare.wooder.dao.ProjectDao;
import org.mushare.wooder.dao.TextFolderDao;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

public class BaseManager {

    @Autowired
    protected GroupDao groupDao;

    @Autowired
    protected ProjectDao projectDao;

    @Autowired
    protected MemberDao memberDao;

    @Autowired
    protected TextFolderDao textFolderDao;

    protected String uuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

}
