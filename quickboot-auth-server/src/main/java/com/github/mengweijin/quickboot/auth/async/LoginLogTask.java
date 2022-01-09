package com.github.mengweijin.quickboot.auth.async;

import cn.hutool.http.useragent.Browser;
import cn.hutool.http.useragent.OS;
import cn.hutool.http.useragent.UserAgent;
import com.github.mengweijin.quickboot.auth.data.entity.LoginLog;
import com.github.mengweijin.quickboot.auth.data.enums.LoginStatus;
import com.github.mengweijin.quickboot.auth.data.enums.LoginType;
import com.github.mengweijin.quickboot.auth.data.service.LoginLogService;
import com.github.mengweijin.quickboot.framework.util.Const;
import com.github.mengweijin.quickboot.framework.util.ServletUtils;
import com.github.mengweijin.quickboot.framework.util.SpringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.concurrent.Future;

/**
 * @author mengweijin
 * @date 2022/1/2
 */
@Slf4j
@Component
public class LoginLogTask {

    @Async
    public Future<String>
    addFailureLoginLog(HttpServletRequest request, String username, String message) {
        return this.addLoginLog(request, username, message, LoginType.LOGIN, LoginStatus.FAILURE);
    }

    @Async
    public Future<String> addSuccessLoginLog(HttpServletRequest request, String username) {
        return this.addLoginLog(request, username, null, LoginType.LOGIN, LoginStatus.SUCCESS);
    }

    @Async
    public Future<String> addLogoutLog(HttpServletRequest request, String username) {
        return this.addLoginLog(request, username, null, LoginType.LOGOUT, LoginStatus.SUCCESS);
    }


    private Future<String> addLoginLog(HttpServletRequest request, String username, String message, LoginType loginType, LoginStatus loginStatus) {
        try {
            log.debug("Add login log.");
            Optional<UserAgent> userAgent = Optional.ofNullable(ServletUtils.getUserAgent(request));

            LoginLog loginLog = new LoginLog();
            loginLog.setUsername(username);
            loginLog.setLoginType(loginType);
            loginLog.setLoginStatus(loginStatus);
            loginLog.setIp(ServletUtils.getClientIP(request));
            loginLog.setOs(userAgent.map(UserAgent::getOs).map(OS::getName).orElse(null));
            loginLog.setBrowser(userAgent.map(UserAgent::getBrowser).map(Browser::getName).orElse(null));
            loginLog.setMessage(message);

            LoginLogService loginLogService = SpringUtils.getBean(LoginLogService.class);
            loginLogService.save(loginLog);
        } catch (Throwable e) {
            log.error("Add Login Log Exception!", e);
            return new AsyncResult<>(Const.FAILURE);
        }

        return new AsyncResult<>(Const.SUCCESS);
    }
}
