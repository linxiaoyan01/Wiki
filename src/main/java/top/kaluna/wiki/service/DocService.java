package top.kaluna.wiki.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import top.kaluna.wiki.domain.Content;
import top.kaluna.wiki.domain.Doc;
import top.kaluna.wiki.domain.DocExample;
import top.kaluna.wiki.exception.BusinessException;
import top.kaluna.wiki.exception.BusinessExceptionCode;
import top.kaluna.wiki.mapper.ContentMapper;
import top.kaluna.wiki.mapper.DocMapper;
import top.kaluna.wiki.mapper.DocMapperCust;
import top.kaluna.wiki.req.DocQueryReq;
import top.kaluna.wiki.req.DocSaveReq;
import top.kaluna.wiki.resp.DocQueryResp;
import top.kaluna.wiki.resp.PageResp;
import top.kaluna.wiki.util.CopyUtil;
import top.kaluna.wiki.util.RedisUtil;
import top.kaluna.wiki.util.RequestContext;
import top.kaluna.wiki.util.SnowFlake;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Yuery
 * @date 2022/1/15/0015 - 23:36
 */
@Service
public class DocService {
    @Resource
    private DocMapper docMapper;

    @Resource
    private SnowFlake snowFlake;

    @Resource
    private ContentMapper contentMapper;

    @Resource
    private DocMapperCust docMapperCust;

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private WsService wsService;

//    @Resource
//    private RocketMQTemplate rocketMQTemplate;

    private static final Logger LOG = LoggerFactory.getLogger(DocService.class);

    public PageResp<DocQueryResp> list(DocQueryReq docQueryReq){

        DocExample docExample = new DocExample();
        docExample.setOrderByClause("sort asc");
        final DocExample.Criteria criteria = docExample.createCriteria();
//        if(!ObjectUtils.isEmpty(docQueryReq.getName())){
//            criteria.andNameLike("%"+ docQueryReq.getName()+"%");
//        }
        //两个请求参数
        PageHelper.startPage(docQueryReq.getPage(), docQueryReq.getSize());
        final List<Doc> docs = docMapper.selectByExample(docExample);

        PageInfo<Doc> pageInfo = new PageInfo<>(docs);
        LOG.info("总行数：{}", pageInfo.getTotal());
        LOG.info("总页数：{}",pageInfo.getPages());

        final List<DocQueryResp> respsList = CopyUtil.copyList(docs, DocQueryResp.class);

        PageResp<DocQueryResp> pageResp = new PageResp<>();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(respsList);
        return pageResp;
    }

    @Transactional
    public void save(DocSaveReq docQueryReq) {
        Doc doc = CopyUtil.copy(docQueryReq, Doc.class);
        Content content = CopyUtil.copy(docQueryReq, Content.class);
        if(ObjectUtils.isEmpty(docQueryReq.getId())){
            //新增
            doc.setId(snowFlake.nextId());
            doc.setViewCount(0);
            doc.setVoteCount(0);
            docMapper.insert(doc);
            content.setId(doc.getId());
            contentMapper.insert(content);
        }else {
            //更新
            docMapper.updateByPrimaryKey(doc);
            int count = contentMapper.updateByPrimaryKeyWithBLOBs(content);
            if(count == 0){
                contentMapper.insert(content);
            }
        }
    }

    public void delete(Long id) {
        docMapper.deleteByPrimaryKey(id);
    }
    public void delete(List<String> ids) {
        DocExample docExample = new DocExample();
        DocExample.Criteria criteria = docExample.createCriteria();
        criteria.andIdIn(ids);
        docMapper.deleteByExample(docExample);
    }

    public List<DocQueryResp> all(Long ebookId) {
        DocExample docExample = new DocExample();
        docExample.createCriteria().andEbookIdEqualTo(ebookId);
        docExample.setOrderByClause("sort asc");
        List<Doc> docList = docMapper.selectByExample(docExample);
        //列表复制
        final List<DocQueryResp> docQueryResps = CopyUtil.copyList(docList, DocQueryResp.class);
        return docQueryResps;
    }
    public String findContent(Long id) {
        Content content = contentMapper.selectByPrimaryKey(id);
        //文档阅读数加一
        docMapperCust.increaseViewCount(id);
        if(content != null){
            return content.getContent();
        }
        return "";
    }

    public void vote(Long id) {
        //docMapperCust.increaseVoteCount(id);
        //远程IP+doc，id作为key，24小时内不能重复
        String key = RequestContext.getRemoteAddr();
        if(redisUtil.validateRepeat("DOC_VOTE_"+id+"_"+key, 3600*24)){
            docMapperCust.increaseVoteCount(id);
        }else{
            throw new BusinessException(BusinessExceptionCode.VOTE_REPEAT);
        }
        final Doc docDb = docMapper.selectByPrimaryKey(id);
        String logId = MDC.get("LOG_ID");
        //rocketMQTemplate.convertAndSend("VOTE_TOPIC","《"+docDb.getName()+"》被点赞");
        wsService.sendInfo("《"+docDb.getName()+"》被点赞！", logId);
    }
    public void updateEbookInfo() {
        docMapperCust.updateEbookInfo();
    }
}


