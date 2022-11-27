package com.github.mengweijin.generator.system.dto;

import cn.hutool.core.date.LocalDateTimeUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

/**
 * @author mengweijin
 * @date 2022/11/6
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class GenerateConfigDTO extends TableInfoDTO {

    private String ignoredColumns;

    private String entityName;

    private String packagePath;

    private String author;

    private String date = LocalDateTimeUtil.formatNormal(LocalDate.now());

    public void initTableInfo(TableInfoDTO dto) {
        if(dto == null) {
            return;
        }
        this.setName(dto.getName());
        this.setHavePrimaryKey(dto.getHavePrimaryKey());
        this.setFieldNames(dto.getFieldNames());
        this.setComment(dto.getComment());
        this.setFields(dto.getFields());
    }

}
