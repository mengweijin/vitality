package com.github.mengweijin.vitality.generator.domain.bo;

import com.github.mengweijin.vitality.framework.util.SpringBootMainClassUtils;
import lombok.Data;
import org.dromara.hutool.core.util.SystemUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mengweijin
 * @since 2022/11/27
 */
@Data
public class GeneratorArgsBO {

    private String templateId;

    private String tableName;

    private List<String> tablePrefix = new ArrayList<>(List.of("VTL_", "SYS_"));

    private String packages = SpringBootMainClassUtils.getSpringBootApplicationClassPackage();

    private String module = "system";

    private String author = SystemUtil.get("user.name", false);
}
