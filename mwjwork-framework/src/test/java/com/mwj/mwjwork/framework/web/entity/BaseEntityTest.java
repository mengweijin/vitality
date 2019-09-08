package com.mwj.mwjwork.framework.web.entity;

import com.mwj.mwjwork.framework.idgenerator.TimestampIdGenerator;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

@Slf4j
public class BaseEntityTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void className() {
        String expectedPath = "com.mwj.mwjwork.framework.idgenerator.TimestampIdGenerator";
        String actualPath = TimestampIdGenerator.class.getName();
        log.debug(actualPath);
        assertEquals(expectedPath, actualPath);
    }
}