package com.xuxue.crawler.frame;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 *
 * @author xuxue
 * @time 2017-7-13
 */
@Component
@ConfigurationProperties("damatu")
public class DaMaTuConfig {

    private  String appID;
    private String user;
    private String password;
    private String appKey;
    private String rquestUrl;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getAppID() {
        return appID;
    }

    public void setAppID(String appID) {
        this.appID = appID;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getRquestUrl() {
        return rquestUrl;
    }

    public void setRquestUrl(String rquestUrl) {
        this.rquestUrl = rquestUrl;
    }
}
