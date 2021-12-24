package com.jfeat.sms.sdk;

import com.jfeat.sms.sdk.utils.HttpKit;
import com.jfeat.sms.sdk.utils.RequestParameter;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author jackyhuang
 * @date 2021/12/24
 */
public class TestHttpKit {

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
    public void testPostJson() {
        String url = baseUrl + "/json";
        server.enqueue(new MockResponse().setBody("hello"));
        String result = new HttpKit().url(url).postJson("abcd").exec();
        logger.debug(result);
        assertEquals(result, "hello");
    }

    @Test
    public void testPostForm() {
        String url = baseUrl + "/form";
        server.enqueue(new MockResponse().setBody("world"));
        List<RequestParameter> params = new ArrayList<>();
        params.add(new RequestParameter("username", "abc"));
        params.add(new RequestParameter("password", "123"));
        String result = new HttpKit().url(url).postForm(params).exec();
        logger.debug(result);
    }
}
