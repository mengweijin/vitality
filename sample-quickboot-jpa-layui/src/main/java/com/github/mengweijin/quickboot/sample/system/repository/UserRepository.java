package com.github.mengweijin.quickboot.sample.system.repository;

import com.github.mengweijin.quickboot.jpa.repository.BaseJpaRepository;
import com.github.mengweijin.quickboot.sample.system.entity.User;
import com.github.mengweijin.quickboot.sample.system.enums.Role;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author mengweijin
 */
@Repository
public interface UserRepository extends BaseJpaRepository<User, Long> {

    List<User> findByName(String name);

    /**
     * 投影数据数据库中的字段到接口（JPA 会自动实例化一个代理对象返回）
     * 也可以使用：@Query(value = "SELECT * FROM user", nativeQuery = true) 但是要注意数据库字段名和UserSchoolDTO中的对应。
     *
     * @return
     */
    @Query(value = "SELECT name, age, role, address_id as addressId FROM user", nativeQuery = true)
    List<UserSchoolDTO> findUserSchoolDTO();

    interface UserSchoolDTO {

        String getName();

        Integer getAge();

        Role getRole();

        Long getAddressId();

        String getSchool();
    }
}
