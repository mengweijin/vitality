package com.github.mengweijin.vitality.generator.domain.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author mengweijin
 * @since 2022/11/5
 */
@Data
public class TableInfoVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 6418269741293497370L;

    private String name;

    private Boolean havePrimaryKey;

    private String fieldNames;

    private String comment;

    @JsonIgnore
    private List<TableFieldVO> fields = new ArrayList<>();

}
