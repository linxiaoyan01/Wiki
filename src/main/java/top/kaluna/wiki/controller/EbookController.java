package top.kaluna.wiki.controller;

import org.springframework.web.bind.annotation.*;
import top.kaluna.wiki.req.EbookQueryReq;
import top.kaluna.wiki.req.EbookSaveReq;
import top.kaluna.wiki.resp.CommonResp;
import top.kaluna.wiki.resp.EbookQueryResp;
import top.kaluna.wiki.resp.PageResp;
import top.kaluna.wiki.service.EbookService;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author Yuery
 * @date 2022/1/15/0015 - 23:20
 */
@RestController
@RequestMapping("/ebook")
public class EbookController {

    @Resource
    private EbookService ebookService;

    @GetMapping("/list")
    public CommonResp<PageResp<EbookQueryResp>> list(@Valid EbookQueryReq ebookQueryReq){
        CommonResp<PageResp<EbookQueryResp>> resp = new CommonResp<>();
        PageResp<EbookQueryResp> list = ebookService.list(ebookQueryReq);
        resp.setContent(list);
        return resp;
    }
    @PostMapping("/save")
    public CommonResp save(@RequestBody EbookSaveReq ebookQueryReq){
        CommonResp resp = new CommonResp<>();
        ebookService.save(ebookQueryReq);
        return resp;
    }
    @DeleteMapping("/delete/{id}")
    public CommonResp delete(@PathVariable Long id){
        CommonResp resp = new CommonResp<>();
        ebookService.delete(id);
        return resp;
    }
}
