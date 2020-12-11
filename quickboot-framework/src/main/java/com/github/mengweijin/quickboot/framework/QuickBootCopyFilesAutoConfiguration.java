package com.github.mengweijin.quickboot.framework;

import cn.hutool.system.OsInfo;
import com.github.mengweijin.quickboot.framework.constant.Const;
import com.github.mengweijin.quickboot.framework.util.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;

/**
 * @author Meng Wei Jin
 * @description 应用启动初始化
 * 注解@Order如果多个自定义ApplicationRunner，用来标明执行顺序，从小到大加载
 **/
@Deprecated
//@Configuration
@Order(1)
@Slf4j
public class QuickBootCopyFilesAutoConfiguration implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (new OsInfo().isWindows()) {
            // 从应用程序jar里面的classpath下的files目录，拷贝 7z等文件到应用程序jar包同级目录
            FileUtil.copyJarDirectoryToDirectory("files", Const.PROJECT_PATH);
            log.debug("Copy classpath:files to " + Const.PROJECT_PATH + "files finished!");
        }
    }

}
