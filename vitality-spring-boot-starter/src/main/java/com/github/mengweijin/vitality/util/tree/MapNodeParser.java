package com.github.mengweijin.vitality.util.tree;

import cn.hutool.core.util.StrUtil;

import java.util.Map;

/**
 * @author mengweijin
 * @date 2022/6/11
 */
public class MapNodeParser extends NodeParser<Map<String, Object>> {

    public MapNodeParser(NodeParserConfig nodeParserConfig) {
        super(nodeParserConfig);
    }

    @Override
    public String getParentIdValue(Map<String, Object> map) {
        return StrUtil.toStringOrNull(map.get(this.getNodeParserConfig().getParentIdKey()));
    }

    @Override
    public NodeTree<Map<String, Object>> parse(Map<String, Object> map, int weight) {
        NodeTree<Map<String, Object>> nodeTree = new NodeTree<>();
        NodeParserConfig nodeParserConfig = this.getNodeParserConfig();

        nodeTree.setId(StrUtil.toStringOrNull(map.get(nodeParserConfig.getIdKey())));
        nodeTree.setParentId(StrUtil.toStringOrNull(map.get(nodeParserConfig.getParentIdKey())));
        nodeTree.setName(StrUtil.toStringOrNull(map.get(nodeParserConfig.getNameKey())));
        nodeTree.setExtra(map);
        nodeTree.setWeight(weight);
        return nodeTree;
    }

}
