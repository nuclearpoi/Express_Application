package com.bs.express.ui.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.bmob.express.R;
import com.bs.express.base.BaseActivity;
import com.bs.express.bean.OrderBean;
import com.bs.express.bean.UserBean;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;


public class WordDetailActivity extends BaseActivity  {
    private String objectId;
    private OrderBean order;
    private TextView et_title;
    private TextView et_name;
    private TextView et_phone;
    private TextView et_address;
    private TextView et_money;
    private TextView tv_time;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worddetail);
        initView();
        onSetTitle("Order");
        objectId =  getIntent().getStringExtra("data");
        getDetail();

    }

    private void initView() {
        et_title = findViewById(R.id.et_title);
        et_name = findViewById(R.id.et_name);
        et_phone = findViewById(R.id.et_phone);
        et_address = findViewById(R.id.et_address);
        tv_time = findViewById(R.id.tv_time);
        et_money = findViewById(R.id.et_money);
        Button send = findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (order!=null&&!order.getStatus().equals("3")){
                    over();
                }

            }
        });
    }


    private void getDetail() {
        BmobQuery<OrderBean> query = new BmobQuery<OrderBean>();
        query.include("userbean");
        query.addWhereEqualTo("objectId",objectId);
        query.findObjects(new FindListener<OrderBean>() {

            @Override
            public void done(List<OrderBean> diaries, BmobException e) {
                if (e == null) {
                    for (OrderBean tr : diaries) {
                        order = tr;
                        et_title.setText(order.getTitle());
                        et_name.setText(order.getName());
                        et_phone.setText(order.getPhone());
                        et_address.setText(order.getAddress());
                        tv_time.setText(order.getTime());
                        et_money.setText(order.getMoney());
                    }
                } else {
                }
            }

        });

    }


    private void over() {
        new AlertDialog.Builder(this)
                .setTitle("TIPS")
                .setMessage("The order has been completed?")
                .setPositiveButton("confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        OrderBean orderBean =  new OrderBean();
                        orderBean.setAcceptUser(BmobUser.getCurrentUser(UserBean.class));
                        orderBean.setStatus("3");
                        orderBean.update(order.getObjectId(), new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e==null){
                                    onToast("success");
                                    finish();
                                }
                            }
                        });
                    }
                }).setNegativeButton("cancel",null)
                .create().show();
    }







}
