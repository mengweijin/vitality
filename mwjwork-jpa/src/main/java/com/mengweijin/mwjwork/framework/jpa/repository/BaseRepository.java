package com.mengweijin.mwjwork.framework.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseRepository<T, ID> extends JpaRepository<T, ID> {

    <S extends T> S saveDynamic(ID id, S entity);

    <S extends T> S saveDynamicAndFlush(ID id, S entity);
}
