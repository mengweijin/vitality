package com.github.mengweijin.vitality.util.tree;

import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;

/**
 * @author mengweijin
 * @date 2022/6/11
 */
public class EntityNodeParser<S extends Object> extends NodeParser<S> {

    public EntityNodeParser(NodeParserConfig nodeParserConfig) {
        super(nodeParserConfig);
    }

    @Override
    public String getParentIdValue(S object) {
        return StrUtil.toStringOrNull(ReflectUtil.getFieldValue(object, this.getNodeParserConfig().getParentIdKey()));
    }

    @Override
    public NodeTree<S> parse(S object, int weight) {
        NodeTree<S> nodeTree = new NodeTree<>();
        NodeParserConfig nodeParserConfig = this.getNodeParserConfig();

        nodeTree.setId(StrUtil.toStringOrNull(ReflectUtil.getFieldValue(object, nodeParserConfig.getIdKey())));
        nodeTree.setParentId(StrUtil.toStringOrNull(ReflectUtil.getFieldValue(object, nodeParserConfig.getParentIdKey())));
        nodeTree.setName(StrUtil.toStringOrNull(ReflectUtil.getFieldValue(object, nodeParserConfig.getNameKey())));
        nodeTree.setExtra(object);
        nodeTree.setWeight(weight);
        return nodeTree;
    }
}
