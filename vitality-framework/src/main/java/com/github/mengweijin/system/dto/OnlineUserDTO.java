package com.github.mengweijin.system.dto;

import cn.dev33.satoken.session.SaSession;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 在线用户 DTO
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class OnlineUserDTO extends SaSession {

}
