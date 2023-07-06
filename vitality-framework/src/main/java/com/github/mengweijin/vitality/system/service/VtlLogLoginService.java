package com.github.mengweijin.vitality.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.mengweijin.vitality.framework.util.Ip2regionUtils;
import com.github.mengweijin.vitality.framework.util.ServletUtils;
import com.github.mengweijin.vitality.system.dto.VtlLogLoginDTO;
import com.github.mengweijin.vitality.system.entity.VtlLogLogin;
import com.github.mengweijin.vitality.system.enums.ELoginType;
import com.github.mengweijin.vitality.system.mapper.VtlLogLoginMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.dromara.hutool.core.text.StrUtil;
import org.dromara.hutool.http.useragent.UserAgent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

/**
 * 登录日志记录表 服务类
 *
 * @author mengweijin
 * @since 2023-07-06
 */
@Service
public class VtlLogLoginService extends ServiceImpl<VtlLogLoginMapper, VtlLogLogin> {

    @Autowired
    private VtlLogLoginMapper vtlLogLoginMapper;

    public VtlLogLoginDTO detailById(Long id) {
        return vtlLogLoginMapper.detailById(id);
    }

    public IPage<VtlLogLoginDTO> page(IPage<VtlLogLoginDTO> page, VtlLogLoginDTO dto){
        return vtlLogLoginMapper.page(page, dto);
    }

    public void addLoginLogAsync(String username, ELoginType loginType, String errorInfo, HttpServletRequest request){
        CompletableFuture.runAsync(() -> {
            UserAgent userAgent = ServletUtils.getUserAgent(request);
            String ip = ServletUtils.getClientIP(request);
            VtlLogLogin logLogin = new VtlLogLogin();
            logLogin.setUsername(username);
            logLogin.setLoginType(loginType);
            logLogin.setIp(ip);
            logLogin.setIpLocation(Ip2regionUtils.search(ip));
            logLogin.setBrowser(userAgent.getBrowser().getName());
            logLogin.setPlatform(userAgent.getPlatform().getName());
            logLogin.setOperatingSystem(userAgent.getOs().getName());
            logLogin.setSucceeded(StrUtil.isBlank(errorInfo) ? 1 : 0);
            logLogin.setErrorInfo(errorInfo);
            this.save(logLogin);
        });
    }
}
