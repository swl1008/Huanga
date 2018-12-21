package com.example.huanga.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.huanga.R;
import com.example.huanga.bean.ShopBean;
import com.example.huanga.view.CountView;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder> {
    private Context mContext;
    private List<ShopBean.DataBean.ListBean> mList = new ArrayList<>();

    public ProductAdapter(Context context, List<ShopBean.DataBean.ListBean> list) {
        this.mContext = context;
        this.mList = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(mContext, R.layout.shop_product_adapter,null);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
        String url = mList.get(i).getImages().split("\\|")[0].replace("https","http");
        Glide.with(mContext).load(url).into(myViewHolder.image);

        myViewHolder.title.setText(mList.get(i).getTitle());
        myViewHolder.price.setText(mList.get(i).getPrice()+"");
        //拿到bean里面的复选框状态
        myViewHolder.check.setChecked(mList.get(i).isCheck());

        //复选框状态改变监听
        myViewHolder.check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //改变bean里面的复选框状态值
                mList.get(i).setCheck(isChecked);
                if (shopCallBackListener != null){
                    shopCallBackListener.callBack();
                }
            }
        });

        myViewHolder.product_view.setData(this,mList,i);

        myViewHolder.product_view.setOnCallBackListener(new CountView.CallBackListener() {
            @Override
            public void callBack() {
                if (shopCallBackListener != null){
                    shopCallBackListener.callBack();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title,price;
        ImageView image;
        CheckBox check;
        CountView product_view;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.product_title);
            price = itemView.findViewById(R.id.product_price);
            image = itemView.findViewById(R.id.product_image);
            check = itemView.findViewById(R.id.product_check);
            product_view = itemView.findViewById(R.id.product_view);
        }
    }

    public void selectOrRemoveAll(boolean isSelectAll){
        for (ShopBean.DataBean.ListBean bean : mList){
            bean.setCheck(isSelectAll);
        }
        notifyDataSetChanged();
    }

    private ShopCallBackListener shopCallBackListener;

    public void setListener(ShopCallBackListener listener) {
        this.shopCallBackListener = listener;
    }

    public interface ShopCallBackListener{
        void callBack();
    }
}
