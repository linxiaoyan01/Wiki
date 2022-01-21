package top.kaluna.wiki.req;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

/**
 * @author Yuery
 * @date 2022/1/15/0015 - 23:31
 */
public class UserLoginReq {

    @NotEmpty(message = "密码不能为空")
    @Pattern(regexp = "^(?![0~9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,32}$",message = "[密码]规则不正确")
    private String password;


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @NotEmpty(message = "登陆名不能为空")
    private String loginName;

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("UserLoginReq{");
        sb.append("password='").append(password).append('\'');
        sb.append(", loginName='").append(loginName).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
