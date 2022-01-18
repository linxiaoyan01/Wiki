package top.kaluna.wiki.controller;

import org.springframework.web.bind.annotation.*;
import top.kaluna.wiki.req.CategoryQueryReq;
import top.kaluna.wiki.req.CategorySaveReq;
import top.kaluna.wiki.resp.CommonResp;
import top.kaluna.wiki.resp.CategoryQueryResp;
import top.kaluna.wiki.resp.PageResp;
import top.kaluna.wiki.service.CategoryService;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author Yuery
 * @date 2022/1/15/0015 - 23:19
 */
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Resource
    private CategoryService categoryService;

    @GetMapping("/list")
    public CommonResp<PageResp<CategoryQueryResp>> list(@Valid CategoryQueryReq categoryQueryReq){
        CommonResp<PageResp<CategoryQueryResp>> resp = new CommonResp<>();
        PageResp<CategoryQueryResp> list = categoryService.list(categoryQueryReq);
        resp.setContent(list);
        return resp;
    }
    @PostMapping("/save")
    public CommonResp save(@Valid @RequestBody CategorySaveReq categoryQueryReq){
        CommonResp resp = new CommonResp<>();
        categoryService.save(categoryQueryReq);
        return resp;
    }
    @DeleteMapping("/delete/{id}")
    public CommonResp delete(@PathVariable Long id){
        CommonResp resp = new CommonResp<>();
        categoryService.delete(id);
        return resp;
    }
}
