package org.mushare.wooder.service;

import org.mushare.wooder.service.common.Result;

public interface GroupManager {

    Result create(String email, String name, String password);

}
