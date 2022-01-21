package top.kaluna.wiki.controller;

import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import top.kaluna.wiki.req.UserLoginReq;
import top.kaluna.wiki.req.UserQueryReq;
import top.kaluna.wiki.req.UserResetPasswordReq;
import top.kaluna.wiki.req.UserSaveReq;
import top.kaluna.wiki.resp.CommonResp;
import top.kaluna.wiki.resp.PageResp;
import top.kaluna.wiki.resp.UserLoginResp;
import top.kaluna.wiki.resp.UserQueryResp;
import top.kaluna.wiki.service.UserService;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.nio.charset.StandardCharsets;

/**
 * @author Yuery
 * @date 2022/1/15/0015 - 23:20
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @GetMapping("/list")
    public CommonResp<PageResp<UserQueryResp>> list(@Valid UserQueryReq userQueryReq){
        CommonResp<PageResp<UserQueryResp>> resp = new CommonResp<>();
        PageResp<UserQueryResp> list = userService.list(userQueryReq);
        resp.setContent(list);
        return resp;
    }
    @PostMapping("/save")
    public CommonResp save(@Valid @RequestBody UserSaveReq userSaveReq){
        userSaveReq.setPassword(DigestUtils.md5DigestAsHex(userSaveReq.getPassword().getBytes(StandardCharsets.UTF_8)));
        CommonResp resp = new CommonResp<>();
        userService.save(userSaveReq);
        return resp;
    }
    @DeleteMapping("/delete/{id}")
    public CommonResp delete(@PathVariable Long id){
        CommonResp resp = new CommonResp<>();
        userService.delete(id);
        return resp;
    }
    @PostMapping("/reset-password")
    public CommonResp resetPassword(@Valid @RequestBody UserResetPasswordReq req){
        req.setPassword(DigestUtils.md5DigestAsHex(req.getPassword().getBytes(StandardCharsets.UTF_8)));
        CommonResp resp = new CommonResp();
        userService.resetPassword(req);
        return resp;
    }
    @PostMapping("/login")
    public CommonResp login(@Valid @RequestBody UserLoginReq req){
        req.setPassword(DigestUtils.md5DigestAsHex(req.getPassword().getBytes(StandardCharsets.UTF_8)));
        CommonResp<UserLoginResp> resp = new CommonResp();
        UserLoginResp userLoginResp = userService.login(req);
        resp.setContent(userLoginResp);
        return resp;
    }

}
