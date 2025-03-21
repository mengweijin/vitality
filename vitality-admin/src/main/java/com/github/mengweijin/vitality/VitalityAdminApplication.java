package com.github.mengweijin.vitality;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author mengweijin
 */
@Slf4j
@SpringBootApplication
public class VitalityAdminApplication {

	public static void main(String[] args) {
		try {
            long start = System.currentTimeMillis();
			SpringApplication.run(VitalityAdminApplication.class, args);
            long end = System.currentTimeMillis();
            log.info("-----------------------------------------------------------------------");
            log.info("----------- Vitality Admin startup success!");
            log.info("----------- Started Vitality Admin in {} seconds", (end - start) / 1000D);
            log.info("-----------------------------------------------------------------------");
		} catch (Exception e) {
            log.error("----------------------------------------------------------------------");
            log.error("---------- Vitality Admin startup failed!");
            log.error("----------------------------------------------------------------------");
		}
	}
}
