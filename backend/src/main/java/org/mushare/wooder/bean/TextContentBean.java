package org.mushare.wooder.bean;

import lombok.Data;
import org.mushare.wooder.bean.common.BaseBean;
import org.mushare.wooder.domain.TextContent;

import java.util.Date;

@Data
public class TextContentBean extends BaseBean {

    private String string;

    public TextContentBean(TextContent content) {
        this.id = content.getId();
        this.createdAt = new Date(content.getCreatedAt());
        this.updatedAt = new Date(content.getUpdatedAt());
        this.string = content.getString();
    }
}
