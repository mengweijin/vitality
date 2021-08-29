package com.github.mengweijin.quickboot.framework.cache;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author mengweijin
 */
@Data
@Accessors(chain = true)
public class CacheExpireTask implements Serializable {

    private String cacheName;

    private Object cacheKey;
}
