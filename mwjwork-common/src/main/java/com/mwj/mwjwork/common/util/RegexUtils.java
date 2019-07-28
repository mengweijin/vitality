package com.mwj.mwjwork.common.util;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author mengweijin
 */
public class RegexUtils {
	
	/**
	 * 是否为正整数
	 * @param value
	 * @return
	 */
	public static boolean isInt(String value) {
		if(value == null || value.length() == 0){
			return false;
		}
		return value.matches("^\\d+$");
	}
	
	
	/**
	 * 是否为正浮点数
	 * @param value
	 * @return
	 */
	public static boolean isDouble(String value) {
		if(value == null || value.length() == 0){
			return false;
		}
		return value.matches("^\\d+\\.\\d+$");
	}
	
	/**
	 * 是否为百分比形式的数（以‘%’符号结尾）
	 * @param value
	 * @return
	 */
	public static boolean isRatio(String value) {
		if(value == null || value.length() == 0){
			return false;
		}
		return value.matches("^\\d+\\.\\d+%$");
	}

	/**
	 * 检测是否能被正则匹配到
	 * @param str
	 * @param regex 正则表达式
	 */
	public static boolean regexCheck(String str, String regex){
		if(StringUtils.isAnyEmpty(str, regex)){
			return false;
		}

		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);

		return matcher.find();
	}

	/**
	 * 抽取字符串中正则匹配到的字符串
	 * @param str
	 * @param regex 正则表达式
	 */
	public static String regexMatcherFirst(String str, String regex){
		if(StringUtils.isAnyEmpty(str, regex)){
			return null;
		}

		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);

		while(matcher.find()){
			return matcher.group();
		}

		return null;
	}

	/**
	 * 抽取字符串中正则匹配到的字符串
	 * @param str
	 * @param regex 正则表达式
	 */
	public static List<String> regexMatcherAll(String str, String regex){
		List<String> list = new ArrayList<>();
		if(StringUtils.isAnyEmpty(str, regex)){
			return list;
		}

		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);

		while(matcher.find()){
			list.add(matcher.group());
		}

		return list;
	}

}
