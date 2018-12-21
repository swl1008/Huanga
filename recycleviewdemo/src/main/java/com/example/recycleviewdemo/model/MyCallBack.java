package com.example.recycleviewdemo.model;

public interface MyCallBack<T> {
    void success(T data);
    void failed(Exception e);
}
