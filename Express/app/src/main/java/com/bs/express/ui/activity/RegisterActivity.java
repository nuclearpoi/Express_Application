package com.bs.express.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import com.bmob.express.R;
import com.bs.express.base.BaseActivity;
import com.bs.express.bean.UserBean;
import com.bs.express.utils.StatusBarUtil;

//import butterknife.BindView;
//import butterknife.ButterKnife;
//import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;


public class RegisterActivity extends BaseActivity implements View.OnClickListener {

    EditText etName;
    EditText etPwd;
    Button btLogin;
    private RegisterActivity context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        context = this;
        onSetTitle("Sign up");

        StatusBarUtil.setColor(context, getResources().getColor(R.color.colorPrimary), 0);
        initView();


    }

    private void initView() {
        etName = findViewById(R.id.et_name);
        etPwd = findViewById(R.id.et_pwd);
        btLogin = findViewById(R.id.bt_login);
        btLogin.setOnClickListener(this);
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_login:
                String nickname = etName.getText().toString();
                String pwd = etPwd.getText().toString();
                if (TextUtils.isEmpty(nickname)) {
                    onToast("User name cannot be empty");
                    return;
                }

                if (TextUtils.isEmpty(pwd)) {
                    onToast("password cannot be empty");
                    return;
                }

                showProgressDialog(context, "loading");

                UserBean user = new UserBean();
                user.setUsername(nickname);                  //设置用户名，如果没有传用户名，则默认为手机号码
                user.setPassword(pwd);
                user.signUp(new SaveListener<UserBean>() {
                    @Override
                    public void done(UserBean userBean, BmobException e) {
                        hidProgressDialog();
                        if(e==null){
                            onToast("success");
                            finish();
                        }else{
                            onToast("fail:" + e.getMessage());
                        }
                    }
                });
                break;
        }



    }
}
