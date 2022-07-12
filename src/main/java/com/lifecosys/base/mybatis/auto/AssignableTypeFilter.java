package com.lifecosys.base.mybatis.auto;

import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.util.ClassUtils;

import java.io.IOException;

/**
 * @author <a href="mailto:hyysguyang@gmail.com">Young Gu</a>
 */
public class AssignableTypeFilter<T> implements TypeFilter {

    private Class<T> targetClass;

    public AssignableTypeFilter(Class<T> targetClass) {
        this.targetClass = targetClass;
    }

    @Override
    public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {
        String className = metadataReader.getClassMetadata().getClassName();
        return !targetClass.getName().equals(className) && isAssignableFrom(className);
    }

    private boolean isAssignableFrom(String className) {
        try {
            Class<?> clazz = ClassUtils.forName(className, getClass().getClassLoader());
            return targetClass.isAssignableFrom(clazz);
        } catch (Throwable ex) {
            // Class not regularly loadable - can't determine a match that way.
        }

        return false;
    }

}
