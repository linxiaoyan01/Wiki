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
import top.kaluna.wiki.req.EbookQueryReq;
import top.kaluna.wiki.req.EbookSaveReq;
import top.kaluna.wiki.resp.EbookQueryResp;
import top.kaluna.wiki.resp.PageResp;
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

    public PageResp<EbookQueryResp> list(EbookQueryReq ebookQueryReq){

        EbookExample ebookExample = new EbookExample();
        final EbookExample.Criteria criteria = ebookExample.createCriteria();
        if(!ObjectUtils.isEmpty(ebookQueryReq.getName())){
            criteria.andNameLike("%"+ ebookQueryReq.getName()+"%");
        }
        //两个请求参数
        PageHelper.startPage(ebookQueryReq.getPage(), ebookQueryReq.getSize());
        final List<Ebook> ebooks = ebookMapper.selectByExample(ebookExample);

        PageInfo<Ebook> pageInfo = new PageInfo<>(ebooks);
        LOG.info("总行数：{}", pageInfo.getTotal());
        LOG.info("总页数：{}",pageInfo.getPages());

        final List<EbookQueryResp> respsList = CopyUtil.copyList(ebooks, EbookQueryResp.class);

        PageResp<EbookQueryResp> pageResp = new PageResp<>();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(respsList);
        return pageResp;
    }

    public void save(EbookSaveReq ebookQueryReq) {
        Ebook ebook = CopyUtil.copy(ebookQueryReq, Ebook.class);
        if(ObjectUtils.isEmpty(ebook)){
            //新增
            ebookMapper.insert(ebook);
        }else {
            ebookMapper.updateByPrimaryKey(ebook);
        }
    }
}
