package com.github.mengweijin.quickboot.sample.system.repository;

import com.github.mengweijin.quickboot.jpa.repository.BaseJpaRepository;
import com.github.mengweijin.quickboot.sample.system.entity.Address;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends BaseJpaRepository<Address, Long> {

}
