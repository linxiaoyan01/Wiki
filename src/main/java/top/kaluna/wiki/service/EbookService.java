package top.kaluna.wiki.service;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import top.kaluna.wiki.domain.Ebook;
import top.kaluna.wiki.domain.EbookExample;
import top.kaluna.wiki.mapper.EbookMapper;
import top.kaluna.wiki.req.EbookReq;
import top.kaluna.wiki.resp.EbookResp;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Yuery
 * @date 2022/1/16/0016 - 14:11
 */
@Service
public class EbookService {
    @Resource
    private EbookMapper ebookMapper;

    public List<EbookResp> list(EbookReq ebookReq){
        EbookExample ebookExample = new EbookExample();
        final EbookExample.Criteria criteria = ebookExample.createCriteria();
        criteria.andNameLike("%"+ebookReq.getName()+"%");
        final List<Ebook> ebooks = ebookMapper.selectByExample(ebookExample);

        List<EbookResp> respsList = new ArrayList<>();
        for (Ebook ebook :
                ebooks) {
            EbookResp ebookResp = new EbookResp();
            BeanUtils.copyProperties(ebook, ebookResp);
            respsList.add(ebookResp);
        }
        return respsList;
    }
}
