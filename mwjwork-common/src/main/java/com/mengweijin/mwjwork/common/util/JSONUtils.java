package com.mengweijin.mwjwork.common.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * @author Meng WEi Jin
 */
public class JSONUtils {

	/**
	 * 格式化输出JSON
	 * @param object
	 * @return
	 */
	public static String format(Object object){
		return JSON.toJSONString(
				object,
				SerializerFeature.PrettyFormat,
				SerializerFeature.WriteMapNullValue,
				SerializerFeature.WriteDateUseDateFormat
		);
	}
}
