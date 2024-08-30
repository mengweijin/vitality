package com.github.mengweijin.vitality.system.controller;

import com.github.mengweijin.vitality.system.service.SseService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

/**
 * @author mengweijin
 */
@Slf4j
@AllArgsConstructor
@Validated
@RestController
@RequestMapping("/sse")
public class SseController {

    private SseService sseService;

    /**
     * @param token token
     * @return SseEmitter
     */
    @GetMapping("/connect")
    public SseEmitter connect(String token) {
        return sseService.connect(token);
    }

}
