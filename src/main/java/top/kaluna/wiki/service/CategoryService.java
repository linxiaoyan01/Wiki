package top.kaluna.wiki.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import top.kaluna.wiki.domain.Category;
import top.kaluna.wiki.domain.CategoryExample;
import top.kaluna.wiki.mapper.CategoryMapper;
import top.kaluna.wiki.req.CategoryQueryReq;
import top.kaluna.wiki.req.CategorySaveReq;
import top.kaluna.wiki.resp.CategoryQueryResp;
import top.kaluna.wiki.resp.PageResp;
import top.kaluna.wiki.util.CopyUtil;
import top.kaluna.wiki.util.SnowFlake;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Yuery
 * @date 2022/1/15/0015 - 23:35
 */
@Service
public class CategoryService {
    @Resource
    private CategoryMapper categoryMapper;

    @Resource
    private SnowFlake snowFlake;

    private static final Logger LOG = LoggerFactory.getLogger(CategoryService.class);

    public PageResp<CategoryQueryResp> list(CategoryQueryReq categoryQueryReq){

        CategoryExample categoryExample = new CategoryExample();
        final CategoryExample.Criteria criteria = categoryExample.createCriteria();
//        if(!ObjectUtils.isEmpty(categoryQueryReq.getName())){
//            criteria.andNameLike("%"+ categoryQueryReq.getName()+"%");
//        }
        //两个请求参数
        PageHelper.startPage(categoryQueryReq.getPage(), categoryQueryReq.getSize());
        final List<Category> categorys = categoryMapper.selectByExample(categoryExample);

        PageInfo<Category> pageInfo = new PageInfo<>(categorys);
        LOG.info("总行数：{}", pageInfo.getTotal());
        LOG.info("总页数：{}",pageInfo.getPages());

        final List<CategoryQueryResp> respsList = CopyUtil.copyList(categorys, CategoryQueryResp.class);

        PageResp<CategoryQueryResp> pageResp = new PageResp<>();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(respsList);
        return pageResp;
    }

    public void save(CategorySaveReq categoryQueryReq) {
        Category category = CopyUtil.copy(categoryQueryReq, Category.class);
        if(ObjectUtils.isEmpty(categoryQueryReq.getId())){
            //新增
            category.setId(snowFlake.nextId());
            categoryMapper.insert(category);
        }else {
            categoryMapper.updateByPrimaryKey(category);
        }
    }

    public void delete(Long id) {
        categoryMapper.deleteByPrimaryKey(id);
    }
}

