package com.jfeat.am.module.email.services.impl;

import com.jfeat.am.module.email.mail.MailTask;
import com.jfeat.am.module.email.mail.TextMailSender;

/**
 * Created by ehngjen on 4/17/2015.
 */
public class TextMailTask extends MailTask {
    public TextMailTask() {
        super(new TextMailSender());
    }
}
