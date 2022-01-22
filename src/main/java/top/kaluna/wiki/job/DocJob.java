package top.kaluna.wiki.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import top.kaluna.wiki.service.DocService;
import top.kaluna.wiki.util.SnowFlake;

import javax.annotation.Resource;

/**
 * @author Yuery
 * @date 2022/1/15/0015 - 23:28
 */
@Component
public class DocJob {
    private final Logger LOG = LoggerFactory.getLogger(DocJob.class);

    @Resource
    private DocService docService;
    @Resource
    private SnowFlake snowFlake;

    /**
     * 每30秒更新电子书信息
     *  从5秒开始,每30秒执行一次
     */
    @Scheduled(cron = "5/30 * * * * ?")
    public void cron(){
        LOG.info("更新电子书下的文档数据开始");
        long start = System.currentTimeMillis();
        docService.updateEbookInfo();
        LOG.info("更新电子书下的文档数据结束，耗时：{}毫秒",System.currentTimeMillis() - start);
    }
}
