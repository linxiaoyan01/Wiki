package top.kaluna.wiki.util;

import java.io.Serializable;

/**
 * @author Yuery
 * @date 2022/1/15/0015 - 23:37
 */
public class RequestContext implements Serializable {
    private static ThreadLocal<String> remoteAddr = new ThreadLocal<>();

    public static String getRemoteAddr(){
        return remoteAddr.get();
    }
    public static void setRemoteAddr(String remoteAddr){
        RequestContext.remoteAddr.set(remoteAddr);
    }

}
