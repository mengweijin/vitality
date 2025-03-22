package com.github.mengweijin.vita.system.domain.vo;

import com.github.mengweijin.vita.framework.jackson.sensitive.ESensitiveStrategy;
import com.github.mengweijin.vita.framework.jackson.sensitive.Sensitive;
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
public class UserVO extends User {

    /**
     * 身份证号
     */
    @Sensitive(strategy = ESensitiveStrategy.ID_CARD)
    private String idCard;

    /**
     * 电子邮箱
     */
    @Sensitive(strategy = ESensitiveStrategy.EMAIL)
    private String email;

    /**
     * 移动电话
     */
    @Sensitive(strategy = ESensitiveStrategy.MOBILE_PHONE)
    private String mobile;

    /**
     * TOTP 动态口令
     */
    @Sensitive(strategy = ESensitiveStrategy.DEFAULT)
    private String totp;

    @Translation(translateType = ETranslateType.DEPT_ID_TO_NAME, field = "deptId")
    private String deptName;

    @Translation(translateType = ETranslateType.USER_ID_TO_AVATAR, field = "id")
    private String avatar;
}
