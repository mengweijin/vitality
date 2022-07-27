package com.github.mengweijin.mybatisplus.demo;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import org.junit.jupiter.api.Test;

/**
 * @author mengweijin
 * @date 2022/4/7
 */
public class DemoTest {

    @Test
    void generateUuid() {
        System.out.println(StrUtil.sub(IdUtil.fastSimpleUUID(), 0, 15));
    }

    @Test
    void test() {
        System.out.println("哈哈".length());
    }
}
