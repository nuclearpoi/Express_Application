package com.bs.express.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.bmob.express.R;
import com.bs.express.base.BaseActivity;
import com.bs.express.bean.ExpressInfo;
import com.bs.express.ui.adapter.ExpressListAdapter;

import java.util.List;


public class SearchActivity extends BaseActivity {


    RecyclerView recyclerview;
    private List<ExpressInfo.ListEntity> list ;
    private ExpressListAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train_list);
        onSetTitle("Info");
        list = (List<ExpressInfo.ListEntity>) getIntent().getSerializableExtra("list");
        initView();
    }


    private void initView() {
        recyclerview = findViewById(R.id.recyclerview);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ExpressListAdapter(this,list);
        recyclerview.setAdapter(adapter);

    }




}
