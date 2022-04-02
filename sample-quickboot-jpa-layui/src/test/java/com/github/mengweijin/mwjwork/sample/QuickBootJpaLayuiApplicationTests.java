package com.github.mengweijin.mwjwork.sample;

import com.github.mengweijin.quickboot.framework.util.SpringUtils;
import com.github.mengweijin.quickboot.sample.QuickBootJpaLayuiApplication;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Junit5 不需要添加注解：@RunWith(SpringRunner.class)
 */
@SpringBootTest(classes = QuickBootJpaLayuiApplication.class)
public class QuickBootJpaLayuiApplicationTests {

    @Test
    public void contextLoads() {
        Assertions.assertNotNull(SpringUtils.getApplicationContext());
    }

}
