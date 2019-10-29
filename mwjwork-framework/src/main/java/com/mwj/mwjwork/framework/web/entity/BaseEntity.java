package com.mwj.mwjwork.framework.web.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.mwj.mwjwork.common.util.date.DateFormatUtil;
import com.mwj.mwjwork.framework.idgenerator.TimestampIdGenerator;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author Meng Wei Jin
 * @description
 * @date Create in 2019-07-28 15:18
 **/
@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

    private static final String ID_GENERATOR_NAME = "ID_GENERATOR";

    private static final String STRATEGY_TIMESTAMP = "com.mwj.mwjwork.framework.idgenerator.TimestampIdGenerator";

    private static final String STRATEGY_SNOWFLAKE = "com.mwj.mwjwork.framework.idgenerator.SnowflakeIdGenerator";

    /**
     * 自动生成主键
     * JsonSerialize：JavaScript 无法处理 Java 的长整型 Long 导致精度丢失，具体表现为主键最后两位永远为 0
     */
    @Id
    @GenericGenerator(name = ID_GENERATOR_NAME, strategy = STRATEGY_TIMESTAMP)
    @GeneratedValue(generator = ID_GENERATOR_NAME)
    @JsonSerialize(using = ToStringSerializer.class)
    @Column(name = "ID")
    protected Long id;

    @CreatedDate
    @DateTimeFormat(pattern = DateFormatUtil.YYYY_MM_DD_HH_MM_SS)
    @JsonFormat(pattern = DateFormatUtil.YYYY_MM_DD_HH_MM_SS)
    @Column(name = "CREATE_TIME")
    protected LocalDateTime createTime;

    @LastModifiedDate
    @DateTimeFormat(pattern = DateFormatUtil.YYYY_MM_DD_HH_MM_SS)
    @JsonFormat(pattern = DateFormatUtil.YYYY_MM_DD_HH_MM_SS)
    @Column(name = "UPDATE_TIME")
    protected LocalDateTime updateTime;

    @CreatedBy
    @Column(name = "CREATE_BY")
    protected String createBy;

    @LastModifiedBy
    @Column(name = "UPDATE_BY")
    protected String updateBy;

}
