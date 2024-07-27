package com.github.mengweijin.system.controller;

import com.github.mengweijin.framework.mvc.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author mengweijin
 * @date 2022/10/30
 */
@Controller
@Validated
public class IndexController extends BaseController {

    @GetMapping({"/", "/index" })
    public String index() {
        return redirect("vitality/index.html");
    }

}
