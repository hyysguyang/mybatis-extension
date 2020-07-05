package com.lifecosys.base.mybatis;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.lang.reflect.Type;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author <a href="mailto:hyysguyang@gmail.com">Young Gu</a>
 */
@MappedJdbcTypes(JdbcType.JAVA_OBJECT)
@MappedTypes(value = BaseBo.class)
public class BaseBoTypeHandler extends BaseTypeHandler<BaseBo> {

    public BaseBoTypeHandler() {
        System.out.println("#######################");
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, BaseBo parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.getId());
    }

    @Override
    public BaseBo getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String string = rs.getString(columnName);
        Type rawType = getRawType();
//        ApplicationContextHolder.getBean(SqlSessionTemplate.class).getMapper()
//        GlobalConfigUtils.getGlobalConfig()
        return null;
    }

    @Override
    public BaseBo getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return null;
    }

    @Override
    public BaseBo getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return null;
    }
}
