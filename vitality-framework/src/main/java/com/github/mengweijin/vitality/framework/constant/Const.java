package com.github.mengweijin.vitality.framework.constant;

import java.io.File;
import java.nio.charset.Charset;

/**
 * 通用常量信息
 *
 * @author Meng Wei Jin
 **/
public final class Const {

    public static final String TILDA = "~";

    public static final String BACKTICK = "`";

    public static final String EXCLAMATION_MARK = "!";

    public static final String AT = "@";

    public static final String HASH = "#";

    public static final String DOLLAR = "$";

    public static final String PERCENT = "%";

    public static final String CARET = "^";

    public static final String AMPERSAND = "&";

    public static final String ASTERISK = "*";

    public static final String STAR = ASTERISK;

    public static final String LEFT_BRACKET = "(";

    public static final String RIGHT_BRACKET = ")";

    public static final String DASH = "-";

    public static final String UNDERSCORE = "_";

    public static final String PLUS = "+";

    public static final String EQUAL = "=";

    public static final String LEFT_BRACE = "{";

    public static final String RIGHT_BRACE = "}";

    public static final String LEFT_SQ_BRACKET = "[";

    public static final String RIGHT_SQ_BRACKET = "]";

    public static final String PIPE = "|";

    public static final String BACK_SLASH = "\\";

    public static final String SLASH = "/";

    public static final String COLON = ":";

    public static final String SEMICOLON = ";";

    public static final String QUOTE = "\"";

    public static final String SINGLE_QUOTE = "'";

    public static final String LEFT_CHEV = "<";

    public static final String RIGHT_CHEV = ">";

    public static final String COMMA = ",";

    public static final String DOT = ".";

    public static final String DOTDOT = "..";

    public static final String QUESTION_MARK = "?";

    public static final String EMPTY = "";

    public static final String NEWLINE = "\n";

    public static final String NEWLINE_HTML = "<br>";

    public static final String TAB = "\t";

    public static final String RETURN = "\r";

    public static final String CRLF = "\r\n";

    public static final String SPACE = " ";

    public static final String NULL = "null";

    public static final String TRUE = "true";

    public static final String FALSE = "false";

    public static final String YES = "YES";

    public static final String NO = "NO";

    public static final String Y = "Y";

    public static final String N = "N";

    public static final String ON = "ON";

    public static final String OFF = "OFF";

    public static final String ZERO = "0";

    public static final String ONE = "1";

    /**
     * 成功
     */
    public static final String SUCCESS = "SUCCESS";

    /**
     * 失败
     */
    public static final String FAILURE = "FAILURE";

    public static final String SUCCESS_CODE = ZERO;

    public static final String FAILURE_CODE = ONE;


    public static final String DOT_XML = ".xml";

    public static final String HTML_NBSP = "&nbsp;";

    public static final String HTML_AMP = "&amp;";

    public static final String HTML_QUOTE = "&quot;";

    public static final String HTML_LT = "&lt;";

    public static final String HTML_GT = "&gt;";

    public static final String DOLLAR_LEFT_BRACE = "${";

    public static final String HASH_LEFT_BRACE = "#{";

    /**
     * 系统字符集编码
     */
    public static final String CHARSET_DEFAULT = Charset.defaultCharset().name();

    /**
     * java项目根路径
     */
    public static final String PROJECT_PATH = System.getProperty("user.dir")  + File.separatorChar;

    /**
     * 操作系统的临时目录 Temp\  已经携带 File.separatorChar 字符
     */
    public static final String JAVA_TMP_PATH = System.getProperty("java.io.tmpdir");

}
