package com.bs.express.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import com.bmob.express.R;
import com.bs.express.base.BaseActivity;
import com.bs.express.bean.Address;
import com.bs.express.ui.adapter.NoteListAdapter;
import java.util.ArrayList;
import java.util.List;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;


public class AddressListActivity extends AppCompatActivity {


    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwRefresh;
    private List<Address> list = new ArrayList<>();
    private NoteListAdapter mAdapter;
    private String type;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addresslist);
        initView();
       type =  getIntent().getStringExtra("type");
        init();
    }



    private void initView() {
        findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mRecyclerView = findViewById(R.id.recyclerView);
        mSwRefresh = findViewById(R.id.sw_refresh);
        findViewById(R.id.tv_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddressListActivity.this,AddAddressActivity.class));
            }
        });

        mSwRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                init();

            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new NoteListAdapter(this, list);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new NoteListAdapter.ItemClickListener() {
            @Override
            public void setOnItemClickListener(int position) {
                if (!TextUtils.isEmpty(type)){
                    Intent intent =   new Intent();
                    intent.putExtra("data",list.get(position));
                    setResult(RESULT_OK,intent);
                    finish();
                }
            }

            @Override
            public void setOnItemLongClickListener(int position) {
                showDel(position);
            }
        });
    }
    private void showDel(final int position) {
        new AlertDialog.Builder(this)
                .setTitle("TIPS")
                .setMessage("Do you confirm deletion?")
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        del(position);
                    }
                }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }


        }).create().show();
    }

    private void del(int position) {
        list.remove(position);
        mAdapter.notifyDataSetChanged();
    }



    private void init() {
        BmobQuery<Address> query = new BmobQuery<>();
        query.order("-createdAt");
        query.addWhereEqualTo("user", BmobUser.getCurrentUser());
        query.findObjects(new FindListener<Address>() {

            @Override
            public void done(List<Address> addresss, BmobException e) {
                if (e == null) {
                    list.clear();
                    for (Address address : addresss) {
                        list.add(address);
                    }
                    mAdapter.notifyDataSetChanged();
                    mSwRefresh.setRefreshing(false);
                } else {
                    mSwRefresh.setRefreshing(false);
                }
            }

        });


    }

}
