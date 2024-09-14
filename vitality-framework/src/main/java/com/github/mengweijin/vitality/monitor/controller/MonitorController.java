package com.github.mengweijin.vitality.monitor.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.session.TokenSign;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.mengweijin.vitality.framework.domain.R;
import com.github.mengweijin.vitality.monitor.domain.vo.monitor.MonitorVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mengweijin
 * @since 2022/10/30
 */
@RestController
@RequestMapping("/monitor")
public class MonitorController {

    @SaCheckPermission("monitor:application:query")
    @GetMapping("/server")
    public MonitorVO serverInfo() {
        return new MonitorVO();
    }

    @SaCheckPermission("system:userOnline:query")
    @GetMapping("/user-online/page")
    public IPage<SaSession> page(Page<SaSession> page, @RequestParam(value = "keyword", defaultValue = "") String keyword) {
        long size = page.getSize();
        long start = (page.getCurrent() - 1) * size;

        List<SaSession> dtoList = new ArrayList<>();

        // 分页获取所有已登录的会话id。size = -1 时，为获取所有。
        List<String> sessionIdList = StpUtil.searchSessionId(keyword, (int) start, (int) size, false);
        for (String sessionId : sessionIdList) {
            // 根据会话id，查询对应的 SaSession 对象，此处一个 SaSession 对象即代表一个登录的账号
            SaSession session = StpUtil.getSessionBySessionId(sessionId);
            dtoList.add(session);
        }

        // 获取所有已登录的会话id
        List<String> allSessionIdList = StpUtil.searchSessionId("", 0, -1, false);
        page.setTotal(allSessionIdList.size());
        page.setRecords(dtoList);

        return page;
    }

    @SaCheckPermission("system:userOnline:query")
    @GetMapping("/user-online/token-sign-list/{sessionId}")
    public List<TokenSign> tokenSignList(@PathVariable("sessionId") String sessionId) {
        SaSession session = StpUtil.getSessionBySessionId(sessionId);
        // 查询这个账号都在哪些设备登录了
        return session.getTokenSignList();
    }

    @SaCheckPermission("system:userOnline:kickOut")
    @PostMapping("/user-online/kick-out/username/{username}")
    public R<Void> kickoffByLoginId(@PathVariable("username") String loginId) {
        // 强制指定账号注销下线
        StpUtil.logout(loginId);
        return R.success();
    }

    @SaCheckPermission("system:userOnline:kickOut")
    @PostMapping("/user-online/kick-out/token/{token}")
    public R<Void> kickoffByToken(@PathVariable("token") String token) {
        // 强制指定 Token 注销下线
        StpUtil.logoutByTokenValue(token);
        return R.success();
    }

}