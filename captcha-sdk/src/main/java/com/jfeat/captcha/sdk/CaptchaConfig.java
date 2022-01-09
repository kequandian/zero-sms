package com.jfeat.captcha.sdk;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author jackyhuang
 * @date 2022/1/8
 */
@Component
@ConfigurationProperties(prefix = "io.captcha")
public class CaptchaConfig {
    private boolean enabled = true;
    private String uuidHeaderName = "x-captcha-code-uuid";
    private String codeHeaderName = "x-captcha-code";
    private int ttlSeconds = 60;
    private int codeLength = 4;
    /**
     * 0: 数字
     * 1：字母
     * 2：数字字母混合
     */
    private int type = 0;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public int getTtlSeconds() {
        return ttlSeconds;
    }

    public void setTtlSeconds(int ttlSeconds) {
        this.ttlSeconds = ttlSeconds;
    }

    public int getCodeLength() {
        return codeLength;
    }

    public void setCodeLength(int codeLength) {
        this.codeLength = codeLength;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUuidHeaderName() {
        return uuidHeaderName;
    }

    public void setUuidHeaderName(String uuidHeaderName) {
        this.uuidHeaderName = uuidHeaderName;
    }

    public String getCodeHeaderName() {
        return codeHeaderName;
    }

    public void setCodeHeaderName(String codeHeaderName) {
        this.codeHeaderName = codeHeaderName;
    }
}
