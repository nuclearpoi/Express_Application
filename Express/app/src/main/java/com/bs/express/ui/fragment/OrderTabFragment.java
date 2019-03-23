package com.bs.express.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.bmob.express.R;
import com.bs.express.bean.OrderBean;
import com.bs.express.ui.activity.OrderDetailActivity;
import com.bs.express.ui.adapter.OrderListAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;


public class OrderTabFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private String type;
    private RecyclerView recyclerview;
    private SwipeRefreshLayout swipeRefreshLayout;
    private List<OrderBean> list = new ArrayList<>();
    private OrderListAdapter adapter;

    public static OrderTabFragment newInstance(String type) {
        OrderTabFragment fragment = new OrderTabFragment();
        Bundle args = new Bundle();
        args.putString("type",type);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order, null);
        Bundle bundle = getArguments();
        type = bundle.getString("type");
        initView(view);
        getData();
        return view;

    }

    private void initView(View view) {
        swipeRefreshLayout = view.findViewById(R.id.swiperefreshlayout);
        recyclerview = view.findViewById(R.id.recyclerview);
        recyclerview.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        recyclerview.setItemAnimator(new DefaultItemAnimator());
        adapter = new OrderListAdapter(getActivity(),list);
        recyclerview.setAdapter(adapter);
        swipeRefreshLayout.setOnRefreshListener(this);
        adapter.setOnItemClickListener(new OrderListAdapter.ItemClickListener() {
            @Override
            public void setOnItemClickListener(int position) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), OrderDetailActivity.class);
                intent.putExtra("data",list.get(position).getObjectId());
                startActivity(intent);
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
        if (type.equals("My Order")) {
            query.addWhereEqualTo("userbean", BmobUser.getCurrentUser());
        }
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
