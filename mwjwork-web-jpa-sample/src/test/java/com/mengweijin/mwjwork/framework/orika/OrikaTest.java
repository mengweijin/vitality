package com.mengweijin.mwjwork.framework.orika;

import cn.hutool.core.date.DatePattern;
import com.mengweijin.mwjwork.sample.MwjworkSampleApplication;
import ma.glasnost.orika.MapperFacade;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.format.DateTimeFormatter;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MwjworkSampleApplication.class)
public class OrikaTest {

    @Autowired
    private MapperFacade mapperFacade;

    @Test
    public void orikaTest() {
        BeanA beanA = new BeanA(
                "张三",
                12,
                "abc@qq.com",
                "2020-01-01",
                "12:12:12",
                "2020-01-01T12:12:12",
                "2020-01-01",
                "2020-01-01 12:12",
                "2020-01-01 12:12:12.123",
                "2020-01-01 12:12:12",
                "2020年01月01日"
                );
        BeanB beanB = mapperFacade.map(beanA, BeanB.class);
        Assert.assertEquals(beanA.getNameA(), beanB.getNameB());
        Assert.assertNull(beanB.getAgeB());
        Assert.assertEquals(beanA.getMail(), beanB.getMail());
        Assert.assertEquals(beanA.getLocalDate(), beanB.getLocalDate().format(DateTimeFormatter.ISO_LOCAL_DATE));
        Assert.assertEquals(beanA.getLocalTime(), beanB.getLocalTime().format(DateTimeFormatter.ISO_LOCAL_TIME));
        Assert.assertEquals(beanA.getLocalDateTime(), beanB.getLocalDateTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));

        Assert.assertEquals(beanA.getNormDate(), DatePattern.NORM_DATE_FORMAT.format(beanB.getNormDate()));
        Assert.assertEquals(beanA.getNormDateTimeMinute(), DatePattern.NORM_DATETIME_MINUTE_FORMAT.format(beanB.getNormDateTimeMinute()));
        Assert.assertEquals(beanA.getNormDateTimeMs(), DatePattern.NORM_DATETIME_MS_FORMAT.format(beanB.getNormDateTimeMs()));
        Assert.assertEquals(beanA.getNormDateTime(), DatePattern.NORM_DATETIME_FORMAT.format(beanB.getNormDateTime()));
        Assert.assertEquals(beanA.getChineseDate(), DatePattern.CHINESE_DATE_FORMAT.format(beanB.getChineseDate()));

    }

}