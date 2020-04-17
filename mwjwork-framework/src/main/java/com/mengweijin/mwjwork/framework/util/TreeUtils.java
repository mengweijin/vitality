package com.mengweijin.mwjwork.framework.util;

import cn.hutool.core.util.ReflectUtil;
import com.alibaba.fastjson.JSONObject;
import com.mengweijin.mwjwork.framework.exception.ServerException;
import lombok.extern.slf4j.Slf4j;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Meng Wei Jin
 * @description 组装JSON格式的树结构工具类
 **/
@Slf4j
public class TreeUtils {

    /**
     * 根据父id递归查找过滤其下所有的子
     * @param list 所有数据集合
     * @param parentIds 父id数组
     * @return
     */
    public static List<Map<String, Object>> filterMapByPid(List<Map<String, Object>> list, String... parentIds){
        return filterMapByRootId("id", "pid", list, parentIds);
    }

    /**
     * 根据父id递归查找过滤其下所有的子
     * @param list 所有数据集合
     * @param parentIds 父id数组
     * @return
     */
    public static List<Map<String, Object>> filterMapByParentId(List<Map<String, Object>> list, String... parentIds){
        return filterMapByRootId("id", "parentId", list, parentIds);
    }

    /**
     * 根据父id递归查找过滤其下所有的子
     * @param idName id属性名称，如：id
     * @param parentIdName 父id属性名称，如：parentId、pid等
     * @param list 所有数据集合
     * @param parentIds 父id数组
     * @return
     */
    public static List<Map<String, Object>> filterMapByRootId(String idName, String parentIdName, List<Map<String, Object>> list, String... parentIds){
        List<Map<String, Object>> resultList = new ArrayList<>();
        for (String parentId: parentIds){
            //  lambda表达式 根据parentId过滤
            List<Map<String, Object>> filterList = list.stream()
                    .filter(map -> parentId.equals(String.valueOf(map.get(parentIdName))))
                    .collect(Collectors.toList());

            // 如果过滤到数据，添加到返回结果集合，并往下递归查找添加
            if(filterList.size() > 0){
                resultList.addAll(filterList);

                List<String> parentIdList = new ArrayList<>(filterList.size());
                filterList.forEach(map -> parentIdList.add(String.valueOf(map.get(idName))));
                // 递归
                resultList.addAll(filterMapByRootId(idName, parentIdName, list, parentIdList.toArray(new String[parentIdList.size()])));
            }
        }

        return resultList;
    }

    public static <T> List<T> filterBeanByPid(List<T> list, String... parentIds) throws ServerException {
        return filterBeanByRootId("id", "pid", list, parentIds);
    }

    public static <T> List<T> filterBeanByParentId(List<T> list, String... parentIds) throws ServerException {
        return filterBeanByRootId("id", "parentId", list, parentIds);
    }

    /**
     * 根据父id递归查找过滤其下所有的子
     * @param idName id属性名称，如：id
     * @param parentIdName 父id属性名称，如：parentId、pid等
     * @param list 所有数据集合
     * @param parentIds 父id数组
     * @param <E>
     * @return
     * @throws ServerException
     */
    public static <E> List<E> filterBeanByRootId(String idName, String parentIdName, List<E> list, String... parentIds) throws ServerException {
        List<E> resultList = new ArrayList<>();
        try{
            for (String parentId: parentIds){
                //  lambda表达式 根据parentId过滤
                List<E> filterList = list.stream()
                        .filter(e -> parentId.equals(String.valueOf(ReflectUtil.getFieldValue(e, parentIdName))))
                        .collect(Collectors.toList());

                // 如果过滤到数据，添加到返回结果集合，并往下递归查找添加
                if(filterList.size() > 0){
                    resultList.addAll(filterList);

                    List<String> parentIdList = new ArrayList<>(filterList.size());
                    filterList.forEach(e -> parentIdList.add(String.valueOf(ReflectUtil.getFieldValue(e, idName))));
                    // 递归
                    resultList.addAll(filterBeanByRootId(idName, parentIdName, list, parentIdList.toArray(new String[parentIdList.size()])));
                }
            }
        } catch (RuntimeException e){
            log.error(e.getMessage(), e);
            throw new ServerException(e);
        }

        return resultList;
    }

    public static <T> List<JSONObject> buildJsonBeanTreeByPid(List<T> list, String rootId) throws ServerException {
        return buildJsonBeanTree(list, rootId, "id", "pid");
    }

    public static <T> List<JSONObject> buildJsonBeanTreeByParentId(List<T> list, String rootId) throws ServerException {
        return buildJsonBeanTree(list, rootId, "id", "parentId");
    }

    /**
     * @param list    所有数据集合
     * @param rootId  根id
     * @param idName  实体类对应的id的属性名。如：Person对象下有id属性，属性名即为id
     * @param pidName 实体类对应的父id的属性名。如：Person对象下有parentId属性，属性名即为parentId
     * @param <E>     实体类泛型
     * @return
     * @throws ServerException
     */
    public static <E> List<JSONObject> buildJsonBeanTree(List<E> list, String rootId, String idName, String pidName) {
        List<JSONObject> jsonTreeList = new ArrayList<>();
        try {
            for (E e: list){
                String pid = String.valueOf(ReflectUtil.getFieldValue(e, pidName));
                if (rootId.equals(pid)) {
                    // 获取主键值 id
                    String id = String.valueOf(ReflectUtil.getFieldValue(e, idName));

                    JSONObject newNode = new JSONObject();

                    Field[] fields = e.getClass().getDeclaredFields();
                    for (Field field: fields) {
                        newNode.fluentPut(field.getName(), ReflectUtil.getFieldValue(e, field));
                    }
                    newNode.fluentPut("children", buildJsonBeanTree(list, id, idName, pidName));

                    jsonTreeList.add(newNode);
                }
            }
        } catch (RuntimeException e){
            log.error(e.getMessage(), e);
            throw new ServerException(e);
        }

        return jsonTreeList;
    }

    public static List<JSONObject> buildJsonMapTreeByPid(List<Map<String, Object>> list, String rootId) {
        return buildJsonMapTree(list, rootId, "id", "pid");
    }

    public static List<JSONObject> buildJsonMapTreeByParentId(List<Map<String, Object>> list, String rootId) {
        return buildJsonMapTree(list, rootId, "id", "parentId");
    }

    /**
     * @param list    所有数据集合
     * @param rootId  根id
     * @param idName  实体类对应的id的属性名。如：Person对象下有id属性，属性名即为id
     * @param pidName 实体类对应的父id的属性名。如：Person对象下有parentId属性，属性名即为parentId
     * @return
     * @throws ServerException
     */
    public static List<JSONObject> buildJsonMapTree(List<Map<String, Object>> list, String rootId, String idName, String pidName) {
        List<JSONObject> jsonTreeList = new ArrayList<>();

        list.forEach(map -> {
            String pid = String.valueOf(map.get(pidName));
            if (rootId.equals(pid)) {
                // 获取主键值 id
                String id = String.valueOf(map.get(idName));

                JSONObject newNode = new JSONObject();

                newNode.putAll(map);
                newNode.fluentPut("children", buildJsonMapTree(list, id, idName, pidName));

                jsonTreeList.add(newNode);
            }
        });

        return jsonTreeList;
    }

}
