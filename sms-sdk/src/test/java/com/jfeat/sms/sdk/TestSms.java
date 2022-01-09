package com.jfeat.sms.sdk;

import com.jfeat.code.store.MemoryStore;
import com.jfeat.sms.sdk.vendor.aliyun.AliyunSms;
import com.jfeat.sms.sdk.vendor.aliyun.AliyunSmsConfig;
import com.jfeat.sms.sdk.vendor.vanus.VanusSms;
import com.jfeat.sms.sdk.vendor.vanus.VanusSmsConfig;
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
import java.util.HashMap;
import java.util.Map;

/**
 * @author jackyhuang
 * @date 2021/12/24
 */
public class TestSms {

    private Logger logger = LoggerFactory.getLogger(TestHttpKit.class);
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
    public void testVanusSms() {
        server.enqueue(new MockResponse().setResponseCode(200));

        VanusSmsConfig config = new VanusSmsConfig();
        config.setAccount("account1");
        config.setPassword("pwd1");
        config.setUrl(baseUrl + "/vanusSms");
        config.setUserId("user1");
        Sms sms = new VanusSms(config);

        sms.sendCaptcha("13800000000", "Register");
        String code = MemoryStore.me().read("13800000000-Register");
        logger.debug("code=" + code);
        assertNotNull(code);
    }

    @Test
    public void testVanusSmsFactory() {
        server.enqueue(new MockResponse().setResponseCode(200));

        Map<String, String> configMap = new HashMap<>();
        configMap.put("account", "account1");
        configMap.put("password", "pwd1");
        configMap.put("userId", "uid");
        configMap.put("url", baseUrl + "/vanusSms");
        Sms sms = SmsFactory.me().getSms("vanus", configMap);

        sms.sendCaptcha("13800000000", "Register");
        String code = MemoryStore.me().read("13800000000-Register");
        logger.debug("code=" + code);
        assertNotNull(code);
    }

    @Test
    @Ignore
    public void testAliyunSms() {
        server.enqueue(new MockResponse().setResponseCode(200));

        AliyunSmsConfig config = new AliyunSmsConfig();
        config.setAccessKeyId("accesskey");
        config.setAccessSecret("accesssecret");
        config.setSignName("SignName");
        config.setTemplateCode("TempCode");
        config.setTemplateParam("");
        Sms sms = new AliyunSms(config);

        sms.sendCaptcha("13800000000", "Register");
        String code = MemoryStore.me().read("13800000000-Register");
        logger.debug("code=" + code);
        assertNotNull(code);
    }
}
