package com.example.huanga.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.huanga.R;
import com.example.huanga.adapter.ProductAdapter;
import com.example.huanga.bean.ShopBean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CountView extends LinearLayout {
    private EditText editText;
    public CountView(Context context) {
        super(context);
        init(context);
    }

    public CountView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CountView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }
    private Context context;
    private ProductAdapter productAdapter;
    private List<ShopBean.DataBean.ListBean> list = new ArrayList<>();
    private int num;
    private int position;

    public void init(final Context context){
        this.context = context;
        View view = View.inflate(context, R.layout.shop_price_layout,null);
        ImageView image_jia = view.findViewById(R.id.image_jia);
        ImageView image_jian = view.findViewById(R.id.image_jian);
        editText = view.findViewById(R.id.edit_price);
        addView(view);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                try {
                    num = Integer.parseInt(s.toString());
                }catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(context,"数量最少为1", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });




        image_jia.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                num++;
                editText.setText(num+"");
                list.get(position).setNum(num);
                listener.callBack();
                productAdapter.notifyItemChanged(position);
            }
        });
        image_jian.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(num>1){
                    num--;
                }else {
                    Toast.makeText(context,"我是有底线的啊", Toast.LENGTH_SHORT).show();
                }

                editText.setText(num+"");
                list.get(position).setNum(num);
                listener.callBack();
                productAdapter.notifyItemChanged(position);
            }
        });

    }



    private CallBackListener listener;
    public void setOnCallBackListener(CallBackListener listener){
        this.listener = listener;
    }

    public void setData(ProductAdapter productAdapter, List<ShopBean.DataBean.ListBean> list, int i) {
         this.list = list;
         this.productAdapter = productAdapter;
         position = i;
         num = list.get(position).getNum();
         editText.setText(num+"");
    }

    public interface CallBackListener{
        void callBack();
    }
}
