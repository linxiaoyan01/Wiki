package top.kaluna.wiki.req;

/**
 * @author Yuery
 * @date 2022/1/15/0015 - 23:31
 */
public class UserQueryReq extends PageReq{


    private String loginName;

    public String getLoginName(){
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }
}
