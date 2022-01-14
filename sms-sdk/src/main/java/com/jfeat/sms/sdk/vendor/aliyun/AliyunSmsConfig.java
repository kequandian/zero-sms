package com.jfeat.sms.sdk.vendor.aliyun;

import com.jfeat.sms.sdk.SmsConfig;
import com.jfeat.sms.sdk.SmsTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * aliyun sms config
 * @author jackyhuang
 * @date 2021/12/23
 */
public class AliyunSmsConfig extends SmsConfig {
    private String accessKeyId;
    private String accessSecret;

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
}
