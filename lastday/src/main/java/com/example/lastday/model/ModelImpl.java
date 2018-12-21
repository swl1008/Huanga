package com.example.lastday.model;

import com.example.lastday.OkHttp.ICallBck;
import com.example.lastday.OkHttp.OkHttpUtil;

import java.util.Map;

public class ModelImpl implements Imodel{
    @Override
    public void responseData(String url, Map<String, String> params, Class clazz, final MyCallBack callBack) {
        OkHttpUtil.getInstance().doPost(url, params, clazz, new ICallBck() {
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
