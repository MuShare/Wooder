package org.mushare.wooder.service;

import org.mushare.wooder.bean.TextBean;
import org.mushare.wooder.service.common.Result;
import org.mushare.wooder.service.common.ResultList;

public interface TextManager {

    Result add(String identifier, String textfolderId, String memberId);

    ResultList<TextBean> getTextsByTextfolderId(String textfolderId, String memberId);

}
