package com.github.mengweijin.mwjwork.jpa;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

/**
 * 注释：@DateTimeFormat(pattern = DateFormatUtil.YYYY_MM_DD_HH_MM_SS)
 * 注释：@JsonFormat(pattern = DateFormatUtil.YYYY_MM_DD_HH_MM_SS)
 * 注释：启用UTC时间就不需要了
 *
 * 格式化日期相关配置（不知道为什么不生效）：
 * # GMT+8, UTC
 * spring.jackson.time-zone=GMT+8
 * spring.jackson.date-format=yyyy-MM-dd HH:mm:ss
 *
 * @author Meng Wei Jin
 * @description
 * @date Create in 2019-07-28 15:18
 **/
@Data
@DynamicInsert
@DynamicUpdate
@SelectBeforeUpdate
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

    private static final String ID_GENERATOR_NAME = "ID_GENERATOR";

    private static final String STRATEGY_TIMESTAMP = "com.github.mengweijin.mwjwork.jpa.idgenerator.TimestampIdGenerator";

    private static final String STRATEGY_SNOWFLAKE = "com.github.mengweijin.mwjwork.jpa.idgenerator.SnowflakeIdGenerator";

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
    //@DateTimeFormat(pattern = DateFormatUtil.YYYY_MM_DD_HH_MM_SS)
    //@JsonFormat(pattern = DateFormatUtil.YYYY_MM_DD_HH_MM_SS)
    @Column(name = "CREATE_TIME")
    protected LocalDateTime createTime;

    @LastModifiedDate
    //@DateTimeFormat(pattern = DateFormatUtil.YYYY_MM_DD_HH_MM_SS)
    //@JsonFormat(pattern = DateFormatUtil.YYYY_MM_DD_HH_MM_SS)
    @Column(name = "UPDATE_TIME")
    protected LocalDateTime updateTime;

    @CreatedBy
    @Column(name = "CREATE_BY")
    protected String createBy;

    @LastModifiedBy
    @Column(name = "UPDATE_BY")
    protected String updateBy;

    /**
     * 删除标记 --系统只做逻辑删除
     */
    @Column
    protected Integer deleted;

}
