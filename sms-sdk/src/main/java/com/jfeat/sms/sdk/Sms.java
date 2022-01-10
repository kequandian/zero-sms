package com.jfeat.sms.sdk;

/**
 * @author jackyhuang
 * @date 2021/12/23
 */
public interface Sms {

    void sendMessage(String phone, String operation, String message);

    void sendCaptcha(String phone, String operation);

    void sendCaptcha(String phone, String operation, String code);

    boolean verifyCaptcha(String phone, String operation, String code);
}
