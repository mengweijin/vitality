package com.github.mengweijin.framework.util.excel;

import org.apache.poi.ss.usermodel.Sheet;
import org.dromara.hutool.core.io.file.FileUtil;
import org.dromara.hutool.poi.excel.ExcelUtil;
import org.dromara.hutool.poi.excel.style.DefaultStyleSet;
import org.dromara.hutool.poi.excel.writer.ExcelWriteConfig;
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
class ExcelUtilsBeanTest {

    public static String TARGET = System.getProperty("user.dir") + "/target/export.xlsx";

    @Test
    void export() {
        FileUtil.del(TARGET);

        // 通过工具类创建writer
        ExcelWriter writer = ExcelUtil.getWriter(TARGET);

        //自定义标题别名
        Map<String, String> map = new LinkedHashMap<>();
        map.put("name", "姓名");
        map.put("age", "年龄");
        map.put("score", "分数");
        map.put("isPass", "是否通过");
        map.put("examDate", "考试时间");
        ExcelWriteConfig config = writer.getConfig();
        config.setHeaderAlias(map);

        // 默认的，未添加alias的属性也会写出，如果想只写出加了别名的字段，可以调用此方法排除之
        config.setOnlyAlias(true);

        // 自动换行
        DefaultStyleSet styleSet = (DefaultStyleSet) writer.getStyleSet();
        styleSet.setWrapText();

        // 合并单元格后的标题行，使用默认标题样式
        writer.merge(4, "一班成绩单");
        // 一次性写出内容，使用默认样式，强制输出标题
        writer.write(testData(), true);

        // 列宽自适应（官方方法中文宽度有问题）
        // writer.autoSizeColumnAll(true, 0F);
        // writer.autoSizeColumnAll(true, 2F);
        // 列宽自适应
        autoSizeColumn(writer);

        // 关闭writer，释放内存
        writer.close();
    }

    /**
     * Hutool工具类ExcelWriter导出Excel列宽自适应：
     * 官方已经提供了解决方案，可以直接调用：writer.autoSizeColumnAll();
     * 这一行代码一定要在写出内容后调用，即：如果你的代码中有类似writer.write(list)的片段，一定要放在这个片段之后，否则不会生效！
     * <p>
     * 同时，在 hutool 低版本中 autoSizeColumn() 对于中文的支持并不完美，会出现宽度不足的情况。解决方法如下。
     * 使用这个方法，就可以不用再使用官方提供的方法了。
     * <p>
     * 如果使用新版本，直接使用官方方法即可。
     *
     * @param writer ExcelWriter
     */
    public static void autoSizeColumn(ExcelWriter writer) {
        // 单元格最大宽度有限制，超过会报错
        int maxWith = 256 * 255;

        Sheet sheet = writer.getSheet();
        for (int i = 0; i < writer.getColumnCount(); i++) {
            int orgWidth = sheet.getColumnWidth(i);
            // 调整每一列宽度
            sheet.autoSizeColumn(i);
            // 解决自动设置列宽中文不准确的问题
            int newWidth = sheet.getColumnWidth(i) * 2;
            if (newWidth > maxWith) {
                sheet.setColumnWidth(i, maxWith);
            } else {
                sheet.setColumnWidth(i, Math.max(newWidth, orgWidth));
            }
        }
    }

    private List<TestBean> testData() {
        TestBean bean1 = new TestBean();
        bean1.setName("张三");
        bean1.setAge(22);
        bean1.setPass(true);
        bean1.setScore(66.30);
        bean1.setExamDate(LocalDateTime.now());

        TestBean bean2 = new TestBean();
        bean2.setName("李四（设置所有列为自动宽度测试）阿斯顿法国红酒看来微软推哦怕时代法国红酒看来的风格和健康的法国红酒看来法国红酒看来法国红酒看来的风格和健康阿斯顿法国红酒看来微软推哦怕时代法国红酒看来的风格和健康的法国红酒看来法国红酒看来法国红酒看来的风格和健康阿斯顿法国红酒看来微软推哦怕时代法国红酒看来的风格和健康的法国红酒看来法国红酒看来法国红酒看来的风格和健康阿斯顿法国红酒看来微软推哦怕时代法国红酒看来的风格和健康的法国红酒看来法国红酒看来法国红酒看来的风格和健康");
        bean2.setAge(28);
        bean2.setPass(false);
        bean2.setScore(38.50);
        bean2.setExamDate(LocalDateTime.now());

        List<TestBean> list = new ArrayList<>();
        Collections.addAll(list, bean1, bean2);
        return list;
    }

}
