package org.mushare.wooder.service.common;

import org.mushare.wooder.dao.GroupDao;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

public class BaseManager {

    @Autowired
    protected GroupDao groupDao;

    protected String uuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

}
