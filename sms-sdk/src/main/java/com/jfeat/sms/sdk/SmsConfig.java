package com.jfeat.sms.sdk;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jackyhuang
 * @date 2021/12/23
 */
public abstract class SmsConfig {

    /**
     * 发送短信的模版，该模版通过String.format进行captcha的注入，因此需要提供 %s 占位
     * 如：验证码是：%s, 有效时间2分钟，请尽快验证。
     */

    private List<SmsTemplate> templates = new ArrayList<>();

    /**
     * 随机验证码的长度，默认4位
     */
    private int captchaCount = 4;
    /**
     * 验证码的留传时间，默认60秒
     */
    private int captchaTtlSeconds = 60;

    public List<SmsTemplate> getTemplates() {
        return templates;
    }

    public void setTemplates(List<SmsTemplate> templates) {
        this.templates = templates;
    }

    public int getCaptchaCount() {
        return captchaCount;
    }

    public void setCaptchaCount(int captchaCount) {
        this.captchaCount = captchaCount;
    }

    public int getCaptchaTtlSeconds() {
        return captchaTtlSeconds;
    }

    public void setCaptchaTtlSeconds(int captchaTtlSeconds) {
        this.captchaTtlSeconds = captchaTtlSeconds;
    }
}
