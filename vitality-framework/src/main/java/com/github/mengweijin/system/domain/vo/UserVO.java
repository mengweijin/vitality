package com.github.mengweijin.system.domain.vo;

import com.github.mengweijin.system.domain.entity.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

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
@Accessors(chain = true)
public class UserVO extends User {

}
