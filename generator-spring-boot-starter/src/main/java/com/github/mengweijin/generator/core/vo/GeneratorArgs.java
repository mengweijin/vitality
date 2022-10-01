package com.github.mengweijin.generator.core.vo;

import cn.hutool.system.SystemUtil;
import com.github.mengweijin.generator.util.Utils;
import lombok.Data;

/**
 * @author mengweijin
 * @date 2022/9/3
 */
@Data
public class GeneratorArgs {

    private String templateId;

    private String tableName;

    private boolean ignorePrefix;

    private boolean ignoreSuffix;

    private String author = SystemUtil.get("user.name", false);

    private String packageName = Utils.getMainClassPackage();
}
