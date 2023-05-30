package com.github.mengweijin.vitality.system.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.mengweijin.vitality.system.entity.VtlLogError;
import com.github.mengweijin.vitality.system.mapper.VtlLogErrorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author mengweijin
 * @date 2023/4/1
 */
@Service
public class VtlLogErrorService extends ServiceImpl<VtlLogErrorMapper, VtlLogError> {

    @Autowired
    private VtlLogErrorMapper errorLogMapper;

}
