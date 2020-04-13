package com.mengweijin.video.downloader.web.entity;

import com.mengweijin.mwjwork.jpa.BaseEntity;
import com.mengweijin.video.downloader.validator.VideoUrlValidate;
import com.mengweijin.video.downloader.web.enums.TaskStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotEmpty;

/**
 * @author mengweijin
 */
@Data
@Accessors(chain = true)
@Entity
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Task extends BaseEntity {

    @Column(length = 255, nullable = true)
    private String name;

    /**
     * Example:
     * 广场舞地盘: http://www.gcwdp.com/zxgcw/144199.html
     * 播视网: http://www.boosj.com/8494541.html
     */
    @NotEmpty
    @VideoUrlValidate
    @Column(length = 255, nullable = false)
    private String url;

    @Enumerated(value = EnumType.STRING)
    @Column
    private TaskStatus status;

    @Column(length = 255)
    private String attachment;

    @Column(length = 255)
    private String attachmentName;

    @Column(length = Integer.MAX_VALUE)
    private String errorMessage;
}
