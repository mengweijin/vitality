package com.github.mengweijin.vitality.generator.domain.vo;

import lombok.Data;
import java.io.Serializable;
import java.util.List;

/**
 * @author mengweijin
 * @since 2022/10/30
 */
@Data
public class TemplateVO implements Serializable {

    private String id;

    private String parentId;

    private String name;

    private String content;

    private int seq;

    private List<TemplateVO> children;

}
