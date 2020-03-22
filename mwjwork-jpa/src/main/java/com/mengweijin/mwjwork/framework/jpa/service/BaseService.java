package com.mengweijin.mwjwork.framework.jpa.service;

import com.mengweijin.mwjwork.framework.jpa.repository.BaseJpaRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

/**
 * @author Meng Wei Jin
 * @date Create in 2019-10-29 22:42
 **/
public interface BaseService<T, ID extends Serializable, R extends BaseJpaRepository<T, ID>> {

    /**
     * 获取Repository
     *
     * @return
     */
    R getRepository();

    T update(ID id, T entity);

    T updateAndFlush(ID id, T entity);

    List<T> findAll();

    List<T> findAll(Sort sort);

    List<T> findAllById(Iterable<ID> ids);

    <S extends T> S saveAndFlush(S entity);

    T getOne(ID id);

    List<T> findAll(Example<T> example);

    <S extends T> List<S> findAll(Example<S> example, Sort sort);

    <S extends T> Page<S> findAll(Example<S> example, Pageable pageable);

    <S extends T> long count(Example<S> example);

    <S extends T> boolean exists(Example<S> example);

    Page<T> findAll(Pageable pageable);

    <S extends T> S save(S entity);

    <S extends T> Iterable<S> saveAll(Iterable<S> entities);

    Optional<T> findById(ID id);

    boolean existsById(ID id);

    void deleteById(ID id);

    void deleteAll();

}
