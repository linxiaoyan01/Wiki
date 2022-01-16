package top.kaluna.wiki.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.kaluna.wiki.domain.Ebook;
import top.kaluna.wiki.resp.CommonResp;
import top.kaluna.wiki.service.EbookService;

import javax.annotation.Resource;
import java.util.List;

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
    public CommonResp<List<Ebook>> list(){
        CommonResp<List<Ebook>> resp = new CommonResp<>();
        List<Ebook> list = ebookService.list();
        resp.setContent(list);
        return resp;
    }
}
