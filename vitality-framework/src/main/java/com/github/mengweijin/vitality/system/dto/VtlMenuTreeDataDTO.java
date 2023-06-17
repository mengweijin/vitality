package com.github.mengweijin.vitality.system.dto;

import com.github.mengweijin.vitality.system.entity.VtlMenu;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.dromara.hutool.core.collection.CollUtil;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    private String openType;

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

    public static List<VtlMenuTreeDataDTO> buildTree(List<VtlMenuTreeDataDTO> list, Long parentId) {
        Map<Long, List<VtlMenuTreeDataDTO>> collect = list.stream().collect(Collectors.groupingBy(VtlMenuTreeDataDTO::getParentId));
        for (VtlMenuTreeDataDTO node : list) {
            List<VtlMenuTreeDataDTO> children = collect.get(node.getId());
            if(CollUtil.isNotEmpty(children)) {
                children.sort(Comparator.comparingInt(VtlMenuTreeDataDTO::getSeq));
                node.setChildren(children);
            }
        }
        return collect.get(parentId);
    }
}
