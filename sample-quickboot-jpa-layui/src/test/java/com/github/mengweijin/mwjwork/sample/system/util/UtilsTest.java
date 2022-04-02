package com.github.mengweijin.mwjwork.sample.system.util;

import com.github.mengweijin.quickboot.jpa.BaseEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UtilsTest {

    @Test
    public void test(){
        Assertions.assertEquals("com.github.mengweijin.quickboot.jpa.BaseEntity", BaseEntity.class.getName());
    }
}
