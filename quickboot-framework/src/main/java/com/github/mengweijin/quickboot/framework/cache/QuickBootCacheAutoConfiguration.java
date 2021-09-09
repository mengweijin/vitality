package com.github.mengweijin.quickboot.framework.cache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.annotation.ProxyCachingConfiguration;
import org.springframework.context.annotation.Configuration;

/**
 * @author Meng Wei Jin
 * @description EnableCaching 开启缓存
 **/
@Slf4j
@Configuration
@EnableCaching
@AutoConfigureAfter({ProxyCachingConfiguration.class})
public class QuickBootCacheAutoConfiguration {


}
