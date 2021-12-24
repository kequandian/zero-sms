package com.jfeat.sms.sdk.vendor.vanus;

import com.jfeat.sms.sdk.AbstractSms;
import com.jfeat.sms.sdk.utils.HttpKit;
import com.jfeat.sms.sdk.utils.RequestParameter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jackyhuang
 * @date 2021/12/23
 */
public class VanusSms extends AbstractSms {

    private VanusSmsConfig config;

    private String action = "send";

    public VanusSms(VanusSmsConfig config) {
        super(config);
        this.config = config;
    }

    @Override
    public void sendMessage(String phone, String message) {
        logger.debug("sendMessage: phone={}, message={}", phone, message);
        List<RequestParameter> params = new ArrayList<>();
        params.add(new RequestParameter("action", action));
        params.add(new RequestParameter("userid", config.getUserId()));
        params.add(new RequestParameter("account", config.getAccount()));
        params.add(new RequestParameter("password", config.getPassword()));
        params.add(new RequestParameter("mobile", phone));
        params.add(new RequestParameter("content", message));
        String result = new HttpKit().url(config.getUrl())
                .postForm(params)
                .exec();
        //TODO handle the result
        logger.debug("result: {}", result);
    }
}
