package com.github.mengweijin.quickboot.framework.util;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.LocalDateTimeUtil;

import java.time.LocalDateTime;

/**
 * 仅适用于单机应用部署
 * <p>
 * 随机生成20位时间戳ID，集群部署下可能重复
 * synchronized 有如下几种锁：
 * 1、加在非静态方法获取的是对象锁，多个对象多个锁，因此只有才单例模式下才能实现同步；
 * 2、加在静态方法获取的是类锁，一定能实现同步；
 * 3、加载需要同步的代码块中，用synchronized(this)获取的是类对象锁(此处为：TimestampIdUtils)。
 * 如果存在全局变量 Object LOCK = new Object();，当使用synchronized(LOCK)时，获取的是对象锁。
 *
 * @author mengweijin
 */
public class TimestampIdUtils {

	/**
	 * 同步监视器，
	 * 它的含义是：线程开始执行同步代码块之前，必须先获得对同步代码块的锁定。
	 * 任何时刻只能有一个线程可以获得对同步监视器的锁定，当同步代码块执行完成后，该线程会释放对该同步监视器的锁定。
	 */
	private static final Object LOCK = new Object();

	/**
	 * 时间戳ID
	 *
	 * @return 标准日期格式：yyyyMMddHHmmssSSS
	 */
	public static long timestampId() {
		synchronized (LOCK) {
			return Long.parseLong(LocalDateTimeUtil.format(LocalDateTime.now(), DatePattern.PURE_DATETIME_MS_PATTERN));
		}
	}

}
