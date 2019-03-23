package com.bs.express.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.bmob.express.R;
import com.bs.express.ui.activity.NewOrderActivity;
import com.bs.express.ui.adapter.MyPageAdapter;

import java.util.ArrayList;


public class OrderFragment extends Fragment {


    private ArrayList<Fragment> list;
    private TabLayout tablayout;
    private ViewPager viewpager;
    private MyPageAdapter adapter;
    private ImageView ivAdd;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_order, container, false);
        init(view);
        return view;
    }
    private void init(View view) {
        ivAdd = view.findViewById(R.id.iv_add);
        tablayout = view.findViewById(R.id.tablayout);
        viewpager = view.findViewById(R.id.viewpager);
        list = new ArrayList<>();
        list.add(OrderTabFragment.newInstance("My Order"));
        list.add(OrderTabFragment.newInstance("Acceptable Order"));
        adapter = new MyPageAdapter(getChildFragmentManager(), list);
        viewpager.setAdapter(adapter);
        tablayout.setupWithViewPager(viewpager);
        ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), NewOrderActivity.class));
            }
        });
    }


}
