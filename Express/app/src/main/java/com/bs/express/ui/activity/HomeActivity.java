package com.bs.express.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bmob.express.R;
import com.bs.express.base.BaseActivity;
import com.bs.express.ui.fragment.HomeFragment;
import com.bs.express.ui.fragment.OrderFragment;
import com.bs.express.ui.fragment.SettingFragment;


public class HomeActivity extends BaseActivity implements View.OnClickListener {



    private HomeActivity context;
    private HomeFragment homeFragment;
    private SettingFragment settingFragment;
    private OrderFragment wordFragment;
    private Fragment[] fragments;
    private RelativeLayout[] TAB;
    private ImageView[] IMAGE;
    private int index;
    private int currentTabIndex;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        context = this;
        initView();
    }



    @Override
    protected void onSaveInstanceState(Bundle outState) {
       // super.onSaveInstanceState(outState);
    }

    private void initView() {

        if (null == homeFragment) {
            homeFragment = new HomeFragment();
        }
        if (null == wordFragment) {
            wordFragment = new OrderFragment();
        }
        if (null == settingFragment) {
            settingFragment = new SettingFragment();
        }
        fragments = new Fragment[]{homeFragment,wordFragment,
                settingFragment};
        TAB = new RelativeLayout[3];
        IMAGE = new ImageView[3];
        TAB[0] = findViewById(R.id.rl_home);
        TAB[1] = findViewById(R.id.rl_t);
        TAB[2] =  findViewById(R.id.rl_me);

        IMAGE[0] = findViewById(R.id.iv_home);
        IMAGE[1] = findViewById(R.id.iv_word);
        IMAGE[2] = findViewById(R.id.ib_me);
        IMAGE[0].setSelected(true);
        TAB[0].setOnClickListener(this);
        TAB[1].setOnClickListener(this);
        TAB[2].setOnClickListener(this);
        // 添加显示第一个fragment
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, homeFragment, "home")
                .add(R.id.fragment_container, wordFragment, "order")
                .add(R.id.fragment_container, settingFragment, "set")
                .hide(settingFragment).hide(wordFragment)
                .show(homeFragment).commit();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_home:
                index = 0;
                break;

            case R.id.rl_t:
                index = 1;
                break;
            case R.id.rl_me:
                index = 2;
                break;
        }
        if (currentTabIndex != index) {
            FragmentTransaction trx = getSupportFragmentManager()
                    .beginTransaction();
            trx.hide(fragments[currentTabIndex]);
            if (!fragments[index].isAdded()) {
                trx.add(R.id.fragment_container, fragments[index]);
            }
            trx.show(fragments[index]).commit();
        }
        IMAGE[currentTabIndex].setSelected(false);
        // 把当前tab设为选中状态
        IMAGE[index].setSelected(true);
        currentTabIndex = index;
    }
    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            if((System.currentTimeMillis()-exitTime) > 2000){
                Toast.makeText(getApplicationContext(), "Press again to exit app", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
