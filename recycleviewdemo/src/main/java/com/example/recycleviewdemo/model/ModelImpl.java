package com.example.recycleviewdemo.model;

import com.example.recycleviewdemo.okhttp.ICallBack;
import com.example.recycleviewdemo.okhttp.OkHttpUtils;

import java.util.Map;

public class ModelImpl implements Imodel {
    @Override
    public void requestData(String url, Map<String, String> params, Class clazz, final MyCallBack callBack) {
        OkHttpUtils.getInstance().doPost(url, params, clazz, new ICallBack() {
            @Override
            public void success(Object obj) {
                callBack.success(obj);
            }

            @Override
            public void failed(Exception e) {
                callBack.failed(e);
            }
        });
    }
}
