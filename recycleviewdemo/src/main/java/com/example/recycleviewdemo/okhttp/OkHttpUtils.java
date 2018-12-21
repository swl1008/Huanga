package com.example.recycleviewdemo.okhttp;

import android.os.Handler;
import android.os.Looper;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

public class OkHttpUtils {
    public static OkHttpUtils instance;
    private OkHttpClient httpClient;
    private Handler handler = new Handler(Looper.myLooper());
    public OkHttpUtils(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient = new OkHttpClient.Builder()
                .writeTimeout(5000, TimeUnit.SECONDS)
                .readTimeout(5000,TimeUnit.SECONDS)
                .connectTimeout(5000,TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .build();
    }

    public static OkHttpUtils getInstance() {
        if (instance == null){
            synchronized (OkHttpUtils.class){
                instance = new OkHttpUtils();
            }
        }
        return instance;
    }

    public void doPost(final String url, Map<String,String> params, final Class clazz, final ICallBack callBack){

        FormBody.Builder builder = new FormBody.Builder();

        for (Map.Entry<String,String> entry : params.entrySet()){
            builder.add(entry.getKey(),entry.getValue());
        }

        RequestBody body = builder.build();

        Request request = new Request.Builder()
                .post(body)
                .url(url)
                .build();

        Call call = httpClient.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.failed(e);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response){
                String result = null;
                try {
                    result = response.body().string();
                    Gson gson = new Gson();
                    final Object o = gson.fromJson(result, clazz);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            callBack.success(o);
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

    }
}
