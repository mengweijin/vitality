package com.github.mengweijin.generator.system.dto;

import lombok.Data;

import java.util.List;

/**
 * @author mengweijin
 * @date 2022/11/27
 */
@Data
public class GenerateArgsDTO {

    private List<String> templateIdList;

    private GenerateConfigDTO config;
}
