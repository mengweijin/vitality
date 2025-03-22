package com.github.mengweijin.vitality.system.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.repository.CrudRepository;
import com.github.mengweijin.vitality.system.domain.entity.Notice;
import com.github.mengweijin.vitality.system.mapper.NoticeMapper;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hutool.core.text.StrUtil;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * <p>
 *  Notice Service
 *  Add @Transactional(rollbackFor = Exception.class) if you need.
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Slf4j
@Service
public class NoticeService extends CrudRepository<NoticeMapper, Notice> {

    /**
     * Custom paging query
     * @param page page
     * @param notice {@link Notice}
     * @return IPage
     */
    public IPage<Notice> page(IPage<Notice> page, Notice notice){
        LambdaQueryWrapper<Notice> query = new LambdaQueryWrapper<>();
        query
                .eq(StrUtil.isNotBlank(notice.getName()), Notice::getName, notice.getName())
                .eq(StrUtil.isNotBlank(notice.getDescription()), Notice::getDescription, notice.getDescription())
                .eq(StrUtil.isNotBlank(notice.getReleased()), Notice::getReleased, notice.getReleased())
                .eq(!Objects.isNull(notice.getId()), Notice::getId, notice.getId())
                .eq(!Objects.isNull(notice.getCreateBy()), Notice::getCreateBy, notice.getCreateBy())
                .eq(!Objects.isNull(notice.getCreateTime()), Notice::getCreateTime, notice.getCreateTime())
                .eq(!Objects.isNull(notice.getUpdateBy()), Notice::getUpdateBy, notice.getUpdateBy())
                .eq(!Objects.isNull(notice.getUpdateTime()), Notice::getUpdateTime, notice.getUpdateTime());
        return this.page(page, query);
    }
}
