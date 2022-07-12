package com.lifecosys.base.mybatis.bo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * @author <a href="mailto:hyysguyang@gmail.com">Young Gu</a>
 */
@TableName("test_user_role")
public class TestUserRole extends BaseBo {
    @TableField("test_user_id")
    private TestUser testUser;
    @TableField("test_role_id")
    private TestRole testRole;

    public TestUserRole() {
    }

    public TestUser getTestUser() {
        return testUser;
    }

    public void setTestUser(TestUser testUser) {
        this.testUser = testUser;
    }

    public TestRole getTestRole() {
        return testRole;
    }

    public void setTestRole(TestRole testRole) {
        this.testRole = testRole;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TestUserRole{");
        sb.append("testUser=").append(testUser);
        sb.append(", testRole=").append(testRole);
        sb.append(", id='").append(id).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
