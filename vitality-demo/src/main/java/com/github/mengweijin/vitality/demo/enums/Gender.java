package com.github.mengweijin.vitality.demo.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

/**
 * 1. 使用 @JsonFormat(shape = JsonFormat.Shape.OBJECT) 注解来让枚举按照类（对象）的格式进行序列化。
 * 2. 使用 @JsonCreator 注解标记一个通过枚举码来查询枚举的方法，Jackson 会使用这个有参构造器进行反序列化。
 * <p>
 * 在 User 中使用 @JsonProperty 来指定反序列化时接收的属性名
 *
 * @author mengweijin
 */
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Gender {

    MALE(1, "男"), FEMALE(2, "女"), OTHER(3, "其他");

    private int code;
    private String value;

    Gender(int code, String value) {
        this.code = code;
        this.value = value;
    }

    /**
     * 使用 @JsonCreator 注解标记一个通过枚举码来查询枚举的方法，Jackson 会使用这个有参构造器进行反序列化。
     * 数据库保存的是什么，这里的参数就要用什么来做。
     * 比如：这个例子中使用的是 Gender.name() 属性（MALE, FEMALE, OTHER）保存在数据库中的。
     *
     * @param name MALE, FEMALE, OTHER
     * @return Gender
     */
    @JsonCreator
    public static Gender ofName(String name) {
        for (Gender gender : Gender.values()) {
            if (gender.name().equalsIgnoreCase(name)) {
                return gender;
            }
        }
        return Gender.OTHER;
    }

}
