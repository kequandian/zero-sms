package com.jfeat.sms.sdk;

/**
 * @author jackyhuang
 * @date 2021/12/23
 */
public interface Store {
    void save(String key, String value, int ttlSeconds);
    String read(String key);
    void delete(String key);
}
