package com.github.mengweijin.vitality.framework.mybatis.entity;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 * @author Meng Wei Jin
 * @since 2019-07-28
 **/
@Data
@EqualsAndHashCode(callSuper = true)
public abstract class ExpandBaseEntity extends BaseEntity {

    /**
     * 逻辑删除。不是每张表都需要
     * logically deleted
     */
    @TableLogic
    protected String deleted;

    /**
     * 乐观锁。不是每张表都需要
     */
    @Version
    protected Long version;
}
