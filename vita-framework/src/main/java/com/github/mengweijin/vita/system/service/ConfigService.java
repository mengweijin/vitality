package com.github.mengweijin.vita.system.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.repository.CrudRepository;
import com.github.mengweijin.vita.system.constant.ConfigConst;
import com.github.mengweijin.vita.system.domain.entity.Config;
import com.github.mengweijin.vita.system.mapper.ConfigMapper;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hutool.core.text.StrUtil;
import org.dromara.hutool.core.util.BooleanUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * <p>
 *  Config Service
 *  Add @Transactional(rollbackFor = Exception.class) if you need.
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Slf4j
@Service
public class ConfigService extends CrudRepository<ConfigMapper, Config> {

    @Override
    public List<Config> list() {
        return super.list();
    }

    /**
     * Custom paging query
     * @param page page
     * @param config {@link Config}
     * @return IPage
     */
    public IPage<Config> page(IPage<Config> page, Config config){
        LambdaQueryWrapper<Config> query = new LambdaQueryWrapper<>();
        query
                .eq(StrUtil.isNotBlank(config.getVal()), Config::getVal, config.getVal())
                .eq(StrUtil.isNotBlank(config.getRemark()), Config::getRemark, config.getRemark())
                .eq(!Objects.isNull(config.getId()), Config::getId, config.getId())
                .eq(!Objects.isNull(config.getCreateBy()), Config::getCreateBy, config.getCreateBy())
                .eq(!Objects.isNull(config.getCreateTime()), Config::getCreateTime, config.getCreateTime())
                .eq(!Objects.isNull(config.getUpdateBy()), Config::getUpdateBy, config.getUpdateBy())
                .eq(!Objects.isNull(config.getUpdateTime()), Config::getUpdateTime, config.getUpdateTime())
                .like(StrUtil.isNotBlank(config.getName()), Config::getName, config.getName())
                .like(StrUtil.isNotBlank(config.getCode()), Config::getCode, config.getCode());
        return this.page(page, query);
    }

    public Config getByCode(String code) {
        return this.lambdaQuery().eq(Config::getCode, code).one();
    }

    public boolean getCaptchaEnabled() {
        Config config = this.getByCode(ConfigConst.CAPTCHA_ENABLED);
        return BooleanUtil.toBoolean(config.getVal());
    }
}
