package com.example.huanga.model;

public interface MyCallBack<T> {
    void success(T data);
    void failed(Exception e);
}
