package com.lifecosys.base.mybatis.bo;

import java.util.Objects;

/**
 *
 * @author <a href="mailto:hyysguyang@gmail.com">Young Gu</a>
 */
public abstract class BaseBo {
    protected String id;

    public BaseBo() {
    }

    public BaseBo(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseBo baseBo = (BaseBo) o;
        return Objects.equals(id, baseBo.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
