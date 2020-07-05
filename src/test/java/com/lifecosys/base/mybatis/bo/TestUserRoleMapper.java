package com.lifecosys.base.mybatis.bo;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.io.Serializable;
import java.util.List;

/**
 * @author <a href="mailto:hyysguyang@gmail.com">Young Gu</a>
 */
@Mapper
public interface TestUserRoleMapper extends BaseMapper<TestUserRole> {
    List<TestUserRole> selectUserRoles(Serializable userId);
}
