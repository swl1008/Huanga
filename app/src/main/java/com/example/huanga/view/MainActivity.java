package com.example.huanga.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.huanga.Apis;
import com.example.huanga.Constants;
import com.example.huanga.R;
import com.example.huanga.adapter.ShopAdapter;
import com.example.huanga.bean.ShopBean;
import com.example.huanga.presenter.PresenterImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements Iview{

    private TextView all_price,summit;
    private CheckBox checkBox;
    private ShopAdapter shopAdapter;
    private PresenterImpl presenter;
    private List<ShopBean.DataBean> mList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter = new PresenterImpl(this);
        initView();
        getData();
    }

    public void initView(){
        all_price = findViewById(R.id.all_price);
        summit = findViewById(R.id.summit);
        checkBox = findViewById(R.id.checkbox);

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkSeller(checkBox.isChecked());
                shopAdapter.notifyDataSetChanged();
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recycle);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        shopAdapter = new ShopAdapter(this);
        recyclerView.setAdapter(shopAdapter);

        shopAdapter.setListener(new ShopAdapter.ShopCallBackListener() {
            @Override
            public void callBack(List<ShopBean.DataBean> list) {
                //初始化总价
                double totalPrice=0;
                //初始化勾选商品数量
                int num=0;
                //商品总数
                int totalNum = 0;

                for (int a=0;a<list.size();a++){
                    //获取商家里的商品
                    List<ShopBean.DataBean.ListBean> listAll = list.get(a).getList();

                    for (int i=0;i<listAll.size();i++){
                        totalNum += listAll.get(i).getNum();

                        if (listAll.get(i).isCheck()){
                            totalPrice += (listAll.get(i).getPrice()*listAll.get(i).getNum());
                            num += listAll.get(i).getNum();
                        }

                    }
                }

                if (num<totalNum){
                    checkBox.setChecked(false);
                }else {
                    checkBox.setChecked(true);
                }


                all_price.setText("合计为："+totalPrice);
                summit.setText("去结算（"+num+")");

            }
        });



    }
    public void getData(){
        Map<String,String> map = new HashMap<>();
        map.put(Constants.UID,"71");
        presenter.startRequest(Apis.URL,map,ShopBean.class);
    }
    @Override
    public void showResponseData(Object data) {
            if (data instanceof ShopBean){
                ShopBean shopBean = (ShopBean) data;
                mList = shopBean.getData();
                if (mList != null){
                    mList.remove(0);
                    shopAdapter.setList(mList);
                }
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

    public void checkSeller(boolean bool){
        double totalPrice=0;
        int num =0;
        for (int a=0;a<mList.size();a++){
            ShopBean.DataBean bean = mList.get(a);
            bean.setCheck(bool);

            List<ShopBean.DataBean.ListBean> listBeans = mList.get(a).getList();

            for (int i=0;i<listBeans.size();i++){

                listBeans.get(i).setCheck(bool);

                totalPrice += (listBeans.get(i).getPrice()*listBeans.get(i).getNum());

                num += listBeans.get(i).getNum();

            }

        }

        if (bool){
            all_price.setText("合计为："+totalPrice);
            summit.setText("去结算（"+num+")");
        }else {
            all_price.setText("合计为：0.00");
            summit.setText("去结算（0)");
        }

    }
}
