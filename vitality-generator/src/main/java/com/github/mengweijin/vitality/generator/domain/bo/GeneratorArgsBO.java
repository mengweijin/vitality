package com.github.mengweijin.vitality.generator.domain.bo;

import com.github.mengweijin.vitality.framework.util.SpringBootMainClassUtils;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @author mengweijin
 * @since 2022/11/27
 */
@Data
public class GeneratorArgsBO {

    @NotBlank
    private String templateId;

    @NotBlank
    private String templateName;

    @NotBlank
    private String templateContent;

    @NotBlank
    private String tableName;

    private String tablePrefix = String.join(",", "VTL_", "SYS_");

    private String packages = SpringBootMainClassUtils.getSpringBootApplicationClassPackage();

    private String module;

    private String author = "mengweijin";
}
