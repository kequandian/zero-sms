package com.jfeat.sms.sdk;

import com.jfeat.sms.sdk.vendor.aliyun.AliyunSms;
import com.jfeat.sms.sdk.vendor.aliyun.AliyunSmsConfig;
import com.jfeat.sms.sdk.vendor.vanus.VanusSms;
import com.jfeat.sms.sdk.vendor.vanus.VanusSmsConfig;

import java.util.Map;

/**
 * @author jackyhuang
 * @date 2021/12/26
 */
public class SmsFactory {
    private static final SmsFactory instance = new SmsFactory();
    private SmsFactory() {}

    public static SmsFactory me() {
        return instance;
    }

    public Sms getSms(String vendor, Map<String, String> configMap) {
        //TODO get redis config and new RedisStore
        if ("vanus".equalsIgnoreCase(vendor)) {
            return getVanusSms(configMap);
        }
        if ("aliyun".equalsIgnoreCase(vendor)) {
            return getAliyunSms(configMap);
        }
        throw new RuntimeException("vendor " + vendor + " not found");
    }

    private Sms getAliyunSms(Map<String, String> configMap) {
        AliyunSmsConfig config = new AliyunSmsConfig();
        config.setAccessKeyId(configMap.get("accessKeyId"));
        config.setAccessSecret(configMap.get("accessSecret"));
        config.setCaptchaCount(Integer.parseInt(configMap.getOrDefault("captchaCount", "6")));
        config.setCaptchaTtlSeconds(Integer.parseInt(configMap.getOrDefault("captchaTtlSeconds", "60")));
        config.setSignName(configMap.get("signName"));
        config.setTemplateCode(configMap.get("templateCode"));
        config.setTemplateParam(configMap.get("templateParam"));
        return new AliyunSms(config);
    }

    private Sms getVanusSms(Map<String, String> configMap) {
        VanusSmsConfig config = new VanusSmsConfig();
        config.setAccount(configMap.get("account"));
        config.setCaptchaCount(Integer.parseInt(configMap.getOrDefault("captchaCount", "6")));
        config.setCaptchaTtlSeconds(Integer.parseInt(configMap.getOrDefault("captchaTtlSeconds", "60")));
        config.setCaptchaTemplate(configMap.get("captchaTemplate"));
        config.setPassword(configMap.get("password"));
        config.setUrl(configMap.get("url"));
        config.setUserId(configMap.get("userId"));
        return new VanusSms(config);
    }
}
