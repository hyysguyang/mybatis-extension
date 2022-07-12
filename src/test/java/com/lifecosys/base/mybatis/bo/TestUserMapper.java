package com.lifecosys.base.mybatis.bo;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author <a href="mailto:hyysguyang@gmail.com">Young Gu</a>
 */
@Mapper
public interface TestUserMapper extends BaseMapper<TestUser> {
	TestUser selectUser(String id);
}
