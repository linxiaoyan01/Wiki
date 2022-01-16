package top.kaluna.wiki.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.kaluna.wiki.domain.Test;
import top.kaluna.wiki.service.TestService;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author Yuery
 * @date 2022/1/15/0015 - 21:22
 */
@RestController
public class TestController {
    @Value("${test.hello:TEST}")
    private String testHello;

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
}
