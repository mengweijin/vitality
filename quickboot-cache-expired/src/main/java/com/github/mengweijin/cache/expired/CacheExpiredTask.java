package com.github.mengweijin.cache.expired;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.cache.CacheManager;

import java.util.Set;

/**
 * @author mengweijin
 */
@Data
@Accessors(chain = true)
public class CacheExpiredTask {

    private CacheManager cacheManager;

    private Set<String> cacheNames;

    private Object cacheKey;
}
