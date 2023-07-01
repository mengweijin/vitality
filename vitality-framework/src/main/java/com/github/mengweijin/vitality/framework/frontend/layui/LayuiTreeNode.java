package com.github.mengweijin.vitality.framework.frontend.layui;

import lombok.Data;
import org.dromara.hutool.core.collection.CollUtil;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author mengweijin
 * @date 2022/11/5
 */
@Data
public class LayuiTreeNode {

    /** 节点标题 */
    private String title;
    /** 节点唯一索引值，用于对指定节点进行各类操作 */
    private String id;
    /** 节点字段名 */
    private String field;
    /** 子节点。支持设定选项同父节点 */
    private List<LayuiTreeNode> children = new ArrayList<>();
    /** 点击节点弹出新窗口对应的 url。需开启 isJump 参数 */
    private String href;
    /** 节点是否初始展开，默认 false */
    private Boolean spread = false;
    /** 节点是否初始为选中状态（如果开启复选框的话），默认 false */
    private Boolean checked = false;
    /** 节点是否为禁用状态。默认 false */
    private Boolean disabled = false;

    private String parentId;
    private Integer seq;

    public static List<LayuiTreeNode> buildTree(List<LayuiTreeNode> list, String parentId) {
        Map<String, List<LayuiTreeNode>> collect = list.stream().collect(Collectors.groupingBy(LayuiTreeNode::getParentId));
        for (LayuiTreeNode node : list) {
            List<LayuiTreeNode> children = collect.get(node.getId());
            if(CollUtil.isNotEmpty(children)) {
                children.sort(Comparator.comparingInt(LayuiTreeNode::getSeq));
                node.setChildren(children);
            }
        }
        return collect.get(parentId);
    }
}
