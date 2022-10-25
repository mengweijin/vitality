package com.github.mengweijin.quickboot.demo.controller;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.mengweijin.quickboot.demo.async.AsyncFactory;
import com.github.mengweijin.quickboot.demo.entity.User;
import com.github.mengweijin.quickboot.demo.enums.Gender;
import com.github.mengweijin.quickboot.demo.service.UserService;
import com.github.mengweijin.quickboot.mybatis.Pager;
import com.github.mengweijin.quickboot.redis.inteceptor.RepeatSubmit;
import com.github.mengweijin.quickboot.redis.limiter.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * UserController API
 *
 * @author mengweijin
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AsyncFactory asyncFactory;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Cache 测试接口
     *
     * @apiNote 注解 {@link org.springframework.cache.annotation.Cacheable Cacheable} 中的参数 {@code cacheNames} 表示使用哪一个缓存名称。
     * 注解 CacheExpired 中的参数：
     * {@code expire} 表示过期时间。
     * {@code chronoUnit} 表示过期时间单位。
     * @return {@link String String}
     */
    //@CacheExpired(expire = 10, chronoUnit = ChronoUnit.SECONDS)
    @Cacheable(cacheNames = "user")
    @GetMapping("/cache")
    public String hello(){
        log.info("Entered hello method.");
        return "Hello";
    }

    /**
     * 测试 RepeatableFilter Api
     *
     * @apiNote 请求：http://localhost:8080/user/repeatable?id=12
     * requestBody={ "name":"aaaaa" }
     * 1. 未添加 RepeatableFilter 时，在 LogAspect 类中报错，
     *      因为在 SpringMVC 中为了映射 @RequestBody 注释的参数，已经读取过一次 RequestBody 的流数据了，
     *      而流默认只能被读取一次，第二次就读不到数据了
     * 2. 添加 RepeatableFilter 后，返回正常结果 id=12, name=aaaaa。此时 LogAspect 类中记录的日志也正常。
     * @param id RequestParam
     * @param name RequestBody
     */
    @PostMapping("/repeatable")
    public void repeatable(@RequestParam String id, @RequestBody String name){
        log.info("id=" + id);
        log.info("name=" + name);
    }

    /**
     * 测试 XSS API
     *
     * @apiNote http://localhost:8080/user/xss?html=&lt;script&gt;aaaaa&lt;/script&gt;bbbb
     * @return bbbb
     */
    @GetMapping("/xss")
    public String xss(String html){
        log.info("Entered xss method.");
        return html;
    }

    @RateLimiter(count = 3)
    @GetMapping("/list")
    public List<User> getUser() {
        return userService.list();
    }

    @GetMapping("/call")
    public String callApi(String api) throws JsonProcessingException {
        log.info("api=" + api);
        Object object = restTemplate.getForObject(api, Object.class);
        log.info("object=" + object);
        String result = objectMapper.writeValueAsString(object);
        log.info("result=" + result);
        return result;
    }

    @GetMapping("/page")
    public IPage<User> getPage(Page<User> page){
        return userService.page(page);
    }

    @GetMapping("/pager")
    public Pager<User> getPager(Pager<User> pager) {
        final IPage<User> page = userService.page(pager.toPage());
        return pager.toPager(page);
    }

    @GetMapping("/get/{id}")
    public User getUserById(@PathVariable("id") Long id){
        return userService.getById(id);
    }

    @RepeatSubmit
    @PostMapping("/save")
    public void addUser(){
        User user = new User();
        user.setName("user" + DateUtil.format(new Date(), DatePattern.NORM_DATETIME_MS_PATTERN));
        user.setGender(Gender.MALE);
        user.setDeleted(0);
        userService.saveOrUpdate(user);
    }

    @PutMapping("/update")
    public void updateUser(@RequestBody User user){
        userService.saveOrUpdate(user);
    }

    @DeleteMapping("/delete/{id}")
    public void updateUser(@PathVariable("id") Long id){
        userService.removeById(id);
    }

    @GetMapping("/async")
    public String async(String name) throws ExecutionException, InterruptedException {
        Future<String> stringFuture = asyncFactory.doTaskInAsync(name);
        log.info("Continue execute code.");
        String s = stringFuture.get();
        log.info("Async Completed. result=" + s);
        return "async";
    }
}
