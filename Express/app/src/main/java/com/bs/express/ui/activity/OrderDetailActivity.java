package com.bs.express.ui.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.bmob.express.R;
import com.bs.express.base.BaseActivity;
import com.bs.express.bean.Comment;
import com.bs.express.bean.OrderBean;
import com.bs.express.bean.UserBean;
import com.bs.express.ui.adapter.CommentAdapter;


import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;


public class OrderDetailActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {
    private SwipeRefreshLayout swiperefreshlayout;
    private RecyclerView recyclerview;
    private List<Comment> list = new ArrayList<>();
    private CommentAdapter adapter;
    private EditText etComment;
    private String objectId;
    private OrderBean order;
    private TextView tvRight;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trend);
        onSetTitle("Order");
        init();
        getDetail();
    }


    private void getDetail() {
        BmobQuery<OrderBean> query = new BmobQuery<OrderBean>();
        query.include("userbean");
        query.addWhereEqualTo("objectId",objectId);
        query.findObjects(new FindListener<OrderBean>() {

            @Override
            public void done(List<OrderBean> diaries, BmobException e) {
                if (e == null) {
                    list.clear();
                    for (OrderBean tr : diaries) {
                        order = tr;
                        adapter = new CommentAdapter(OrderDetailActivity.this,order,list);
                        recyclerview.setAdapter(adapter);
                        if (!order.getUserbean().getObjectId().equals(BmobUser.getCurrentUser().getObjectId())){
                            tvRight.setVisibility(View.VISIBLE);
                        }

                        getCommentList();
                    }
                    adapter.notifyDataSetChanged();
                } else {
                    swiperefreshlayout.setRefreshing(false);
                }
            }

        });

    }
    private void init() {
        tvRight = findViewById(R.id.tv_right);
        tvRight.setText("accept");
        objectId =  getIntent().getStringExtra("data");
        swiperefreshlayout = findViewById(R.id.swiperefreshlayout);
        recyclerview = findViewById(R.id.recyclerview);
        etComment = findViewById(R.id.et_comment);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        recyclerview.setItemAnimator(new DefaultItemAnimator());
        recyclerview.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        swiperefreshlayout.setOnRefreshListener(this);
        findViewById(R.id.bt_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                comment();
            }
        });
        tvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (order.getStatus().equals("1")) {
                    showAccept();
                }else  if (order.getStatus().equals("3")) {
                    onToast("This order is closed");
                }else  if (order.getStatus().equals("2")) {
                    onToast("This order has been accepted");
                }
            }
        });
    }

    private void showAccept() {
        new AlertDialog.Builder(this)
                .setTitle("TIPS")
                .setMessage("Do you accept this order?")
                .setPositiveButton("confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        OrderBean orderBean =  new OrderBean();
                        orderBean.setAcceptUser(BmobUser.getCurrentUser(UserBean.class));
                        orderBean.setStatus("2");
                        orderBean.update(order.getObjectId(), new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e==null){
                                    onToast("success");
                                    order.setStatus("2");
                                }
                            }
                        });
                    }
                }).setNegativeButton("cancel",null)
                .create().show();
    }

    @Override
    public void onRefresh() {
        getCommentList();
    }

    private void getCommentList() {
        BmobQuery<Comment> query = new BmobQuery<Comment>();
        query.order("-createdAt");
        query.include("user");
        query.addWhereEqualTo("orderId",objectId);
        query.findObjects(new FindListener<Comment>() {

            @Override
            public void done(List<Comment> diaries, BmobException e) {
                if (e == null) {
                    list.clear();
                    for (Comment tr : diaries) {
                        list.add(tr);
                    }
                    swiperefreshlayout.setRefreshing(false);
                    adapter.notifyDataSetChanged();
                } else {
                    swiperefreshlayout.setRefreshing(false);
                }
            }

        });

    }
    public void hideSoftInputView() {
        InputMethodManager manager = ((InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE));
        if (getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (getCurrentFocus() != null)
                manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    private void comment(){
        String text = etComment.getText().toString();
        Comment comment = new Comment();
        comment.content=text;
        comment.orderId = objectId;
        comment.user = BmobUser.getCurrentUser(UserBean.class);
        comment.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e==null){
                    hideSoftInputView();
                    getCommentList();
                    etComment.setText("");
                    etComment.clearFocus();
                    Toast.makeText(OrderDetailActivity.this,"success", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(OrderDetailActivity.this,"net error",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
