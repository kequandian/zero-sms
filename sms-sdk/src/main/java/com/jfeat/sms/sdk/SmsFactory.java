package com.jfeat.sms.sdk;

import com.alibaba.fastjson.JSONObject;
import com.jfeat.sms.sdk.vendor.aliyun.AliyunSms;
import com.jfeat.sms.sdk.vendor.aliyun.AliyunSmsConfig;
import com.jfeat.sms.sdk.vendor.venus.VenusSms;
import com.jfeat.sms.sdk.vendor.venus.VenusSmsConfig;

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

    public Sms getSms(String vendor, JSONObject configMap) {
        //TODO get redis config and new RedisStore
        if ("venus".equalsIgnoreCase(vendor)) {
            return new VenusSms(configMap.toJavaObject(VenusSmsConfig.class));
        }
        if ("aliyun".equalsIgnoreCase(vendor)) {
            return new AliyunSms(configMap.toJavaObject(AliyunSmsConfig.class));
        }
        throw new SmsException("vendor " + vendor + " not found");
    }

}
