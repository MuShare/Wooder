package org.mushare.wooder.service.impl;

import org.mushare.wooder.bean.TextFolderBean;
import org.mushare.wooder.domain.TextFolder;
import org.mushare.wooder.service.TextFolderManager;
import org.mushare.wooder.service.common.BaseManager;
import org.mushare.wooder.service.common.Result;
import org.mushare.wooder.service.common.ResultList;
import org.springframework.stereotype.Service;

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
                return new TextFolderBean(textFolder, false);
            }).collect(Collectors.toList()));
        });
    }

    @Override
    public Result<TextFolderBean> textfolderInfo(String textfolderId, String memberId) {
        return authTextFolder(textfolderId, memberId, textFolder -> {
            return Result.data(new TextFolderBean(textFolder, true));
        });
    }

}
