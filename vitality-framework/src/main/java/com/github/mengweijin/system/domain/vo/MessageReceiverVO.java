package com.github.mengweijin.system.domain.vo;

import com.github.mengweijin.system.domain.entity.MessageReceiver;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * MessageReceiver VO
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class MessageReceiverVO extends MessageReceiver {

}
