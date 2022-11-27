package com.github.mengweijin.generator.system.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.FileUtil;
import com.github.mengweijin.generator.system.dto.TemplateDTO;
import com.github.mengweijin.vitality.cache.CacheConst;
import com.github.mengweijin.vitality.layui.LayuiTree;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author mengweijin
 * @date 2022/8/14
 */
@Service
public class TemplateService {

    @Cacheable(
            value = CacheConst.NAME_DEFAULT,
            key = CacheConst.KEY_CLASS_METHOD,
            unless = CacheConst.UNLESS_LIST
    )
    public List<LayuiTree> tree() {
        List<File> templateFileList = FileUtil.loopFiles("ftl/",
                file -> file.isFile() && file.getName().toLowerCase().endsWith(".ftl"));

        Map<String, List<TemplateDTO>> map = templateFileList.stream()
                .map(file -> {
                    TemplateDTO dto = new TemplateDTO();
                    dto.setCategory(FileUtil.getParent(file, 1).getName());
                    dto.setName(file.getName());
                    dto.setContent(FileUtil.readUtf8String(file));
                    return dto;
                })
                .collect(Collectors.groupingBy(TemplateDTO::getCategory));

        List<LayuiTree> treeList = new ArrayList<>();
        map.forEach((k, v) -> {
            LayuiTree node = new LayuiTree();
            node.setTitle(k);
            node.setId(null);
            node.setChildren(this.toLayuiTreeList(v));
            node.setSpread(true);
            treeList.add(node);
        });

        return treeList;
    }

    private List<LayuiTree> toLayuiTreeList(List<TemplateDTO> templateList) {
        if(CollUtil.isEmpty(templateList)) {
            return new ArrayList<>();
        }
        return templateList.stream().map(tpl -> {
            LayuiTree node = new LayuiTree();
            node.setTitle(tpl.getName());
            node.setId(tpl.getCategory() + "_" + tpl.getName());
            node.setChildren(null);
            node.setSpread(true);
            return node;
        }).collect(Collectors.toList());
    }

}
