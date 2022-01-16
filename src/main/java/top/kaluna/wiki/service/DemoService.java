package top.kaluna.wiki.service;

import org.springframework.stereotype.Service;
import top.kaluna.wiki.domain.Demo;
import top.kaluna.wiki.mapper.DemoMapper;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Yuery
 * @date 2022/1/15/0015 - 23:35
 */
@Service
public class DemoService {
    @Resource
    private DemoMapper demoMapper;

    public List<Demo> list(){
        return demoMapper.selectByExample(null);
    }
}
