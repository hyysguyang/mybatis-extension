package com.lifecosys.base.mybatis.bo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:hyysguyang@gmail.com">Young Gu</a>
 */
@TableName("test_user")
public class TestUser extends BaseBo {
    private String username;

    @TableField("test_role_id")
    private TestRole testRole;

    @TableField(exist = false)
    private List<TestUserRole> testUserRoleList=new ArrayList<>();

    public TestUser() {
    }

    public TestUser(String id, String username, TestRole testRole) {
        super(id);
        this.username = username;
        this.testRole = testRole;
    }

    public List<TestUserRole> getTestUserRoleList() {
        return testUserRoleList;
    }

    public void setTestUserRoleList(List<TestUserRole> testUserRoleList) {
        this.testUserRoleList = testUserRoleList;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public TestRole getTestRole() {
        return testRole;
    }

    public void setTestRole(TestRole testRole) {
        this.testRole = testRole;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
