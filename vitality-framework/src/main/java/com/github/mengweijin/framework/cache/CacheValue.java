package com.github.mengweijin.framework.cache;

import java.io.Serializable;

/**
 * ehcache的value值必须支持序列化接口，不能使用Object代替，这里声明一个缓存基类，所有缓存value对象必须实现Serializable
 * @author mengweijin
 * @since 2022/10/29
 */
public interface CacheValue extends Serializable {
}
