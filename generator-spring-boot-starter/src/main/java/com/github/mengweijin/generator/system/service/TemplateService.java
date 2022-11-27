package com.github.mengweijin.generator.system.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.FileUtil;
import com.github.mengweijin.generator.system.dto.TemplateDTO;
import com.github.mengweijin.vitality.dtree.DTreeDTO;
import com.github.mengweijin.vitality.dtree.DTreeNode;
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

    public DTreeDTO tree() {
        List<File> templateFileList = FileUtil.loopFiles("ftl/",
                file -> file.isFile() && file.getName().toLowerCase().endsWith(".ftl"));

        Map<String, List<TemplateDTO>> map = templateFileList.stream()
                .map(file -> {
                    TemplateDTO dto = new TemplateDTO();
                    dto.setCategory(FileUtil.getParent(file, 1).getName());
                    dto.setName(file.getName());
                    dto.setContent(FileUtil.readUtf8String(file));
                    dto.setAbsolutePath(file.getAbsolutePath());
                    return dto;
                })
                .collect(Collectors.groupingBy(TemplateDTO::getCategory));

        List<DTreeNode> treeNodeList = new ArrayList<>();
        map.forEach((k, v) -> {
            DTreeNode node = new DTreeNode();
            node.setId(k);
            node.setTitle(k);
            node.setLast(false);
            node.setParentId(null);
            node.setChildren(this.toDTreeNodeList(k, v));
            treeNodeList.add(node);
        });

        DTreeDTO treeDTO = new DTreeDTO();
        treeDTO.setData(treeNodeList);
        return treeDTO;
    }

    private List<DTreeNode> toDTreeNodeList(String parentId, List<TemplateDTO> templateList) {
        if(CollUtil.isEmpty(templateList)) {
            return new ArrayList<>();
        }
        return templateList.stream().map(tpl -> {
            DTreeNode node = new DTreeNode();
            node.setId(tpl.getAbsolutePath());
            node.setTitle(tpl.getName());
            node.setLast(true);
            node.setParentId(parentId);
            node.setChildren(null);
            return node;
        }).collect(Collectors.toList());
    }



}
