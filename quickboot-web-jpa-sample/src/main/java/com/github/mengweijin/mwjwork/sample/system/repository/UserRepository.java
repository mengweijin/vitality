package com.github.mengweijin.mwjwork.sample.system.repository;

import com.github.mengweijin.mwjwork.jpa.repository.BaseJpaRepository;
import com.github.mengweijin.mwjwork.sample.system.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends BaseJpaRepository<User, Long> {

    List<User> findByName(String name);

}
