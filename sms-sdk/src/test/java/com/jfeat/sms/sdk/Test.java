package com.jfeat.sms.sdk;

import com.alibaba.fastjson.JSONObject;
import com.jfeat.code.store.MemoryStore;
import com.jfeat.sms.sdk.vendor.aliyun.AliyunSms;
import com.jfeat.sms.sdk.vendor.aliyun.AliyunSmsConfig;
import com.jfeat.sms.sdk.vendor.weiwebs.WeiWebsSms;
import com.jfeat.sms.sdk.vendor.weiwebs.WeiWebsSmsConfig;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import static java.lang.Thread.sleep;
import static org.junit.Assert.assertNotNull;

/**
 * @author jackyhuang
 * @date 2021/12/24
 */
public class Test {

    private Logger logger = LoggerFactory.getLogger(Test.class);
    private MockWebServer server;
    private int port = 9999;
    private String baseUrl = "http://127.0.0.1:" + port;

    @Before
    public void setup() throws IOException {
        server = new MockWebServer();
        server.start(port);
    }

    @After
    public void tearDown() throws IOException {
        if (server != null) {
            server.shutdown();
        }
    }
    @org.junit.Test
    public void testVenusSms() throws InterruptedException {
        server.enqueue(new MockResponse().setResponseCode(200));

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
        sms.sendMessage("phone","test",template);
        Thread.sleep(5000);
        sms.sendMessage("phone","test",template);

//        sms.sendCaptcha("15322315902", "login");
//        String code = MemoryStore.me().read("15322315902-login");
//        logger.debug("code=" + code);
//        assertNotNull(code);
    }
}
