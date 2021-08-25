package com.github.mengweijin.mybatisplus.demo.ComponentScan.test;

import lombok.Data;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@Data
@Component
@ComponentScan(value = "com.github.mengweijin.mybatisplus.demo.ComponentScan.entity")
public class ComponentScanConfiguration {

}
