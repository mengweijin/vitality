package com.github.mengweijin.quickboot.jpa;

import org.hibernate.dialect.Dialect;
import org.hibernate.type.AbstractSingleColumnStandardBasicType;
import org.hibernate.type.DiscriminatorType;
import org.hibernate.type.descriptor.sql.VarcharTypeDescriptor;

/**
 * A type that maps between {@link java.sql.Types#VARCHAR VARCHAR} and {@link String}
 * <p>
 * 情景：当数据库字段为 char(10) 类型时，使用jpa插入数据长度不够 10 时，数据库会自动追加空格；
 * 当再次使用jpa查询数据时，返回到前台的该char字段（在java中使用String接收）就会多出一些空格，需要去除。
 * 一般除非字段长度总是固定的，数据库才可以考虑使用char类型，否则请使用varchar类型，这样就可以避免这个问题。
 * <p>
 * 如何使用：在java实体类对应的字段方法或属性添加 @Type(type = TrimStringType.TYPE)
 * 或者 @Type(type = "com.github.mengweijin.quickboot.jpa.TrimStringType")
 * <p>
 * 代码参考自 org.hibernate.type.StringType.java
 *
 * @author Meng Wei Jin
 * @date Create in 2019-10-29 20:40
 **/
public class TrimStringType extends AbstractSingleColumnStandardBasicType<String> implements DiscriminatorType<String> {

    private static final String TYPE = "com.github.mengweijin.quickboot.jpa.TrimStringType";

    public static final TrimStringType INSTANCE = new TrimStringType();

    public TrimStringType() {
        super(VarcharTypeDescriptor.INSTANCE, TrimStringTypeDescriptor.INSTANCE);
    }

    @Override
    public String getName() {
        return "trimString";
    }

    @Override
    protected boolean registerUnderJavaType() {
        return true;
    }

    @Override
    public String objectToSQLString(String value, Dialect dialect) throws Exception {
        return '\'' + value + '\'';
    }

    @Override
    public String stringToObject(String xml) throws Exception {
        return xml;
    }

    @Override
    public String toString(String value) {
        return value;
    }
}
