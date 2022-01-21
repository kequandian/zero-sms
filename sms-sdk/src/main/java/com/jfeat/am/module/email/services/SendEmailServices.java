package com.jfeat.am.module.email.services;

import com.jfeat.am.module.email.properties.MailSenderProperties;

public interface SendEmailServices {
    boolean sendTextEmail(MailSenderProperties mailSenderInfo);

    boolean sendHtmlEmail(MailSenderProperties mailSenderInfo);
}
