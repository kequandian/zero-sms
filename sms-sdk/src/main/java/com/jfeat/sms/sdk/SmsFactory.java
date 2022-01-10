package com.jfeat.sms.sdk;

import com.jfeat.sms.sdk.vendor.aliyun.AliyunSms;
import com.jfeat.sms.sdk.vendor.aliyun.AliyunSmsConfig;
import com.jfeat.sms.sdk.vendor.aliyun.SmsTemplate;
import com.jfeat.sms.sdk.vendor.vanus.VanusSms;
import com.jfeat.sms.sdk.vendor.vanus.VanusSmsConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author jackyhuang
 * @date 2021/12/26
 */
public class SmsFactory {
    private static final SmsFactory instance = new SmsFactory();

    private SmsFactory() {
    }

    public static SmsFactory me() {
        return instance;
    }

    public Sms getSms(String vendor, Map<String, Object> configMap) {
        //TODO get redis config and new RedisStore
        if ("vanus".equalsIgnoreCase(vendor)) {
            return getVanusSms(configMap);
        }
        if ("aliyun".equalsIgnoreCase(vendor)) {
            return getAliyunSms(configMap);
        }
        throw new SmsException("vendor " + vendor + " not found");
    }

    private Integer getInteger(Map<String, Object> map, String key, int defaultValue) {
        Object res = map.getOrDefault(key, defaultValue);
        if (res instanceof Integer) {
            return (Integer) res;
        }
        if (res instanceof String) {
            return Integer.parseInt((String) res);
        }
        throw new SmsException("Invalid type of " + key);
    }

    private String getString(Map<String, Object> map, String key) {
        return (String) map.get(key);
    }

    private Map<String, Object> getMap(Map<String, Object> map, String key) {
        return (Map<String, Object>) map.get(key);
    }

    private Sms getAliyunSms(Map<String, Object> configMap) {
        AliyunSmsConfig config = new AliyunSmsConfig();
        config.setAccessKeyId(this.getString(configMap, "accessKeyId"));
        config.setAccessSecret(this.getString(configMap, "accessSecret"));
        config.setCaptchaCount(this.getInteger(configMap, "captchaCount", 6));
        config.setCaptchaTtlSeconds(this.getInteger(configMap,"captchaTtlSeconds", 60));
        Map<String, Object> templatesMap = this.getMap(configMap, "templates");
        List<SmsTemplate> templates = new ArrayList<>();
        templatesMap.forEach((key, value) -> {
            Map<String, Object> map = (Map<String, Object>) value;
            SmsTemplate smsTemplate = new SmsTemplate();
            smsTemplate.setOperation(key);
            smsTemplate.setSignName(this.getString(map, "signName"));
            smsTemplate.setTemplateCode(this.getString(map, "templateCode"));
            smsTemplate.setTemplateParam(this.getString(map, "templateParam"));
            templates.add(smsTemplate);
        });
        config.setTemplates(templates);
        return new AliyunSms(config);
    }

    private Sms getVanusSms(Map<String, Object> configMap) {
        VanusSmsConfig config = new VanusSmsConfig();
        config.setAccount(this.getString(configMap, "account"));
        config.setCaptchaCount(this.getInteger(configMap, "captchaCount", 6));
        config.setCaptchaTtlSeconds(this.getInteger(configMap, "captchaTtlSeconds", 60));
        config.setCaptchaTemplate(this.getString(configMap, "captchaTemplate"));
        config.setPassword(this.getString(configMap, "password"));
        config.setUrl(this.getString(configMap, "url"));
        config.setUserId(this.getString(configMap, "userId"));
        return new VanusSms(config);
    }
}
