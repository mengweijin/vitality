package com.github.mengweijin.system.dto;

import com.github.mengweijin.system.entity.PostDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 岗位管理表 DTO
 *
 * @author mengweijin
 * @since 2023-06-09
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PostDTO extends PostDO {

    private Long userCount;

}
