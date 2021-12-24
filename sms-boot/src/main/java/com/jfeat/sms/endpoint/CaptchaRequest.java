package com.jfeat.sms.endpoint;

/**
 * @author jackyhuang
 * @date 2021/12/24
 */
public class CaptchaRequest {
    private String phone;
    // used by verification
    private String code;
    private String operation;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }
}
