package com.bs.express.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import com.bmob.express.R;
import com.bs.express.base.BaseActivity;

import java.io.File;

import cn.bmob.v3.BmobUser;

public class GuideActivity extends BaseActivity {

    private ImageView iv_guide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_guide);
        iv_guide = (ImageView) findViewById(R.id.iv_guide);
        initFile();
        final ScaleAnimation scaleAnim = new ScaleAnimation(1.0f, 1.2f, 1.0f, 1.2f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        scaleAnim.setFillAfter(true);
        scaleAnim.setDuration(3000);
        scaleAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }
            @Override
            public void onAnimationEnd(Animation animation) {
                if(BmobUser.getCurrentUser()!=null){
                    onOpen(new Intent(GuideActivity.this, HomeActivity.class));
                    finish();
              }else {
                    onOpen(new Intent(GuideActivity.this, LoginActivity.class));
                    finish();
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        iv_guide.startAnimation(scaleAnim);
    }
    private void initFile() {
        File dir = new File("/sdcard/app");

        if (!dir.exists()) {
            dir.mkdirs();
        }

    }
}
