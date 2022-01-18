package com.jfeat.sms.endpoint;

import com.jfeat.sms.sdk.Sms;
import com.jfeat.sms.sdk.SmsTemplate;
import com.jfeat.sms.sdk.vendor.venus.VenusSms;
import com.jfeat.sms.sdk.vendor.venus.VenusSmsConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author jackyhuang
 * @date 2021/12/24
 */
@RestController
public class SmsController {

    private Logger logger = LoggerFactory.getLogger(SmsController.class);

    private Sms getSms() {
        // TODO read from configservice
        VenusSmsConfig config = new VenusSmsConfig();
        config.setAccount("account1");
        config.setPassword("pwd1");
        config.setUrl("http://127.0.0.1:8080/vanusSms");
        config.setUserId("user1");
        List<SmsTemplate> templates = new ArrayList<>();
        SmsTemplate template = new SmsTemplate();
        template.setOperation("register");
        template.setTemplateCode("dummy");
        template.setTemplateParam("{\"code\": \"%s\"}");
        template.setSignName("dummy");
        template.setTemplateContent("Your code is {code}, valid in 2 minutes.");
        templates.add(template);
        config.setTemplates(templates);
        return new VenusSms(config);
    }

    @PostMapping("/api/sms/v1/captcha")
    public void captcha(@RequestBody CaptchaRequest req) {
        logger.debug("captcha: " + req);
        getSms().sendCaptcha(req.getPhone(), req.getOperation());
    }

    @PostMapping("/api/u/sms/v1/captcha")
    public void captchaUserEnd(@RequestBody CaptchaRequest req) {
        logger.debug("captcha: " + req);
        getSms().sendCaptcha(req.getPhone(), req.getOperation());
    }

    /**
     * 应用层应该根据verify结果进行业务的处理。
     * @param req
     * @return
     */
    @PostMapping("/api/sms/v1/verify")
    public boolean verify(@RequestBody CaptchaRequest req) {
        logger.debug("verify: " + req);
        return getSms().verifyCaptcha(req.getPhone(), req.getOperation(), req.getCode());
    }

    @PostMapping("/api/u/sms/v1/verify")
    public boolean verifyUserEnd(@RequestBody CaptchaRequest req) {
        logger.debug("verify: " + req);
        return getSms().verifyCaptcha(req.getPhone(), req.getOperation(), req.getCode());
    }

    @PostMapping(value = "/vanusSms", consumes = "application/x-www-form-urlencoded;charset=UTF-8")
    public void vanusSms(@RequestParam Map<String, String> req) {
        logger.debug("vanusSms: " + req);
    }
}
