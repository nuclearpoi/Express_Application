package com.bs.express.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bmob.express.R;
import com.bs.express.base.BaseActivity;
import com.bs.express.utils.StatusBarUtil;

//import butterknife.BindView;
//import butterknife.ButterKnife;
//import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;


public class ChangeActivity extends BaseActivity implements View.OnClickListener {
//    @BindView(R.id.at_title)
    TextView atTitle;
//    @BindView(R.id.at_toolbar)
    Toolbar atToolbar;
//    @BindView(R.id.et_write_pwd)
    EditText etWritePwd;
//    @BindView(R.id.btn_login)
    Button btnLogin;

    private ChangeActivity context;
    private String type;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change);
//        ButterKnife.bind(this);
        context = this;
        StatusBarUtil.setColor(ChangeActivity.this, getResources().getColor(R.color.colorPrimary), 0);
        type = getIntent().getStringExtra("type");
        init();

    }

    private void init() {
        atTitle = findViewById(R.id.at_title);
        atToolbar = findViewById(R.id.at_toolbar);
        etWritePwd = findViewById(R.id.et_write_pwd);
        btnLogin = findViewById(R.id.btn_login);

        btnLogin.setOnClickListener(this);


        switch (type){
            case "phone":
                onSetTitle("phone");
                etWritePwd.setHint("");
                break;
            case "email":
                onSetTitle("email");
                etWritePwd.setHint("");
                break;


        }
    }


//    @OnClick(R.id.btn_login)
    public void onClick() {

    }

    @Override
    public void onClick(View v) {
        String pwd = etWritePwd.getText().toString().trim();
        BmobUser user = BmobUser.getCurrentUser();
        if (type.equals("phone")){
            if (TextUtils.isEmpty(pwd)) {
                Toast.makeText(context, "cannot be empty", Toast.LENGTH_SHORT).show();
                return;
            }
            BmobUser newUser = new BmobUser();
            newUser.setMobilePhoneNumber(pwd);
            BmobUser bmobUser = BmobUser.getCurrentUser();
            newUser.update(bmobUser.getObjectId(),new UpdateListener() {
                @Override
                public void done(BmobException e) {
                    if(e==null){
                        onToast("success");
                        finish();
                    }else{
                        onToast("fail:" + e.getMessage());
                    }
                }
            });
        }
        if (type.equals("email")){
            if (TextUtils.isEmpty(pwd)) {
                Toast.makeText(context, "email cannot be empty", Toast.LENGTH_SHORT).show();
                return;
            }
            BmobUser newUser = new BmobUser();
            newUser.setEmail(pwd);
            BmobUser bmobUser = BmobUser.getCurrentUser();
            newUser.update(bmobUser.getObjectId(),new UpdateListener() {
                @Override
                public void done(BmobException e) {
                    if(e==null){
                        onToast("success");
                        finish();
                    }else{
                        onToast("fail:" + e.getMessage());
                    }
                }
            });

        }
    }
}
