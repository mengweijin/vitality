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
			SpringApplication.run(VitalityAdminApplication.class, args);
			log.info("Vitality startup success!");
		} catch (Exception e) {
			log.info("Vitality startup failed!");
		}
	}
}
