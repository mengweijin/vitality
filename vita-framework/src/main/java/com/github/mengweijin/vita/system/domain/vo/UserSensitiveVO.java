package com.github.mengweijin.vita.system.domain.vo;

import com.github.mengweijin.vita.framework.jackson.translation.ETranslateType;
import com.github.mengweijin.vita.framework.jackson.translation.Translation;
import com.github.mengweijin.vita.system.domain.entity.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * User VO
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserSensitiveVO extends User {

    @Translation(translateType = ETranslateType.DEPT_ID_TO_NAME, field = "deptId")
    private String deptName;

    @Translation(translateType = ETranslateType.USER_ID_TO_AVATAR, field = "id")
    private String avatar;
}
