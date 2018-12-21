package com.example.huanga.model;

import com.example.huanga.okhttp.ICallBack;
import com.example.huanga.okhttp.OkHttpUtil;

import java.util.Map;

public class ModelImpl implements Imodel {
    @Override
    public void requestData(String url, Map<String, String> params, Class clazz, final MyCallBack callBack) {
        OkHttpUtil.getInstance().doPost(url, params, clazz, new ICallBack() {
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
