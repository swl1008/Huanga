package com.example.huanga.presenter;

import com.example.huanga.model.ModelImpl;
import com.example.huanga.model.MyCallBack;
import com.example.huanga.view.Iview;

import java.util.Map;

public class PresenterImpl implements Ipresenter{

    private Iview iView;
    private ModelImpl model;

    public PresenterImpl(Iview iView) {
        this.iView = iView;
        model = new ModelImpl();
    }
    @Override
    public void startRequest(String url, Map<String, String> params, Class clazz) {

        model.requestData(url, params, clazz, new MyCallBack() {
            @Override
            public void success(Object data) {
                iView.showResponseData(data);
            }

            @Override
            public void failed(Exception e) {
                iView.showResponseFail(e);
            }
        });
    }

    public void onDetach(){
        if (iView != null){
            iView = null;
        }
        if (model != null){
            model = null;
        }
    }
}
