package com.github.mengweijin.vitality.system.domain.vo;

import com.github.mengweijin.vitality.system.domain.entity.Config;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * Config VO
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class ConfigVO extends Config {

}
