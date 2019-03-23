package com.bs.express.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.bmob.express.R;
import com.bs.express.base.BaseActivity;
import com.bs.express.bean.OrderBean;
import com.bs.express.ui.adapter.OrderListAdapter;
import java.util.ArrayList;
import java.util.List;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;


public class WorkListActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {


    private RecyclerView recyclerview;
    private SwipeRefreshLayout swipeRefreshLayout;
    private List<OrderBean> list = new ArrayList<>();
    private OrderListAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worklist);
        initView();
        onSetTitle("work");
        getData();
    }



    private void initView() {
        swipeRefreshLayout = findViewById(R.id.swiperefreshlayout);
        recyclerview = findViewById(R.id.recyclerview);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        recyclerview.setItemAnimator(new DefaultItemAnimator());
        adapter = new OrderListAdapter(this,list);
        recyclerview.setAdapter(adapter);
        swipeRefreshLayout.setOnRefreshListener(this);
        adapter.setOnItemClickListener(new OrderListAdapter.ItemClickListener() {
            @Override
            public void setOnItemClickListener(int position) {
                Intent intent = new Intent();
                intent.setClass(WorkListActivity.this, WordDetailActivity.class);
                intent.putExtra("data",list.get(position).getObjectId());
                startActivity(intent);
                finish();
            }

        });
    }

    @Override
    public void onRefresh() {
        getData();
    }
    private void getData() {
        BmobQuery<OrderBean> query = new BmobQuery<>();
        query.order("-createdAt");
        query.addWhereEqualTo("acceptUser", BmobUser.getCurrentUser());
        query.include("userbean");
        query.findObjects(new FindListener<OrderBean>() {

            @Override
            public void done(List<OrderBean> diaries, BmobException e) {
                if (e == null) {
                    list.clear();
                    for (OrderBean tr : diaries) {
                        list.add(tr);
                    }
                    swipeRefreshLayout.setRefreshing(false);
                    adapter.notifyDataSetChanged();
                } else {
                    swipeRefreshLayout.setRefreshing(false);
                }
            }

        });
    }
}
