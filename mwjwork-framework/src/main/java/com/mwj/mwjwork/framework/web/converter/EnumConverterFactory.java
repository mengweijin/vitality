package com.mwj.mwjwork.framework.web.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

import java.util.Map;
import java.util.WeakHashMap;

/**
 * @author Meng Wei Jin
 * @date Create in 2019-10-28 22:50
 **/
public class EnumConverterFactory implements ConverterFactory<String, BaseEnum> {

    private static final Map<Class, Converter> converterMap = new WeakHashMap<>();

    @Override
    public <T extends BaseEnum> Converter<String, T> getConverter(Class<T> targetType) {
        Converter converter = converterMap.get(targetType);
        if(converter == null) {
            converter = new StringToEnumConverter<>(targetType);
            converterMap.put(targetType, converter);
        }
        return converter;
    }

    class StringToEnumConverter<T extends BaseEnum> implements Converter<String, T> {

        private final Class<T> enumType;

        private StringToEnumConverter(Class<T> enumType) {
            this.enumType = enumType;
        }

        @Override
        public T convert(String source) {
            for(T enumObj: enumType.getEnumConstants()){
                if(enumObj.toString().equals(source)){
                    return enumObj;
                }
            }
            return null;
        }

    }

}
