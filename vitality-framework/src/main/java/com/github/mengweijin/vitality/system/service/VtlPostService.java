package com.github.mengweijin.vitality.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.mengweijin.vitality.framework.exception.ClientException;
import com.github.mengweijin.vitality.system.dto.VtlPostDTO;
import com.github.mengweijin.vitality.system.entity.VtlPost;
import com.github.mengweijin.vitality.system.entity.VtlUserPostRlt;
import com.github.mengweijin.vitality.system.mapper.VtlPostMapper;
import org.dromara.hutool.core.collection.CollUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

/**
 * 岗位管理表 服务类
 *
 * @author mengweijin
 * @since 2023-06-09
 */
@Service
public class VtlPostService extends ServiceImpl<VtlPostMapper, VtlPost> {

    @Autowired
    private VtlPostMapper vtlPostMapper;

    @Autowired
    private VtlUserPostRltService vtlUserPostRltService;

    @Override
    public boolean removeById(Serializable id) {
        Long count = vtlUserPostRltService.lambdaQuery().eq(VtlUserPostRlt::getPostId, id).count();
        if(count > 0) {
            throw new ClientException("Users already exist in current post and cannot be deleted!");
        }
        return super.removeById(id);
    }

    public VtlPostDTO detailById(Long id) {
        return vtlPostMapper.detailById(id);
    }

    public IPage<VtlPostDTO> page(IPage<VtlPostDTO> page, VtlPostDTO dto){
        return vtlPostMapper.page(page, dto);
    }

    public boolean setDisabledValue(Long id, boolean disabled) {
        return this.lambdaUpdate().set(VtlPost::getDisabled, disabled).eq(VtlPost::getId, id).update();
    }

    public List<VtlPostDTO> getByUserId(Long userId) {
        return vtlPostMapper.getByUserId(userId);
    }

    public void addUsers(Long postId, List<Long> userIdList) {
        if(CollUtil.isEmpty(userIdList)) {
            return;
        }
        List<Long> alreadyExistedUserIdList = vtlUserPostRltService.lambdaQuery()
                .select(VtlUserPostRlt::getUserId)
                .eq(VtlUserPostRlt::getPostId, postId)
                .in(VtlUserPostRlt::getUserId, userIdList)
                .list()
                .stream().map(VtlUserPostRlt::getUserId).toList();

        List<VtlUserPostRlt> userPostRltList = userIdList.stream()
                .filter(userId -> alreadyExistedUserIdList.stream().noneMatch(existed -> existed.equals(userId)))
                .map(userId -> {
                    VtlUserPostRlt rlt = new VtlUserPostRlt();
                    rlt.setUserId(userId);
                    rlt.setPostId(postId);
                    return rlt;
                })
                .toList();

        if (CollUtil.isNotEmpty(userPostRltList)) {
            vtlUserPostRltService.saveBatch(userPostRltList);
        }
    }

    public void removeUsers(Long postId, List<Long> userIdList) {
        if(CollUtil.isEmpty(userIdList)) {
            return;
        }
        vtlUserPostRltService.lambdaUpdate()
                .eq(VtlUserPostRlt::getPostId, postId)
                .in(VtlUserPostRlt::getUserId, userIdList)
                .remove();
    }
}
