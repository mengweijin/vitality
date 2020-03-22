package com.mengweijin.mwjwork.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * @author mengweijin
 */
@NoRepositoryBean
public interface BaseJpaRepository<T, ID> extends JpaRepository<T, ID> {

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
}
