package com.jfeat.captcha.sdk;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.util.Base64;

/**
 * @author jackyhuang
 * @date 2022/1/8
 */
@RestController
public class CaptchaController {

    private static final Logger logger = LoggerFactory.getLogger(CaptchaController.class);

    @Resource
    private CaptchaConfig captchaConfig;

    /**
     * 验证码生成，通过图片流返回
     * TODO: 添加限流控制，防止同一个请求源的过多频繁访问
     */
    @GetMapping("/api/captcha/v1/code")
    public CaptchaResponse captcha() {
        if (!captchaConfig.isEnabled()) {
            throw new CaptchaException("Captcha not enabled.");
        }
        try {
            CaptchaComposer composer = new CaptchaComposer(captchaConfig);
            ComposeResult composeResult = composer.compose();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            ImageIO.write(composeResult.getImage(), "JPEG", stream);
            Base64.Encoder encoder = Base64.getEncoder();
            String base64EncodedStr = encoder.encodeToString(stream.toByteArray());
            CaptchaResponse captchaResponse = new CaptchaResponse();
            captchaResponse.setUuid(composeResult.getUuid());
            captchaResponse.setBase64Data("data:image/png;base64," + base64EncodedStr);
            return captchaResponse;
        } catch (Exception e) {
            logger.error("render captcha image to response stream failure >>>>   ", e);
            throw new CaptchaException("Render captcha image error.");
        }
    }

    /**
     * 这个是例子
     */
    @Captcha
    @GetMapping("/captcha2")
    public void captcha2() {
        logger.info("Cathcha2.");
    }
}
