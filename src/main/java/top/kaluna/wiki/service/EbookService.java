package top.kaluna.wiki.service;

import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import top.kaluna.wiki.domain.Ebook;
import top.kaluna.wiki.domain.EbookExample;
import top.kaluna.wiki.mapper.EbookMapper;
import top.kaluna.wiki.req.EbookReq;
import top.kaluna.wiki.resp.EbookResp;
import top.kaluna.wiki.util.CopyUtil;

import javax.annotation.Resource;
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
        if(!ObjectUtils.isEmpty(ebookReq.getName())){
            criteria.andNameLike("%"+ebookReq.getName()+"%");
        }
        final List<Ebook> ebooks = ebookMapper.selectByExample(ebookExample);

        final List<EbookResp> respsList = CopyUtil.copyList(ebooks, EbookResp.class);
        return respsList;
    }
}
