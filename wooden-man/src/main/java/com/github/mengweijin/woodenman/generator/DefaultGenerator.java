package com.github.mengweijin.woodenman.generator;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.github.mengweijin.quickboot.jdbc.driver.DynamicDriverDataSource;

/**
 * @author mengweijin
 * @date 2022/8/16
 */
public class DefaultGenerator extends AutoGenerator {

    public DefaultGenerator(DynamicDriverDataSource dataSource) {
        super(new DataSourceConfig.Builder(dataSource).build());
    }

    @Override
    public ConfigBuilder getConfig() {
        return new ConfigBuilder(this.getPackageInfo(), this.getDataSource(), this.getStrategy(), this.getTemplate(), this.getGlobalConfig(), this.injection);
    }
}
