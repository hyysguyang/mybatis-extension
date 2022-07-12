package com.lifecosys.base.mybatis.auto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.autoconfigure.AutoConfigurationPackages;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author <a href="mailto:hyysguyang@gmail.com">Young Gu</a>
 */
public class AutoMyBatisBeanRegistrar implements ImportBeanDefinitionRegistrar, BeanFactoryAware {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    private BeanFactory beanFactory;

    protected Class<?> baseMapperClass;
    protected Class<?> baseServiceClass;
    private Class<?> boClass;

    public AutoMyBatisBeanRegistrar(Class<?> boClass,Class<?> baseMapperClass, Class<?> baseServiceClass) {
        this.boClass = boClass;
        this.baseMapperClass = baseMapperClass;
        this.baseServiceClass = baseServiceClass;
    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {

        List<String> packages = AutoConfigurationPackages.get(this.beanFactory);

        if (logger.isDebugEnabled()) {
            logger.debug("Searching for mappers annotated with @Mapper and implement @Bo, Using auto-configuration base package:{}", packages);
        }

        AutoBeanCreator autoBeanCreator = new AutoBeanCreator(registry, this.baseMapperClass,this.baseServiceClass);
        autoBeanCreator.addIncludeFilter(new AssignableTypeFilter(boClass));
        autoBeanCreator.scan(StringUtils.toStringArray(packages));
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }


}

