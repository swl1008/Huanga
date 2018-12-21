package com.example.lastday.presenter;

import com.example.lastday.model.ModelImpl;
import com.example.lastday.model.MyCallBack;
import com.example.lastday.view.Iview;

import java.util.Map;

public class PresenterImpl implements Ipresenter {
    private Iview iview;
    private ModelImpl model;

    public PresenterImpl(Iview iview) {
        this.iview = iview;
        model = new ModelImpl();
    }


    public void onDetach(){
        if (iview != null){
            iview = null;
        }
        if (model != null){
            model = null;
        }
    }

    @Override
    public void requestData(String url, Map<String, String> params, Class clazz) {
        model.responseData(url, params, clazz, new MyCallBack() {
            @Override
            public void success(Object data) {
                iview.showResponseData(data);
            }

            @Override
            public void failed(Exception e) {
                iview.showResponseFail(e);
            }
        });
    }
}
