package com.lifecosys.base.mybatis.auto.simple;

import com.lifecosys.base.mybatis.auto.AutoMyBatisBeanRegistrar;

/**
 * @author <a href="mailto:hyysguyang@gmail.com">Young Gu</a>
 */
public class MyBatisAutoBeanRegistrar extends AutoMyBatisBeanRegistrar {

    public MyBatisAutoBeanRegistrar() {
        super(Bo.class, BaseMapper.class, BaseService.class);
    }
}
