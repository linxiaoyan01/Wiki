package top.kaluna.wiki.controller;

import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author Yuery
 * @date 2022/1/15/0015 - 21:22
 */
@RestController
public class HelloController {
    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }
    @PostMapping("/hello/post")
    public String helloPost(@RequestParam Map<String, String> map){
        return "hello post, " + map.get("name");
    }
}
