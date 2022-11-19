package com.github.mengweijin.vitality.util.tree;

import cn.hutool.core.comparator.CompareUtil;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author mengweijin
 * @date 2022/6/11
 */
@Data
public class NodeTree<T> implements Comparable<NodeTree<T>>, Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private String id;

    /**
     * 父节点ID
     */
    private String parentId;

    /**
     * 名称
     */
    private CharSequence name;

    /**
     * 扩展字段
     */
    private T extra;

    /**
     * 顺序 越小优先级越高 默认0
     */
    private Comparable<?> weight = 0;

    private List<NodeTree<T>> children;

    public NodeTree() {
    }

    /**
     * 构造
     *
     * @param id       ID
     * @param parentId 父节点ID
     * @param name     名称
     */
    public NodeTree(String id, String parentId, String name) {
        this.id = id;
        this.parentId = parentId;
        this.name = name;
    }

    @Override
    public int compareTo(NodeTree<T> node) {
        if(null == node){
            return 1;
        }
        final Comparable weight = this.getWeight();
        final Comparable weightOther = node.getWeight();
        return CompareUtil.compare(weight, weightOther);
    }
}
