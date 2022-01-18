package com.jfeat.sms.sdk.vendor.venus;

import com.alibaba.fastjson.JSONObject;
import com.jfeat.sms.sdk.AbstractSms;
import com.jfeat.sms.sdk.SmsTemplate;
import com.jfeat.sms.sdk.utils.HttpKit;
import com.jfeat.sms.sdk.utils.RequestParameter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author jackyhuang
 * @date 2021/12/23
 */
public class VenusSms extends AbstractSms {

    private VenusSmsConfig config;

    private String action = "send";

    public VenusSms(VenusSmsConfig config) {
        super(config);
        this.config = config;
    }

    @Override
    public void sendMessage(String phone, String code, SmsTemplate template) {
        logger.debug("sendMessage: phone={}, code={}", phone, code);
        List<RequestParameter> params = new ArrayList<>();
        params.add(new RequestParameter("action", action));
        params.add(new RequestParameter("userid", config.getUserId()));
        params.add(new RequestParameter("account", config.getAccount()));
        params.add(new RequestParameter("pswd", config.getPswd()));
        params.add(new RequestParameter("mobile", phone));
        params.add(new RequestParameter("content", getContent(template, code)));
        String result = new HttpKit().url(config.getUrl())
                .postForm(params)
                .exec();
        //TODO handle the result
        logger.debug("result: {}", result);
    }

    private String getContent(SmsTemplate template, String code) {
        // Your code is {code}, valid in 2 minutes.
        String templateContent = template.getTemplateContent();
        // {"code": "%s"} => {"code": "123456"}
        JSONObject jsonObject = JSONObject.parseObject(formatTemplateParam(template, code));
        for (Map.Entry entry : jsonObject.entrySet()) {
            String value = (String) entry.getValue();
            templateContent = templateContent.replace("{" + entry.getKey() + "}", value);
        }
        return templateContent;
    }
}
