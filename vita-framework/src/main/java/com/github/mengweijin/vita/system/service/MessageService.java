package com.github.mengweijin.vita.system.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.repository.CrudRepository;
import com.github.mengweijin.vita.framework.constant.Const;
import com.github.mengweijin.vita.framework.satoken.LoginHelper;
import com.github.mengweijin.vita.system.domain.entity.Message;
import com.github.mengweijin.vita.system.domain.entity.MessageReceiver;
import com.github.mengweijin.vita.system.enums.EMessageCategory;
import com.github.mengweijin.vita.system.enums.EMessageTemplate;
import com.github.mengweijin.vita.system.mapper.MessageMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hutool.core.collection.CollUtil;
import org.dromara.hutool.core.text.StrUtil;
import org.dromara.hutool.core.thread.ThreadUtil;
import org.dromara.hutool.extra.spring.SpringUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

/**
 * <p>
 * Message Service
 * Add @Transactional(rollbackFor = Exception.class) if you need.
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Slf4j
@Service
@AllArgsConstructor
public class MessageService extends CrudRepository<MessageMapper, Message> {

    private MessageReceiverService messageReceiverService;

    private TransactionTemplate transactionTemplate;

    private final ExecutorService executorService = ThreadUtil.newFixedExecutor(Const.PROCESSORS * 2, "thread-pool-message-", true);

    /**
     * Custom paging query
     *
     * @param page    page
     * @param message {@link Message}
     * @return IPage
     */
    public IPage<Message> page(IPage<Message> page, Message message) {
        LambdaQueryWrapper<Message> query = new LambdaQueryWrapper<>();
        query
                .eq(StrUtil.isNotBlank(message.getCategory()), Message::getCategory, message.getCategory())
                .eq(StrUtil.isNotBlank(message.getTitle()), Message::getTitle, message.getTitle())
                .eq(StrUtil.isNotBlank(message.getContent()), Message::getContent, message.getContent())
                .eq(!Objects.isNull(message.getId()), Message::getId, message.getId())
                .eq(!Objects.isNull(message.getCreateBy()), Message::getCreateBy, message.getCreateBy())
                .eq(!Objects.isNull(message.getCreateTime()), Message::getCreateTime, message.getCreateTime())
                .eq(!Objects.isNull(message.getUpdateBy()), Message::getUpdateBy, message.getUpdateBy())
                .eq(!Objects.isNull(message.getUpdateTime()), Message::getUpdateTime, message.getUpdateTime());
        return this.page(page, query);
    }

    public void sendMessageToRole(Long roleId, EMessageCategory category, EMessageTemplate template, Object... args) {
        UserRoleService userRoleService = SpringUtil.getBean(UserRoleService.class);
        Set<Long> userIds = userRoleService.getUserIdsByRoleId(roleId);
        this.sendMessageToUsersAsync(userIds, category, template, args);
    }

    public void sendMessageToRole(String roleCode, EMessageCategory category, EMessageTemplate template, Object... args) {
        UserRoleService userRoleService = SpringUtil.getBean(UserRoleService.class);
        Set<Long> userIds = userRoleService.getUserIdsByRoleCode(roleCode);
        this.sendMessageToUsersAsync(userIds, category, template, args);
    }

    public void sendMessageToDept(Long deptId, EMessageCategory category, EMessageTemplate template, Object... args) {
        UserService userService = SpringUtil.getBean(UserService.class);
        Set<Long> userIds = userService.getUserIdsInDeptId(deptId);
        this.sendMessageToUsersAsync(userIds, category, template, args);
    }

    public void sendMessageToPost(Long postId, EMessageCategory category, EMessageTemplate template, Object... args) {
        UserPostService userPostService = SpringUtil.getBean(UserPostService.class);
        Set<Long> userIds = userPostService.getUserIdsByPostId(postId);
        this.sendMessageToUsersAsync(userIds, category, template, args);
    }


    public void sendMessageToUser(Long receiveUserId, EMessageCategory category, EMessageTemplate template, Object... args) {
        this.sendMessageToUsersAsync(Set.of(receiveUserId), category, template, args);
    }

    public void sendMessageToUsersAsync(Set<Long> userIds, EMessageCategory category, EMessageTemplate template, Object... args) {
        Long loginId = LoginHelper.getLoginUserIdQuietly();
        CompletableFuture.runAsync(() -> {
            transactionTemplate.executeWithoutResult(status -> {
                Message message = new Message();
                message.setCategory(category.getValue());
                message.setTitle(StrUtil.format(template.getTitle(), args));
                message.setContent(StrUtil.format(template.getContent(), args));

                message.setCreateBy(loginId);
                message.setUpdateBy(loginId);
                this.save(message);

                if (CollUtil.isEmpty(userIds)) {
                    log.warn("The user id in Set was empty when send message to users! message = {}", message);
                    return;
                }

                List<MessageReceiver> messageReceiverList = new ArrayList<>();
                userIds.forEach(userId -> {
                    MessageReceiver msgReceiver = new MessageReceiver();
                    msgReceiver.setMessageId(message.getId());
                    msgReceiver.setUserId(userId);

                    msgReceiver.setCreateBy(loginId);
                    msgReceiver.setUpdateBy(loginId);
                    messageReceiverList.add(msgReceiver);
                });
                messageReceiverService.saveBatch(messageReceiverList, Constants.DEFAULT_BATCH_SIZE);
            });

        }, executorService);
    }

}
