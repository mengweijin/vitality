package com.github.mengweijin.vitality.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.mengweijin.vitality.framework.domain.R;
import com.github.mengweijin.vitality.system.domain.entity.Message;
import com.github.mengweijin.vitality.system.service.MessageService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 *  Message Controller
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Slf4j
@AllArgsConstructor
@Validated
@RestController
@RequestMapping("/system/message")
public class MessageController {

    private MessageService messageService;

    /**
     * <p>
     * Get Message page by Message
     * </p>
     * @param page page
     * @param message {@link Message}
     * @return Page<Message>
     */
    @SaCheckPermission("system:message:query")
    @GetMapping("/page")
    public IPage<Message> page(Page<Message> page, Message message) {
        return messageService.page(page, message);
    }

    /**
     * <p>
     * Get Message list by Message
     * </p>
     * @param message {@link Message}
     * @return List<Message>
     */
    @SaCheckPermission("system:message:query")
    @GetMapping("/list")
    public List<Message> list(Message message) {
        return messageService.list(new LambdaQueryWrapper<>(message));
    }

    /**
     * <p>
     * Get Message by id
     * </p>
     * @param id id
     * @return Message
     */
    @SaCheckPermission("system:message:query")
    @GetMapping("/{id}")
    public Message getById(@PathVariable("id") Long id) {
        return messageService.getById(id);
    }

    /**
     * <p>
     * Add Message
     * </p>
     * @param message {@link Message}
     */
    @SaCheckPermission("system:message:create")
    @PostMapping("/create")
    public R<Void> create(@Valid @RequestBody Message message) {
        boolean bool = messageService.save(message);
        return R.ajax(bool);
    }

    /**
     * <p>
     * Update Message
     * </p>
     * @param message {@link Message}
     */
    @SaCheckPermission("system:message:update")
    @PostMapping("/update")
    public R<Void> update(@Valid @RequestBody Message message) {
        boolean bool = messageService.updateById(message);
        return R.ajax(bool);
    }

    /**
     * <p>
     * Delete Message by id(s), Multiple ids can be separated by commas ",".
     * </p>
     * @param ids id
     */
    @SaCheckPermission("system:message:delete")
    @PostMapping("/delete/{ids}")
    public R<Void> delete(@PathVariable("ids") Long[] ids) {
        return R.ajax(messageService.removeBatchByIds(Arrays.asList(ids)));
    }

}

