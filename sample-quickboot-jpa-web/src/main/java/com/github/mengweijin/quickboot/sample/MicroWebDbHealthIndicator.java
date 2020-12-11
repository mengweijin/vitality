package com.github.mengweijin.quickboot.sample;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Import library spring-boot-starter-actuator
 * Customer HealthIndicator to check db health.
 * Similarly, you can check the health of the subservices.
 *
 * @author mengweijin
 */
@Slf4j
@Component
public class MicroWebDbHealthIndicator implements HealthIndicator {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Health health() {
        try {
            if (true) {
                throw new RuntimeException("The DB is not available.");
            }
            jdbcTemplate.execute("select 1");
            return new Health.Builder(Status.UP).build();
        } catch (RuntimeException e) {
            log.error("Health Check error!", e);
            Map<String, Object> map = new HashMap<>(1);
            map.put("error", e.getMessage());
            //return new Health.Builder(Status.DOWN, map).build();
            return new Health.Builder(Status.OUT_OF_SERVICE, map).build();
        }
    }
}
