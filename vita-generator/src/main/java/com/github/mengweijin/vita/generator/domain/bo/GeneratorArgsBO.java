package com.github.mengweijin.vita.generator.domain.bo;

import com.github.mengweijin.vita.framework.util.SpringBootMainClassUtils;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @author mengweijin
 * @since 2022/11/27
 */
@Data
public class GeneratorArgsBO {

    private String templateId;

    private String templateName;

    private String templateContent;

    @NotBlank
    private String tableName;

    private String tablePrefix = String.join(",", "VTL_", "SYS_");

    private String packages = SpringBootMainClassUtils.getSpringBootApplicationClassPackage();

    private String module = "system";

    private String author = "mengweijin";
}
