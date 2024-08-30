package com.github.mengweijin.generator.service;

import com.github.mengweijin.generator.dto.TemplateDTO;
import org.dromara.hutool.core.data.id.IdUtil;
import org.dromara.hutool.core.io.file.FileUtil;
import org.dromara.hutool.core.text.StrUtil;
import org.springframework.stereotype.Service;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author mengweijin
 * @since 2022/8/14
 */
@Service
public class TemplateService {

    private static final List<TemplateDTO> TPL_LIST = new ArrayList<>();

    public List<TemplateDTO> findTemplate() {
        if(!TPL_LIST.isEmpty()) {
            return TPL_LIST;
        }

        List<File> templateFileList = FileUtil.loopFiles("generator/",
                file -> file.isFile() && file.getName().toLowerCase().endsWith(".ftl"));

        List<TemplateDTO> list = templateFileList.stream()
                .map(file -> {
                    TemplateDTO dto = new TemplateDTO();
                    dto.setId(IdUtil.simpleUUID());
                    dto.setCategory(file.getParentFile().getName());
                    dto.setName(StrUtil.subBefore(file.getName(), ".", true));
                    dto.setContent(FileUtil.readUtf8String(file));
                    return dto;
                })
                .toList();
        TPL_LIST.addAll(list);
        return TPL_LIST;
    }

    public TemplateDTO findTemplateById(String id) {
        List<TemplateDTO> templateList = this.findTemplate();
        Optional<TemplateDTO> first = templateList.stream().filter(tpl -> tpl.getId().equals(id)).findFirst();
        return first.orElse(null);
    }

}
