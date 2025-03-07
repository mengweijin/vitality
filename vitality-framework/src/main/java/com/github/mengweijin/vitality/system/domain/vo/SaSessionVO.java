package com.github.mengweijin.vitality.system.domain.vo;

import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.session.TokenSign;
import com.github.mengweijin.vitality.framework.jackson.sensitive.ESensitiveStrategy;
import com.github.mengweijin.vitality.framework.jackson.sensitive.Sensitive;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author mengweijin
 * @since 2024/11/10
 */
@Data
@NoArgsConstructor
public class SaSessionVO {

    /**
     * 此 SaSession 的 id
     */
    private String id;

    /**
     * 此 SaSession 的 类型
     */
    private String type;

    /**
     * 所属 loginType
     */
    private String loginType;

    /**
     * 所属 loginId （当此 SaSession 属于 Account-Session 时，此值有效）
     */
    private Object loginId;

    /**
     * 所属 Token （当此 SaSession 属于 Token-Session 时，此值有效）
     */
    @Sensitive(strategy = ESensitiveStrategy.DEFAULT)
    private String token;

    /**
     * 此 SaSession 的创建时间（13位时间戳）
     */
    private long createTime;

    /**
     * 此 Session 绑定的 Token 签名列表
     */
    private List<TokenSignVO> tokenSignList = new ArrayList<>();

    public SaSessionVO(SaSession session) {
        this.id = session.getId();
        this.type = session.getType();
        this.loginType = session.getLoginType();
        this.loginId = session.getLoginId();
        this.token = session.getToken();
        this.createTime = session.getCreateTime();
        this.tokenSignList = this.copyTokenSignList(session.getTokenSignList());
    }

    private List<TokenSignVO> copyTokenSignList(List<TokenSign> list) {
        return list.stream().map(TokenSignVO::new).collect(Collectors.toList());
    }
}
