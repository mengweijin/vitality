package com.github.mengweijin.vitality.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.session.TokenSign;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.mengweijin.vitality.framework.domain.R;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mengweijin
 * @date 2022/10/30
 */
@RestController
@RequestMapping("/online-user")
public class OnlineUserController {

    @GetMapping("/page")
    public IPage<SaSession> page(Page<SaSession> page, @RequestParam(value = "keyword", defaultValue = "") String keyword) {
        long size = page.getSize();
        long start = (page.getCurrent() - 1) * size;

        List<SaSession> dtoList = new ArrayList<>();

        // 分页获取所有已登录的会话id。size = -1 时，为获取所有。
        List<String> sessionIdList = StpUtil.searchSessionId(keyword, (int) start, (int) size, false);
        for (String sessionId : sessionIdList) {
            // 根据会话id，查询对应的 SaSession 对象，此处一个 SaSession 对象即代表一个登录的账号
            SaSession session = StpUtil.getSessionBySessionId(sessionId);
            // 查询这个账号都在哪些设备登录了，依据上面的示例，账号A 的 tokenSign 数量是 3，账号B 的 tokenSign 数量是 2
            //List<TokenSign> tokenSignList = session.getTokenSignList();
            // System.out.println("会话id：" + sessionId + "，共在 " + tokenSignList.size() + " 设备登录");
            dtoList.add(session);
        }

        // 获取所有已登录的会话id
        List<String> allSessionIdList = StpUtil.searchSessionId("", 0, -1, false);
        page.setTotal(allSessionIdList.size());
        page.setRecords(dtoList);

        return page;
    }

    @GetMapping("/tokenSignList/{sessionId}")
    public List<TokenSign> tokenSignList(@PathVariable("sessionId") String sessionId) {
        SaSession session = StpUtil.getSessionBySessionId(sessionId);
        return session.getTokenSignList();
    }

    @SaCheckPermission("system:onlineUser:delete")
    @DeleteMapping("/kickoffByLoginId/{loginId}")
    public R kickoffByLoginId(@PathVariable("loginId") String loginId) {
        StpUtil.logout(loginId);  // 强制指定账号注销下线
        return R.success();
    }

    @SaCheckPermission("system:onlineUser:delete")
    @DeleteMapping("/kickoffByToken/{token}")
    public R kickoffByToken(@PathVariable("token") String token) {
        StpUtil.logoutByTokenValue(token);  // 强制指定 Token 注销下线
        return R.success();
    }
}
