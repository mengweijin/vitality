package com.github.mengweijin.vitality.generator.service;

import com.github.mengweijin.vitality.generator.vo.TemplateVO;
import org.dromara.hutool.core.collection.CollUtil;
import org.dromara.hutool.core.io.file.FileUtil;
import org.dromara.hutool.core.io.resource.ResourceUtil;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author mengweijin
 * @since 2022/8/14
 */
@Service
public class TemplateService {

    public static final String TEMPLATE_DIR = "velocity";

    public static final String TEMPLATE_SUFFIX = ".vm";

    public static final File TEMPLATE_FILES = FileUtil.file(ResourceUtil.getResource(TEMPLATE_DIR).getUrl());

    public static List<TemplateVO> TEMPLATE_CACHE = new ArrayList<>();

    public List<TemplateVO> loadTemplateTree() {
        List<File> templateList = FileUtil.loopFiles(TEMPLATE_FILES, item -> item.isDirectory() || (item.isFile() && item.getName().toLowerCase().endsWith(TEMPLATE_SUFFIX)));
        if (CollUtil.isEmpty(TEMPLATE_CACHE)) {
            TEMPLATE_CACHE = this.buildTemplateList(templateList);
        }
        return this.treeTemplateVO(TEMPLATE_CACHE, TEMPLATE_FILES.getPath());
    }

    private List<TemplateVO> buildTemplateList(List<File> templateList) {
        List<TemplateVO> list = new ArrayList<>();
        templateList.forEach(file -> {
            TemplateVO vo = new TemplateVO();
            vo.setId(file.getPath());
            vo.setParentId(file.getParentFile().getPath());
            vo.setName(file.getName());
            if (file.isFile()) {
                vo.setContent(FileUtil.readUtf8String(file));
            }
            list.add(vo);

            collectParentTemplateNode(file.getParentFile(), list);
        });
        return list;
    }

    private void collectParentTemplateNode(File file, List<TemplateVO> list) {
        if (TEMPLATE_FILES.getPath().equals(file.getPath()) || list.stream().anyMatch(i -> i.getId().equals(file.getPath()))) {
            return;
        }
        TemplateVO vo = new TemplateVO();
        vo.setId(file.getPath());
        vo.setParentId(file.getParentFile().getPath());
        vo.setName(file.getName());
        list.add(vo);

        collectParentTemplateNode(file, list);
    }

    private List<TemplateVO> treeTemplateVO(List<TemplateVO> list, String parentId) {
        Map<String, List<TemplateVO>> collect = list.stream().collect(Collectors.groupingBy(TemplateVO::getParentId));
        for (TemplateVO node : list) {
            List<TemplateVO> children = collect.get(node.getId());
            if (CollUtil.isNotEmpty(children)) {
                children.sort(Comparator.comparingInt(TemplateVO::getSeq));
                node.setChildren(children);
            }
        }
        return collect.get(parentId);
    }

}
