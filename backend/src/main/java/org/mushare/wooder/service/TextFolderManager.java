package org.mushare.wooder.service;

import org.mushare.wooder.bean.TextFolderBean;
import org.mushare.wooder.service.common.Result;
import org.mushare.wooder.service.common.ResultList;

public interface TextFolderManager {

    Result add(String name, String projectId, String memberId);

    ResultList<TextFolderBean> getFoldersByProjectId(String projectId, String memberId);

    Result<TextFolderBean> textfolderInfo(String textfolderId, String memberId);

}
