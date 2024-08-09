package com.github.mengweijin.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.mengweijin.framework.domain.R;
import com.github.mengweijin.system.domain.entity.MessageReceiver;
import com.github.mengweijin.system.service.MessageReceiverService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 *  MessageReceiver Controller
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Slf4j
@AllArgsConstructor
@Validated
@RestController
@RequestMapping("/system/message-receiver")
public class MessageReceiverController {

    private MessageReceiverService messageReceiverService;

    /**
     * <p>
     * Get MessageReceiver page by MessageReceiver
     * </p>
     * @param page page
     * @param messageReceiver {@link MessageReceiver}
     * @return Page<MessageReceiver>
     */
    @SaCheckPermission("system:messageReceiver:query")
    @GetMapping("/page")
    public IPage<MessageReceiver> page(Page<MessageReceiver> page, MessageReceiver messageReceiver) {
        return messageReceiverService.page(page, messageReceiver);
    }

    /**
     * <p>
     * Get MessageReceiver list by MessageReceiver
     * </p>
     * @param messageReceiver {@link MessageReceiver}
     * @return List<MessageReceiver>
     */
    @SaCheckPermission("system:messageReceiver:query")
    @GetMapping("/list")
    public List<MessageReceiver> list(MessageReceiver messageReceiver) {
        return messageReceiverService.list(new QueryWrapper<>(messageReceiver));
    }

    /**
     * <p>
     * Get MessageReceiver by id
     * </p>
     * @param id id
     * @return MessageReceiver
     */
    @SaCheckPermission("system:messageReceiver:query")
    @GetMapping("/{id}")
    public MessageReceiver getById(@PathVariable("id") Long id) {
        return messageReceiverService.getById(id);
    }

    /**
     * <p>
     * Add MessageReceiver
     * </p>
     * @param messageReceiver {@link MessageReceiver}
     */
    @SaCheckPermission("system:messageReceiver:create")
    @PostMapping
    public R<Void> add(@Valid @RequestBody MessageReceiver messageReceiver) {
        boolean bool = messageReceiverService.save(messageReceiver);
        return R.ajax(bool);
    }

    /**
     * <p>
     * Update MessageReceiver
     * </p>
     * @param messageReceiver {@link MessageReceiver}
     */
    @SaCheckPermission("system:messageReceiver:update")
    @PutMapping
    public R<Void> update(@Valid @RequestBody MessageReceiver messageReceiver) {
        boolean bool = messageReceiverService.updateById(messageReceiver);
        return R.ajax(bool);
    }

    /**
     * <p>
     * Delete MessageReceiver by id(s), Multiple ids can be separated by commas ",".
     * </p>
     * @param ids id
     */
    @SaCheckPermission("system:messageReceiver:delete")
    @DeleteMapping("/{ids}")
    public R<Void> delete(@PathVariable("ids") Long[] ids) {
        int i = messageReceiverService.getBaseMapper().deleteByIds(Arrays.asList(ids));
        return R.ajax(i);
    }

}

