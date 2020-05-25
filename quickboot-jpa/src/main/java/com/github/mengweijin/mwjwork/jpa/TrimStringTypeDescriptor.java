package com.github.mengweijin.mwjwork.jpa;

import org.hibernate.engine.jdbc.CharacterStream;
import org.hibernate.engine.jdbc.internal.CharacterStreamImpl;
import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.AbstractTypeDescriptor;
import org.hibernate.type.descriptor.java.DataHelper;

import java.io.Reader;
import java.io.StringReader;
import java.sql.Clob;

/**
 * Descriptor for {@link String} handling.
 *
 * 情景：当数据库字段为 char(10) 类型时，使用jpa插入数据长度不够 10 时，数据库会自动追加空格；
 * 当再次使用jpa查询数据时，返回到前台的该char字段（在java中使用String接收）就会多出一些空格，需要去除。
 * 一般除非字段长度总是固定的，数据库才可以考虑使用char类型，否则请使用varchar类型，这样就可以避免这个问题。
 *
 * 如何使用：在java实体类对应的字段方法或属性添加 @Type(type = TrimStringType.TYPE)
 * 或者 @Type(type = "com.mwj.mwjwork.framework.jpa.TrimStringType")
 *
 * 代码参考自 org.hibernate.type.descriptor.java.StringTypeDescriptor.java
 *
 * @author Meng Wei Jin
 * @date Create in 2019-10-29 20:36
 **/
public class TrimStringTypeDescriptor extends AbstractTypeDescriptor<String> {

    public static final TrimStringTypeDescriptor INSTANCE = new TrimStringTypeDescriptor();

    public TrimStringTypeDescriptor() {
        super(String.class);
    }

    @Override
    public String toString(String value) {
        return value;
    }

    @Override
    public String fromString(String string) {
        return string;
    }

    @Override
    @SuppressWarnings({"unchecked"})
    public <X> X unwrap(String value, Class<X> type, WrapperOptions options) {
        if (value == null) {
            return null;
        }
        if (String.class.isAssignableFrom(type)) {
            return (X) value;
        }
        if (Reader.class.isAssignableFrom(type)) {
            return (X) new StringReader(value);
        }
        if (CharacterStream.class.isAssignableFrom(type)) {
            return (X) new CharacterStreamImpl(value);
        }
        // Since NClob extends Clob, we need to check if type is an NClob
        // before checking if type is a Clob. That will ensure that
        // the correct type is returned.
        if (DataHelper.isNClob(type)) {
            return (X) options.getLobCreator().createNClob(value);
        }
        if (Clob.class.isAssignableFrom(type)) {
            return (X) options.getLobCreator().createClob(value);
        }

        throw unknownUnwrap(type);
    }

    @Override
    public <X> String wrap(X value, WrapperOptions options) {
        if (value == null) {
            return null;
        }
        if (String.class.isInstance(value)) {
            // 前后去空格
            return ((String) value).trim();
        }
        if (Reader.class.isInstance(value)) {
            return DataHelper.extractString((Reader) value);
        }
        if (Clob.class.isInstance(value)) {
            return DataHelper.extractString((Clob) value);
        }

        throw unknownWrap(value.getClass());
    }
}
