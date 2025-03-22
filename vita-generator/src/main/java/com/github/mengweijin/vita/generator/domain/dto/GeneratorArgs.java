package com.github.mengweijin.vita.generator.domain.dto;

import com.github.mengweijin.vita.framework.mybatis.entity.BaseEntity;
import com.github.mengweijin.vita.generator.domain.bo.GeneratorArgsBO;
import com.github.mengweijin.vita.generator.util.GeneratorUtils;
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
        this.tablePrefix = GeneratorUtils.parseTablePrefix(bo.getTablePrefix());
        this.packages = bo.getPackages();
        this.moduleName = bo.getModule();
        this.author = bo.getAuthor();
    }
}
