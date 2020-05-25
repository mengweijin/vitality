package com.github.mengweijin.mwjwork.sample.system.repository;

import com.github.mengweijin.mwjwork.jpa.repository.BaseJpaRepository;
import com.github.mengweijin.mwjwork.sample.system.entity.Address;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends BaseJpaRepository<Address, Long> {

}
