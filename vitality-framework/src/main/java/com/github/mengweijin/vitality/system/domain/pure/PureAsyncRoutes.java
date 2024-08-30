package com.github.mengweijin.vitality.system.domain.pure;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.mengweijin.vitality.system.domain.entity.Menu;
import com.github.mengweijin.vitality.system.enums.EYesNo;
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

    private List<PureAsyncRoutesData> data;

    public static PureAsyncRoutes tree(List<Menu> list, Long parentId) {
        List<PureAsyncRoutesData> collect = list.stream().map(menu -> {
            PureAsyncRoutesMeta meta = new PureAsyncRoutesMeta();
            meta.setTitle(menu.getTitle());
            meta.setIcon(menu.getIcon());
            meta.setShowLink(EYesNo.Y.getValue().equals(menu.getShowLink()));
            meta.setRoles(null);
            meta.setAuths(Collections.singletonList(menu.getPermission()));
            meta.setFrameSrc(menu.getIframe());

            PureAsyncRoutesData data = new PureAsyncRoutesData();
            data.setId(menu.getId());
            data.setParentId(menu.getParentId());
            data.setSeq(menu.getSeq());
            data.setPath(menu.getRouterPath());
            data.setName(menu.getRouterName());
            data.setMeta(meta);
            data.setChildren(null);
            return data;
        }).toList();
        List<PureAsyncRoutesData> pureAsyncRoutesDataList = treePureAsyncRoutesData(collect, parentId);
        return new PureAsyncRoutes(true, Optional.ofNullable(pureAsyncRoutesDataList).orElse(new ArrayList<>()));
    }

    public static List<PureAsyncRoutesData> treePureAsyncRoutesData(List<PureAsyncRoutesData> list, Long parentId) {
        Map<Long, List<PureAsyncRoutesData>> collect = list.stream().collect(Collectors.groupingBy(PureAsyncRoutesData::getParentId));
        for (PureAsyncRoutesData node : list) {
            List<PureAsyncRoutesData> children = collect.get(node.getId());
            if (CollUtil.isNotEmpty(children)) {
                children.sort(Comparator.comparingInt(PureAsyncRoutesData::getSeq));
                node.setChildren(children);
            }
        }
        return collect.get(parentId);
    }

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class PureAsyncRoutesData {

        private Long id;

        private Long parentId;

        private Integer seq;

        // 路由路径
        private String path;

        // 路由名称（必须保持唯一）
        private String name;

        private PureAsyncRoutesMeta meta;

        private List<PureAsyncRoutesData> children;
    }

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class PureAsyncRoutesMeta {

        private String title;

        private String icon;

        private Boolean showLink;

        private List<String> roles;

        private List<String> auths;

        private String frameSrc;
    }
}
