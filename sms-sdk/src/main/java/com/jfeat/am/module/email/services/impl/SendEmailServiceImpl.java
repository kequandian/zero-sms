package com.jfeat.am.module.email.services.impl;

import com.jfeat.am.module.email.mail.HtmlMailSender;
import com.jfeat.am.module.email.mail.TextMailSender;
import com.jfeat.am.module.email.properties.MailSenderProperties;
import com.jfeat.am.module.email.services.SendEmailServices;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("SendEmailServices")
public class SendEmailServiceImpl implements SendEmailServices {

    @Resource
    TextMailSender textMailSender;
    @Resource
    HtmlMailSender htmlMailSender;

    @Override
    public boolean sendTextEmail(MailSenderProperties mailSenderInfo){

        boolean send = textMailSender.send(mailSenderInfo);
        return send;
    }


    @Override
    public boolean sendHtmlEmail(MailSenderProperties mailSenderInfo){

        boolean send = htmlMailSender.send(mailSenderInfo);
        return send;
    }

}
