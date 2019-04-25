package org.mushare.wooder.service.impl;

import org.mushare.wooder.bean.GroupBean;
import org.mushare.wooder.domain.Group;
import org.mushare.wooder.service.GroupManager;
import org.mushare.wooder.service.common.BaseManager;
import org.mushare.wooder.service.common.Result;
import org.mushare.wooder.service.common.ResultCode;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GroupManagerImpl extends BaseManager implements GroupManager {

    @Override
    public Result create(String email, String name, String password) {
        Group group = groupDao.getByEmail(email);
        if (group != null) {
            return Result.error(ResultCode.GroupEmailRegistered);
        }
        group = new Group();
        group.setCreatedAt(System.currentTimeMillis());
        group.setUpdatedAt(group.getCreatedAt());
        group.setEmail(email);
        group.setPassword(password);
        group.setName(name);
        groupDao.save(group);
        return Result.success();
    }

    @Override
    public Result<GroupBean> login(String email, String password) {
        Group group = groupDao.getByEmail(email);
        if (group == null) {
            return Result.error(ResultCode.GrouprEmailNotRegistered);
        }
        if (!group.getPassword().equals(password)) {
            return Result.error(ResultCode.GroupPasswordWrong);
        }
        return Result.data(new GroupBean(group));
    }

    @Override
    public Result<GroupBean> groupInfo(String groupId) {
        Optional<Group> group = groupDao.findById(groupId);
        if (!group.isPresent()) {
            return Result.error(ResultCode.GroupIdError);
        }
        return Result.data(new GroupBean(group.get()));
    }

}
