package com.github.mengweijin.vita.generator.domain.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author mengweijin
 * @since 2022/11/5
 */
@Data
public class ContentVO implements Serializable {

    private String templateName;

    private String fileName;

    private String content;

}
