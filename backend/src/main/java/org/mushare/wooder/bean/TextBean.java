package org.mushare.wooder.bean;

import lombok.Data;
import org.mushare.wooder.bean.common.BaseBean;
import org.mushare.wooder.domain.Text;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class TextBean extends BaseBean {

    private String identifier;
    private String name;
    private List<String> platforms;

    public TextBean(Text text) {
        this.id = text.getId();
        this.createdAt = new Date(text.getCreatedAt());
        this.updatedAt = new Date(text.getUpdatedAt());
        this.identifier = text.getIdentifer();
        this.name = text.getName();
        this.platforms = new ArrayList<>();
        if (text.getAndroid()) {
            this.platforms.add("android");
        }
        if (text.getIos()) {
            this.platforms.add("ios");
        }
    }

}
