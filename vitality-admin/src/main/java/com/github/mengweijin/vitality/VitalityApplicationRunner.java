package com.github.mengweijin.vitality;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @author mengweijin
 * @since 2023/6/6
 */
@Slf4j
@Component
public class VitalityApplicationRunner implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.debug("startup");
    }
}
