package com.github.mengweijin.vitality.util.tree;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import lombok.Data;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author mengweijin
 * @date 2022/6/11
 */
@Data
public abstract class NodeParser<T> {

    private NodeParserConfig nodeParserConfig;

    public NodeParser(NodeParserConfig nodeParserConfig){
        this.nodeParserConfig = ObjectUtil.defaultIfNull(nodeParserConfig, NodeParserConfig.DEFAULT_CONFIG);
    }

    public abstract String getParentIdValue(T object);

    public abstract NodeTree<T> parse(T object, int weight);

    public List<NodeTree<T>> parseList(List<T> list, Comparator<T> comparator) {
        if(CollUtil.isEmpty(list)) {
            return new ArrayList<>();
        }
        List<NodeTree<T>> result = new ArrayList<>();
        list.stream()
                .collect(Collectors.groupingBy(this::getParentIdValue))
                .forEach((k, v) -> result.addAll(parseChildrenList(v, comparator)));
        return result;
    }

    public List<NodeTree<T>> parseChildrenList(List<T> childrenList, Comparator<T> comparator) {
        childrenList = CollUtil.sort(childrenList, comparator);
        AtomicInteger index = new AtomicInteger(1);
        return childrenList.stream()
                .map(object -> this.parse(object, index.getAndIncrement()))
                .collect(Collectors.toList());
    }
}
