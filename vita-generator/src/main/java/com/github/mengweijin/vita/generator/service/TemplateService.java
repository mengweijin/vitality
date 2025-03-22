package com.github.mengweijin.vita.generator.service;

import com.github.mengweijin.vita.framework.util.JarFileUtils;
import com.github.mengweijin.vita.generator.domain.vo.TemplateVO;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hutool.core.collection.CollUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author mengweijin
 * @since 2022/8/14
 */
@Slf4j
@Service
public class TemplateService {

    public static final String TEMPLATE_DIR = "velocity";

    public List<TemplateVO> getTemplateList() {
        List<JarFileUtils.ContentInfo> list = JarFileUtils.loadContentInfo(TEMPLATE_DIR);

        list.forEach(item -> {
            log.debug("id={}, parentId={}, name={}, directory={}", item.getId(), item.getParentId(), item.getName(), item.isDirectory());
        });

        List<TemplateVO> templateVOList = list.stream()
                .map(TemplateVO::new)
                .toList();

        log.debug("filter list:");
        templateVOList.forEach(item -> {
            log.debug("id={}, parentId={}, name={}", item.getId(), item.getParentId(), item.getName());
        });

        return templateVOList;
    }

    private List<TemplateVO> treeTemplateVO(List<TemplateVO> list, String parentId) {
        Map<String, List<TemplateVO>> collect = list.stream().collect(Collectors.groupingBy(TemplateVO::getParentId));
        for (TemplateVO node : list) {
            List<TemplateVO> children = collect.get(node.getId());
            if (CollUtil.isNotEmpty(children)) {
                node.setChildren(children);
            }
        }
        return collect.get(parentId);
    }

}
