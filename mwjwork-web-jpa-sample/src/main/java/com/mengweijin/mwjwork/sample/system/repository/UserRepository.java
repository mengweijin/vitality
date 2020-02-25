package com.mengweijin.mwjwork.sample.system.repository;

import com.mengweijin.mwjwork.framework.jpa.repository.BaseRepository;
import com.mengweijin.mwjwork.sample.system.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends BaseRepository<User, Long> {

    List<User> findByName(String name);

}
