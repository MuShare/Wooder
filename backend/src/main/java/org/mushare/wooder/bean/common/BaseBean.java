package org.mushare.wooder.bean.common;

import java.util.Date;

public class BaseBean {

    protected String id;
    protected Date createdAt;
    protected Date updatedAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    protected String unwrapString(String value) {
        return value == null ? "" : value;
    }

    protected int unwrapInteger(Integer value) {
        return value == null ? 0 : value;
    }

    protected long unwrapLong(Long value) {
        return value == null ? 0 : value;
    }

    protected double unwrapDouble(Double value) {
        return value == null ? 0 : value;
    }

}
