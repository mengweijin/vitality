package com.github.mengweijin.quickboot.jpa.repository;

import com.github.mengweijin.quickboot.jpa.EntityMapCamelCaseResultTransformer;
import org.hibernate.query.internal.NativeQueryImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.beans.FeatureDescriptor;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * @author Meng Wei Jin
 * @description
 * @date Create in 2019-08-12 22:09
 **/
@Transactional(rollbackFor = Exception.class)
@NoRepositoryBean
public class SimpleBaseJpaRepository<T, ID> extends SimpleJpaRepository<T, ID> implements BaseJpaRepository<T, ID> {

    private final JpaEntityInformation<T, ?> entityInformation;

    private final EntityManager entityManager;

    SimpleBaseJpaRepository(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityInformation = entityInformation;
        this.entityManager = entityManager;
    }

    @Override
    public <S extends T> S update(ID id, S entity) {
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
    public <S extends T> S updateAndFlush(ID id, S entity) {
        S result = update(id, entity);
        this.flush();
        return result;
    }

    /**
     * native query
     * @param sql sql
     * @param paramMap parameter map
     * @return List
     */
    @Override
    public List findByNativeSQL(String sql , Map<String, Serializable> paramMap) {
        Query query = entityManager.createNativeQuery(sql);
        query.unwrap(NativeQueryImpl.class).setResultTransformer(EntityMapCamelCaseResultTransformer.INSTANCE);
        fillParameter(query, paramMap);
        return query.getResultList();
    }

    /**
     * native update
     * @param sql sql
     * @param paramMap parameter map
     * @return int
     */
    @Override
    public int updateByNativeSQL(String sql, Map<String, Serializable> paramMap) {
        Query query = entityManager.createNativeQuery(sql);
        fillParameter(query, paramMap);
        return query.executeUpdate();
    }

    /**
     * fill parameter in sql
     * @param query query
     * @param paramMap parameter map
     */
    private void fillParameter(Query query, Map<String, Serializable> paramMap) {
        if(paramMap != null && !paramMap.isEmpty()){
            paramMap.forEach(query::setParameter);
        }
    }

    private static String[] getNullPropertyNames(Object source) {
        final BeanWrapper wrappedSource = new BeanWrapperImpl(source);
        return Stream.of(wrappedSource.getPropertyDescriptors())
                .map(FeatureDescriptor::getName)
                .filter(propertyName -> wrappedSource.getPropertyValue(propertyName) == null)
                .toArray(String[]::new);
    }
}
