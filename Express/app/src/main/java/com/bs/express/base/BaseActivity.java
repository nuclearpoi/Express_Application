package com.bs.express.base;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bs.express.utils.DialogUtils;
import com.bmob.express.R;


public class BaseActivity extends AppCompatActivity {


    public Toolbar toolbar;

    //设置标题
    public void onSetTitle(String title) {
        toolbar = findViewById(R.id.at_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationIcon(R.drawable.back_arr);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onReturn();
            }
        });
        TextView mTitle = toolbar.findViewById(R.id.at_title);
        mTitle.setText(title.toString());
    }

    public void onReturn(){
       finish();
    }


    public void onOpen(Intent intent) {
        this.startActivity(intent);
    }
    public void onFinish() {
        finish();
    }
    public void onToast(int res) {
        onToast(getResources().getString(res));
    }
    public void onToast(String msg) {
        Toast.makeText(this,msg, Toast.LENGTH_SHORT).show();
    }


    public void showProgressDialog(Context context , String content) {
        DialogUtils.showProgressDialog(context,content,false);
    }

    public void hidProgressDialog() {
        DialogUtils.hideProgressDialog();
    }


}