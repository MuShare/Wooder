package org.mushare.wooder.bean;

import lombok.Data;
import org.mushare.wooder.bean.common.BaseBean;
import org.mushare.wooder.domain.Text;

import java.util.Date;

@Data
public class TextBean extends BaseBean {

    private String identifier;
    private String name;
    private boolean android;
    private boolean ios;

    public TextBean(Text text) {
        this.id = text.getId();
        this.createdAt = new Date(text.getCreatedAt());
        this.updatedAt = new Date(text.getUpdatedAt());
        this.identifier = text.getIdentifer();
        this.name = text.getName();
        this.android = text.getAndroid();
        this.ios = text.getIos();
    }

}
