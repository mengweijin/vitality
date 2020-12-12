package com.github.mengweijin.mybatisplus.demo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.mengweijin.generator.ConfigProperty;
import com.github.mengweijin.generator.EDefaultTemplatePath;
import com.github.mengweijin.quickboot.framework.web.BaseController;
import com.github.mengweijin.quickboot.mybatis.BaseEntity;

public class CodeGenerator {

    public static void main(String[] args) {
        // ConfigProperty中字段的值请参考类中的注释
        ConfigProperty config = new ConfigProperty();
        config.setAuthor("Meng Wei Jin");
        config.setTemplateLocation(EDefaultTemplatePath.MYBATIS_PLUS.getTemplatePath());
        config.setPackagePath("com.github.mengweijin.mybatisplus.aaaaaaaaaaaaaaaaaa");

        config.setTables(new String[]{"SYS_USER"});
        config.setTablePrefix("SYS_");

        config.setSuperEntityClass(BaseEntity.class);
        config.setSuperDaoClass(BaseMapper.class);
        config.setSuperServiceClass(IService.class);
        config.setSuperServiceImplClass(ServiceImpl.class);
        config.setSuperControllerClass(BaseController.class);
        config.setSuperEntityColumns(new String[]{"CREATE_BY", "CREATE_TIME", "UPDATE_BY", "UPDATE_TIME", "DELETED"});

        config.setDbUrl("jdbc:h2:file:C:/Source/code/gitee/quickboot/h2/test;AUTO_SERVER=TRUE;DB_CLOSE_ON_EXIT=FALSE;MODE=MYSQL");
        config.setDbDriverName("org.h2.Driver");
        config.setDbUsername("sa");
        config.setDbPassword("");

        new com.github.mengweijin.generator.CodeGenerator(config).run();
    }
}
