package com.lifecosys.base.mybatis;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lifecosys.base.mybatis.auto.simple.BaseService;
import com.lifecosys.base.mybatis.bo.DemoRoleBO;
import com.lifecosys.base.mybatis.bo.TestRoleMapper;
import javassist.NotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.ScriptUtils;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

/**
 * @author <a href="mailto:hyysguyang@gmail.com">Young Gu</a>
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(classes = TestConfig.class)
public class AutoMapperTest {

    @Autowired
    DataSource dataSource;


    @Autowired
    BaseMapper<DemoRoleBO> demoBOBaseMapper;

    @Autowired
    BaseService<DemoRoleBO> demoBoService;

    @Autowired
    TestRoleMapper testRoleMapper;

    @BeforeAll
    public void beforeAll() throws SQLException, NotFoundException {
        Resource resource = new ClassPathResource("db.sql");
        ScriptUtils.executeSqlScript(dataSource.getConnection(), resource);
    }

    @Test
    public void simpleBo() {

        List<DemoRoleBO> demoRoleBOS = demoBOBaseMapper.selectList(null);
        Assertions.assertThat(demoRoleBOS).hasSize(2);
        Assertions.assertThat(demoRoleBOS).contains(new DemoRoleBO("20000","role-1"));
        Assertions.assertThat(demoRoleBOS).contains(new DemoRoleBO("20001","role-2"));
        Assertions.assertThat(demoRoleBOS).isEqualTo(demoBoService.list());
        Assertions.assertThat(testRoleMapper.selectList(null)).hasSize(2);
    }


}
