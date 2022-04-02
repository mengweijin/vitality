package com.github.mengweijin.quickboot.sample.system.service;

import com.github.mengweijin.quickboot.jpa.service.BaseService;
import com.github.mengweijin.quickboot.sample.system.entity.Address;
import com.github.mengweijin.quickboot.sample.system.repository.AddressRepository;

/**
 * @author Meng Wei Jin
 * @date Create in 2019-07-28 0:36
 **/
public interface AddressService extends BaseService<Address, Long, AddressRepository> {

}
