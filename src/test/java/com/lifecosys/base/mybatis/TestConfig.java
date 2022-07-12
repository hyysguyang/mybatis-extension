package com.lifecosys.base.mybatis;

import com.lifecosys.base.mybatis.auto.simple.MyBatisAutoBeanRegistrar;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Import;

/**
 * @author <a href="mailto:hyysguyang@gmail.com">Young Gu</a>
 */
@Import({ MyBatisAutoBeanRegistrar.class})
@MapperScan(basePackages = "com.lifecosys")
@EnableAutoConfiguration()
public class TestConfig {

}
