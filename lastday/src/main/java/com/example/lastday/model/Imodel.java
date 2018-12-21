package com.example.lastday.model;

import java.util.Map;

public interface Imodel {
    void responseData(String url, Map<String,String> params,Class clazz,MyCallBack callBack);
}
