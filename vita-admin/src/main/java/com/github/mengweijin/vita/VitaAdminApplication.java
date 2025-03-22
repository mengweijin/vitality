package com.github.mengweijin.vita;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author mengweijin
 */
@Slf4j
@SpringBootApplication
public class VitaAdminApplication {

	public static void main(String[] args) {
		try {
            long start = System.currentTimeMillis();
			SpringApplication.run(VitaAdminApplication.class, args);
            long end = System.currentTimeMillis();
            log.info("-----------------------------------------------------------------------");
            log.info("----------- Vita Admin startup success!");
            log.info("----------- Started Vita Admin in {} seconds", (end - start) / 1000D);
            log.info("-----------------------------------------------------------------------");
		} catch (Exception e) {
            log.error("----------------------------------------------------------------------");
            log.error("---------- Vita Admin startup failed!");
            log.error("----------------------------------------------------------------------");
		}
	}
}
