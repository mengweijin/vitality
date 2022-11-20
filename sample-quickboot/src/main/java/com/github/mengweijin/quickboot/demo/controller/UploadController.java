package com.github.mengweijin.vitality.demo.controller;

import com.github.mengweijin.vitality.util.UploadUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * UserController API
 *
 * @author mengweijin
 */
@Slf4j
@RestController
@RequestMapping("/upload")
public class UploadController {

    @PostMapping
    public void upload(HttpServletRequest request) {
        List<String> list = UploadUtils.upload(request);
        // 或者
        //UploadUtils.upload(request, pathList -> { });
    }
}
