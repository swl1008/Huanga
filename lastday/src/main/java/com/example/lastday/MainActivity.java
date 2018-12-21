package com.example.lastday;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.lastday.adapter.ShopTypeAdapter;
import com.example.lastday.adapter.ShopTypeProductAdapter;
import com.example.lastday.bean.ShopTypeBean;
import com.example.lastday.bean.ShopTypeProductBean;
import com.example.lastday.presenter.PresenterImpl;
import com.example.lastday.view.Iview;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements Iview{

    private PresenterImpl presenter;
    private ShopTypeAdapter shopTypeAdapter;
    private ShopTypeProductAdapter shopTypeProductAdapter;
    private RecyclerView mRecyclerViewShopType, mRecyclerViewShop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter = new PresenterImpl(this);
        initShopTypeView();
        initShopTypeProductView();
        getTypeData();
    }

    /**
     * 初始化左侧recyclerView,加载左侧adapter
     */
    private void initShopTypeView(){
        mRecyclerViewShop = findViewById(R.id.recyclerview_shop_type);
        LinearLayoutManager linearLayoutManagerLeft = new LinearLayoutManager(this);
        linearLayoutManagerLeft.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerViewShop.setLayoutManager(linearLayoutManagerLeft);
        mRecyclerViewShop.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        shopTypeAdapter = new ShopTypeAdapter(this);
        mRecyclerViewShop.setAdapter(shopTypeAdapter);

        //添加接口回调，用来接收左侧recyclerView的cid
        shopTypeAdapter.setOnClickListener(new ShopTypeAdapter.OnClickListener() {
            @Override
            public void onClick(int position, String cid) {
                //拿到cid之后，通过接口获得对应的数据，展示在右侧列表中
                getShopData(cid);
                Toast.makeText(MainActivity.this,cid+"",Toast.LENGTH_SHORT).show();
            }
        });

    }

    /**
     * 初始化右侧recyclerView,加载右侧adapter
     */
    private void initShopTypeProductView(){
        mRecyclerViewShopType = findViewById(R.id.recyclerview_shop);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerViewShopType.setLayoutManager(linearLayoutManager);
        mRecyclerViewShopType.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        shopTypeProductAdapter = new ShopTypeProductAdapter(this);
        mRecyclerViewShopType.setAdapter(shopTypeProductAdapter);
    }



    /**
     * 获取左侧列表数据
     */
    private void getTypeData(){
        Map<String,String> map = new HashMap<>();
        presenter.requestData(Apis.URL_PRODUCT_GET_CATAGORY,map,ShopTypeBean.class);
    }
    /**
     * 获取右侧列表数据
     * @param cid
     */
    private void getShopData(String cid){
        Map<String, String> map = new HashMap<>();
        map.put(Constants.CID,cid);
        presenter.requestData(Apis.URL_PRODUCT_GET_PRODUCT_CATAGORY,map,ShopTypeProductBean.class);
    }

    @Override
    public void showResponseData(Object data) {
        if (data instanceof ShopTypeBean) {
            //获取数据后，展示左侧列表
            ShopTypeBean shopTypeBean = (ShopTypeBean) data;
            shopTypeAdapter.setData(shopTypeBean.getData());
        } else if (data instanceof ShopTypeProductBean) {
            //获取数据后，展示右侧列表
            ShopTypeProductBean shopTypeProductBean = (ShopTypeProductBean) data;
            shopTypeProductAdapter.setData(shopTypeProductBean.getData());

            //将右侧列表滚到顶部
            mRecyclerViewShopType.scrollToPosition(0);
        }
    }

    @Override
    public void showResponseFail(Object data) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDetach();
    }
}
