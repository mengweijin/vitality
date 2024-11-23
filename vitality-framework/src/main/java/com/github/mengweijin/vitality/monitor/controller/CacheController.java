package com.github.mengweijin.vitality.monitor.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.github.mengweijin.vitality.framework.cache.CacheFactory;
import com.github.mengweijin.vitality.framework.domain.R;
import com.github.mengweijin.vitality.monitor.domain.vo.CacheVO;
import org.dromara.hutool.core.text.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.cache.Cache;
import javax.cache.CacheManager;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author mengweijin
 * @since 2022/10/30
 */
@RestController
@RequestMapping("/monitor/cache")
public class CacheController {

    @Autowired
    private CacheManager cacheManager;

    @SaCheckPermission("monitor:cache:query")
    @GetMapping("/names")
    public Collection<String> getCacheNames() {
        return CacheFactory.getCacheNames();
    }

    @SaCheckPermission("monitor:cache:query")
    @GetMapping("/query")
    public List<CacheVO> getCacheByName(@RequestParam("cacheName") String cacheName) {
        List<CacheVO> list = new ArrayList<>();
        Cache<Object, Object> cache = cacheManager.getCache(cacheName);
        if (cache != null) {
            for (Cache.Entry<Object, Object> next : cache) {
                String key = StrUtil.toString(next.getKey());
                list.add(new CacheVO(key, next.getValue()));
            }
        }
        return list;
    }

    @SaCheckPermission("monitor:cache:delete")
    @PostMapping("/remove")
    public R<Void> remove(@RequestParam("cacheName") String cacheName, @RequestParam(name = "cacheKey", required = false) Serializable cacheKey) {
        Cache<Object, Object> cache = cacheManager.getCache(cacheName);
        if (cacheKey == null || StrUtil.isBlank(cacheKey.toString())) {
            cache.clear();
            return R.success();
        } else {
            boolean removed = cache.remove(cacheKey);
            return R.ajax(removed);
        }
    }

    @SaCheckPermission("monitor:cache:delete")
    @PostMapping("/clear")
    public R<Void> clear() {
        Collection<String> cacheNames = this.getCacheNames();
        for (String cacheName : cacheNames) {
            Cache<Object, Object> cache = cacheManager.getCache(cacheName);
            cache.clear();
        }
        return R.success();
    }
}