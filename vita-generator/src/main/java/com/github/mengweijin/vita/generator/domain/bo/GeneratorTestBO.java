package com.github.mengweijin.vita.generator.domain.bo;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.dromara.hutool.core.io.file.FileUtil;

import java.io.File;

/**
 * Test Only.
 *
 * @author mengweijin
 * @since 2022/11/27
 */
@Deprecated
@Data
public class GeneratorTestBO {

    @NotBlank
    private String templateId;

    @NotBlank
    private String tableName;

    public GeneratorArgsBO toGeneratorArgsBO() {
        File file = FileUtil.file(this.templateId);
        GeneratorArgsBO args = new GeneratorArgsBO();
        args.setTemplateId(this.templateId);
        args.setTemplateName(file.getName());
        args.setTableName(this.tableName);
        args.setTemplateContent(FileUtil.readUtf8String(file));
        return args;
    }

}
