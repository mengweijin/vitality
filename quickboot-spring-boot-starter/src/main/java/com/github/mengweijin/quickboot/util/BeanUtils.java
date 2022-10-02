package com.github.mengweijin.quickboot.util;

import cn.hutool.core.lang.func.LambdaUtil;
import com.github.mengweijin.quickboot.mybatis.entity.BaseEntity;
import org.springframework.beans.BeansException;

/**
 * @author mengweijin
 * @date 2022/10/2
 */
public class BeanUtils extends org.springframework.beans.BeanUtils {

    public static <E extends BaseEntity> void copyPropertiesIgnoreBaseEntityProperties(E source, Object target) throws BeansException {
        String idFieldName = LambdaUtil.getFieldName(BaseEntity::getId);
        String createByFieldName = LambdaUtil.getFieldName(BaseEntity::getCreateBy);
        String createTimeFieldName = LambdaUtil.getFieldName(BaseEntity::getCreateTime);
        String updateByFieldName = LambdaUtil.getFieldName(BaseEntity::getUpdateBy);
        String updateTimeFieldName = LambdaUtil.getFieldName(BaseEntity::getUpdateTime);
        copyProperties(source, target, idFieldName, createByFieldName, createTimeFieldName, updateByFieldName, updateTimeFieldName);
    }
}
