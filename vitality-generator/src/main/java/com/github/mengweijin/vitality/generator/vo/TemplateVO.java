package com.github.mengweijin.vitality.generator.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @author mengweijin
 * @since 2022/10/30
 */
@Data
@Accessors(chain = true)
public class TemplateVO implements Serializable {

    private String id;

    private String parentId;

    private String name;

    private String content;

    private int seq;

    private List<TemplateVO> children;

}
