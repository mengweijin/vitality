package com.github.mengweijin.vitality.system.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author mengweijin
 * @since 2024/11/16
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CacheVO {

    private String name;

    private String key;

    private Object value;
}
