package com.github.mengweijin.framework.jackson.translation.strategy;

import com.github.mengweijin.framework.jackson.translation.ETranslateType;
import com.github.mengweijin.framework.jackson.translation.Translation;

/**
 * 数据翻译转换接口
 *
 * @author mengweijin
 * @since 2023/5/20
 */
public interface ITranslationStrategy<T> {

    /**
     * 支持的翻译类型
     * @return ETranslateType
     */
    ETranslateType translateType();

    /**
     * 数据翻译
     *
     * @param value 需要被翻译的值
     * @return 返回转换后的值
     */
    T translation(Object value, Translation translation);

}
