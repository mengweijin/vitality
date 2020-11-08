package com.github.mengweijin.quickboot.sample.system.controller;

import com.github.mengweijin.quickboot.framework.web.BaseController;
import com.github.mengweijin.quickboot.jpa.page.Pager;
import com.github.mengweijin.quickboot.sample.system.entity.Address;
import com.github.mengweijin.quickboot.sample.system.entity.User;
import com.github.mengweijin.quickboot.sample.system.service.AddressService;
import com.github.mengweijin.quickboot.sample.system.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * @author Meng Wei Jin
 * @description
 * @date Create in 2019-07-27 17:27
 **/
@Api(value = "用户接口API", tags = "用户接口")
@Validated
@RestController
@RequestMapping("/user")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private AddressService addressService;

    @PostMapping("/insert")
    public void insert(@Valid User user, @Valid Address address) {
        Address address1 = addressService.save(address);
        user.setAddressId(address1.getId());
        User user1 = userService.saveAndFlush(user);
    }

    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @ApiOperation(value = "update 更新用户", notes = "update 更新用户 notes", responseContainer = "List")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户ID")
    })
    @PostMapping("/update/{id}")
    public void update(@PathVariable("id") Long id) {
        User user = new User();
        user.setId(id);
        user.setName("Tom");
        userService.update(id, user);
    }

    @GetMapping("/all")
    public List<User> findAll() {
        return userService.findAll();
    }

    @GetMapping("/page")
    public Page findPage(Pager pager) {
        Pageable pageable = PageRequest.of(pager.getCurrent(), pager.getSize());
        return userService.findAll(pageable);
    }

    @GetMapping("/dsl")
    public List<User> findAllByQueryDsl(User user) {
        return userService.findAllByQueryDsl(user);
    }

    @GetMapping("/nativeSql")
    public List<Object> findStudentAddress(String userName, String city) {
        return userService.findStudentAddress(userName, city);
    }
}
