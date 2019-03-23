package com.bs.express.ui.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bmob.express.R;
import com.bs.express.bean.UserBean;
import com.bs.express.ui.activity.AddressListActivity;
import com.bs.express.ui.activity.LoginActivity;
import com.bs.express.ui.activity.UserInfoActivity;
import com.bs.express.ui.activity.WorkListActivity;

import cn.bmob.v3.BmobUser;


public class SettingFragment extends Fragment implements View.OnClickListener {
    private FragmentActivity context;
    private LinearLayout ll_logout;
    private LinearLayout ll_person;
    private LinearLayout ll_address;
    private LinearLayout ll_server;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_set, container,
                false);
        context = getActivity();
        initView(view);
        return view;
    }

    private void initView(View view) {
        TextView tv_id = view.findViewById(R.id.tv_id);
        tv_id.setText("ID:"+BmobUser.getCurrentUser(UserBean.class).getUsername());
        ll_logout = view.findViewById(R.id.ll_logout);
        ll_person = view.findViewById(R.id.ll_person);
        ll_server = view.findViewById(R.id.ll_server);
        ll_address = view.findViewById(R.id.ll_address);
        LinearLayout ll_mywork =  view.findViewById(R.id.ll_myword);
        ll_person.setOnClickListener(this);
        ll_logout.setOnClickListener(this);
        ll_address.setOnClickListener(this);
        ll_server.setOnClickListener(this);
        ll_mywork.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ll_logout:
                new AlertDialog.Builder(getActivity())
                        .setTitle("TIPS")
                        .setMessage("Are you sure you want to log out?")
                        .setPositiveButton("confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                                BmobUser.logOut();
                                startActivity(new Intent(getActivity(), LoginActivity.class));
                                getActivity().finish();

                            }
                        }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();

                break;
            case R.id.ll_server:
                new AlertDialog.Builder(getActivity())
                        .setTitle("TIPS")
                        .setMessage("customer service telephone numbers 00000000000")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        }).create().show();

                break;
            case R.id.ll_person:
                startActivity(new Intent(getActivity(), UserInfoActivity.class));
                break;
            case R.id.ll_address:
                startActivity(new Intent(getActivity(), AddressListActivity.class));
                break;
            case R.id.ll_myword:
                startActivity(new Intent(getActivity(), WorkListActivity.class));
                break;
        }
    }
}
