package com.github.mengweijin.generator.system.dto;

import lombok.Data;

/**
 * @author mengweijin
 * @date 2022/11/27
 */
@Data
public class GenerateArgsDTO {

    private String templateId;

    private GenerateConfigDTO config;
}
