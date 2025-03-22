package com.github.mengweijin.framework.util.excel;

import org.dromara.hutool.core.io.file.FileUtil;
import org.dromara.hutool.poi.excel.ExcelUtil;
import org.dromara.hutool.poi.excel.style.DefaultStyleSet;
import org.dromara.hutool.poi.excel.writer.ExcelWriter;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author mengweijin
 */
class ExcelUtilsMapTest {

    public static String TARGET = System.getProperty("user.dir") + "/target/export.xlsx";

    @Test
    void export() {
        FileUtil.del(TARGET);

        // 通过工具类创建writer
        ExcelWriter writer = ExcelUtil.getWriter(TARGET);

        // 自动换行
        DefaultStyleSet styleSet = (DefaultStyleSet) writer.getStyleSet();
        styleSet.setWrapText();

        // 合并单元格后的标题行，使用默认标题样式
        writer.merge(4, "一班成绩单");
        // 一次性写出内容，使用默认样式，强制输出标题
        writer.write(testMapData(), true);

        // 列宽自适应（官方方法中文宽度有问题）
        ExcelUtilsBeanTest.autoSizeColumn(writer);

        // 关闭writer，释放内存
        writer.close();
    }


    private List<Map<String, Object>> testMapData() {
        Map<String, Object> row1 = new LinkedHashMap<>();
        row1.put("姓名", "张三");
        row1.put("年龄", 23);
        row1.put("成绩", 88.32);
        row1.put("是否合格", true);
        row1.put("考试日期", LocalDateTime.now());

        Map<String, Object> row2 = new LinkedHashMap<>();
        row2.put("姓名", "李四（设置所有列为自动宽度测试）阿斯顿法国红酒看来微软推哦怕时代法国红酒看来的风格和健康的法国红酒看来法国红酒看来法国红酒看来的风格和健康阿斯顿法国红酒看来微软推哦怕时代法国红酒看来的风格和健康的法国红酒看来法国红酒看来法国红酒看来的风格和健康阿斯顿法国红酒看来微软推哦怕时代法国红酒看来的风格和健康的法国红酒看来法国红酒看来法国红酒看来的风格和健康阿斯顿法国红酒看来微软推哦怕时代法国红酒看来的风格和健康的法国红酒看来法国红酒看来法国红酒看来的风格和健康");
        row2.put("年龄", 33);
        row2.put("成绩", 59.50);
        row2.put("是否合格", false);
        row2.put("考试日期", LocalDateTime.now());

        List<Map<String, Object>> list = new ArrayList<>();
        Collections.addAll(list, row1, row2);
        return list;
    }

}
