package com.jfeat.sms.sdk.vendor.vanus;

import com.jfeat.sms.sdk.SmsConfig;

/**
 * example:
 * {
 *     "url": "http://www.test.com/sms",
 *     "userId": "userid",
 *     "account": "theaccount",
 *     "password": "pwd",
 *     "captchaTemplate": "code is %s, valid in 1 minute.",
 *     "ttl": 60
 * }
 *
 * @author jackyhuang
 * @date 2021/12/23
 */
public class VanusSmsConfig implements SmsConfig {

    private String url;
    private String userId;
    private String account;
    private String password;
    private String captchaTemplate;
    private int captchaCount = 4;
    private int captchaTtlSeconds = 60;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getCaptchaTemplate() {
        return captchaTemplate;
    }

    public void setCaptchaTemplate(String captchaTemplate) {
        this.captchaTemplate = captchaTemplate;
    }

    @Override
    public int getCaptchaCount() {
        return captchaCount;
    }

    public void setCaptchaCount(int captchaCount) {
        this.captchaCount = captchaCount;
    }

    @Override
    public int getCaptchaTtlSeconds() {
        return captchaTtlSeconds;
    }

    public void setCaptchaTtlSeconds(int captchaTtlSeconds) {
        this.captchaTtlSeconds = captchaTtlSeconds;
    }
}
