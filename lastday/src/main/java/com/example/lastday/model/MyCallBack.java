package com.example.lastday.model;

public interface MyCallBack<T> {
    void success(T data);
    void failed(Exception e);
}
