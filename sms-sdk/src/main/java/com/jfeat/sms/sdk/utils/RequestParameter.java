package com.jfeat.sms.sdk.utils;

/**
 * @author jackyhuang
 * @date 2021/12/24
 */
public class RequestParameter {
    private String name;
    private String value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public RequestParameter(String name, String value) {
        this.name = name;
        this.value = value;
    }
}
