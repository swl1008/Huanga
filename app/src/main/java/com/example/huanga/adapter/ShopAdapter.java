package com.example.huanga.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.huanga.R;
import com.example.huanga.bean.ShopBean;

import java.util.ArrayList;
import java.util.List;

public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.MyViewHolder> {
    private Context mContext;
    private List<ShopBean.DataBean> mList = new ArrayList<>();

    public ShopAdapter(Context context) {
        this.mContext = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(mContext, R.layout.shop_seller_adapter,null);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int i) {
        myViewHolder.seller_text.setText(mList.get(i).getSellerName());

        //商品的适配器
        final ProductAdapter productAdapter = new ProductAdapter(mContext,mList.get(i).getList());

        //布局
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        myViewHolder.recyclerView.setLayoutManager(linearLayoutManager);
        myViewHolder.recyclerView.setAdapter(productAdapter);

        //拿到bean里面的复选框状态值
        myViewHolder.seller_check.setChecked(mList.get(i).isCheck());

        //商品的监听
        productAdapter.setListener(new ProductAdapter.ShopCallBackListener() {
            @Override
            public void callBack() {
                if (shopCallBackListeber != null){
                    shopCallBackListeber.callBack(mList);
                }

                List<ShopBean.DataBean.ListBean> listBeans = mList.get(i).getList();

                boolean isAllChecked = true;
                for (ShopBean.DataBean.ListBean listBean : listBeans){
                    if (!listBean.isCheck()){
                        isAllChecked = false;
                        break;
                    }
                }

                myViewHolder.seller_check.setChecked(isAllChecked);
                mList.get(i).setCheck(isAllChecked);

            }
        });

        //底部复选框全选
        myViewHolder.seller_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mList.get(i).setCheck(myViewHolder.seller_check.isChecked());
                productAdapter.selectOrRemoveAll(myViewHolder.seller_check.isChecked());
            }
        });


    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void setList(List<ShopBean.DataBean> list){
        this.mList = list;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        RecyclerView recyclerView;
        TextView seller_text;
        CheckBox seller_check;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            recyclerView = itemView.findViewById(R.id.seller_recycle);
            seller_check = itemView.findViewById(R.id.seller_check);
            seller_text = itemView.findViewById(R.id.seller_text);
        }
    }

    private ShopCallBackListener shopCallBackListeber;

    public void setListener(ShopCallBackListener listener) {
        this.shopCallBackListeber = listener;
    }

    public interface ShopCallBackListener{
        void callBack(List<ShopBean.DataBean> list);
    }
}
