package com.github.mengweijin.vitality.system.domain.bo;

import com.github.mengweijin.vitality.framework.constant.ConstRegex;
import com.github.mengweijin.vitality.framework.mybatis.entity.BaseEntity;
import com.github.mengweijin.vitality.framework.validator.annotation.BusinessCheck;
import com.github.mengweijin.vitality.framework.validator.group.Group;
import com.github.mengweijin.vitality.system.validator.rule.UsernameDuplicateCheckRule;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 *
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserBO extends BaseEntity {

    /**
     * 部门ID
     */
    private Long deptId;

    /**
     * 用户登录名（字母数字下划线）
     */
    @NotBlank(groups = {Group.Create.class})
    @Pattern(regexp = ConstRegex.GENERAL, message = "{user.username.pattern}")
    @BusinessCheck(groups = {Group.Create.class}, checkRule = UsernameDuplicateCheckRule.class)
    private String username;

    /**
     * 用户昵称
     */
    @NotBlank
    private String nickname;

    /**
     * 登录密码
     */
    @NotBlank(groups = {Group.Create.class})
    @Pattern(groups = {Group.Create.class}, regexp = ConstRegex.PASSWORD, message = "{user.password.pattern}")
    private String password;

    /**
     * 身份证号
     */
    @Pattern(regexp = ConstRegex.CITIZEN_ID, message = "{user.idCard.pattern}")
    private String idCard;

    /**
     * 性别。关联数据字典：user_gender
     */
    private String gender;

    /**
     * 电子邮箱
     */
    @Email
    private String email;

    /**
     * 移动电话
     */
    @Pattern(regexp = ConstRegex.MOBILE, message = "{user.mobile.pattern}")
    private String mobile;

    /**
     * 是否禁用。[Y, N]
     */
    private String disabled;

    /**
     * 备注
     */
    private String remark;

}
