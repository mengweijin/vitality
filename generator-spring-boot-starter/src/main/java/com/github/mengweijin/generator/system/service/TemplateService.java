package com.github.mengweijin.generator.system.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import com.github.mengweijin.generator.system.dto.TemplateDTO;
import com.github.mengweijin.vitality.cache.CacheConst;
import com.github.mengweijin.vitality.dtree.DTreeDTO;
import com.github.mengweijin.vitality.dtree.DTreeNode;
import org.springframework.aop.framework.AopContext;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author mengweijin
 * @date 2022/8/14
 */
@Service
public class TemplateService {

    @Cacheable(
            cacheNames = CacheConst.NAME_NEVER_EXPIRE,
            key = CacheConst.KEY_CLASS_METHOD,
            unless = CacheConst.UNLESS_LIST
    )
    public List<TemplateDTO> findTemplate() {
        List<File> templateFileList = FileUtil.loopFiles("ftl/",
                file -> file.isFile() && file.getName().toLowerCase().endsWith(".ftl"));

        return templateFileList.stream()
                .map(file -> {
                    TemplateDTO dto = new TemplateDTO();
                    dto.setId(IdUtil.simpleUUID());
                    dto.setCategory(file.getParentFile().getName());
                    dto.setName(file.getName());
                    dto.setContent(FileUtil.readUtf8String(file));
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public TemplateDTO findTemplateById(String id) {
        TemplateService templateService = (TemplateService) AopContext.currentProxy();
        List<TemplateDTO> templateList = templateService.findTemplate();
        Optional<TemplateDTO> first = templateList.stream().filter(tpl -> tpl.getId().equals(id)).findFirst();
        return first.orElse(null);
    }

    public DTreeDTO tree() {
        TemplateService templateService = (TemplateService) AopContext.currentProxy();
        List<TemplateDTO> templateList = templateService.findTemplate();

        Map<String, List<TemplateDTO>> map = templateList.stream().collect(Collectors.groupingBy(TemplateDTO::getCategory));

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
            node.setId(tpl.getId());
            node.setTitle(tpl.getName());
            node.setLast(true);
            node.setParentId(parentId);
            node.setChildren(null);
            return node;
        }).collect(Collectors.toList());
    }



}
