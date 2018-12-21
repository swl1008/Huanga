package com.example.lastday.presenter;

import java.util.Map;

public interface Ipresenter {
    void requestData(String url, Map<String,String> params,Class clazz);
}
