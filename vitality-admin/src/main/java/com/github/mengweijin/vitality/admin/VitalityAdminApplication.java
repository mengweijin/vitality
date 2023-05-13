package com.github.mengweijin.vitality.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author mengweijin
 */
@MapperScan({ "com.github.mengweijin.vitality.**.mapper" })
@SpringBootApplication
public class VitalityAdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(VitalityAdminApplication.class, args);
	}
}
