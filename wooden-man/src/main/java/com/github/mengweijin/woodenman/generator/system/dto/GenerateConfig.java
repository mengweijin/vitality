package com.github.mengweijin.woodenman.generator.system.dto;

import cn.hutool.core.date.LocalDateTimeUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author mengweijin
 * @date 2022/11/6
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class GenerateConfig extends TableInfoDTO {

    private String ignoredColumns;

    private String entityName;

    private String packagePath;

    private String author;

    private String date = LocalDateTimeUtil.format(LocalDate.now(), DateTimeFormatter.ISO_LOCAL_DATE);

    public void build(TableInfoDTO dto) {
        this.setName(dto.getName());
        this.setHavePrimaryKey(dto.getHavePrimaryKey());
        this.setFieldNames(dto.getFieldNames());
        this.setComment(dto.getComment());
        this.setFields(dto.getFields());
    }

}
