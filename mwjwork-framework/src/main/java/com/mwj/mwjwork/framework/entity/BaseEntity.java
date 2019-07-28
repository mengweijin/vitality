package com.mwj.mwjwork.framework.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mwj.mwjwork.common.util.date.DateFormatUtil;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
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

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {

    private static final String ID_GENERATOR_NAME = "ID_GENERATOR";

    @Id
    @GenericGenerator(name = ID_GENERATOR_NAME, strategy = "com.mwj.mwjwork.framework.idgenerator.TimestampIdGenerator")
    @GeneratedValue(generator = ID_GENERATOR_NAME)
    @Getter
    @Setter
    protected Long id;

    @CreatedDate
    @DateTimeFormat(pattern = DateFormatUtil.YYYY_MM_DD_HH_MM_SS)
    @JsonFormat(pattern = DateFormatUtil.YYYY_MM_DD_HH_MM_SS)
    @Getter
    @Setter
    protected LocalDateTime createTime;

    @LastModifiedDate
    @DateTimeFormat(pattern = DateFormatUtil.YYYY_MM_DD_HH_MM_SS)
    @JsonFormat(pattern = DateFormatUtil.YYYY_MM_DD_HH_MM_SS)
    @Getter
    @Setter
    protected LocalDateTime updateTime;


}
