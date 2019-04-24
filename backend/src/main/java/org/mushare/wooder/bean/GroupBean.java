package org.mushare.wooder.bean;

import lombok.Data;
import org.mushare.wooder.bean.common.BaseBean;
import org.mushare.wooder.domain.Group;

import java.util.Date;

@Data
public class GroupBean extends BaseBean {

    private String email;
    private String name;

    public GroupBean(Group group) {
        this.id = group.getId();
        this.createdAt = new Date(group.getCreatedAt());
        this.updatedAt = new Date(group.getUpdatedAt());
        this.email = group.getEmail();
        this.name = group.getName();
    }
}
