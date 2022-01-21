package com.jfeat.am.module.email.api;

import com.jfeat.am.module.email.properties.MailSenderProperties;
import com.jfeat.am.module.email.services.SendEmailServices;
import com.jfeat.crud.base.tips.SuccessTip;
import com.jfeat.crud.base.tips.Tip;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/crud/email")
public class SendEmailEndpoint {
    @Resource
    SendEmailServices sendEmailServices;

    @PostMapping("/text")
    public Tip sendEmail(@RequestBody MailSenderProperties mailSenderInfo){
        boolean b = sendEmailServices.sendTextEmail(mailSenderInfo);
        return SuccessTip.create(b);
    }

    @PostMapping("/html")
    public Tip sendHtmlEmail(@RequestBody MailSenderProperties mailSenderInfo){
        boolean b = sendEmailServices.sendHtmlEmail(mailSenderInfo);
        return SuccessTip.create(b);
    }
}
