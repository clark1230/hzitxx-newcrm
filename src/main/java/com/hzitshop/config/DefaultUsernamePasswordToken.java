package com.hzitshop.config;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * 自定义令牌
 */
public class DefaultUsernamePasswordToken extends UsernamePasswordToken {
    /**
     * 登陆类型
     */
    private String loginType;

    public DefaultUsernamePasswordToken() {
    }

    public DefaultUsernamePasswordToken(String username, String password) {
        super(username, password);
    }

    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }
}
