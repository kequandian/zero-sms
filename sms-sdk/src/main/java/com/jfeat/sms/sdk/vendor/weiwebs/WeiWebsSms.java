package com.jfeat.sms.sdk.vendor.weiwebs;

import com.alibaba.fastjson.JSONObject;
import com.jfeat.sms.sdk.AbstractSms;
import com.jfeat.sms.sdk.SmsTemplate;
import com.jfeat.sms.sdk.utils.HttpKit;
import com.jfeat.sms.sdk.utils.RequestParameter;

import java.util.*;

/**
 * @author jackyhuang
 * @date 2021/12/23
 */
public class WeiWebsSms extends AbstractSms {

    private WeiWebsSmsConfig config;

//    private String action = "send";

    private String needstatus = "false";

    public WeiWebsSms(WeiWebsSmsConfig config) {
        super(config);
        this.config = config;
    }

    @Override
    public void sendMessage(String phone, String code, SmsTemplate template) {
        if(!checkSendMessageInterval(phone,code))return;
        logger.info("sms-sdk: send msg: phone={}, code={}", phone, code);
        List<RequestParameter> params = new ArrayList<>();
        params.add(new RequestParameter("needstatus", needstatus));
//        params.add(new RequestParameter("userid", config.getUserId()));
        params.add(new RequestParameter("account", config.getAccount()));
        params.add(new RequestParameter("pswd", config.getPswd()));
        params.add(new RequestParameter("mobile", phone));
        params.add(new RequestParameter("msg",getContent(template,code)));
        String result = new HttpKit().url(config.getUrl())
                .postForm(params)
                .exec();
        logger.info("sms-sdk: send msg http result: {}", result);
    }

    private String getContent(SmsTemplate template, String code) {
        // Your code is {code}, valid in 2 minutes.
        String templateContent = template.getTemplateContent();
        // {"code": "%s"} => {"code": "123456"}

        String paramString = formatTemplateParam(template, code);
        if(paramString.length()>0){
            JSONObject jsonObject = JSONObject.parseObject(paramString);
            for (Map.Entry entry : jsonObject.entrySet()) {
                String value = (String) entry.getValue();
                templateContent = templateContent.replace("{" + entry.getKey() + "}", value);
            }
        }
        return templateContent;
    }
}
