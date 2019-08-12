package com.mwj.mwjwork.sample.system.repository;

import com.mwj.mwjwork.framework.jpa.BaseRepository;
import com.mwj.mwjwork.sample.system.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends BaseRepository<User, Long> {

    List<User> findByName(String name);

}
