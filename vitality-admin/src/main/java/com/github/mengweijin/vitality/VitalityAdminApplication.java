package com.github.mengweijin.vitality;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author mengweijin
 */
@Slf4j
@MapperScan({"com.github.mengweijin.**.mapper"})
@ComponentScan({"com.github.mengweijin"})
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
