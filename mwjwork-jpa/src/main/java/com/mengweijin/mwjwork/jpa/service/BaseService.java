package com.mengweijin.mwjwork.jpa.service;

import com.mengweijin.mwjwork.jpa.repository.BaseJpaRepository;
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

    /**
     * extend BaseJpaRepository
     */
    T update(ID id, T entity);

    /**
     * extend BaseJpaRepository
     */
    T updateAndFlush(ID id, T entity);

    /**
     * extend JpaRepository
     */
    List<T> findAll();

    /**
     * extend JpaRepository
     */
    List<T> findAll(Sort sort);

    /**
     * extend JpaRepository
     */
    List<T> findAllById(Iterable<ID> ids);

    /**
     * extend JpaRepository
     */
    <S extends T> List<S> saveAll(Iterable<S> entities);

    /**
     * extend JpaRepository
     */
    <S extends T> S saveAndFlush(S entity);

    /**
     * extend JpaRepository
     */
    void deleteInBatch(Iterable<T> entities);

    /**
     * extend JpaRepository
     */
    void deleteAllInBatch();

    /**
     * extend JpaRepository
     */
    T getOne(ID id);

    /**
     * extend JpaRepository
     */
    List<T> findAll(Example<T> example);

    /**
     * extend JpaRepository
     */
    <S extends T> List<S> findAll(Example<S> example, Sort sort);

    /**
     * extend QueryByExampleExecutor
     */
    <S extends T> Optional<S> findOne(Example<S> example);

    /**
     * extend QueryByExampleExecutor
     */
    <S extends T> Page<S> findAll(Example<S> example, Pageable pageable);

    /**
     * extend QueryByExampleExecutor
     */
    <S extends T> long count(Example<S> example);

    /**
     * extend QueryByExampleExecutor
     */
    <S extends T> boolean exists(Example<S> example);

    /**
     * extend PagingAndSortingRepository
     */
    Page<T> findAll(Pageable pageable);

    /**
     * extend CrudRepository
     */
    <S extends T> S save(S entity);

    /**
     * extend CrudRepository
     */
    Optional<T> findById(ID id);

    /**
     * extend CrudRepository
     */
    boolean existsById(ID id);

    /**
     * extend CrudRepository
     */
    long count();

    /**
     * extend CrudRepository
     */
    void deleteById(ID id);

    /**
     * extend CrudRepository
     */
    void delete(T entity);

    /**
     * extend CrudRepository
     */
    void deleteAll(Iterable<? extends T> entities);

    /**
     * extend CrudRepository
     */
    void deleteAll();

}
