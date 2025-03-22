package com.github.mengweijin.vitality;

import lombok.extern.slf4j.Slf4j;
import org.dromara.hutool.core.data.id.IdUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author mengweijin
 */
@Slf4j
public class IdGeneratorTest {

    @Test
    public void generateId() {
        for (int i = 0; i < 10; i++) {
            long id = IdUtil.getSnowflakeNextId();
            System.out.println(id);
            Assertions.assertTrue(id > 0);
        }
    }
}
