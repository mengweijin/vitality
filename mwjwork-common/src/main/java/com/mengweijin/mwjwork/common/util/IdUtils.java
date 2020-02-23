package com.mengweijin.mwjwork.common.util;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.mengweijin.mwjwork.common.util.date.DateFormatUtil;

import java.util.Date;

/**
 * @author mengweijin
 */
public class IdUtils extends IdUtil {

	/**
	 * 参数1为终端ID
	 * 参数2为数据中心ID
	 */
	private static final Snowflake snowflake = IdUtil.createSnowflake(1, 1);

	/**
	 * 生成ID
	 * @return
	 */
	public static long getSnowflakeId(){
		return snowflake.nextId();
	}

	/**
	 * 随机生成20位时间戳ID，可能重复
	 * synchronized 有如下几种锁：
	 * 1、加在非静态方法获取的是对象锁，多个对象多个锁，因此只有才单例模式下才能实现同步；
	 * 2、加在静态方法获取的是类锁，一定能实现同步；
	 * 3、加载需要同步的代码块中，用synchronized(this)获取的是类锁(此处为：IDGenerator)。
	 * 如果存在全局变量 String str = "任意字符";，当使用synchronized(str)时，获取的是对象锁。
	 * @return
	 */

	/**
	 * 同步监视器，
	 * 它的含义是：线程开始执行同步代码块之前，必须先获得对同步代码块的锁定。
	 * 任何时刻只能有一个线程可以获得对同步监视器的锁定，当同步代码块执行完成后，该线程会释放对该同步监视器的锁定。
	 */
	private static final String TIMESTAMP_ID = "TIMESTAMP_ID";

	/**
	 * 时间戳ID
	 * @return
	 */
	public static String timestampId() {
		synchronized(TIMESTAMP_ID){
			return DateFormatUtil.format(new Date(), DateFormatUtil.YYYYMMDDHHMMSSSSS);
		}

	}

}
