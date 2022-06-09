package com.github.mengweijin.quickboot.framework.util.tree;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author mengweijin
 * @date 2022/6/11
 */
@Data
@Accessors(chain = true)
public class NodeParserConfig implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 默认属性配置对象
     */
    public static NodeParserConfig DEFAULT_CONFIG = new NodeParserConfig();

    /**
     * 属性名配置字段
     */
    private String idKey = "id";

    private String parentIdKey = "parentId";

    private String nameKey = "name";

}
