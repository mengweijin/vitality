package com.github.mengweijin.quickboot.framework.util;

import com.alibaba.testable.core.annotation.MockInvoke;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @author mengweijin
 * @date 2021/12/17
 */
class ServletUtilsTest {

    public static class Mock {

        @MockInvoke(targetClass = RequestContextHolder.class)
        private RequestAttributes getRequestAttributes() {
            MockHttpServletRequest mockRequest = new MockHttpServletRequest();
            mockRequest.setParameter("normal", "normal");
            mockRequest.setParameter("blank", "");
            mockRequest.setParameter("space", " ");
            mockRequest.setParameter("integer", "1");
            return new ServletRequestAttributes(mockRequest);
        }
    }

    @Test
    void getParameter() {
        Assertions.assertEquals("normal", ServletUtils.getParameter("normal", "default"));
        Assertions.assertEquals("default", ServletUtils.getParameter("blank", "default"));
        Assertions.assertEquals("default", ServletUtils.getParameter("space", "default"));
        Assertions.assertEquals("default", ServletUtils.getParameter("notExist", "default"));
    }

    @Test
    void getParameterToInt() {
        Assertions.assertEquals(1, ServletUtils.getParameterToInt("integer"));
        Assertions.assertNull(ServletUtils.getParameterToInt("blank"));
        Assertions.assertThrows(NumberFormatException.class, () -> {
            ServletUtils.getParameterToInt("normal");
        });
    }
}