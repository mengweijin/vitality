package com.github.mengweijin.vitality.system.dto;

import com.github.mengweijin.vitality.system.entity.VtlMessage;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统消息（通知，待办）记录表 DTO
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class VtlMessageDTO extends VtlMessage {

}
