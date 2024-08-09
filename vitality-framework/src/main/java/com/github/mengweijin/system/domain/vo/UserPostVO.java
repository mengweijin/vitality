package com.github.mengweijin.system.domain.vo;

import com.github.mengweijin.system.domain.entity.UserPost;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * UserPost VO
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class UserPostVO extends UserPost {

}
