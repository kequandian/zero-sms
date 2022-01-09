package com.jfeat.captcha.sdk;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.RenderedImage;

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
     * @param response
     */
    @GetMapping("/api/captcha/v1/code")
    public void captcha(HttpServletResponse response) {
        if (!captchaConfig.isEnabled()) {
            throw new CaptchaException("Captcha not enabled.");
        }
        try {
            CaptchaComposer composer = new CaptchaComposer(captchaConfig);
            ComposeResult composeResult = composer.compose();
            response.addHeader(captchaConfig.getUuidHeaderName(), composeResult.getUuid());
            ImageIO.write(composeResult.getImage(), "JPEG", response.getOutputStream());
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
