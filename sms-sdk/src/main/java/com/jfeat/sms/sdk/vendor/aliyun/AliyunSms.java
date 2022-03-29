package com.jfeat.sms.sdk.vendor.aliyun;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.jfeat.sms.sdk.AbstractSms;
import com.jfeat.sms.sdk.SmsException;
import com.jfeat.sms.sdk.SmsTemplate;

import java.util.Date;
import java.util.HashMap;
import java.util.Optional;

/**
 * @author jackyhuang
 * @date 2021/12/23
 */
public class AliyunSms extends AbstractSms {

    private AliyunSmsConfig config;

    private String regionId = "cn-hangzhou";
    /**
     * 短信服务的服务接入地址
     */
    private String domain = "dysmsapi.aliyuncs.com";
    /**
     * API的版本号
     */
    private String version = "2017-05-25";
    /**
     * API的名称
     */
    private String action = "SendSms";

    public AliyunSms(AliyunSmsConfig config) {
        super(config);
        this.config = config;
    }

    /**
     *
     * @param phone 接收短信的手机号码
     * @param code 短信验证码
     * @param template 短信模版
     */
    @Override
    public void sendMessage(String phone, String code, SmsTemplate template) {
        if(!checkSendMessageInterval(phone,code))return;
        logger.info("sms-sdk: send msg: phone={}, code={}", phone, code);
        DefaultProfile profile = DefaultProfile.getProfile(regionId, config.getAccessKeyId(), config.getAccessSecret());
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain(domain);
        request.setSysVersion(version);
        request.setSysAction(action);
        request.putQueryParameter("PhoneNumbers", phone);
        request.putQueryParameter("SignName", template.getSignName());
        request.putQueryParameter("TemplateCode", template.getTemplateCode());
        request.putQueryParameter("TemplateParam", formatTemplateParam(template, code));
        try {
            CommonResponse response = client.getCommonResponse(request);
            logger.info("sms-sdk: http response: {}", response.getData());
        } catch (ClientException e) {
            logger.error(e.getMessage());
        }
    }


}
