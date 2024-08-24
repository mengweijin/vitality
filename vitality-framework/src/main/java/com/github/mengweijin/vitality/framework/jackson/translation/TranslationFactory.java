package com.github.mengweijin.vitality.framework.jackson.translation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.mengweijin.vitality.framework.jackson.translation.strategy.ITranslationStrategy;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author mengweijin
 */
@Slf4j
@Component
@AllArgsConstructor
public class TranslationFactory {

    private ObjectMapper objectMapper;

    private List<ITranslationStrategy<?>> translationList;

    public static final Map<ETranslateType, ITranslationStrategy<?>> TRANSLATION_STRATEGY_MAP = new ConcurrentHashMap<>();

    @SuppressWarnings({"unused"})
    @PostConstruct
    public void init() {
        for (ITranslationStrategy<?> strategy : translationList) {
            if(strategy.translateType() == null) {
                log.warn("{} : was not set translationType!", strategy.getClass().getName());
            }
            TRANSLATION_STRATEGY_MAP.put(strategy.translateType(), strategy);
        }

        // 设置 Bean 序列化修改器
        objectMapper.setSerializerFactory(objectMapper.getSerializerFactory().withSerializerModifier(new TranslationBeanSerializerModifier()));
    }

    public static ITranslationStrategy<?> getTranslation(ETranslateType translateType) {
        return TRANSLATION_STRATEGY_MAP.get(translateType);
    }
}
