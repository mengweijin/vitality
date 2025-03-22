package com.github.mengweijin.vitality.generator.domain.vo;

import com.github.mengweijin.vitality.framework.util.JarFileUtils;
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

    private boolean directory;

    private String name;

    private String content;

    private List<TemplateVO> children;

    public TemplateVO(JarFileUtils.ContentInfo contentInfo) {
        this.id = contentInfo.getId();
        this.parentId = contentInfo.getParentId();
        this.directory = contentInfo.isDirectory();
        this.name = contentInfo.getName();
        this.content = contentInfo.getContent();
    }
}
