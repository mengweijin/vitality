package com.mengweijin.mwjwork.sample.system.repository;

import com.mengweijin.mwjwork.jpa.repository.BaseJpaRepository;
import com.mengweijin.mwjwork.sample.system.entity.Address;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends BaseJpaRepository<Address, Long> {

}
