package com.github.mengweijin.vitality.util;

import com.alibaba.excel.EasyExcel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * 注解：@ExcelProperty(index = 2) {@link com.alibaba.excel.annotation.ExcelProperty}
 * @author mengweijin
 * @date 2022/11/20
 */
@Slf4j
public class ExcelUtils {

    /**
     * 读 Excel
     *
     * @param filePath 文件路径
     * @param cls 对象
     * */
    public static <T> List<T> read(String filePath, Class<T> cls) {
        return EasyExcel.read(filePath).head(cls).sheet().doReadSync();
    }

    /**
     * 写数据到指定的 Excel 文件
     *
     * @param targetFile 文件
     * @param cls
     * */
    public static <T> void write(Class<T> cls, List<T> list, File targetFile) {
        EasyExcel.write(targetFile, cls).sheet("sheet1").doWrite(list);
    }

    /**
     * 读 Excel
     *
     * @param request 请求对象
     * @param cls 对象
     * */
    public static <T> List<T> upload(HttpServletRequest request, Class<T> cls) {
        try {
            return EasyExcel.read(request.getInputStream()).head(cls).sheet().doReadSync();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 写 Excel
     *
     * @param response 响应对象
     * @param cls
     * */
    public static <T> void download(String fileName, Class<T> cls, List<T> list, HttpServletRequest request, HttpServletResponse response) {
        try{
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;fileName=" + DownLoadUtils.setFileName(request, fileName));
            EasyExcel.write(response.getOutputStream(), cls).sheet("sheet1").doWrite(list);
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
