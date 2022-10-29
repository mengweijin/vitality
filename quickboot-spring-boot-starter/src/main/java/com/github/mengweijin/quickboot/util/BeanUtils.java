package com.github.mengweijin.quickboot.util;

import cn.hutool.core.lang.func.LambdaUtil;
import com.github.mengweijin.quickboot.mybatis.entity.BaseEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mengweijin
 * @date 2022/10/2
 */
public class BeanUtils extends org.springframework.beans.BeanUtils {

    public static <E extends BaseEntity> void copyPropertiesIgnoreBaseEntityProperties(E source, Object target) {
        String idFieldName = LambdaUtil.getFieldName(BaseEntity::getId);
        String createByFieldName = LambdaUtil.getFieldName(BaseEntity::getCreateBy);
        String createTimeFieldName = LambdaUtil.getFieldName(BaseEntity::getCreateTime);
        String updateByFieldName = LambdaUtil.getFieldName(BaseEntity::getUpdateBy);
        String updateTimeFieldName = LambdaUtil.getFieldName(BaseEntity::getUpdateTime);
        copyProperties(source, target, idFieldName, createByFieldName, createTimeFieldName, updateByFieldName, updateTimeFieldName);
    }

    public static <E> List<E> copyList(List<Object> sourceList, Class<E> cls) {
        List<E> list = new ArrayList<>();
        if(sourceList != null) {
            try {
                for (Object obj : sourceList) {
                    E instance = cls.newInstance();
                    copyProperties(obj, instance);
                    list.add(instance);
                }
            } catch (InstantiationException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return list;
    }
}
