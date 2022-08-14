package com.github.mengweijin.quickboot.domain;

import cn.hutool.extra.spring.SpringUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import ma.glasnost.orika.MapperFacade;

/**
 * @author mengweijin
 * @date 2022/5/17
 */
public final class P {

    private P(){
    }

    public static MapperFacade mapperFacade() {
        return SpringUtil.getBean(MapperFacade.class);
    }

    public static ObjectMapper objectMapper() {
        return SpringUtil.getBean(ObjectMapper.class);
    }
}
