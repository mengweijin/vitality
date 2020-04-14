package com.mengweijin.app.videodownloader.entity;

import com.mengweijin.app.videodownloader.enums.TaskStatus;
import com.mengweijin.app.videodownloader.validator.VideoUrlValidate;
import com.mengweijin.mwjwork.jpa.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

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
@Entity(name = "video_downloader_task")
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Task extends BaseEntity {

    @Length(max = 60)
    @Column(length = 60, nullable = true)
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
    @Column(length = 10)
    private TaskStatus status;

    @Column(length = 255)
    private String attachmentPath;

    @Column(length = 255)
    private String attachmentName;

    @Column(length = Integer.MAX_VALUE)
    private String errorMessage;
}
