package com.github.mengweijin.woodenman.generator.system.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.mengweijin.layui.model.LayuiTree;
import com.github.mengweijin.quickboot.util.Const;
import com.github.mengweijin.woodenman.generator.system.entity.Template;
import com.github.mengweijin.woodenman.generator.system.mapper.TemplateMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author mengweijin
 * @date 2022/8/14
 */
@Service
public class TemplateService extends ServiceImpl<TemplateMapper, Template> {

    @Autowired
    private TemplateMapper templateMapper;

    public List<LayuiTree> tree() {
        List<Template> list = this.lambdaQuery().orderByAsc(Template::getBuiltIn).list();
        Map<String, List<Template>> map = list.stream().collect(Collectors.groupingBy(Template::getCategory));

        LayuiTree mainTree = new LayuiTree();
        map.forEach((k, v) -> {
            LayuiTree node = new LayuiTree();
            node.setTitle(k);
            node.setId(null);
            node.setChildren(this.toLayuiTreeList(v));
            node.setSpread(true);
            mainTree.getChildren().add(node);
        });

        return mainTree.getChildren();
    }

    private List<LayuiTree> toLayuiTreeList(List<Template> templateList) {
        if(CollUtil.isEmpty(templateList)) {
            return new ArrayList<>();
        }
        return templateList.stream().map(tpl -> {
            LayuiTree node = new LayuiTree();
            node.setTitle(tpl.getName());
            node.setId(tpl.getId().toString());
            node.setChildren(null);
            node.setSpread(true);
            return node;
        }).collect(Collectors.toList());
    }

    public void initSystemBuiltInTemplates() {
        List<File> templateFileList = FileUtil.loopFiles("templates/generator/ftl/",
                file -> file.isFile() && file.getName().toLowerCase().endsWith(".ftl"));

        List<Template> templateList = new ArrayList<>();
        templateFileList.forEach(file -> {
            Template template = new Template();
            template.setCategory(file.getParentFile().getName());
            template.setName(StrUtil.subBefore(file.getName(), Const.DOT, true) + "<span style='color: #b1abab;'>（系统内置）</span>");
            template.setContent(FileUtil.readString(file, StandardCharsets.UTF_8));
            template.setSuffix(StrUtil.subAfter(file.getName(), Const.DOT, true));
            template.setBuiltIn(true);
            templateList.add(template);
        });
        this.saveBatch(templateList);
    }

}
