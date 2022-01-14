package com.jfeat.sms.sdk.vendor.venus;

import com.jfeat.sms.sdk.SmsConfig;

/**
 * example:
 * {
 *     "url": "http://www.test.com/sms",
 *     "userId": "userid",
 *     "account": "theaccount",
 *     "password": "pwd",
 *     "captchaTemplate": "code is %s, valid in 1 minute.",
 *     "captchaTtlSeconds": 60,
 *     "captchaCount": 4
 * }
 *
 * @author jackyhuang
 * @date 2021/12/23
 */
public class VenusSmsConfig extends SmsConfig {

    private String url;
    private String userId;
    private String account;
    private String password;

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
}
