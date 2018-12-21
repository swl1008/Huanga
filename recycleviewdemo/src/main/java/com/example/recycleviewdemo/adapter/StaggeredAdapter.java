package com.example.recycleviewdemo.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.recycleviewdemo.R;
import com.example.recycleviewdemo.bean.UserBean;

import java.util.ArrayList;
import java.util.List;

public class StaggeredAdapter extends RecyclerView.Adapter<StaggeredAdapter.ViewHolder>{
    private List<UserBean.DataBean> mList;
    private Context mContext;

    public StaggeredAdapter(Context mContext) {
        this.mContext = mContext;
        mList = new ArrayList<>();
    }
    public void setmList(List<UserBean.DataBean> lists){
        mList.clear();
        if(lists!=null){
            mList.addAll(lists);
        }
        notifyDataSetChanged();
    }
    //删除
    public void delData(int i){
        mList.remove(i);
        notifyItemRemoved(i);
        notifyItemRangeRemoved(i,mList.size());
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.staggered_item,viewGroup,false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        UserBean.DataBean dataBean = mList.get(i);
        Glide.with(mContext).load(dataBean.getThumbnail_pic_s()).into(viewHolder.image);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.staggered_image);
        }
    }


}
