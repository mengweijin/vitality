package com.mengweijin.mwjwork.sample.system.service.impl;

import com.mengweijin.mwjwork.jpa.service.BaseServiceImpl;
import com.mengweijin.mwjwork.sample.system.entity.Address;
import com.mengweijin.mwjwork.sample.system.repository.AddressRepository;
import com.mengweijin.mwjwork.sample.system.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Meng Wei Jin
 * @date Create in 2019-10-29 21:46
 **/
@Transactional(rollbackFor = Exception.class)
@Service
public class AddressServiceImpl extends BaseServiceImpl<Address, Long, AddressRepository> implements AddressService {

    @Autowired
    private AddressRepository addressRepository;

}
