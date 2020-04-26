package com.mengweijin.app;

import com.mengweijin.generator.CodeGenerator;
import com.mengweijin.generator.ConfigProperty;
import com.mengweijin.mwjwork.framework.web.BaseController;
import com.mengweijin.mwjwork.jpa.BaseEntity;
import com.mengweijin.mwjwork.jpa.repository.BaseJpaRepository;
import com.mengweijin.mwjwork.jpa.service.BaseService;
import com.mengweijin.mwjwork.jpa.service.BaseServiceImpl;
import org.junit.jupiter.api.Test;

public class CodeGeneratorTest {

    @Test
    void codeGenerator() {
        ConfigProperty config = new ConfigProperty();
        config.setAuthor("Meng Wei Jin");
        config.setTemplateLocation("templates/jpa/");
        config.setPackagePath("com.mengweijin.aaaaaaaaaaaaaaaaaaaaaaaaa");
        config.setTables(new String[]{"AT_TEST_CASE", "AT_TEST_STEP"});
        config.setTablePrefix("");
        config.setSuperEntityClass(BaseEntity.class);
        config.setSuperDaoClass(BaseJpaRepository.class);
        config.setSuperServiceClass(BaseService.class);
        config.setSuperServiceImplClass(BaseServiceImpl.class);
        config.setSuperControllerClass(BaseController.class);
        config.setSuperEntityColumns(new String[]{"CREATE_BY", "CREATE_TIME", "UPDATE_BY", "UPDATE_TIME", "DELETED"});
        config.setDbUrl("jdbc:mysql://192.168.233.155:3306/tester?useUnicode=true&useSSL=false&characterEncoding=utf8");
        config.setDbDriverName("com.mysql.cj.jdbc.Driver");
        config.setDbUsername("root");
        config.setDbPassword("root");
        new CodeGenerator(config).run();
    }
}
