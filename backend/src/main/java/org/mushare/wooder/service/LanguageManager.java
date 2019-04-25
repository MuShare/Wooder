package org.mushare.wooder.service;

import org.mushare.wooder.bean.LanguageBean;
import org.mushare.wooder.service.common.Result;
import org.mushare.wooder.service.common.ResultList;

public interface LanguageManager {

    Result add(String identifier, String name, String projectId, String memberId);

    ResultList<LanguageBean> getLanguagesByProjectId(String projectId, String memberId);

}
