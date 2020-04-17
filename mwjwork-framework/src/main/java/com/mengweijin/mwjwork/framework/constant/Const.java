package com.mengweijin.mwjwork.framework.constant;

import java.io.File;
import java.nio.charset.Charset;

/**
 * @description 通用常量信息
 *
 * @author Meng Wei Jin
 **/
public interface Const {

    String TILDA = "~";

    String BACKTICK = "`";

    String EXCLAMATION_MARK = "!";

    String AT = "@";

    String HASH = "#";

    String DOLLAR = "$";

    String PERCENT = "%";

    String CARET = "^";

    String AMPERSAND = "&";

    String ASTERISK = "*";

    String STAR = ASTERISK;

    String LEFT_BRACKET = "(";

    String RIGHT_BRACKET = ")";

    String DASH = "-";

    String UNDERSCORE = "_";

    String PLUS = "+";

    String EQUALS = "=";

    String LEFT_BRACE = "{";

    String RIGHT_BRACE = "}";

    String LEFT_SQ_BRACKET = "[";

    String RIGHT_SQ_BRACKET = "]";

    String PIPE = "|";

    String BACK_SLASH = "\\";

    String SLASH = "/";

    String COLON = ":";

    String SEMICOLON = ";";

    String QUOTE = "\"";

    String SINGLE_QUOTE = "'";

    String LEFT_CHEV = "<";

    String RIGHT_CHEV = ">";

    String COMMA = ",";

    String DOT = ".";

    String DOTDOT = "..";

    String QUESTION_MARK = "?";

    String EMPTY = "";

    String NEWLINE = "\n";

    String NEWLINE_HTML = "<br>";

    String TAB = "\t";

    String RETURN = "\r";

    String CRLF = "\r\n";

    String SPACE = " ";

    String NULL = "null";

    String TRUE = "true";

    String FALSE = "false";

    String YES = "yes";

    String NO = "no";

    String Y = "y";

    String N = "n";

    String ON = "on";

    String OFF = "off";

    String ZERO = "0";

    String ONE = "1";

    /**
     * 成功
     */
    String SUCCESS = "SUCCESS";

    /**
     * 失败
     */
    String FAILED = "FAILED";

    String SUCCESS_CODE = ZERO;

    String FAILED_CODE = ONE;


    String DOT_XML = ".xml";

    String HTML_NBSP = "&nbsp;";

    String HTML_AMP = "&amp;";

    String HTML_QUOTE = "&quot;";

    String HTML_LT = "&lt;";

    String HTML_GT = "&gt;";

    String DOLLAR_LEFT_BRACE = "${";

    String HASH_LEFT_BRACE = "#{";

    /**
     * 系统字符集编码
     */
    String CHARSET_DEFAULT = Charset.defaultCharset().name();

    /**
     * java项目根路径
     */
    String PROJECT_PATH = System.getProperty("user.dir")  + File.separatorChar;

    /**
     * 操作系统的临时目录 Temp\  已经携带 File.separatorChar 字符
     */
    String JAVA_TMP_PATH = System.getProperty("java.io.tmpdir");

}
