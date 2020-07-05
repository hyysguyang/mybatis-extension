package com.lifecosys.base.mybatis;

import com.baomidou.mybatisplus.entity.TableFieldInfo;
import com.baomidou.mybatisplus.entity.TableInfo;
import com.baomidou.mybatisplus.mapper.AutoSqlInjector;
import org.apache.ibatis.builder.MapperBuilderAssistant;
import org.apache.ibatis.executor.keygen.KeyGenerator;
import org.apache.ibatis.mapping.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:hyysguyang@gmail.com">Young Gu</a>
 */
public class DefaultSqlInjector extends AutoSqlInjector {

    private static final Logger logger = LoggerFactory.getLogger(DefaultSqlInjector.class);

    @Override
    public MappedStatement addMappedStatement(Class<?> mapperClass, String id, SqlSource sqlSource, SqlCommandType sqlCommandType, Class<?> parameterClass, String resultMap, Class<?> resultType, KeyGenerator keyGenerator, String keyProperty, String keyColumn) {
        String resultMapId = BaseBo.class.isAssignableFrom(resultType) ? mapperClass.getName() + ".baseResultMap" : resultMap;
        return super.addMappedStatement(mapperClass, id, sqlSource, sqlCommandType, parameterClass, resultMapId, resultType, keyGenerator, keyProperty, keyColumn);
    }


    @Override
    protected void injectSql(MapperBuilderAssistant builderAssistant, Class<?> mapperClass, Class<?> modelClass, TableInfo table) {
        decorateModel(table, modelClass);
        super.injectSql(builderAssistant, mapperClass, modelClass, table);
    }

    private void decorateModel(TableInfo table, Class<?> modelClass) {
        List<TableFieldInfo> boFields = table.getFieldList().stream().filter(it -> BaseBo.class.isAssignableFrom(it.getPropertyType())).collect(Collectors.toList());
        if (logger.isInfoEnabled() && !boFields.isEmpty()) {
            List<String> boFieldInfo = boFields.stream().map(it -> it.getProperty() + " -> " + it.getPropertyType()).collect(Collectors.toList());
            logger.info("Found dependent bean {} with modelClass {}", boFieldInfo, modelClass);
        }

        boFields.forEach(it -> it.setEl(it.getProperty() + ".id"));
        List<ResultMapping> resultMappingList = boFields.stream().map(it ->
                builderAssistant.buildResultMapping(
                        modelClass, it.getProperty(), it.getProperty(), it.getPropertyType(), null,
                        it.getPropertyType().getName() + "Mapper.selectById", null, null, null, null,
                        new ArrayList<ResultFlag>(), null, null, false)
        ).collect(Collectors.toList());

//        ReflectionUtils.doWithFields(modelClass, it -> {
//            ResultMapping resultMapping = builderAssistant.buildResultMapping(
//                    modelClass, it.getName(), "id", it.getType(), null,
//                    it.getAnnotation(TableField.class).value(), null, null, null, null,
//                    new ArrayList<ResultFlag>(), null, null, false);
//            resultMappingList.add(resultMapping);
//        }, it -> Collection.class.isAssignableFrom(it.getType()) && !it.getAnnotation(TableField.class).value().isEmpty());


        if (BaseBo.class.isAssignableFrom(modelClass)) {
            builderAssistant.addResultMap("baseResultMap", modelClass, null, null, resultMappingList, true);
        }
    }

}

