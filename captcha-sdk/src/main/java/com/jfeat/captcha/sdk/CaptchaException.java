package com.jfeat.captcha.sdk;

/**
 * @author jackyhuang
 * @date 2022/1/9
 */
public class CaptchaException extends RuntimeException {
    CaptchaException(String message) {
        super(message);
    }
}
