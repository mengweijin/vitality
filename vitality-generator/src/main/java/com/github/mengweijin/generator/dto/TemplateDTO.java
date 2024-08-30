package com.github.mengweijin.generator.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author mengweijin
 * @date 2022/10/30
 */
@Data
@Accessors(chain = true)
public class TemplateDTO implements Serializable {

    private String id;

    private String category;

    private String name;

    private String content;

}
