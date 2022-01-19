package top.kaluna.wiki.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import top.kaluna.wiki.domain.Doc;
import top.kaluna.wiki.domain.DocExample;
import top.kaluna.wiki.mapper.DocMapper;
import top.kaluna.wiki.req.DocQueryReq;
import top.kaluna.wiki.req.DocSaveReq;
import top.kaluna.wiki.resp.DocQueryResp;
import top.kaluna.wiki.resp.PageResp;
import top.kaluna.wiki.util.CopyUtil;
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

    public void save(DocSaveReq docQueryReq) {
        Doc doc = CopyUtil.copy(docQueryReq, Doc.class);
        if(ObjectUtils.isEmpty(docQueryReq.getId())){
            //新增
            doc.setId(snowFlake.nextId());
            docMapper.insert(doc);
        }else {
            docMapper.updateByPrimaryKey(doc);
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

    public List<DocQueryResp> all() {
        DocExample docExample = new DocExample();
        docExample.setOrderByClause("sort asc");
        List<Doc> docList = docMapper.selectByExample(docExample);
        //列表复制
        final List<DocQueryResp> docQueryResps = CopyUtil.copyList(docList, DocQueryResp.class);
        return docQueryResps;
    }
}


