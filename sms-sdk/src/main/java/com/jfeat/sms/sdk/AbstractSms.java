package com.jfeat.sms.sdk;

import com.jfeat.code.store.MemoryStore;
import com.jfeat.code.store.Store;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.HashMap;
import java.util.Optional;


/**
 * @author jackyhuang
 * @date 2021/12/23
 */
public abstract class AbstractSms implements Sms {
    protected Logger logger = LoggerFactory.getLogger(AbstractSms.class);
    protected HashMap<String, Date> sendMessageHashMap = new HashMap<>();

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
        if(config.getDebugMode()!=null && config.getDebugMode()==1) {
            return;
        }
        sendMessage(phone, code, getTemplate(operation));
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

    protected abstract void sendMessage(String phone, String code, SmsTemplate template);

    protected boolean checkSendMessageInterval(String phone, String code)
    {
        Date now = new Date();
        int interval = config.getSendMessageInterval();
        String key = phone+code;
        if(this.sendMessageHashMap.containsKey(key)) {
            if(now.getTime()-this.sendMessageHashMap.get(key).getTime()>=(interval*1000)) {
                this.sendMessageHashMap.put(key,now);
                return true;
            }
            else return false;
        }
        else {
            this.sendMessageHashMap.put(key,now);
            return true;
        }
    }

    protected String formatTemplateParam(SmsTemplate template, String code) {
        if (template.getTemplateParam() != null) {
            return String.format(template.getTemplateParam(), code);
        }
        return "";
    }

    private SmsTemplate getTemplate(String operation) {
        Optional<SmsTemplate> templateOpt = config.getTemplates().stream()
                .filter(x -> x.getOperation().equalsIgnoreCase(operation))
                .findFirst();
        if (!templateOpt.isPresent()) {
            logger.warn("template not found for operation {}", operation);
            throw new SmsException("Template not found");
        }

        return templateOpt.get();
    }

    private String getKey(String phone, String operation) {
        return phone + "-" + operation;
    }

    private String generateCaptcha() {
        return RandomStringUtils.randomNumeric(this.config.getCaptchaCount());
    }
}
