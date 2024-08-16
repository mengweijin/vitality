package com.github.mengweijin.framework.satoken;

import cn.dev33.satoken.listener.SaTokenListener;
import cn.dev33.satoken.listener.SaTokenListenerForLog;
import cn.dev33.satoken.stp.SaLoginModel;
import com.github.mengweijin.framework.util.ServletUtils;
import com.github.mengweijin.system.enums.ELoginType;
import com.github.mengweijin.system.service.LogLoginService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Refer to {@link SaTokenListenerForLog}. It can only be reached if the operation succeeds.
 * @author mengweijin
 * @since 2023/7/8
 */
@Component
@AllArgsConstructor
public class SaTokenListenerImpl implements SaTokenListener {

    private LogLoginService logLoginService;

    /** 每次登录时触发 */
    @Override
    public void doLogin(String loginType, Object loginId, String tokenValue, SaLoginModel loginModel) {
        logLoginService.addLoginLogAsync((String) loginId, ELoginType.LOGIN, null, ServletUtils.getRequest());
    }

    /** 每次注销时触发 */
    @Override
    public void doLogout(String loginType, Object loginId, String tokenValue) {
        logLoginService.addLoginLogAsync((String) loginId, ELoginType.LOGOUT, null, ServletUtils.getRequest());
    }

    /** 每次被踢下线时触发 */
    @Override
    public void doKickout(String loginType, Object loginId, String tokenValue) {
        logLoginService.addLoginLogAsync((String) loginId, ELoginType.KICK_OUT, null, ServletUtils.getRequest());
    }

    /** 每次被顶下线时触发 */
    @Override
    public void doReplaced(String loginType, Object loginId, String tokenValue) {
        logLoginService.addLoginLogAsync((String) loginId, ELoginType.REPLACED, null, ServletUtils.getRequest());
    }

    /** 每次被封禁时触发 */
    @Override
    public void doDisable(String loginType, Object loginId, String service, int level, long disableTime) {

    }

    /** 每次被解封时触发 */
    @Override
    public void doUntieDisable(String loginType, Object loginId, String service) {

    }

    /** 每次二级认证时触发 */
    @Override
    public void doOpenSafe(String loginType, String tokenValue, String service, long safeTime) {

    }

    /** 每次退出二级认证时触发 */
    @Override
    public void doCloseSafe(String loginType, String tokenValue, String service) {

    }

    /** 每次创建Session时触发 */
    @Override
    public void doCreateSession(String id) {

    }

    /** 每次注销Session时触发 */
    @Override
    public void doLogoutSession(String id) {

    }

    /** 每次Token续期时触发 */
    @Override
    public void doRenewTimeout(String tokenValue, Object loginId, long timeout) {

    }

}
