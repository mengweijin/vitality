package com.github.mengweijin.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.mengweijin.framework.util.Ip2regionUtils;
import com.github.mengweijin.framework.util.ServletUtils;
import com.github.mengweijin.system.dto.LogLoginDTO;
import com.github.mengweijin.system.entity.LogLoginDO;
import com.github.mengweijin.system.enums.ELoginType;
import com.github.mengweijin.system.mapper.LogLoginMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.dromara.hutool.core.text.StrUtil;
import org.dromara.hutool.http.useragent.Browser;
import org.dromara.hutool.http.useragent.UserAgent;
import org.dromara.hutool.http.useragent.UserAgentInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * 登录日志记录表 服务类
 *
 * @author mengweijin
 * @since 2023-07-06
 */
@Service
public class LogLoginService extends ServiceImpl<LogLoginMapper, LogLoginDO> {

    @Autowired
    private LogLoginMapper logLoginMapper;

    public LogLoginDTO detailById(Long id) {
        return logLoginMapper.detailById(id);
    }

    public IPage<LogLoginDTO> page(IPage<LogLoginDTO> page, LogLoginDTO dto){
        return logLoginMapper.page(page, dto);
    }

    public void addLoginLogAsync(String username, ELoginType loginType, String errorInfo, HttpServletRequest request){
        CompletableFuture.runAsync(() -> {
            UserAgent userAgent = ServletUtils.getUserAgent(request);
            String ip = ServletUtils.getClientIP(request);
            LogLoginDO logLogin = new LogLoginDO();
            logLogin.setUsername(username);
            logLogin.setLoginType(loginType);
            logLogin.setIp(ip);
            logLogin.setIpLocation(Ip2regionUtils.search(ip));
            logLogin.setBrowser(Optional.ofNullable(userAgent).map(UserAgent::getBrowser).map(Browser::getName).orElse(null));
            logLogin.setPlatform(Optional.ofNullable(userAgent).map(UserAgent::getPlatform).map(UserAgentInfo::getName).orElse(null));
            logLogin.setOperatingSystem(Optional.ofNullable(userAgent).map(UserAgent::getOs).map(UserAgentInfo::getName).orElse(null));
            logLogin.setSucceeded(StrUtil.isBlank(errorInfo) ? 1 : 0);
            logLogin.setErrorInfo(errorInfo);
            this.save(logLogin);
        });
    }
}
