# Kequandian SMS

## SMS-SDK

![arch](./doc/arch.png?raw=true)



### 如何使用

```java
VanusSmsConfig config = new VanusSmsConfig();
config.setAccount("account1");
config.setPassword("pwd1");
config.setUrl("http://127.0.0.1:8080/vanusSms");
config.setUserId("user1");
Sms sms = new VanusSms(config);
sms.sendCaptcha(req.getPhone(), req.getOperation());
```

或者使用工厂方法, 推荐使用这个方法：
```java
Map<String, Object> configMap = new HashMap<>();
configMap.put("account", "account1");
configMap.put("password", "pwd1");
configMap.put("userId", "uid");
configMap.put("url", baseUrl + "/vanusSms");
Sms sms = SmsFactory.me().getSms("vanus", configMap);
sms.sendCaptcha("1380000000", "register");
```

```yaml
io:
  sms:
    environment: dev  # ["dev","development","prod","production"]: default prod
    captchaRequiredOperations:
      - login
      - register
      - findPassword
    enableVendor: weiwebs
    registeredTestPhones:
      - 180123456
      - 180123458
    registeredPassingCode: 010101
    vendors:
      weiwebs:
        debugMode: 1
        captchaCount: 5
        captchaTtlSeconds: 120
        templates:
          - operation: login
            templateContent: '【元咕咕】您的短信验证码是：{code}，2分钟内有效'
            templateParam: '{"code": "%s"}'
          - operation: register
            templateContent: '【元咕咕】您的短信验证码是：{code}，2分钟内有效'
            templateParam: '{"code": "%s"}'
          - operation: findPassword
            templateContent: '【元咕咕】您的短信验证码是：{code}，2分钟内有效'
            templateParam: '{"code": "%s"}'
        account: MXT801633
        pswd: Mxt801636
        url: http://www.weiwebs.cn/msg/HttpBatchSendSM
      aliyun:
        accessKeyId: keyid
        accessSecret: secret
        captchaCount: 5
        captchaTtlSeconds: 120
        templates:
          - operation: login
            signName: aliyun
            templateParam: '{"code": "%s"}'
            templateCode: T1
```

### 如何扩展

继承`AbstractSms`类，提供`sendMessage`的实现

添加该vendor的一个配置项的实现类

```java
class TencentSmsConfig implements SmsConfig {
  private String key;
  private String secret;
}
class TencentSms extends AbstractSms {
  private TencentSmsConfig config;
  public TencentSms(TencentSmsConfig config) {
    super(config);
    this.config = config;
  }
  @override
  public void sendMessage(String phone, String message) {
    // use tencent sdk to send message
  }
}
```



## SMS-BOOT

SMS SDK的使用示例

```json
POST /api/sms/v1/captcha
{
  "phone": "13800000000",
  "opeartion": "register"
}

POST /api/sms/v1/verify
{
  "phone": "13800000000",
  "opeartion": "register",
  "code": "1235"
}
```

### SMS配置

应用层提供配置的实现，通过`SmsConfig`注入给Sms使用。

配置可能是来自配置文件，也可能来自数据库，或者其他微服务的接口提供。

Verify通常是和业务关联的，建议在业务层通过调用SDK的verify方法来使用，而不是直接提供接口给前端，否则会有漏洞。

## Captcha SDK

提供接口的图形验证码功能

### 配置

```yaml
io:
  captcha:
    enabled：true
    uuidHeaderName: x-captcha-code-uuid
    codeHeaderName: x-captcha-code
    type: 0 # 0: numeric, 1: alphabetic, 2: alphanumeric
    codeLength: 5
    ttlSeconds: 60

```

### 使用方法

#### 接口

GET /api/captcha/v1/code

返回一个图片base64

同时里带上 uuid 作为验证时unique ID用。

```
{
    "uuid": "a312c8b042664ffe954d6fea1f19cbb3",
    "base64Data": "data:image/png;base64,/9j/4AAQSkZJRg....."
}
```

在使用验证码的方法上通过该uuid把验证码通过header带上

```
x-captcha-code-uuid: <uuid>
x-captcha-code: <code>
```

![cpatcha](./doc/captcha.png?raw=true)

#### 注解 

`@Captcha` 用于需要进行图形验证码的方法，处理该注解的方法里面会验证header传入的code与系统保存的code做对比，一致的话就继续执行后续代码，否则返回错误。

