package com.jfeat.am.module.email.mail;

import com.jfeat.am.module.email.properties.MailSenderProperties;

public class MailUtil {
    public static void setSenderInfoByRequest(MailSenderProperties settingInfo, MailSenderProperties bodyInfo){
        if(bodyInfo.getUserName()!=null){
            settingInfo.setFromAddress(bodyInfo.getUserName());
            settingInfo.setUserName(bodyInfo.getUserName());}
        if(bodyInfo.getPassword()!=null){settingInfo.setPassword(bodyInfo.getPassword());}
        if(bodyInfo.getPort()!=null){settingInfo.setPort(bodyInfo.getPort());}
        if(bodyInfo.getHost()!=null){settingInfo.setHost(bodyInfo.getHost());}

    };
}
