package com.github.mengweijin.woodenman.generator;

import com.github.mengweijin.woodenman.generator.async.GeneratorAsyncFactory;
import com.github.mengweijin.woodenman.generator.system.entity.DatasourceInfo;
import com.github.mengweijin.woodenman.generator.system.service.DatasourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author mengweijin
 * @date 2022/10/30
 */
@Component
public class GeneratorApplicationRunner implements ApplicationRunner {

    @Autowired
    private DatasourceService datasourceService;

    @Autowired
    private GeneratorAsyncFactory generatorAsyncFactory;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<DatasourceInfo> list = datasourceService.lambdaQuery().eq(DatasourceInfo::getAutoRefreshDriver, false).list();
        list.forEach(ds -> generatorAsyncFactory.refreshDriver(ds));

        generatorAsyncFactory.initTemplate();
    }
}
