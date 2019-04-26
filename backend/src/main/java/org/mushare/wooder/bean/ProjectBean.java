package org.mushare.wooder.bean;

import lombok.Data;
import org.mushare.wooder.bean.common.BaseBean;
import org.mushare.wooder.domain.Project;

import java.util.Date;

@Data
public class ProjectBean extends BaseBean {

    private String name;

    public ProjectBean() {}

    public ProjectBean(Project project) {
        this.id = project.getId();
        this.createdAt = new Date(project.getCreatedAt());
        this.updatedAt = new Date(project.getUpdatedAt());
        this.name = project.getName();
    }

}
