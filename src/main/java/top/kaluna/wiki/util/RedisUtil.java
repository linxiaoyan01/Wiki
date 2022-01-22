package top.kaluna.wiki.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author Yuery
 * @date 2022/1/15/0015 - 23:37
 */
public class RedisUtil {
    private static final Logger LOG = LoggerFactory.getLogger(RedisUtil.class);

    @Resource
    private RedisTemplate redisTemplate;

    /**
     * @param key
     * @param second
     * @return
     * true: 不存在，放入一个KEY
     * false: 已存在
     */
    public boolean validateRepeat(String key, long second){
        if(redisTemplate.hasKey(key)){
            LOG.info("key已存在：{}",key);
            return false;
        }else {
            LOG.info("key不存在，放入：{}，过期 {} 秒", key, second);
            redisTemplate.opsForValue().set(key, key, second, TimeUnit.SECONDS);
            return true;
        }
    }

}
