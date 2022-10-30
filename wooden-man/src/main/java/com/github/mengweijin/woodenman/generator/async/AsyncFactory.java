package com.github.mengweijin.woodenman.generator.async;

import cn.hutool.extra.spring.SpringUtil;
import com.github.mengweijin.woodenman.generator.service.DatasourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author mengweijin
 * @date 2022/10/30
 */
@Slf4j
@Async
@Component
public class AsyncFactory {

    public void autoSetDatasourceDriverInfo(Long datasourceId) {
        try{
            DatasourceService datasourceService = SpringUtil.getBean(DatasourceService.class);
            datasourceService.refreshDriver(datasourceId);
        } catch (RuntimeException e){
            log.error(e.getMessage(), e);
        }
    }
}
