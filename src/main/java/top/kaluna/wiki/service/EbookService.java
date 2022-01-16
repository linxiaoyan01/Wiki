package top.kaluna.wiki.service;

import org.springframework.stereotype.Service;
import top.kaluna.wiki.domain.Ebook;
import top.kaluna.wiki.mapper.EbookMapper;

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

    public List<Ebook> list(){
        return ebookMapper.selectByExample(null);
    }
}
