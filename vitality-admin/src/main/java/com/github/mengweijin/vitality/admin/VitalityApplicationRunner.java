package com.github.mengweijin.vitality.admin;

import com.github.mengweijin.vitality.system.service.VtlUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @author mengweijin
 * @date 2023/6/6
 */
@Component
public class VitalityApplicationRunner implements ApplicationRunner {

    @Autowired
    private VtlUserService userService;

    @Override
    public void run(ApplicationArguments args) throws Exception {

    }
}
