//package top.kaluna.wiki.job;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
///**
// * @author Yuery
// * @date 2022/1/15/0015 - 23:29
// */
//@Component
//public class TestJob {
//
//    private static final Logger LOG = LoggerFactory.getLogger(TestJob.class);
//
//    /**
//     * 固定时间间隔，fixedRate单位毫秒
//     */
//    @Scheduled(fixedRate = 5000)
//    public void simple() throws InterruptedException {
//        SimpleDateFormat formatter = new SimpleDateFormat("mm:ss");
//        String dateString = formatter.format(new Date());
//        //Thread.sleep(2000);
//        LOG.info("每隔5秒执行一次:{}", dateString);
//    }
//
//    /**
//     * 自定义cron表达式
//     * 只有等上一次执行完成，下一次会在下一个时间点执行，错过就错过
//     * @throws InterruptedException
//     */
//    @Scheduled(cron = "*/2 * * * * ?")
//    public void cron() throws InterruptedException{
//        SimpleDateFormat formatter = new SimpleDateFormat("mm:ss SSS");
//        String dateString = formatter.format(new Date());
//        //Thread.sleep(1500);
//        LOG.info("每隔2秒钟执行一次：{}",dateString);
//    }
//}
