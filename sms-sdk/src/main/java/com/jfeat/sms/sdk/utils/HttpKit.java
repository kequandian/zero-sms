package com.jfeat.sms.sdk.utils;

import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author jackyhuang
 * @date 2021/12/24
 */
public class HttpKit {
    private Logger logger = LoggerFactory.getLogger(HttpKit.class);

    private OkHttpClient client = new OkHttpClient();
    private Request.Builder builder = new Request.Builder();

    public HttpKit url(String url) {
        builder.url(url);
        return this;
    }

    public HttpKit header(String key, String value) {
        builder.header(key, value);
        return this;
    }

    public HttpKit headers(Map<String, String> headers) {
        builder.headers(Headers.of(headers));
        return this;
    }

    public HttpKit postForm(List<RequestParameter> params) {
        final FormBody.Builder formBodyBuilder = new FormBody.Builder();
        if (params != null && params.size() > 0) {
            params.forEach(param -> formBodyBuilder.add(param.getName(), param.getValue()));
        }
        RequestBody requestBody = formBodyBuilder.build();
        builder.post(requestBody);
        return this;
    }

    public HttpKit postJson(String jsonStr) {
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody requestBody = RequestBody.create(JSON, jsonStr);
        builder.post(requestBody);
        return this;
    }

    public String exec() {
        try {
            Request request = builder.build();
            logger.debug("request: {} {}, headers: {}",
                    request.method(),
                    request.url(),
                    request.header("Content-Type"));
            Response response = client.newCall(request).execute();
            logger.debug("result: code={}, message={}",
                    response.code(),
                    response.message());
            if (response.isSuccessful()) {
                return response.body().string();
            }
            throw new HttpException("Response not successful");
        } catch (IOException e) {
            throw new HttpException(e.getMessage());
        }
    }

}
