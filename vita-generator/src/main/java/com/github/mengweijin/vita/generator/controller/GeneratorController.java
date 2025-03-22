package com.github.mengweijin.vita.generator.controller;

import com.github.mengweijin.vita.framework.domain.R;
import com.github.mengweijin.vita.framework.util.ServletUtils;
import com.github.mengweijin.vita.generator.domain.bo.GeneratorArgsBO;
import com.github.mengweijin.vita.generator.domain.bo.GeneratorTestBO;
import com.github.mengweijin.vita.generator.domain.vo.ContentVO;
import com.github.mengweijin.vita.generator.domain.vo.TableInfoVO;
import com.github.mengweijin.vita.generator.domain.vo.TemplateVO;
import com.github.mengweijin.vita.generator.service.GeneratorService;
import com.github.mengweijin.vita.generator.service.TemplateService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.dromara.hutool.core.io.file.FileUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.List;

/**
 * @author mengweijin
 * @since 2022/11/27
 */
@RestController
@AllArgsConstructor
@RequestMapping("/generator")
public class GeneratorController {

    private TemplateService templateService;

    private GeneratorService generatorService;

    @GetMapping("/table/list")
    public List<TableInfoVO> getTableList(String name) {
        return generatorService.selectTableList(name);
    }

    @GetMapping("/template/list")
    public List<TemplateVO> getTemplateList() {
        return templateService.getTemplateList();
    }

    @GetMapping("/args/default")
    public GeneratorArgsBO getDefaultArgs() {
        return new GeneratorArgsBO();
    }

    @PostMapping("/execute")
    public R<ContentVO> execute(@RequestBody GeneratorArgsBO bo) {
        ContentVO contentVO = generatorService.generate(bo);
        return R.success(contentVO);
    }

    /**
     * Test Only.
     *
     * @param bo GeneratorTestBO
     * @return ContentVO
     */
    @Deprecated
    @PostMapping("/executeTest")
    public String executeTest(@RequestBody GeneratorTestBO bo) {
        ContentVO contentVO = generatorService.generate(bo.toGeneratorArgsBO());
        return contentVO.getContent();
    }

    @PostMapping("/download")
    public void download(@RequestBody GeneratorArgsBO bo, HttpServletResponse response) {
        File file = generatorService.download(bo);
        ServletUtils.write(response, file);
        FileUtil.del(file);
    }
}
