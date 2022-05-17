package com.github.mengweijin.quickboot.framework.domain;

import cn.hutool.extra.spring.SpringUtil;
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
}
