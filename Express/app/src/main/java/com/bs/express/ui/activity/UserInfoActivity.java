package com.bs.express.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.bmob.express.R;
import com.bs.express.base.BaseActivity;
import com.bs.express.bean.UserBean;
import com.bs.express.utils.StatusBarUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import java.io.File;

//import butterknife.BindView;
//import butterknife.ButterKnife;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;



public class UserInfoActivity extends BaseActivity implements View.OnClickListener {
    ImageView imgHead;
    TextView tvName;
    TextView tvPhone;
    RelativeLayout rlPhone;
    TextView tvEmail;
    RelativeLayout rlEmail;

    private UserBean currentUser;
    private int type;
    private Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        currentUser = UserBean.getCurrentUser(UserBean.class);
        initView();

        mContext = this;

        StatusBarUtil.setColor(this, getResources().getColor(R.color.colorPrimary), 0);
        type = getIntent().getIntExtra("type", 0);
        initListener();


    }

    private void initView() {

        imgHead = findViewById(R.id.img_head);
        tvName = findViewById(R.id.tv_name);
        tvPhone = findViewById(R.id.tv_phone);
        rlPhone = findViewById(R.id.rl_phone);
        tvEmail = findViewById(R.id.tv_email);
        rlEmail = findViewById(R.id.rl_email);

        if (currentUser.getIcon() != null) {
            String  imgUrl = currentUser.getIcon();
            Glide.with(mContext)
                    .load(imgUrl)
                    .asBitmap() //这句不能少，否则下面的方法会报错
                    .error(R.drawable.ic_logo2)
                    .centerCrop()
                    .into(new BitmapImageViewTarget(imgHead) {
                        @Override
                        protected void setResource(Bitmap resource) {
                            RoundedBitmapDrawable circularBitmapDrawable =
                                    RoundedBitmapDrawableFactory.create(getResources(), resource);
                            circularBitmapDrawable.setCircular(true);
                            imgHead.setImageDrawable(circularBitmapDrawable);
                        }
                    });
        }

//        Glide.with(mContext).load(imgUrl).error().into(imgHead);

        tvName.setText(currentUser.getUsername());
        if (!TextUtils.isEmpty(currentUser.getMobilePhoneNumber())) {
            tvPhone.setText(currentUser.getMobilePhoneNumber());
        }
        if (!TextUtils.isEmpty(currentUser.getEmail())) {
            tvEmail.setText(currentUser.getEmail());
        }
    }

    private void initListener() {
        imgHead.setOnClickListener(this);
        rlPhone.setOnClickListener(this);
        rlEmail.setOnClickListener(this);
    }
    private static final int Take_Photo = 0;
    @Override
    public void onClick(View view) {
        Intent intent;

        switch (view.getId()) {
            case R.id.img_head:
                intent = new Intent(mContext, SubmitImageActivity.class);
                startActivityForResult(intent, Take_Photo);
                break;
            case R.id.rl_phone:
                intent = new Intent();
                intent.setClass(mContext, ChangeActivity.class);
                intent.putExtra("type", "phone");
                onOpen(intent);
                break;
            case R.id.rl_email:
                intent = new Intent();
                intent.setClass(mContext, ChangeActivity.class);
                intent.putExtra("type", "email");
                onOpen(intent);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        currentUser = UserBean.getCurrentUser(UserBean.class);
        tvName.setText(currentUser.getUsername());
        if (!TextUtils.isEmpty(currentUser.getMobilePhoneNumber())) {
            tvPhone.setText(currentUser.getMobilePhoneNumber());
        }
        if (!TextUtils.isEmpty(currentUser.getEmail())) {
            tvEmail.setText(currentUser.getEmail());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case Take_Photo:
                    if (data != null) {
                        String imageName = data.getStringExtra("imageName");
                        //返回数据了
                        Bitmap bitmap = BitmapFactory.decodeFile("/sdcard/app/"
                                + imageName);
                        imgHead.setImageBitmap(bitmap);
                        updateAvatarInServer(imageName);

                    }
                    break;


            }
            super.onActivityResult(requestCode, resultCode, data);
        }

    }

    private void updateAvatarInServer(String imageName) {
        final BmobFile bmobFile = new BmobFile(new File("/sdcard/app/" + imageName));
        bmobFile.upload(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                UserBean userBean = new UserBean();
                userBean.setIcon(bmobFile.getUrl());
                BmobUser user = BmobUser.getCurrentUser();
                userBean.update(user.getObjectId(), new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            Toast.makeText(mContext, "success", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(mContext, "fail", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }

}
