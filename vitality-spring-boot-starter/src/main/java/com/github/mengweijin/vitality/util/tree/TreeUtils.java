package com.github.mengweijin.vitality.util.tree;

import cn.hutool.core.collection.CollUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author mengweijin
 * @date 2022/6/9
 */
public class TreeUtils {

    public static List<NodeTree> build(List list, String parentId) {
        return build(list, parentId, null, null);
    }

    public static List<NodeTree> build(List list, String parentId, NodeParserConfig nodeParserConfig) {
        return build(list, parentId, nodeParserConfig, null);
    }

    public static List<NodeTree> build(List list, String parentId, NodeParserConfig nodeParserConfig, Comparator comparator) {
        if(CollUtil.isEmpty(list)) {
            return new ArrayList<>();
        }
        NodeParser<?> nodeParser = (list.get(0) instanceof Map) ? new MapNodeParser(nodeParserConfig) : new EntityNodeParser(nodeParserConfig);
        List nodeTreeList = nodeParser.parseList(list, comparator);
        return buildNodeTree(nodeTreeList, parentId);
    }

    public static <T> List<NodeTree<T>> buildNodeTree(List<NodeTree<T>> list, String parentId) {
        Map<Serializable, List<NodeTree<T>>> collect = list.stream().collect(Collectors.groupingBy(NodeTree::getParentId));
        for (NodeTree<T> nodeTree : list) {
            List<NodeTree<T>> children = collect.get(nodeTree.getId());
            if(CollUtil.isNotEmpty(children)) {
                Collections.sort(children);
                nodeTree.setChildren(children);
            }
        }
        return collect.get(parentId);
    }
}
