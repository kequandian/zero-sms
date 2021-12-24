package com.jfeat.sms.sdk;

/**
 * @author jackyhuang
 * @date 2021/12/23
 */
public interface SmsConfig {

    /**
     * 发送短信的模版，该模版通过String.format进行captcha的注入，因此需要提供 %s 占位
     * 如：验证码是：%s, 有效时间2分钟，请尽快验证。
     * @return
     */
    String getCaptchaTemplate();

    /**
     * 验证码的留传时间，默认60秒
     * @return
     */
    int getCaptchaTtlSeconds();
}
