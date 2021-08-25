package com.github.mengweijin.mybatisplus.demo.ImportSelector;

import com.github.mengweijin.mybatisplus.demo.ImportSelector.entity.Phone2;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

import java.util.function.Predicate;

@Slf4j
@Data
public class Phone2ImportSelector implements ImportSelector {

    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        log.debug(importingClassMetadata.toString());
        return new String[]{Phone2.class.getName()};
    }

    @Override
    public Predicate<String> getExclusionFilter() {
        log.debug("public Predicate<String> getExclusionFilter()");
        return ImportSelector.super.getExclusionFilter();
    }
}
