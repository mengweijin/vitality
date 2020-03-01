package com.mengweijin.mwjwork.framework.jpa.repository;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.beans.FeatureDescriptor;
import java.util.stream.Stream;

/**
 * @author Meng Wei Jin
 * @description
 * @date Create in 2019-08-12 22:09
 **/
@Transactional(rollbackFor = Exception.class)
@NoRepositoryBean
public class SimpleBaseRepository<T, ID> extends SimpleJpaRepository<T, ID> implements BaseRepository<T, ID> {

    private final JpaEntityInformation<T, ?> entityInformation;

    private final EntityManager entityManager;

    SimpleBaseRepository(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityInformation = entityInformation;
        this.entityManager = entityManager;
    }

    @Override
    public <S extends T> S saveDynamic(ID id, S entity) {
        if (entityInformation.isNew(entity)) {
            entityManager.persist(entity);
            return entity;
        } else {
            S target = (S) this.getOne(id);
            BeanUtils.copyProperties(entity, target, getNullPropertyNames(entity));
            return entityManager.merge(target);
        }
    }

    @Override
    public <S extends T> S saveDynamicAndFlush(ID id, S entity) {
        S result = saveDynamic(id, entity);
        this.flush();
        return result;
    }

    private static String[] getNullPropertyNames(Object source) {
        final BeanWrapper wrappedSource = new BeanWrapperImpl(source);
        return Stream.of(wrappedSource.getPropertyDescriptors())
                .map(FeatureDescriptor::getName)
                .filter(propertyName -> wrappedSource.getPropertyValue(propertyName) == null)
                .toArray(String[]::new);
    }
}
