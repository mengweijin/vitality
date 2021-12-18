package com.github.mengweijin.cache.expired;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.cache.CacheManager;
import java.io.Serializable;
import java.util.Set;

/**
 * @author mengweijin
 */
@Data
@Accessors(chain = true)
public class CacheExpiredTask implements Serializable {

    private CacheManager cacheManager;

    private Set<String> cacheNames;

    private Object cacheKey;
}
