package top.kaluna.wiki.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import top.kaluna.wiki.domain.Test;
import top.kaluna.wiki.service.TestService;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author Yuery
 * @date 2022/1/15/0015 - 21:22
 */
@RestController

public class TestController {
    @Value("${test.hello:TEST}")
    private String testHello;

    @Resource
    private RedisTemplate<Long, String> redisTemplate;

    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    @Resource
    private TestService testService;
    @GetMapping("/hello")
    public String hello(){
        return "hello"+ testHello;
    }
    @PostMapping("/hello/post")
    public String helloPost(@RequestParam Map<String, String> map){
        return "hello post, " + map.get("name");
    }

    @GetMapping("/test/list")
    public List<Test> list(){
        return testService.list();
    }

    @RequestMapping("/test/redis/set/{key}/{value}")
    public String set(@PathVariable Long key, @PathVariable String value){
        redisTemplate.opsForValue().set(key, value,3600, TimeUnit.SECONDS);
        LOG.info("key:{},value:{}",key,value);
        return "success";
    }
    @RequestMapping("/test/redis/get/{key}")
    public Object get(@PathVariable Long key){
        Object object = redisTemplate.opsForValue().get(key);
        LOG.info("key:{},value:{}",key,object);
        return object;
    }
}
