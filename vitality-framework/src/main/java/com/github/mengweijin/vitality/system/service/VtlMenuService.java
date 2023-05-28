package com.github.mengweijin.vitality.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.mengweijin.vitality.framework.constant.SystemConst;
import com.github.mengweijin.vitality.system.dto.VtlMenuDTO;
import com.github.mengweijin.vitality.system.entity.VtlMenu;
import com.github.mengweijin.vitality.system.enums.EMenuType;
import com.github.mengweijin.vitality.system.mapper.VtlMenuMapper;
import com.github.mengweijin.vitality.system.dto.VtlMenuTreeDataDTO;
import org.dromara.hutool.core.bean.BeanUtil;
import org.dromara.hutool.core.collection.CollUtil;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private VtlMenuMapper vtlMenuMapper;

    public IPage<VtlMenuDTO> page(IPage<VtlMenuDTO> page, VtlMenuDTO dto){
        return vtlMenuMapper.page(page, dto);
    }

    public List<VtlMenuTreeDataDTO> tree() {
        List<VtlMenu> menuList = this.lambdaQuery().le(VtlMenu::getType, EMenuType.MENU.getValue()).eq(VtlMenu::getDisabled, 0).list();
        List<VtlMenuTreeDataDTO> voList = BeanUtil.copyToList(menuList, VtlMenuTreeDataDTO.class);
        voList.forEach(item -> item.setHref(item.getUrl()));
        return this.buildTree(voList, SystemConst.MENU_ROOT_ID);
    }

    public List<VtlMenuTreeDataDTO> buildTree(List<VtlMenuTreeDataDTO> list, Long parentId) {
        Map<Long, List<VtlMenuTreeDataDTO>> collect = list.stream().collect(Collectors.groupingBy(VtlMenuTreeDataDTO::getParentId));
        for (VtlMenuTreeDataDTO node : list) {
            List<VtlMenuTreeDataDTO> children = collect.get(node.getId());
            if(CollUtil.isNotEmpty(children)) {
                children.sort(Comparator.comparingInt(VtlMenu::getSeq));
                node.setChildren(children);
            }
        }
        return collect.get(parentId);
    }

}
