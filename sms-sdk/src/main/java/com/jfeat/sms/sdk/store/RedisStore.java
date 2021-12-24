package com.jfeat.sms.sdk.store;

import com.jfeat.sms.sdk.Store;

/**
 * TODO implement it
 * @author jackyhuang
 * @date 2021/12/23
 */
public class RedisStore implements Store {
    @Override
    public void save(String key, String value, int ttl) {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public String read(String key) {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public void delete(String key) {
        throw new RuntimeException("Not implemented");
    }
}
