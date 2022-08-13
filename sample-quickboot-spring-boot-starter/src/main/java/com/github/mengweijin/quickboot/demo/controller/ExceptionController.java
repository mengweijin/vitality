package com.github.mengweijin.quickboot.demo.controller;

import com.github.mengweijin.quickboot.exception.QuickBootException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author mengweijin
 */
@Slf4j
@RestController
@RequestMapping("/exception")
public class ExceptionController {

    @PostMapping
    public void exception() {
        throw new QuickBootException("Test QuickBootException");
    }
}
