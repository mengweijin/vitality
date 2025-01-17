package com.github.mengweijin.vitality.framework.log.appender;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.filter.ThresholdFilter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.IThrowableProxy;
import ch.qos.logback.classic.spi.StackTraceElementProxy;
import ch.qos.logback.core.CoreConstants;
import ch.qos.logback.core.UnsynchronizedAppenderBase;
import ch.qos.logback.core.helpers.Transform;
import cn.dev33.satoken.exception.NotLoginException;
import com.github.mengweijin.vitality.framework.constant.Const;
import com.github.mengweijin.vitality.framework.exception.ClientException;
import com.github.mengweijin.vitality.framework.satoken.LoginHelper;
import com.github.mengweijin.vitality.monitor.domain.entity.LogAlert;
import com.github.mengweijin.vitality.monitor.service.LogAlertService;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hutool.core.reflect.ClassUtil;
import org.dromara.hutool.core.text.StrUtil;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

/**
 * No need additional configuration.
 *
 * @author mengweijin
 * @since 2023/4/1
 */
@Slf4j
@Component
@AllArgsConstructor
public class DbLoggerAppender extends UnsynchronizedAppenderBase<ILoggingEvent> {

    private static final Class<?>[] EXCLUDE_CLASS = {ClientException.class, NotLoginException.class};

    private static final String TAB = StrUtil.fillAfter(Const.EMPTY, ' ', 4);

    private LogAlertService logAlertService;

    /**
     * DbErrorLogAppender初始化
     */
    @PostConstruct
    @SuppressWarnings({"unused"})
    public void init() {
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();

        ThresholdFilter filter = new ThresholdFilter();
        filter.setLevel(Level.WARN.levelStr);
        filter.setContext(context);
        filter.start();
        this.addFilter(filter);
        this.setContext(context);

        context.getLogger(Logger.ROOT_LOGGER_NAME).addAppender(DbLoggerAppender.this);
        super.start();
    }

    @Override
    protected void append(ILoggingEvent loggingEvent) {
        Long loginUserId = LoginHelper.getLoginUserIdQuietly();
        CompletableFuture.runAsync(() -> {
            try {
                LocalDateTime createTime = Instant.ofEpochMilli(loggingEvent.getTimeStamp()).atZone(ZoneId.systemDefault()).toLocalDateTime();
                IThrowableProxy throwableProxy = loggingEvent.getThrowableProxy();

                LogAlert logAlert = new LogAlert();
                logAlert.setLogLevel(loggingEvent.getLevel().levelStr);
                logAlert.setMessage(loggingEvent.getMessage());

                if (loggingEvent.getCallerData() != null && loggingEvent.getCallerData().length > 0) {
                    StackTraceElement element = loggingEvent.getCallerData()[0];
                    logAlert.setClassName(element.getClassName());
                    logAlert.setMethodName(element.getMethodName());
                }

                if (throwableProxy != null) {
                    logAlert.setExceptionName(throwableProxy.getClassName());
                    logAlert.setStackTrace(this.getStackTraceMsg(throwableProxy));
                }

                logAlert.setCreateBy(loginUserId);
                logAlert.setUpdateBy(loginUserId);
                logAlert.setCreateTime(createTime);
                logAlert.setUpdateTime(createTime);

                boolean noneMatch = Arrays.stream(EXCLUDE_CLASS).noneMatch(cls -> ClassUtil.getClassName(cls, false).equals(logAlert.getExceptionName()));
                if (noneMatch) {
                    // 错误日志实体类写入数据库
                    logAlertService.save(logAlert);
                }
            } catch (RuntimeException e) {
                this.addError("Record error log to database failed! " + e.getMessage());
            }
        });
    }

    /**
     * 拼装堆栈跟踪信息
     */
    private String getStackTraceMsg(IThrowableProxy tp) {
        StringBuilder buf = new StringBuilder();
        if (tp != null) {
            while (tp != null) {
                this.renderStackTrace(buf, tp);
                tp = tp.getCause();
            }
        }

        return buf.toString();
    }

    /**
     * 堆栈跟踪信息拼装成html字符串
     */
    private void renderStackTrace(StringBuilder stringBuilder, IThrowableProxy throwableProxy) {
        this.printFirstLine(stringBuilder, throwableProxy);
        int commonFrames = throwableProxy.getCommonFrames();
        StackTraceElementProxy[] stepArray = throwableProxy.getStackTraceElementProxyArray();

        for (int i = 0; i < stepArray.length - commonFrames; ++i) {
            StackTraceElementProxy step = stepArray[i];
            stringBuilder.append(TAB);
            stringBuilder.append(Transform.escapeTags(step.toString()));
            stringBuilder.append(CoreConstants.LINE_SEPARATOR);
        }

        if (commonFrames > 0) {
            stringBuilder.append(TAB);
            stringBuilder.append("... ").append(commonFrames).append(" common frames omitted").append(CoreConstants.LINE_SEPARATOR);
        }

    }

    /**
     * 拼装堆栈跟踪信息第一行
     */
    public void printFirstLine(StringBuilder sb, IThrowableProxy tp) {
        int commonFrames = tp.getCommonFrames();
        if (commonFrames > 0) {
            sb.append(CoreConstants.LINE_SEPARATOR).append("Caused by: ");
        }
        sb.append(tp.getClassName()).append(": ").append(Transform.escapeTags(tp.getMessage()));
        sb.append(CoreConstants.LINE_SEPARATOR);
    }

}
