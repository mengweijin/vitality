package com.github.mengweijin.quickboot.auth.security;

import com.github.mengweijin.quickboot.auth.async.LoginLogTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.stereotype.Component;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Meng Wei Jin
 **/
@Component
public class LogoutSuccessHandlerImpl extends SimpleUrlLogoutSuccessHandler implements LogoutSuccessHandler {

    @Autowired
    private LoginLogTask loginLogTask;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();

        WebAuthenticationDetails auth = (WebAuthenticationDetails)authentication.getDetails();
        //开启异步任务，写入登录日志
        loginLogTask.addLogoutLog(request, username, auth.getRemoteAddress());

        super.onLogoutSuccess(request, response, authentication);
    }
}
