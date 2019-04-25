package org.mushare.wooder.bean;

import lombok.Data;
import org.mushare.wooder.bean.common.BaseBean;
import org.mushare.wooder.domain.Language;

import java.util.Date;

@Data
public class LanguageBean extends BaseBean {

    private String identifier;
    private String name;

    public LanguageBean(Language language) {
        this.id = language.getId();
        this.createdAt = new Date(language.getCreatedAt());
        this.updatedAt = new Date(language.getUpdatedAt());
        this.identifier = language.getIdentifier();
        this.name = language.getName();
    }

}
