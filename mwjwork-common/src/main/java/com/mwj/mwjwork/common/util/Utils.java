package com.mwj.mwjwork.common.util;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @author Meng Wei Jin
 * @description 常用工具类合集，可直接使用父类，这里仅作为记录，防止一些遗忘
 **/
@Deprecated
public interface Utils {

    /**
     * Bean工具类
     */
    class BeanUtil extends org.apache.commons.beanutils.BeanUtils {
    }

    /**
     * Serialization工具类
     */
    class SerializationUtil extends org.apache.commons.lang3.SerializationUtils {
    }

    /**
     * DigestUtils类实现哈希摘要（SHA/MD5） 工具类
     */
    class DigestUtil extends DigestUtils {
        public String encrypt(String userName, String password, String salt){
            return DigestUtils.md5Hex(userName + password + salt);
        }
    }

    /**
     * MapUtils 工具类
     */
    class MapUtil extends org.apache.commons.collections.MapUtils {
    }

    /**
     * CollectionUtils 工具类
     */
    class CollectionUtil extends org.apache.commons.collections.CollectionUtils {
    }

    /**
     * ArrayUtils 工具类
     */
    class ArrayUtil extends org.apache.commons.lang3.ArrayUtils {
    }

}
