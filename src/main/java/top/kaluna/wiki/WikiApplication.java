package top.kaluna.wiki;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author 86158
 */


//@MapperScan("com.kaluna.wiki.mapper")
@EnableScheduling
@EnableAsync
@SpringBootApplication
public class WikiApplication {

	private static final Logger LOG = LoggerFactory.getLogger(WikiApplication.class);

	public static void main(String[] args) {

		SpringApplication app = new SpringApplication(WikiApplication.class);
		Environment environment = app.run(args).getEnvironment();
		LOG.info("启动成功");
		LOG.info("地址：\thttp://127.0.0.1:{}",environment.getProperty("server.port"));
	}


}
