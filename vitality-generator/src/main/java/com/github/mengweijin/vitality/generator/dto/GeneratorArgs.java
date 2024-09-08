package com.github.mengweijin.vitality.generator.dto;

import com.github.mengweijin.vitality.framework.mybatis.entity.BaseEntity;
import lombok.Data;

/**
 * @author mengweijin
 */
@Data
public class GeneratorArgs {

    private String packages;

    private String moduleName;

    private String author = "mengweijin";

    private String templateDir = "vitality-generator/src/main/resources/velocity";

    /**
     * Optional.
     * For example: SYS_, VTL_
     * Note: Separated by commas.
     */
    private String[] tablePrefix;

    /**
     * Optional.
     */
    private String baseEntity = BaseEntity.class.getName();

}
