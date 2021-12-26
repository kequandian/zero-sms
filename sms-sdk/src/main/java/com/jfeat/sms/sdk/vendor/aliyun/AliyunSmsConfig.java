package com.jfeat.sms.sdk.vendor.aliyun;

import com.jfeat.sms.sdk.SmsConfig;

/**
 * example:
 * {
 *     "accessKeyId": "id",
 *     "accessSecret": "secret",
 *     "signName": "sign",
 *     "templateCode": "t001",
 *     "templateParam": "{\"code\": \"%s\"}",
 *     "ttl": 60
 * }
 * @author jackyhuang
 * @date 2021/12/23
 */
public class AliyunSmsConfig implements SmsConfig {
    private String accessKeyId;
    private String accessSecret;
    /**
     * 短信签名名称
     */
    private String signName;
    /**
     * 短信模板ID
     */
    private String templateCode;
    /**
     * 短信模板变量对应的实际值
     */
    private String templateParam;

    private int captchaCount = 4;

    private int captchaTtlSeconds = 60;

    public String getTemplateCode() {
        return templateCode;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }

    /**
     * code是短信模版定义的字段，使用%s给真实的code进行替换
     * "{\"code\": \"%s\"}"
     * @return
     */
    public String getTemplateParam() {
        return templateParam;
    }

    public void setTemplateParam(String templateParam) {
        this.templateParam = templateParam;
    }

    public String getSignName() {
        return signName;
    }

    public void setSignName(String signName) {
        this.signName = signName;
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
