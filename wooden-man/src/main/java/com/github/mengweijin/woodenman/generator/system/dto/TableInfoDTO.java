package com.github.mengweijin.woodenman.generator.system.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author mengweijin
 * @date 2022/11/5
 */
@Data
public class TableInfoDTO implements Serializable {

    private static final long serialVersionUID = 6418269741293497370L;

    private String name;

    private Boolean havePrimaryKey;

    private String fieldNames;

    private String comment;

    @SuppressWarnings("AlibabaPojoNoDefaultValue")
    private List<TableFieldDTO> fields = new ArrayList<>();

}
