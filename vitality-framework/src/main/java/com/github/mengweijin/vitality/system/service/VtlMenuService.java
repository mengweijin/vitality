package com.github.mengweijin.vitality.system.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.mengweijin.vitality.framework.constant.SystemConst;
import com.github.mengweijin.vitality.framework.enums.EMenuType;
import com.github.mengweijin.vitality.system.entity.VtlMenu;
import com.github.mengweijin.vitality.system.mapper.VtlMenuMapper;
import com.github.mengweijin.vitality.system.vo.MenuDataVO;
import org.dromara.hutool.core.bean.BeanUtil;
import org.dromara.hutool.core.collection.CollUtil;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author mengweijin
 * @date 2023/4/1
 */
@Service
public class VtlMenuService extends ServiceImpl<VtlMenuMapper, VtlMenu> {

    public List<MenuDataVO> tree() {
        List<VtlMenu> menuList = this.lambdaQuery().le(VtlMenu::getType, EMenuType.BTN.getValue()).eq(VtlMenu::getDisabled, 0).list();
        List<MenuDataVO> voList = BeanUtil.copyToList(menuList, MenuDataVO.class);
        voList.forEach(item -> item.setHref(item.getUrl()));
        return this.buildTree(voList, SystemConst.MENU_ROOT_ID);
    }

    public List<MenuDataVO> buildTree(List<MenuDataVO> list, Long parentId) {
        Map<Long, List<MenuDataVO>> collect = list.stream().collect(Collectors.groupingBy(MenuDataVO::getParentId));
        for (MenuDataVO node : list) {
            List<MenuDataVO> children = collect.get(node.getId());
            if(CollUtil.isNotEmpty(children)) {
                children.sort(Comparator.comparingInt(VtlMenu::getSeq));
                node.setChildren(children);
            }
        }
        return collect.get(parentId);
    }
}
