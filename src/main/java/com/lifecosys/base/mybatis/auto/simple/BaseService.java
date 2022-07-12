package com.lifecosys.base.mybatis.auto.simple;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @author <a href="mailto:hyysguyang@gmail.com">Young Gu</a>
 */

public class BaseService<T extends Bo<T>> extends ServiceImpl<BaseMapper<T>, T> {}
