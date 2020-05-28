package com.github.mengweijin.quickboot.sample.system.repository;

import com.github.mengweijin.quickboot.jpa.repository.BaseJpaRepository;
import com.github.mengweijin.quickboot.sample.system.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends BaseJpaRepository<User, Long> {

    List<User> findByName(String name);

}
