package org.mushare.wooder.service;

import org.mushare.wooder.bean.GroupBean;
import org.mushare.wooder.service.common.Result;

public interface GroupManager {

    Result create(String email, String name, String password);

    Result<GroupBean> login(String email, String password);

}
