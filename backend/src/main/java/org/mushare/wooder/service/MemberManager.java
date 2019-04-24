package org.mushare.wooder.service;

import org.mushare.wooder.bean.MemberBean;
import org.mushare.wooder.service.common.Result;

public interface MemberManager {

    Result add(String email, String name, String password, String groupId);

    Result<MemberBean> login(String email, String password);

}
