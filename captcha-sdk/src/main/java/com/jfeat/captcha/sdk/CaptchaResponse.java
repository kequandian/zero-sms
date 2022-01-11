package com.jfeat.captcha.sdk;

/**
 * @author jackyhuang
 * @date 2022/1/9
 */
public class CaptchaResponse {
    private String uuid;
    private String base64Data;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getBase64Data() {
        return base64Data;
    }

    public void setBase64Data(String base64Data) {
        this.base64Data = base64Data;
    }
}
