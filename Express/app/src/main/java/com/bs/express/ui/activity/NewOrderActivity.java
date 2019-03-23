package com.bs.express.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.bmob.express.R;
import com.bs.express.base.BaseActivity;
import com.bs.express.bean.Address;
import com.bs.express.bean.OrderBean;
import com.bs.express.bean.UserBean;

import java.text.SimpleDateFormat;
import java.util.Date;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;


public class NewOrderActivity extends BaseActivity implements View.OnClickListener {
    private EditText et_title;
    private TextView et_name;
    private TextView et_phone;
    private TextView et_address;
    private EditText et_money;
    private TextView tv_time;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_release);
        onSetTitle("New Order");
        initView();
    }




    private void initView() {
        et_title = findViewById(R.id.et_title);
        et_name = findViewById(R.id.et_name);
        et_phone = findViewById(R.id.et_phone);
        et_address = findViewById(R.id.et_address);
        tv_time = findViewById(R.id.tv_time);
        et_money = findViewById(R.id.et_money);
        et_phone.setOnClickListener(this);
        et_address.setOnClickListener(this);
        et_name.setOnClickListener(this);
        Button send = findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = et_title.getText().toString();
                String name = et_name.getText().toString();
                String phone = et_phone.getText().toString();
                String address = et_address.getText().toString();
                String money = et_money.getText().toString();
                String time = tv_time.getText().toString();
                if (TextUtils.isEmpty(title)){
                    onToast("Express number can not be empty");
                    return;
                }
                if (TextUtils.isEmpty(name)){
                    onToast("name can not be empty");
                    return;
                }
                if (TextUtils.isEmpty(phone)){
                    onToast("phone can not be empty");
                    return;
                }
                if (TextUtils.isEmpty(address)){
                    onToast("address can not be empty");
                    return;
                }
                if (TextUtils.isEmpty(money)){
                    onToast("fee can not be empty");
                    return;
                }
                if (TextUtils.isEmpty(time)){
                    onToast("deadline can not be empty");
                    return;
                }
                showProgressDialog(NewOrderActivity.this,"loading");
                OrderBean orderBean =  new OrderBean();
                orderBean.setTitle(title);
                orderBean.setName(name);
                orderBean.setAddress(address);
                orderBean.setPhone(phone);
                orderBean.setTime(time);
                orderBean.setStatus("1");
                orderBean.setMoney(money);
                orderBean.setUserbean(BmobUser.getCurrentUser(UserBean.class));
                orderBean.save(new SaveListener<String>() {
                    @Override
                    public void done(String s, BmobException e) {
                        hidProgressDialog();
                        if (e==null){
                            onToast("success");
                            finish();
                        }else{
                            onToast("fail");
                        }
                    }
                });

            }
        });

        tv_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TimePickerView pvTime = new TimePickerBuilder(NewOrderActivity.this, new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        tv_time.setText(getTime(date));
                    }
                }) .setType(new boolean[]{true, true, true, true, true, false})
                        .setLabel("Y","M","D","H","M","")
                        .build();
                pvTime.show();
            }

        });
    }
    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return format.format(date);
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.et_address:
            case R.id.et_name:
            case R.id.et_phone:
                Intent intent = new Intent(NewOrderActivity.this,AddressListActivity.class);
                intent.putExtra("type","choose");
                startActivityForResult(intent,100);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==100&&resultCode==RESULT_OK){
            Address address = (Address) data.getSerializableExtra("data");
            et_name.setText(address.getName());
            et_phone.setText(address.getPhone());
            et_address.setText(address.getAddress());
        }
    }
}
