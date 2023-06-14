package com.github.mengweijin.vitality.system.dto;

import com.github.mengweijin.vitality.system.entity.VtlMenu;
import com.github.mengweijin.vitality.system.enums.EMenuOpenType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @author mengweijin
 * @date 2023/5/20
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode
public class VtlMenuTreeDataDTO implements Serializable {

    private Long id;

    private Integer type;

    private String icon;

    private String title;

    private String href;

    private EMenuOpenType openType;

    private Long parentId;

    private Integer seq;

    private List<VtlMenuTreeDataDTO> children;

    public VtlMenuTreeDataDTO(VtlMenu menu) {
        this.id = menu.getId();
        this.type = menu.getType().getValue();
        this.icon = menu.getIcon();
        this.title = menu.getTitle();
        this.href = menu.getUrl();
        this.openType = menu.getOpenType();
        this.parentId = menu.getParentId();
        this.seq = menu.getSeq();
    }
}
