package top.kaluna.wiki.aspect;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.support.spring.PropertyPreFilters;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import top.kaluna.wiki.util.RequestContext;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Yuery
 * @date 2022/1/15/0015 - 23:23
 */
@Component
@Aspect
public class LogAspect {
    private static final Logger LOG = LoggerFactory.getLogger(LogAspect.class);
    /**
     * 定义一个切点
     */
    @Pointcut("execution(public * top.kaluna.*.controller..*Controller.*(..))")
    public void controllerPointcut(){

    }
    @Before("controllerPointcut()")
    public void doBefore(JoinPoint joinPoint) throws Throwable{
        //开始打印请求日志
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        Signature signature = joinPoint.getSignature();
        String name = signature.getName();

        //打印请求参数
        LOG.info("------ 开始 ------");
        LOG.info("请求地址:{}{}",request.getRequestURL().toString(), request.getMethod());
        LOG.info("类名方法:{}.{}",signature.getDeclaringTypeName(), name);
        LOG.info("远程地址:{}",request.getRemoteAddr());


        RequestContext.setRemoteAddr(getRemoteIp(request));

        //打印请求参数
        Object[] args = joinPoint.getArgs();
        LOG.info("请求参数:{}", JSONObject.toJSON(args));

        Object[] arguments = new Object[args.length];
        for (int i = 0; i < args.length; i++) {
            if(args[i] instanceof ServletRequest || args[i] instanceof ServletResponse || args[i] instanceof MultipartFile){
                continue;
            }
            arguments[i] = args[i];
        }
        //排除字段，敏感字段或太长的字段不显示
        String[] excludeProperties = {"password","file"};
        PropertyPreFilters filters = new PropertyPreFilters();
        final PropertyPreFilters.MySimplePropertyPreFilter mySimplePropertyPreFilter = filters.addFilter();
        mySimplePropertyPreFilter.addExcludes(excludeProperties);
        LOG.info("请求参数:{}",JSONObject.toJSONString(arguments, mySimplePropertyPreFilter));
    }
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
        long startTime = System.currentTimeMillis();
        Object result = proceedingJoinPoint.proceed();
        //排除字段，敏感字段或太长的字段不显示
        String[] excludeProperties = {"password","file"};
        PropertyPreFilters filters = new PropertyPreFilters();
        final PropertyPreFilters.MySimplePropertyPreFilter mySimplePropertyPreFilter = filters.addFilter();
        mySimplePropertyPreFilter.addExcludes(excludeProperties);
        LOG.info("返回结果:{}",JSONObject.toJSONString(result,mySimplePropertyPreFilter));
        LOG.info("------ 结束 耗时:{}ms ------",System.currentTimeMillis() - startTime);
        return result;
    }

    /**
     * 使用nginx做反向代理，需要用该方法才能取到真实的远程ip
     * @param request
     * @return
     */
    public String getRemoteIp(HttpServletRequest request){
        String ip = request.getHeader("x-forwarded-for");
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getHeader("Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
