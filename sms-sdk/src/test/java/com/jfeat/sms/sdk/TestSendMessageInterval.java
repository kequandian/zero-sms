package com.jfeat.sms.sdk;

import com.jfeat.sms.sdk.vendor.weiwebs.WeiWebsSms;
import com.jfeat.sms.sdk.vendor.weiwebs.WeiWebsSmsConfig;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.After;
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import static java.lang.Thread.sleep;
import static org.junit.Assert.assertNotNull;

/**
 * @author jackyhuang
 * @date 2021/12/24
 */
public class TestSendMessageInterval {
    @org.junit.Test
    public void testWeiWebsSmsPass() throws InterruptedException {

        WeiWebsSmsConfig config = new WeiWebsSmsConfig();
        config.setDebugMode(1);
        config.setAccount("MXT801633");
        config.setPswd("Mxt801636");
        config.setUrl("http://www.weiwebs.cn/msg/HttpBatchSendSM");
//        config.setUserId("3371");
        List<SmsTemplate> templates = new ArrayList<>();
        SmsTemplate template = new SmsTemplate();
        template.setOperation("login");
//        template.setTemplateCode("dummy");
        template.setTemplateParam("{\"code\": \"%s\"}");
//        template.setSignName("dummy");
        template.setTemplateContent("【元咕咕】您的短信验证码是：{code}，2分钟内有效");
        templates.add(template);
        config.setTemplates(templates);
        config.setSendMessageInterval(2);
        WeiWebsSms sms = new WeiWebsSms(config);
        sms.sendMessage("13000000000","test",template);
        Thread.sleep(5000);
        sms.sendMessage("13000000000","test",template);
    }
    @org.junit.Test
    public void testWeiWebsSmsFailure() throws InterruptedException {

        WeiWebsSmsConfig config = new WeiWebsSmsConfig();
        config.setDebugMode(1);
        config.setAccount("MXT801633");
        config.setPswd("Mxt801636");
        config.setUrl("http://www.weiwebs.cn/msg/HttpBatchSendSM");
//        config.setUserId("3371");
        List<SmsTemplate> templates = new ArrayList<>();
        SmsTemplate template = new SmsTemplate();
        template.setOperation("login");
//        template.setTemplateCode("dummy");
        template.setTemplateParam("{\"code\": \"%s\"}");
//        template.setSignName("dummy");
        template.setTemplateContent("【元咕咕】您的短信验证码是：{code}，2分钟内有效");
        templates.add(template);
        config.setTemplates(templates);
        config.setSendMessageInterval(30);
        WeiWebsSms sms = new WeiWebsSms(config);
        sms.sendMessage("13000000000","test",template);
        Thread.sleep(5000);
        sms.sendMessage("13000000000","test",template);
    }
}
