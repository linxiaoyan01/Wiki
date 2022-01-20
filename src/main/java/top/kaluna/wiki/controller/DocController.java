package top.kaluna.wiki.controller;

import org.springframework.web.bind.annotation.*;
import top.kaluna.wiki.req.DocQueryReq;
import top.kaluna.wiki.req.DocSaveReq;
import top.kaluna.wiki.resp.CommonResp;
import top.kaluna.wiki.resp.DocQueryResp;
import top.kaluna.wiki.resp.PageResp;
import top.kaluna.wiki.service.DocService;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

/**
 * @author Yuery
 * @date 2022/1/15/0015 - 23:19
 */
@RestController
@RequestMapping("/doc")
public class DocController {

    @Resource
    private DocService docService;

    @GetMapping("/all/{ebookId}")
    public CommonResp all(@PathVariable Long ebookId){
        CommonResp<List<DocQueryResp>> resp = new CommonResp<>();
        List<DocQueryResp> list = docService.all(ebookId);
        resp.setContent(list);
        return resp;
    }
    @GetMapping("/list")
    public CommonResp<PageResp<DocQueryResp>> list(@Valid DocQueryReq docQueryReq){
        CommonResp<PageResp<DocQueryResp>> resp = new CommonResp<>();
        PageResp<DocQueryResp> list = docService.list(docQueryReq);
        resp.setContent(list);
        return resp;
    }
    @PostMapping("/save")
    public CommonResp save(@Valid @RequestBody DocSaveReq docQueryReq){
        CommonResp resp = new CommonResp<>();
        docService.save(docQueryReq);
        return resp;
    }
    @DeleteMapping("/delete/{idsStr}")
    public CommonResp delete(@PathVariable String idsStr){
        CommonResp resp = new CommonResp<>();
        List<String> list = Arrays.asList(idsStr.split(","));
        docService.delete(list);
        return resp;
    }
    @GetMapping("/find-content/{id}")
    public CommonResp<String> findContent(@PathVariable Long id){
        CommonResp<String> resp = new CommonResp<>();
        String content = docService.findContent(id);
        resp.setContent(content);
        return resp;
    }
}
