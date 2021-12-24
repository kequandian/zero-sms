package com.jfeat.sms.sdk.vendor.aliyun;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.jfeat.sms.sdk.AbstractSms;

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
        this.config = config;
    }

    /**
     *
     * @param phone 接收短信的手机号码
     * @param message 插入到消息模版里面的消息体，要和templateParam的参数对应
     */
    @Override
    public void sendMessage(String phone, String message) {
        logger.debug("sendMessage: phone={}, message={}", phone, message);
        DefaultProfile profile = DefaultProfile.getProfile(regionId, config.getAccessKeyId(), config.getAccessSecret());
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain(domain);
        request.setSysVersion(version);
        request.setSysAction(action);
        request.putQueryParameter("PhoneNumbers", phone);
        request.putQueryParameter("SignName", config.getSignName());
        request.putQueryParameter("TemplateCode", config.getTemplateCode());
        request.putQueryParameter("TemplateParam", formatTemplateParam(message));
        try {
            CommonResponse response = client.getCommonResponse(request);
            logger.debug("response: {}", response.getData());
        } catch (ClientException e) {
            logger.error(e.getMessage());
        }
    }

    private String formatTemplateParam(String code) {
        if (config.getTemplateParam() != null) {
            return String.format(config.getTemplateParam(), code);
        }
        return "";
    }
}
