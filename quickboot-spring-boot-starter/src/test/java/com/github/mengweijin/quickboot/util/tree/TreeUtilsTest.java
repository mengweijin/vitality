package com.github.mengweijin.quickboot.util.tree;

import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.core.lang.tree.parser.DefaultNodeParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.jupiter.api.Test;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author mengweijin
 * @date 2022/6/9
 */
class TreeUtilsTest {

    private static int total = 10000;

    @Test
    void test1() {
        hutool();
        nodeTree();
    }

    void hutool() {
        List<TreeNode<Integer>> list = new ArrayList<>();
        for (int i = 0; i < total; i++) {
            TreeNode node = new TreeNode(i, i - 1, "name" + i, null);
            list.add(node);
        }
        long start = System.currentTimeMillis();
        List<Tree<Integer>> build = TreeUtil.build(list, 0);
        long end = System.currentTimeMillis();
        System.out.println("hutool TreeNode: " + (end - start));
    }

    void nodeTree() {
        List<NodeTree<Object>> list = new ArrayList<>();
        for (int i = 0; i < total; i++) {
            NodeTree node = new NodeTree("" + i, "" + (i - 1), "name" + i);
            list.add(node);
        }
        long start = System.currentTimeMillis();
        List<NodeTree<Object>> build = TreeUtils.buildNodeTree(list, "0");
        long end = System.currentTimeMillis();
        System.out.println("nodeTree: " + (end - start));
    }

    @Test
    void test2() throws JsonProcessingException {
        List<User> list = new ArrayList<>();
        for (int i = 0; i < total; i++) {
            list.add(new User(i, i - 1, "name" + i));
        }

        hutoolObjectList(list);
        nodeTreeObjectList(list);
        nodeTreeMapList(list);
    }

    void hutoolObjectList(List<User> list) throws JsonProcessingException {
        long start = System.currentTimeMillis();
        ObjectMapper objectMapper = new ObjectMapper();
        List<TreeNode<Integer>> collect = list.stream().map(user -> {
            TreeNode<Integer> treeNode = new TreeNode<>(user.getId(), user.getParentId(), user.getName(), null);
            try {
                String value = objectMapper.writeValueAsString(user);
                Map map = objectMapper.readValue(value, Map.class);
                treeNode.setExtra(map);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }

            return treeNode;
        }).collect(Collectors.toList());
        List<Tree> build = TreeUtil.build(collect, 0, new DefaultNodeParser());

        long end = System.currentTimeMillis();
        System.out.println("hutool ObjectList: " + (end - start));
    }

    void nodeTreeObjectList(List<User> list) {
        long start = System.currentTimeMillis();
        List<NodeTree> build = TreeUtils.build(list, "0");
        long end = System.currentTimeMillis();
        System.out.println("nodeTreeObject: " + (end - start));
    }

    void nodeTreeMapList(List<User> list) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String value = objectMapper.writeValueAsString(list);
        List mapList = objectMapper.readValue(value, List.class);
        long start = System.currentTimeMillis();
        List<NodeTree> build = TreeUtils.build(mapList, "0");
        long end = System.currentTimeMillis();
        System.out.println("nodeTreeMap: " + (end - start));
    }

    @Data
    @AllArgsConstructor
    class User implements Serializable {
        private Integer id;
        private Integer parentId;
        private String name;
    }
}