package com.github.mengweijin.generator.system.dto;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author mengweijin
 * @date 2022/10/30
 */
@Data
@Accessors(chain = true)
public class TemplateDTO {

    private String category;

    private String name;

    private String content;

}
