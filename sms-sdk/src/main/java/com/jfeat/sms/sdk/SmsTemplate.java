package com.jfeat.sms.sdk;

/**
 * @author jackyhuang
 * @date 2022/1/10
 */
public class SmsTemplate {
    /**
     * 使用该模版的操作，如login
     */
    private String operation;
    /**
     * 短信签名名称
     */
    private String signName;
    /**
     * 短信模版内容, 替换的内容用{}
     * 如：你的验证码是 {code}, 有效期2分钟。
     */
    private String templateContent;
    /**
     * 短信模板ID
     */
    private String templateCode;
    /**
     * 短信模板变量对应的实际值
     *  code是短信模版定义的字段，使用%s给真实的code进行替换
     *     "{\"code\": \"%s\"}"
     */
    private String templateParam;

    public String getOperation() {
        return operation;
    }

    public String getTemplateContent() {
        return templateContent;
    }

    public void setTemplateContent(String templateContent) {
        this.templateContent = templateContent;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getSignName() {
        return signName;
    }

    public void setSignName(String signName) {
        this.signName = signName;
    }

    public String getTemplateCode() {
        return templateCode;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }

    public String getTemplateParam() {
        return templateParam;
    }

    public void setTemplateParam(String templateParam) {
        this.templateParam = templateParam;
    }
}
