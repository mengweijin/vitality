package com.github.mengweijin.vitality.system.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.github.mengweijin.vitality.system.entity.VtlMessage;
import lombok.Data;
import org.dromara.hutool.core.date.DatePattern;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author mengweijin
 * @date 2023/5/21
 */
@Data
public class VtlMessageHeaderMenuDataDTO {

    private String id;

    /**
     * 存放：通知类型
     */
    private String title;

    private List<MessageItemDataVO> children;

    @Data
    public static class MessageItemDataVO {

        @JsonSerialize(using = ToStringSerializer.class)
        private Long id;

        private String avatar;

        private String title;

        private String context;

        private String form;

        @JsonFormat(pattern = DatePattern.NORM_DATE_PATTERN)
        private LocalDateTime time;

        private String urlLink;

        public MessageItemDataVO(VtlMessage message) {
            this.id = message.getId();
            this.avatar = message.getAvatar();
            this.title = message.getTitle();
            this.context = message.getDescription();
            this.form = message.getUpdateByName();
            this.time = message.getUpdateTime();
            this.urlLink = message.getUrlLink();
        }

    }
}
