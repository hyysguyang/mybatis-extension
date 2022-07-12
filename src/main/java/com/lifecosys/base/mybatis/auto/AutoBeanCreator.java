package com.lifecosys.base.mybatis.auto;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;
import javassist.bytecode.SignatureAttribute;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;

/**
 * @author <a href="mailto:hyysguyang@gmail.com">Young Gu</a>
 */
public class AutoBeanCreator extends ClassPathBeanDefinitionScanner {

    private static final Logger logger = LoggerFactory.getLogger(AutoBeanCreator.class);

    protected Class<?> baseMapperClass;
    protected Class<?> baseServiceClass;

    public AutoBeanCreator(BeanDefinitionRegistry registry, Class<?> baseMapperClass, Class<?> baseServiceClass) {
        super(registry, false);
        this.baseMapperClass = baseMapperClass;
        this.baseServiceClass = baseServiceClass;
    }


    @Override
    protected void postProcessBeanDefinition(AbstractBeanDefinition beanDefinition, String beanName) {
        super.postProcessBeanDefinition(beanDefinition, beanName);
        String beanClassName = beanDefinition.getBeanClassName();
        if (logger.isDebugEnabled()) logger.debug("Auto creating bean for bo {}", beanClassName);

        try {
            registerMapper(beanClassName);
            registerService(beanClassName);
        } catch (Exception e) {
            logger.error("Create Bean definition error: " + beanName + ", beanDefinition: " + beanDefinition, e);
            throw new RuntimeException(e);
        }
    }

    private void registerService(String beanClassName) {
        Class<?> serviceClass = createServiceClass(beanClassName);
        GenericBeanDefinition serviceBeanDefinition = new GenericBeanDefinition();
        serviceBeanDefinition.setBeanClass(serviceClass);
        serviceBeanDefinition.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE);
        serviceBeanDefinition.setLazyInit(false);
        getRegistry().registerBeanDefinition(serviceClass.getName(), serviceBeanDefinition);

        if (logger.isInfoEnabled()) logger.info("Auto created bean {} for bo {}",serviceClass.getName(), beanClassName);
    }


    public Class<?> createServiceClass(String className) {
        try {
            if (Class.forName(className + "＿Service_Generated") != null) return Class.forName(className + "＿Service_Generated");
        }  catch (Exception e) {
            //Have not created..
        }

        try {
            String superClassName = this.baseServiceClass.getName();
            ClassPool pool = ClassPool.getDefault();
            CtClass superclass = pool.getCtClass(superClassName);
            CtClass serviceClass = pool.makeClass(className + "＿Service_Generated", superclass);

            SignatureAttribute.TypeArgument[] typeArguments = {new SignatureAttribute.TypeArgument(new SignatureAttribute.ClassType(className))};
            SignatureAttribute.ClassSignature cs = new SignatureAttribute.ClassSignature(null, new SignatureAttribute.ClassType(superClassName, typeArguments), null);
            serviceClass.setGenericSignature(cs.encode());

            return serviceClass.toClass();
        } catch (Exception e) {
            throw new RuntimeException("Create class error", e);
        }
    }

    private void registerMapper(String beanClassName) {
        Class<?> mapperClass = createMapperClass(beanClassName);
        GenericBeanDefinition mapperBeanDefinition = new GenericBeanDefinition();
        mapperBeanDefinition.setBeanClass(MapperFactoryBean.class);
        mapperBeanDefinition.getConstructorArgumentValues().addGenericArgumentValue(mapperClass);
        mapperBeanDefinition.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE);
        mapperBeanDefinition.setLazyInit(false);
        getRegistry().registerBeanDefinition(mapperClass.getName(), mapperBeanDefinition);

        if (logger.isInfoEnabled()) logger.info("Auto created bean {} for bo {}",mapperClass.getName(), beanClassName);
    }


    public Class<?> createMapperClass(String className) {
        try {
            if (Class.forName(className + "_Mapper_Generated") != null) return Class.forName(className + "_Mapper_Generated");
        }  catch (Exception e) {
            //Have not created..
        }


        try {
            String superInterface = this.baseMapperClass.getName();
            ClassPool pool = ClassPool.getDefault();
            CtClass mapperClass = pool.makeInterface(className + "_Mapper_Generated", pool.getCtClass(superInterface));

            SignatureAttribute.TypeArgument[] typeArguments = {new SignatureAttribute.TypeArgument(new SignatureAttribute.ClassType(className))};
            SignatureAttribute.ClassSignature cs = new SignatureAttribute.ClassSignature(null, null, new SignatureAttribute.ClassType[]{new SignatureAttribute.ClassType(superInterface, typeArguments)});
            mapperClass.setGenericSignature(cs.encode());

            return mapperClass.toClass();
        } catch (Exception e) {
            throw new RuntimeException("Create class error", e);
        }
    }


    @Override
    protected boolean checkCandidate(String beanName, BeanDefinition beanDefinition) throws IllegalStateException {
        return false;
    }
}
