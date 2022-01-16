package top.kaluna.wiki.service;

import org.springframework.stereotype.Service;
import top.kaluna.wiki.domain.Test;
import top.kaluna.wiki.mapper.TestMapper;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Yuery
 * @date 2022/1/15/0015 - 23:36
 */
@Service
public class TestService {
    @Resource
    private TestMapper testMapper;

    public List<Test> list(){
        return testMapper.list();
    }
}
