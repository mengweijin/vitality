package com.github.mengweijin.mybatisplus.demo.sourcecode.importannotation;

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

    @Override
    public Class<? extends Group> getImportGroup() {
        return DeferredImportSelector.super.getImportGroup();
    }
}
