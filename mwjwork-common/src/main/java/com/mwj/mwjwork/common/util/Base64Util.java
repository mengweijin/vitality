package com.mwj.mwjwork.common.util;

import com.mwj.mwjwork.common.constant.Const;
import lombok.extern.slf4j.Slf4j;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.nio.charset.Charset;

/**
 * @author Meng Wei Jin
 * @description
 **/
@Slf4j
public class Base64Util {

    private static BASE64Encoder base64Encoder = new BASE64Encoder();

    private static BASE64Decoder base64Decoder = new BASE64Decoder();

    /**
     * 编码字符串
     * @param str
     * @return
     */
    public static String encode(String str){
        return base64Encoder.encode(str.getBytes(Charset.forName(Const.UTF_8)));
    }

    /**
     * 解码字符串
     * @param str
     * @return
     */
    public static String decode(String str) throws IOException {
        return new String(base64Decoder.decodeBuffer(str), Const.UTF_8);
    }

    /**
     * 编码文件
     * 注意：Base64位加密文件可能在服务器端将+号自动处理为“ ”空字符串，
     * 	所以如果发生这种情况，解码时需要替换回来。
     * @param file
     * @return
     */
    public static String encode(File file) throws IOException {
        try(
            InputStream in = new FileInputStream(file);
            ByteArrayOutputStream out = new ByteArrayOutputStream()
        ){
            StringBuilder sb = new StringBuilder();
            byte[] buffer = new byte[1024];
            int len;
            while ((len = in.read(buffer)) != -1) {
                out.write(buffer, 0, len);
                sb.append(base64Encoder.encode(out.toByteArray()));
                out.reset();
            }
            return sb.toString();
        }catch (IOException e){
            log.error(e.getMessage(), e);
            throw new IOException(e);
        }
    }

    /**
     * 解码到文件
     * @param base64Code
     * @param file
     * @return
     */
    public static void decode(String base64Code, File file) throws IOException {
        try(FileOutputStream out = new FileOutputStream(file)){
            byte[] decoderBytes = base64Decoder.decodeBuffer(base64Code);
            out.write(decoderBytes);
        } catch (IOException e){
            log.error(e.getMessage(), e);
            throw new IOException(e);
        }
    }

}
