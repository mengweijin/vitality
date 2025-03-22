package com.github.mengweijin.vita.system.domain.pure;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.mengweijin.vita.system.domain.entity.Menu;
import com.github.mengweijin.vita.system.enums.EYesNo;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.dromara.hutool.core.collection.CollUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author mengweijin
 * @since 2023/6/30
 */
@Data
@AllArgsConstructor
public class PureAsyncRoutes implements Serializable {

    private Boolean success;

    private List<RoutesData> data;

    public static PureAsyncRoutes tree(List<Menu> list, Long parentId) {
        List<RoutesData> collect = list.stream().map(menu -> {
            RoutesData data = new RoutesData();
            data.setId(menu.getId());
            data.setParentId(menu.getParentId());
            data.setSeq(menu.getSeq());
            data.setName(menu.getRouterName());
            data.setPath(menu.getRouterPath());
            data.setComponent(menu.getComponentPath());
            data.setRedirect(menu.getRedirect());
            data.setMeta(getMeta(menu));
            data.setChildren(null);
            return data;
        }).toList();
        List<RoutesData> routesDataList = treePureAsyncRoutesData(collect, parentId);
        return new PureAsyncRoutes(true, Optional.ofNullable(routesDataList).orElse(new ArrayList<>()));
    }

    private static RoutesMeta getMeta(Menu menu) {
        RoutesMeta meta = new RoutesMeta();
        meta.setTitle(menu.getTitle());
        meta.setIcon(menu.getIcon());
        meta.setExtraIcon(menu.getExtraIcon());
        meta.setShowLink(EYesNo.Y.getValue().equals(menu.getShowLink()));
        meta.setShowParent(EYesNo.Y.getValue().equals(menu.getShowParent()));
        meta.setRoles(null);
        meta.setAuths(Collections.singletonList(menu.getPermission()));
        meta.setKeepAlive(EYesNo.Y.getValue().equals(menu.getKeepAlive()));
        meta.setFrameSrc(menu.getIframeSrc());
        meta.setFrameLoading(EYesNo.Y.getValue().equals(menu.getIframeLoading()));
        meta.setTransition(getTransition(menu));
        meta.setHiddenTag(EYesNo.Y.getValue().equals(menu.getHiddenTag()));
        meta.setFixedTag(EYesNo.Y.getValue().equals(menu.getFixedTag()));
        meta.setDynamicLevel(null);
        meta.setActivePath(menu.getActivePath());
        return meta;
    }

    private static Transition getTransition(Menu menu) {
        Transition transition = new Transition();
        transition.setName(null);
        transition.setEnterTransition(menu.getEnterTransition());
        transition.setLeaveTransition(menu.getLeaveTransition());
        return transition;
    }

    public static List<RoutesData> treePureAsyncRoutesData(List<RoutesData> list, Long parentId) {
        Map<Long, List<RoutesData>> collect = list.stream().collect(Collectors.groupingBy(RoutesData::getParentId));
        for (RoutesData node : list) {
            List<RoutesData> children = collect.get(node.getId());
            if (CollUtil.isNotEmpty(children)) {
                children.sort(Comparator.comparingInt(RoutesData::getSeq));
                node.setChildren(children);
            }
        }
        return collect.get(parentId);
    }

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class RoutesData {

        private Long id;

        private Long parentId;

        private Integer seq;

        // 路由路径
        private String path;

        // 路由名称（必须保持唯一）
        private String name;

        private String component;

        private String redirect;

        private RoutesMeta meta;

        private List<RoutesData> children;
    }

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class RoutesMeta {

        private String title;

        private String icon;

        private String extraIcon;

        private Boolean showLink;

        private Boolean showParent;

        private List<String> roles;

        private List<String> auths;

        private Boolean keepAlive;

        private String frameSrc;

        private Boolean frameLoading;

        private Transition transition;

        private Boolean hiddenTag;

        private Boolean fixedTag;

        private Integer dynamicLevel;

        private String activePath;
    }


    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Transition {

        private String name;

        private String enterTransition;

        private String leaveTransition;

    }
}
