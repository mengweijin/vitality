package com.github.mengweijin.vitality.system.domain.vo;

import com.github.mengweijin.vitality.system.domain.entity.LogOperation;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * LogOperation VO
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class LogOperationVO extends LogOperation {

}
