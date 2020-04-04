package com.mengweijin.mwjwork.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author mengweijin
 */
@NoRepositoryBean
public interface BaseJpaRepository<T, ID> extends
        JpaRepository<T, ID>,
        JpaSpecificationExecutor<T> {

    /**
     * 部分更新数据
     * @param id id
     * @param entity entity
     * @param <S> T extend T
     * @return S
     */
    <S extends T> S update(ID id, S entity);

    /**
     * 部分更新数据
     * @param id id
     * @param entity entity
     * @param <S> T extend T
     * @return S
     */
    <S extends T> S updateAndFlush(ID id, S entity);

    /**
     * native query
     * @param sql sql
     * @param paramMap parameter map
     * @return List
     */
    List findByNativeSQL(String sql , Map<String, Serializable> paramMap);

    /**
     * native update
     * @param sql sql
     * @param paramMap parameter map
     * @return int
     */
    int updateByNativeSQL(String sql, Map<String, Serializable> paramMap);
}
