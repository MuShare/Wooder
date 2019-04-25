package org.mushare.wooder.service;

import org.mushare.wooder.bean.MemberBean;
import org.mushare.wooder.service.common.Result;
import org.mushare.wooder.service.common.ResultList;

public interface MemberManager {

    Result add(String email, String name, String password, String groupId);

    ResultList<MemberBean> getMembersByGroupId(String groupId);

    Result<MemberBean> login(String email, String password);

}
