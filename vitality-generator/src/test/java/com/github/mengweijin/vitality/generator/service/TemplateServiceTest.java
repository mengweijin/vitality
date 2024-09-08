package com.github.mengweijin.vitality.generator.service;

import com.github.mengweijin.vitality.generator.vo.TemplateVO;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * @author mengweijin
 */
class TemplateServiceTest {

    @Test
    public void loadTemplateTree() {
        List<TemplateVO> list = new TemplateService().loadTemplateTree();
        System.out.println();
    }
}