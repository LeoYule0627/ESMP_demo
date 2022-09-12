package com.practice.esmp_demo.controller;

import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class MstmbCache implements Cache {

    private Map<String, String> storage = new ConcurrentHashMap<>();

    @Override
    public String getName() {
        return null;
    }

    @Override
    public Object getNativeCache() {
        return null;
    }

    @Override
    public ValueWrapper get(Object key) {
        String k = key.toString();
        String value = storage.get(k);

        return StringUtils.isEmpty(value) ? null : new SimpleValueWrapper(value.toString());
    }

    @Override
    public <T> T get(Object key, Class<T> type) {
        return null;
    }

    @Override
    public <T> T get(Object key, Callable<T> valueLoader) {
        return null;
    }

    @Override
    public void put(Object key, Object value) {
        if (Objects.isNull(value)) {
            return;
        }

        storage.put(key.toString(), value.toString());
    }

    @Override
    public void evict(Object key) {
        storage.remove(key.toString());
    }

    @Override
    public void clear() {

    }
}