package com.example.recycleviewdemo.view;

public interface Iview<T> {
    void showResponseData(T data);
    void showResponseFail(T data);
}
