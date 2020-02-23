package com.mengweijin.mwjwork.common.util.lambda;

import lombok.Data;
import org.junit.Test;

import static org.junit.Assert.*;

public class LambdaWrapperTest {

    @Test
    public void getFieldName() {
        assertEquals("userId", LambdaWrapper.getFieldName(User::getUserId));
        assertEquals("userName", LambdaWrapper.getFieldName(User::getUserName));

        assertEquals("userId", LambdaWrapper.getFieldName(User::setUserId));
        assertEquals("userName", LambdaWrapper.getFieldName(User::setUserName));

    }


    @Data
    class User {
        private Long userId;

        private String userName;
    }
}