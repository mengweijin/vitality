package com.github.mengweijin.vitality.system.domain.vo;

import com.github.mengweijin.vitality.system.domain.entity.Role;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * Role VO
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class RoleVO extends Role {

}
