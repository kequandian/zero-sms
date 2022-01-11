package com.jfeat.sms.sdk.vendor.aliyun;

import com.jfeat.sms.sdk.SmsConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * example:
 * {
 *     "accessKeyId": "id",
 *     "accessSecret": "secret",
 *     "signName": "sign",
 *     "templateCode": "t001",
 *     "templateParam": "{\"code\": \"%s\"}",
 *     "captchaTtlSeconds": 60,
 *     "captchaCount": 4
 * }
 * @author jackyhuang
 * @date 2021/12/23
 */
public class AliyunSmsConfig implements SmsConfig {
    private String accessKeyId;
    private String accessSecret;
    private List<SmsTemplate> templates = new ArrayList<>();

    private int captchaCount = 4;

    private int captchaTtlSeconds = 60;

    public List<SmsTemplate> getTemplates() {
        return templates;
    }

    public void setTemplates(List<SmsTemplate> templates) {
        this.templates = templates;
    }

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getAccessSecret() {
        return accessSecret;
    }

    public void setAccessSecret(String accessSecret) {
        this.accessSecret = accessSecret;
    }

    /**
     * aliyun SMS 有自己的模版，需要用它的模版。这里返回null，这样的话 SMS.sendCaptcha的时候就会只把code传给SMS.sendMessage
     * @return
     */
    @Override
    public String getCaptchaTemplate() {
        return null;
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
