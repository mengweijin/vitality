package com.github.mengweijin.vitality.validator;

import com.github.mengweijin.vitality.validator.annotation.CharsetLength;
import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.LoggerFactory;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.invoke.MethodHandles;
import java.nio.charset.Charset;

/**
 * @author Meng Wei Jin
 * 自定义validator, 验证String类型长度，避免出现以下问题：
 * 当数据库长度限制为10字节，数据库编码为UTF-8(一个汉字占三个字节)，此时数据库只能存储最多3个汉字，而@Length注解只会判断java中字符串的长度，
 * 当传入字符串：“四个汉字”，@Length(max=10)注解判断长度为4，校验通过，而数据库中，四个汉字的字节长度为：3*4=12，此时插入数据会发生SQL执行异常，
 * 所以需要自定义这个注解，自定义validator.
 *
 * 出现以上问题的原因是因为在创建表字段时长度用varchar(20 byte), 添加了byte表名该字段是以字节长度计算，一般情况下要严格避免使用byte,
 * 可以直接使用varchar(20)来定义字段长度，这样就是以字符长度来计算，就不会出现以上问题。
 * @date Create in 2019-10-21 21:06
 **/
public class CharsetLengthValidator implements ConstraintValidator<CharsetLength, CharSequence> {

    private static final Log LOG = LoggerFactory.make(MethodHandles.lookup());

    private int min;

    private int max;

    private String charset;

    @Override
    public void initialize(CharsetLength parameters) {
        min = parameters.min();
        max = parameters.max();
        charset = parameters.charset();
        validateParameters();
    }

    @Override
    public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        int length = value.toString().getBytes(Charset.forName(charset)).length;
        return length >= min && length <= max;
    }

    private void validateParameters() {
        if (min < 0) {
            throw LOG.getMinCannotBeNegativeException();
        }
        if (max < 0) {
            throw LOG.getMaxCannotBeNegativeException();
        }
        if (max < min) {
            throw LOG.getLengthCannotBeNegativeException();
        }
    }

}
