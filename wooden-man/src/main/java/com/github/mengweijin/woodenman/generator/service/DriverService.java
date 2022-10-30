package com.github.mengweijin.woodenman.generator.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.mengweijin.quickboot.util.BeanUtils;
import com.github.mengweijin.woodenman.generator.dto.DriverInfoDTO;
import com.github.mengweijin.woodenman.generator.entity.DriverInfo;
import com.github.mengweijin.woodenman.generator.mapper.DriverMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author mengweijin
 * @date 2022/8/14
 */
@Service
public class DriverService extends ServiceImpl<DriverMapper, DriverInfo> {

    @Autowired
    private DriverMapper driverMapper;

    public Page<DriverInfoDTO> pageDriverInfoDTO(Page<DriverInfo> page, LambdaQueryWrapper<DriverInfo> wrapper) {
        page = this.page(page, wrapper);
        List<DriverInfoDTO> dtoList = page.getRecords().stream().map(this::toDriverInfoDTO).collect(Collectors.toList());
        Page<DriverInfoDTO> dtoPage = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
        dtoPage.setRecords(dtoList);
        return  dtoPage;
    }

    public DriverInfoDTO toDriverInfoDTO(DriverInfo driverInfo) {
        DriverInfoDTO infoDTO = new DriverInfoDTO();
        BeanUtils.copyProperties(driverInfo, infoDTO);
        return infoDTO.init();
    }

    public DriverInfoDTO getDriverInfoDTOById(Long id) {
        return this.toDriverInfoDTO(this.getById(id));
    }
}
