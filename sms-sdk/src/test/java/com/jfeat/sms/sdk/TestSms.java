package com.jfeat.sms.sdk;

import com.alibaba.fastjson.JSONObject;
import com.jfeat.code.store.MemoryStore;
import com.jfeat.sms.sdk.vendor.aliyun.AliyunSms;
import com.jfeat.sms.sdk.vendor.aliyun.AliyunSmsConfig;
import com.jfeat.sms.sdk.vendor.venus.VenusSms;
import com.jfeat.sms.sdk.vendor.venus.VenusSmsConfig;
import com.jfeat.sms.sdk.weiwebs.venus.WeiWebsSms;
import com.jfeat.sms.sdk.weiwebs.venus.WeiWebsSmsConfig;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static org.junit.Assert.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author jackyhuang
 * @date 2021/12/24
 */
public class TestSms {

    private Logger logger = LoggerFactory.getLogger(TestSms.class);
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

    @Test
    public void testVenusSms() {
        server.enqueue(new MockResponse().setResponseCode(200));

        WeiWebsSmsConfig config = new WeiWebsSmsConfig();
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
        Sms sms = new WeiWebsSms(config);

        sms.sendCaptcha("15322315902", "login");
        String code = MemoryStore.me().read("15322315902-login");
        logger.debug("code=" + code);
        assertNotNull(code);
    }

    @Ignore
    @Test
    public void testVenusSmsFactory() {
        server.enqueue(new MockResponse().setResponseCode(200));

        JSONObject configMap = new JSONObject();
        configMap.put("account", "895883631");
        configMap.put("password", "895883631");
        configMap.put("userId", "3371");
        configMap.put("url", "http://zzsms365.com/sms.aspx");
        List<SmsTemplate> templates = new ArrayList<>();
        SmsTemplate template = new SmsTemplate();
        template.setOperation("register");
        template.setTemplateCode("dummy");
        template.setTemplateParam("{\"code\": \"%s\"}");
        template.setSignName("dummy");
        template.setTemplateContent("Your code is {code}, valid in 2 minutes.");
        templates.add(template);
        configMap.put("templates", templates);
        Sms sms = SmsFactory.me().getSms("venus", configMap);

        sms.sendCaptcha("13800000000", "register");
        String code = MemoryStore.me().read("13800000000-register");
        logger.debug("code=" + code);
        assertNotNull(code);
    }

    @Ignore
    @Test
    public void testAliyunSmsFactory() {
        server.enqueue(new MockResponse().setResponseCode(200));

        JSONObject configMap = new JSONObject();
        configMap.put("accessKeyId", "account1");
        configMap.put("accessSecret", "pwd1");
        configMap.put("captchaCount", "8");
        Map<String, Object> templates = new HashMap<>();
        Map<String, String> regTemplate = new HashMap<>();
        regTemplate.put("SignName", "SignName1");
        regTemplate.put("templateCode", "t1");
        regTemplate.put("templateParam", "para");
        templates.put("register", regTemplate);
        configMap.put("templates", templates);
        Sms sms = SmsFactory.me().getSms("aliyun", configMap);

        sms.sendCaptcha("13800000000", "register");
        String code = MemoryStore.me().read("13800000000-register");
        logger.debug("code=" + code);
        assertNotNull(code);
    }

    @Test
    @Ignore
    public void testAliyunSms() {
        server.enqueue(new MockResponse().setResponseCode(200));

        AliyunSmsConfig config = new AliyunSmsConfig();
        config.setAccessKeyId("");
        config.setAccessSecret("");
        SmsTemplate template = new SmsTemplate();
        template.setOperation("register");
        template.setSignName("SmallSaaS");
        template.setTemplateCode("SMS_138535098");
        template.setTemplateParam("{\"code\": \"%s\"}");
        List<SmsTemplate> templates = new ArrayList<>();
        templates.add(template);
        config.setTemplates(templates);
        Sms sms = new AliyunSms(config);

        sms.sendCaptcha("13800000000", "register");
        String code = MemoryStore.me().read("13800000000-register");
        logger.debug("code=" + code);
        assertNotNull(code);
    }
}
