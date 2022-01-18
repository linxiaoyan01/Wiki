package top.kaluna.wiki.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

/**
 * @author Yuery
 * @date 2022/1/15/0015 - 23:24
 */
/**
 * number类型精度为16位，而雪花算法生成的id为19位，因此会导致进度丢失
 * 统一注解，解决前后端交互Long类型精度丢失的问题
 */

@Configuration
public class JacksonConfig {
    @Bean
    public ObjectMapper jacksonObjectMapper(Jackson2ObjectMapperBuilder builder){
        ObjectMapper objectMapper = builder.createXmlMapper(false).build();
        SimpleModule simpleModule = new SimpleModule();

        //JSON Long ==> String
        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
        objectMapper.registerModule(simpleModule);
        return objectMapper;
    }

}
