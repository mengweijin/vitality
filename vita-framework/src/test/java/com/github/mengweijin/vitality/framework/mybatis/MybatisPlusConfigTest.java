package com.github.mengweijin.vitality.framework.mybatis;

import com.baomidou.mybatisplus.core.incrementer.DefaultIdentifierGenerator;
import org.dromara.hutool.core.net.NetUtil;
import org.dromara.hutool.core.text.StrUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author mengweijin
 * @since 2024/12/3
 */
class MybatisPlusConfigTest {

    @Test
    void idGenerator() {
        Long id = new DefaultIdentifierGenerator(NetUtil.getLocalhostV4()).nextId(null);
        String strId = String.valueOf(id);
        Assertions.assertEquals(19, strId.length());

        String subId = StrUtil.subPre(strId, strId.length() - 2);
        Assertions.assertEquals(17, subId.length());
    }
}