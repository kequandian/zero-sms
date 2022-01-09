package com.jfeat.captcha.sdk;

import com.jfeat.code.store.MemoryStore;
import com.jfeat.code.store.Store;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

/**
 * @author jackyhuang
 * @date 2022/1/8
 */
public class CaptchaComposer {
    private static final Logger logger = LoggerFactory.getLogger(CaptchaComposer.class);
    private CaptchaConfig captchaConfig;
    private Store store;

    public CaptchaComposer() {

    }

    public CaptchaComposer(CaptchaConfig captchaConfig) {
        this.captchaConfig = captchaConfig;
        this.store = MemoryStore.me();
    }

    public CaptchaComposer(CaptchaConfig captchaConfig, Store store) {
        this(captchaConfig);
        this.store = store;
    }

    public void setCaptchaConfig(CaptchaConfig captchaConfig) {
        this.captchaConfig = captchaConfig;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public ComposeResult compose() {
        ComposeResult result = new ComposeResult();
        String code = generateCode();
        result.setImage(ImageUtil.me().renderCode(code));
        String uuid = UUID.randomUUID().toString().replaceAll("-","");
        result.setUuid(uuid);
        store.save(uuid, code, captchaConfig.getTtlSeconds());
        return result;
    }

    public boolean verify(String uuid, String code) {
        logger.debug("verify: uuid={}, code={}", uuid, code);
        String storedCode = store.read(uuid);
        if (StringUtils.compare(storedCode, code) == 0) {
            logger.debug("verify passed.");
            store.delete(uuid);
            return true;
        }
        logger.debug("verify failed.");
        return false;
    }

    private String generateCode() {
        int length = captchaConfig.getCodeLength();
        int type = captchaConfig.getType();
        String code = RandomStringUtils.randomNumeric(length);
        if(type == 1) {
            code = RandomStringUtils.randomAlphabetic(length);
        }
        if (type == 2) {
            code = RandomStringUtils.randomAlphanumeric(length);
        }
        return code;
    }
}
