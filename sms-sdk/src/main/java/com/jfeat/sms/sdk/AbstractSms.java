package com.jfeat.sms.sdk;

import com.jfeat.sms.sdk.store.MemoryStore;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author jackyhuang
 * @date 2021/12/23
 */
public abstract class AbstractSms implements Sms {
    protected Logger logger = LoggerFactory.getLogger(AbstractSms.class);

    private SmsConfig config;
    private Store store;

    public AbstractSms() {

    }

    public AbstractSms(SmsConfig config) {
        this.config = config;
        this.store = MemoryStore.me();
    }

    public AbstractSms(SmsConfig config, Store store) {
        this.config = config;
        this.store = store;
    }

    public SmsConfig getConfig() {
        return config;
    }

    public void setConfig(SmsConfig config) {
        this.config = config;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    @Override
    public void sendCaptcha(String phone, String operation) {
        sendCaptcha(phone, operation, generateCaptcha());
    }

    @Override
    public void sendCaptcha(String phone, String operation, String code) {
        logger.debug("sendCaptcha: phone={}, operation={}, code={}", phone, operation, code);
        int ttl = config.getCaptchaTtlSeconds();
        store.save(getKey(phone, operation), code, ttl);
        String message = StringUtils.isEmpty(config.getCaptchaTemplate()) ?
                code : String.format(config.getCaptchaTemplate(), code);
        sendMessage(phone, message);
    }

    @Override
    public boolean verifyCaptcha(String phone, String operation, String code) {
        logger.debug("verifyCaptcha: phone={}, operation={}, code={}", phone, operation, code);
        String storedCode = store.read(getKey(phone, operation));
        if (StringUtils.compare(storedCode, code) == 0) {
            logger.debug("verify passed.");
            store.delete(getKey(phone, operation));
            return true;
        }
        logger.debug("verify failed.");
        return false;
    }

    private String getKey(String phone, String operation) {
        return phone + "-" + operation;
    }

    private String generateCaptcha() {
        return RandomStringUtils.randomNumeric(4);
    }
}
