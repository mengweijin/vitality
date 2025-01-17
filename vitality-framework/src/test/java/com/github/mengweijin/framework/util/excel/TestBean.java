package com.github.mengweijin.framework.util.excel;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author mengweijin
 */
@Data
public class TestBean {
    private String name;
    private int age;
    private double score;
    private boolean isPass;
    private LocalDateTime examDate;
}
