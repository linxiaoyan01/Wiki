package top.kaluna.wiki.rocketmq;

/**
 * @author Yuery
 * @date 2022/1/15/0015 - 23:34
 */
//@Service
//@RocketMQMessageListener(consumerGroup = "default",topic = "VOTE_TOPIC")
//public class VoteTopicConsumer implements RocketMQListener<MessageExt> {
//
//    private static final Logger LOG =  LoggerFactory.getLogger(VoteTopicConsumer.class);
//
//    @Resource
//    public WebSocketServer webSocketServer;
//    @Override
//    public void onMessage(MessageExt messageExt){
//        byte[] body = messageExt.getBody();
//        LOG.info("ROCKETMQ收到消息：{}", new String(body));
//        webSocketServer.sendInfo(new String(body));
//    }
//}
