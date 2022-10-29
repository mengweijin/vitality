package com.github.mengweijin.woodenman.generator.constant;

import lombok.Getter;

/**
 * ClassPathResource resource = new ClassPathResource("templates/generator/docker/");
 * File tempFile = FileUtil.file(Constants.JAVA_IO_TMPDIR + templateName);
 * @author mengweijin
 * @date 2022/9/3
 */
public enum DefaultTemplatePath {

    DOCKER(GeneratorConst.BASE_PATH + "docker/"),

    JPA(GeneratorConst.BASE_PATH + "jpa/"),

    MYBATIS(GeneratorConst.BASE_PATH + "mybatis/"),

    MYBATIS_PLUS(GeneratorConst.BASE_PATH + "mybatis-plus/");

    @Getter
    private final String path;

    DefaultTemplatePath(String path) {
        this.path = path;
    }
}
