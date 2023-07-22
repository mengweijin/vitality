package com.github.mengweijin.vitality.system.dto;

import com.github.mengweijin.vitality.system.entity.NoticeDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 通知记录表 DTO
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class NoticeDTO extends NoticeDO {

    private String receivedByName;

}
