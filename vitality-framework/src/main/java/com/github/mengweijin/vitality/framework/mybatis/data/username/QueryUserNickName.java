package com.github.mengweijin.vitality.framework.mybatis.data.username;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Not useful.
 * Can only be used in *Mapper.java. More detail refer to {@link UserNickNameInnerInterceptor}
 * @author mengweijin
 * @date 2022/11/20
 */
@Deprecated
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface QueryUserNickName {

}
