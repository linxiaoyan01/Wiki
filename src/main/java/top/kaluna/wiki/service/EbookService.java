package top.kaluna.wiki.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger LOG = LoggerFactory.getLogger(EbookService.class);

    public List<EbookResp> list(EbookReq ebookReq){

        EbookExample ebookExample = new EbookExample();
        final EbookExample.Criteria criteria = ebookExample.createCriteria();
        if(!ObjectUtils.isEmpty(ebookReq.getName())){
            criteria.andNameLike("%"+ebookReq.getName()+"%");
        }
        PageHelper.startPage(1, 3);
        final List<Ebook> ebooks = ebookMapper.selectByExample(ebookExample);

        PageInfo<Ebook> pageInfo = new PageInfo<>(ebooks);
        LOG.info("总行数：{}", pageInfo.getTotal());
        LOG.info("总页数：{}",pageInfo.getPages());

        final List<EbookResp> respsList = CopyUtil.copyList(ebooks, EbookResp.class);

        return respsList;
    }
}
