package com.github.mengweijin.vitality.framework.frontend.dtree;

import com.github.mengweijin.vitality.system.entity.VtlMenu;
import lombok.Data;
import org.dromara.hutool.core.collection.CollUtil;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author mengweijin
 * @date 2022/11/27
 */
@Data
public class DTreeNode {

    private String id;

    private String title;

    private Boolean last;

    private String parentId;

    private List<DTreeNode> children = new ArrayList<>();

    private Integer seq;

    public DTreeNode() {}

    public DTreeNode(VtlMenu menu) {
        this.id = String.valueOf(menu.getId());
        this.title = menu.getTitle();
        this.parentId = String.valueOf(menu.getParentId());
        this.seq = menu.getSeq();
    }

    public static List<DTreeNode> buildTree(List<DTreeNode> list, String parentId) {
        Map<String, List<DTreeNode>> collect = list.stream().collect(Collectors.groupingBy(DTreeNode::getParentId));
        for (DTreeNode node : list) {
            List<DTreeNode> children = collect.get(node.getId());
            if(CollUtil.isNotEmpty(children)) {
                children.sort(Comparator.comparingInt(DTreeNode::getSeq));
                node.setChildren(children);
            }
        }
        return collect.get(parentId);
    }
}
