package com.github.mengweijin.mybatisplus.demo.ImportSelector;

import com.github.mengweijin.mybatisplus.demo.ImportSelector.entity.Phone3;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.core.type.AnnotationMetadata;

import java.util.function.Predicate;

@Slf4j
@Data
public class Phone3DeferredImportSelector implements DeferredImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        log.debug(importingClassMetadata.toString());
        return new String[]{Phone3.class.getName()};
    }

    @Override
    public Predicate<String> getExclusionFilter() {
        log.debug(" public Predicate<String> getExclusionFilter()");
        return DeferredImportSelector.super.getExclusionFilter();
    }

}
