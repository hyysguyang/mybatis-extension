package com.lifecosys.base.mybatis;

import com.lifecosys.base.mybatis.bo.*;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ScriptUtils;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
class MybatisORMappingTests {

    @Autowired
    TestUserMapper testUserMapper;

    @Autowired
    TestRoleMapper testRoleMapper;
    @Autowired
    TestUserRoleMapper testUserRoleMapper;
    @Autowired
    DataSource dataSource;

    @Autowired
    SqlSessionTemplate sqlSessionTemplate;
    @Autowired
    JdbcTemplate jdbcTemplate;


    @BeforeAll
    public void beforeAll() throws SQLException {
        Resource resource = new ClassPathResource("db.sql");
        ScriptUtils.executeSqlScript(dataSource.getConnection(), resource);

//        sqlSessionTemplate.getConfiguration().addMapper();

    }

    @Test
    public void simpleBo() {
        TestRole testRole = testRoleMapper.selectById("20000");
        assertThat(testRole.getName()).isEqualTo("role-1");
        testRoleMapper.insert(new TestRole("50000", "role-50000"));
        assertThat(testRoleMapper.selectById("50000").getName()).isEqualTo("role-50000");
    }

    @Test
    public void insertDependentBo() {
        testUserMapper.insert(new TestUser("50000", "user-50000", new TestRole("20000", "role-1")));
        Map<String, Object> results = this.jdbcTemplate.queryForMap("select * from test_user where id='50000'");
        assertThat(results.get("username").toString()).isEqualTo("user-50000");
        assertThat(results.get("test_role_id").toString()).isEqualTo("20000");
    }

    @Test
    public void loadDependentBo() {

        List<TestUserRole> testUserRoles = testUserRoleMapper.selectUserRoles("10000");
        TestUser testUser = testUserMapper.selectUser("10000");
        assertThat(testUser.getUsername()).isEqualTo("mike");
        TestRole expectedTestRole = new TestRole("20000", "role-1");
        assertThat(testUser.getTestRole()).isEqualTo(expectedTestRole);

        List<TestUser> testUsers = testUserMapper.selectBatchIds(Lists.list("10000", "10001"));
        assertThat(testUsers.size()).isEqualTo(2);
        assertThat(testUsers.get(0).getTestRole()).isEqualTo(expectedTestRole);
        assertThat(testUsers.get(1).getTestRole()).isEqualTo(expectedTestRole);

        testUser= testUserMapper.selectOne(new TestUser(null, "mike", null));
        assertThat(testUser.getTestRole()).isEqualTo(expectedTestRole);

        HashMap<String, Object> columnMap = new HashMap<>();
        columnMap.put("username","mike");
        testUsers= testUserMapper.selectByMap(columnMap);
        assertThat(testUsers.size()).isEqualTo(1);
        assertThat(testUsers.get(0).getTestRole()).isEqualTo(expectedTestRole);
    }

}
