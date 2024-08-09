package com.github.mengweijin.system.domain.vo;

import com.github.mengweijin.system.domain.entity.LogError;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * LogError VO
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class LogErrorVO extends LogError {

}
