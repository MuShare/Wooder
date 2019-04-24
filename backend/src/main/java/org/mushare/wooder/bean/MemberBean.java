package org.mushare.wooder.bean;

import lombok.Data;
import org.mushare.wooder.bean.common.BaseBean;
import org.mushare.wooder.domain.Member;

import java.util.Date;

@Data
public class MemberBean extends BaseBean {

    private String email;
    private String name;

    public MemberBean(Member member) {
        this.id = member.getId();
        this.createdAt = new Date(member.getCreatedAt());
        this.updatedAt = new Date(member.getUpdatedAt());
        this.email = member.getEmail();
        this.name = member.getName();
    }

}
