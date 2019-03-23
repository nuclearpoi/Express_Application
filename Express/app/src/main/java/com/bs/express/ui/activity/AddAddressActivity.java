package com.bs.express.ui.activity;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bigkoo.pickerview.view.TimePickerView;
import com.bmob.express.R;
import com.bs.express.base.BaseActivity;
import com.bs.express.bean.Address;
import com.bs.express.bean.UserBean;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;


public class AddAddressActivity extends BaseActivity  {
    EditText et_name;
    EditText et_phone;
    TextView et_address;
    private TextView mBtAdd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        initView();
        onSetTitle("add");
    }



    private void initView() {
        et_name = findViewById(R.id.et_name);
        et_phone = findViewById(R.id.et_phone);
        et_address = findViewById(R.id.et_address);
        mBtAdd = findViewById(R.id.bt_add);
        mBtAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = et_name.getText().toString();
                String phone = et_phone.getText().toString();
                String address = et_address.getText().toString();
                if (TextUtils.isEmpty(name)){
                    onToast("name cannot be empty");
                    return;
                }
                if (TextUtils.isEmpty(phone)){
                    onToast("phone cannot be empty");
                    return;
                }
                if (TextUtils.isEmpty(address)){
                    onToast("address cannot be empty");
                    return;
                }
                Address addressObj = new Address();
                addressObj.setAddress(address);
                addressObj.setName(name);
                addressObj.setPhone(phone);
                addressObj.setUser(BmobUser.getCurrentUser(UserBean.class));
                addressObj.save(new SaveListener<String>() {
                    @Override
                    public void done(String s, BmobException e){
                        finish();
                    }
                });
            }
        });
    }





}
