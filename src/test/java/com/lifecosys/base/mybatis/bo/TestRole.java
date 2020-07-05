package com.lifecosys.base.mybatis.bo;

import com.baomidou.mybatisplus.annotations.TableName;
import com.lifecosys.base.mybatis.BaseBo;

/**
 * @author <a href="mailto:hyysguyang@gmail.com">Young Gu</a>
 */
@TableName("test_role")
public class TestRole extends BaseBo {
    private String name;

    public TestRole(String id, String name) {
        super(id);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TestRole{");
        sb.append("name='").append(name).append('\'');
        sb.append(", id='").append(id).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
