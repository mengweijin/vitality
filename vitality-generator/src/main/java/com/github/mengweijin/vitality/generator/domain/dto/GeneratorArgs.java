package com.github.mengweijin.vitality.generator.domain.dto;

import com.github.mengweijin.vitality.framework.mybatis.entity.BaseEntity;
import com.github.mengweijin.vitality.generator.domain.bo.GeneratorArgsBO;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author mengweijin
 */
@Data
@NoArgsConstructor
public class GeneratorArgs {

    private String packages;

    private String moduleName;

    private String author;

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


    public GeneratorArgs(GeneratorArgsBO bo) {
        this.tablePrefix = bo.getTablePrefix().toArray(new String[0]);
        this.packages = bo.getPackages();
        this.moduleName = bo.getModule();
        this.author = bo.getAuthor();
    }
}
