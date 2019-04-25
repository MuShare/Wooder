package org.mushare.wooder.bean;

import lombok.Data;
import org.mushare.wooder.bean.common.BaseBean;
import org.mushare.wooder.domain.TextFolder;

import java.util.Date;

@Data
public class TextFolderBean extends BaseBean {

    private String name;

    public TextFolderBean(TextFolder textFolder) {
        this.id = textFolder.getId();
        this.createdAt = new Date(textFolder.getCreatedAt());
        this.updatedAt = new Date(textFolder.getUpdatedAt());
        this.name = textFolder.getName();
    }

}
