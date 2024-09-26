package com.github.mengweijin.vitality.framework.thread;

import com.github.mengweijin.vitality.framework.constant.Const;
import org.dromara.hutool.core.thread.ThreadUtil;

import java.util.concurrent.ExecutorService;

/**
 * @author mengweijin
 * @since 2024/8/31
 */
public interface ThreadPools {

    ExecutorService LOGIN_LOG_EXECUTOR_SERVICE = ThreadUtil.newFixedExecutor(Const.PROCESSORS, "thread-pool-login-log-", false);

    ExecutorService OPERATION_LOG_EXECUTOR_SERVICE = ThreadUtil.newFixedExecutor(Const.PROCESSORS, "thread-pool-operation-log-", false);

    ExecutorService ERROR_LOG_EXECUTOR_SERVICE = ThreadUtil.newFixedExecutor(Const.PROCESSORS, "thread-pool-error-log-", true);

    ExecutorService SSE_EXECUTOR_SERVICE = ThreadUtil.newFixedExecutor(Const.PROCESSORS, "thread-pool-sse-", true);

}
