/*
 * Copyright (C) 2014-2015 by ehngjen @ www.jfeat.com
 *
 *  The program may be used and/or copied only with the written permission
 *  from JFeat.com, or in accordance with the terms and
 *  conditions stipulated in the agreement/contract under which the program
 *  has been supplied.
 *
 *  All rights reserved.
 */

package com.jfeat.am.module.email.mail;


import com.jfeat.am.module.email.properties.MailSenderProperties;
import com.jfeat.am.module.email.services.MailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

/**
 * Created by ehngjen on 4/17/2015.
 */
@Service
public class TextMailSender implements MailSender {

    @Autowired
    MailSenderProperties info;

    @Override
    public boolean send(MailSenderProperties mailInfo) {
        //优先根据请求体中的参数进行配置
        MailUtil.setSenderInfoByRequest(info,mailInfo);
        // 判断是否需要身份认证
        MailAuthenticator authenticator = null;
      //  Properties pro = mailInfo.getProperties();
        if (info.isValidate()) {
            // 如果需要身份认证，则创建一个密码验证器
            authenticator = new MailAuthenticator(info.getUserName(), info.getPassword());
        }
        // 根据邮件会话属性和密码验证器构造一个发送邮件的session
        Properties pro = info.getProperties();
        Session sendMailSession = Session.getDefaultInstance(pro, authenticator);
        try {
            // 根据session创建一个邮件消息
            Message mailMessage = new MimeMessage(sendMailSession);
            // 创建邮件发送者地址
            Address from = new InternetAddress(info.getFromAddress());
            // 设置邮件消息的发送者
            mailMessage.setFrom(from);
            // 创建邮件的接收者地址，并设置到邮件消息中
            Address to = new InternetAddress(mailInfo.getToAddress());
            mailMessage.setRecipient(Message.RecipientType.TO, to);
            // 设置邮件消息的主题
            mailMessage.setSubject(mailInfo.getSubject());
            // 设置邮件消息发送的时间
            mailMessage.setSentDate(new Date());
            // 设置邮件消息的主要内容
            String mailContent = mailInfo.getContent();
            mailMessage.setText(mailContent);
            // 发送邮件
            Transport.send(mailMessage);
            return true;
        } catch (MessagingException ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
