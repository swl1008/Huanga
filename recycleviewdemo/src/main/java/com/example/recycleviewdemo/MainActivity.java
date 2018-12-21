package com.example.recycleviewdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.example.recycleviewdemo.adapter.StaggeredAdapter;
import com.example.recycleviewdemo.bean.UserBean;
import com.example.recycleviewdemo.presenter.PresenterImpl;
import com.example.recycleviewdemo.view.Iview;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements Iview{

    private PresenterImpl presemter;
    private RecyclerView recyclerView;
    private final int mSpanCount = 2;
    private StaggeredAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presemter = new PresenterImpl(this);
        initView();
        initData();
    }
    private void initView() {
        //获取资源id
        recyclerView = findViewById(R.id.recycle);
        //创建布局管理器
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(mSpanCount,StaggeredGridLayoutManager.VERTICAL);
        //设置布局管理器
        recyclerView.setLayoutManager(layoutManager);
        //创建适配器
        adapter = new StaggeredAdapter(this);
        recyclerView.setAdapter(adapter);

    }

    private void initData() {
        Map<String,String> params = new HashMap<>();
        presemter.startRequest(Apis.URL_IMAGE,params,UserBean.class);
    }
    @Override
    public void showResponseData(Object data) {
        if(data instanceof UserBean){
            UserBean bean = (UserBean) data;
            adapter.setmList(bean.getData());
        }
    }

    @Override
    public void showResponseFail(Object data) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presemter.onDetach();
    }
}
