package com.hudl.test.ui.core.stepDefinition;


public class ConfigurationOptions {

    private String loginUrl;

    private String userName;

    private String password;

    private int zoomLevel;

    private String transitionScreenshotDir;

    private String mailHost;

    private String mailPort;

    private String mailFrom;

    public String getLoginUrl() {
        return loginUrl;
    }

    public void setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getZoomLevel() {
        return zoomLevel;
    }

    public void setZoomLevel(int zoomLevel) {
        this.zoomLevel = zoomLevel;
    }

    public String getTransitionScreenshotDir() {
        return transitionScreenshotDir;
    }

    public void setTransitionScreenshotDir(String transitionScreenshotDir) {
        this.transitionScreenshotDir = transitionScreenshotDir;
    }

    public String getMailHost() {
        return mailHost;
    }

    public void setMailHost(String mailHost) {
        this.mailHost = mailHost;
    }

    public String getMailPort() {
        return mailPort;
    }

    public void setMailPort(String mailPort) {
        this.mailPort = mailPort;
    }

    public String getMailFrom() {
        return mailFrom;
    }

    public void setMailFrom(String mailFrom) {
        this.mailFrom = mailFrom;
    }
}
