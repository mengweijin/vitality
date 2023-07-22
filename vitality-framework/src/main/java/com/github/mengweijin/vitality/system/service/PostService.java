package com.github.mengweijin.vitality.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.mengweijin.vitality.framework.exception.ClientException;
import com.github.mengweijin.vitality.system.dto.PostDTO;
import com.github.mengweijin.vitality.system.entity.MenuPostRltDO;
import com.github.mengweijin.vitality.system.entity.PostDO;
import com.github.mengweijin.vitality.system.entity.UserPostRltDO;
import com.github.mengweijin.vitality.system.mapper.PostMapper;
import org.dromara.hutool.core.collection.CollUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

/**
 * 岗位管理表 服务类
 *
 * @author mengweijin
 * @since 2023-06-09
 */
@Service
public class PostService extends ServiceImpl<PostMapper, PostDO> {

    @Autowired
    private PostMapper postMapper;
    @Autowired
    private UserPostRltService userPostRltService;
    @Autowired
    private MenuPostRltService menuPostRltService;

    @Override
    public boolean removeById(Serializable id) {
        Long count = userPostRltService.lambdaQuery().eq(UserPostRltDO::getPostId, id).count();
        if(count > 0) {
            throw new ClientException("Users already exist in current post and cannot be deleted!");
        }
        return super.removeById(id);
    }

    public PostDTO detailById(Long id) {
        return postMapper.detailById(id);
    }

    public IPage<PostDTO> page(IPage<PostDTO> page, PostDTO dto){
        return postMapper.page(page, dto);
    }

    public boolean setDisabledValue(Long id, boolean disabled) {
        return this.lambdaUpdate().set(PostDO::getDisabled, disabled).eq(PostDO::getId, id).update();
    }

    public List<PostDTO> getByUserId(Long userId) {
        return postMapper.getByUserId(userId);
    }

    public void addUsers(Long postId, List<Long> userIdList) {
        if(CollUtil.isEmpty(userIdList)) {
            return;
        }
        List<Long> alreadyExistedUserIdList = userPostRltService.lambdaQuery()
                .select(UserPostRltDO::getUserId)
                .eq(UserPostRltDO::getPostId, postId)
                .in(UserPostRltDO::getUserId, userIdList)
                .list()
                .stream().map(UserPostRltDO::getUserId).toList();

        List<UserPostRltDO> userPostRltList = userIdList.stream()
                .filter(userId -> alreadyExistedUserIdList.stream().noneMatch(existed -> existed.equals(userId)))
                .map(userId -> {
                    UserPostRltDO rlt = new UserPostRltDO();
                    rlt.setUserId(userId);
                    rlt.setPostId(postId);
                    return rlt;
                })
                .toList();

        if (CollUtil.isNotEmpty(userPostRltList)) {
            userPostRltService.saveBatch(userPostRltList);
        }
    }

    public void removeUsers(Long postId, List<Long> userIdList) {
        if(CollUtil.isEmpty(userIdList)) {
            return;
        }
        userPostRltService.lambdaUpdate()
                .eq(UserPostRltDO::getPostId, postId)
                .in(UserPostRltDO::getUserId, userIdList)
                .remove();
    }

    @Transactional
    public void setMenu(Long id, List<Long> menuIdList) {
        menuPostRltService.lambdaUpdate().eq(MenuPostRltDO::getPostId, id).remove();
        if(CollUtil.isEmpty(menuIdList)) {
            return;
        }
        List<MenuPostRltDO> list = menuIdList.stream().map(menuId -> new MenuPostRltDO().setPostId(id).setMenuId(menuId)).toList();
        menuPostRltService.saveBatch(list);
    }
}
