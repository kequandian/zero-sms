package com.jfeat.sms.sdk;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

public class TestClient {
    public TestClient() {
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        String url = "http://116.62.212.142/msg/HttpSendSM?";
        HttpClient httpClient = new HttpClient();
        httpClient.getParams().setBooleanParameter("http.protocol.expect-continue", false);
        PostMethod getMethod = new PostMethod(url);
        String Content = URLEncoder.encode("【元咕咕】您的短信验证码是：1234，2分钟内有效", "UTF-8");
        NameValuePair[] data = new NameValuePair[]{new NameValuePair("account", "MXT801633"), new NameValuePair("pswd", "Mxt801636"), new NameValuePair("mobile", "15322315902"), new NameValuePair("needstatus", "false"), new NameValuePair("msg", Content)};
        getMethod.setRequestBody(data);
        getMethod.addRequestHeader("Connection", "close");

        try {
            int statusCode = httpClient.executeMethod(getMethod);
            if (statusCode != 200) {
                System.err.println("Method failed: " + getMethod.getStatusLine());
            }

            byte[] responseBody = getMethod.getResponseBody();
            String str = new String(responseBody, "utf-8");
            if (str.contains("GBK")) {
                str = str.replaceAll("GBK", "utf-8");
            }

            System.out.println(str);
        } catch (HttpException var13) {
            System.out.println(1);
        } catch (IOException var14) {
            System.out.println(2);
        } finally {
            getMethod.releaseConnection();
        }

    }
}
