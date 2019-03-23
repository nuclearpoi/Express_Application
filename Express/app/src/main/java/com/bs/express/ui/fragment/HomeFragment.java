package com.bs.express.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bmob.express.R;
import com.bs.express.base.Constant;
import com.bs.express.bean.Company;
import com.bs.express.bean.ExpressInfo;
import com.bs.express.net.ListExpressCallback;
import com.bs.express.ui.activity.HomeActivity;
import com.bs.express.ui.activity.SearchActivity;
import com.zhy.http.okhttp.OkHttpUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;


public class HomeFragment extends Fragment implements View.OnClickListener {

    List<Company> options1Items;
    private OptionsPickerView<Company> pvOptions;
    private LinearLayout llCompany;
    private TextView tvName;
    private EditText etNum;
    private String companyCode;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_home, container, false);
        initView(view);
        init();
        return view;
    }

    private void initView(View view) {
        llCompany = view.findViewById(R.id.ll_company);
        tvName = view.findViewById(R.id.tv_name);
        etNum = view.findViewById(R.id.et_num);
        Button btSearch = view.findViewById(R.id.bt_search);
        llCompany.setOnClickListener(this);
        btSearch.setOnClickListener(this);
    }
    private void init() {
        options1Items = new ArrayList<>();
        options1Items.add(new Company("顺丰","SFEXPRESS"));
        options1Items.add(new Company("申通","STO"));
        options1Items.add(new Company("圆通","YTO"));
        options1Items.add(new Company("中通","ZTO"));
        options1Items.add(new Company("韵达","YUNDA"));
        options1Items.add(new Company("百世快递","HTKY"));
        options1Items.add(new Company("天天","TTKDEX"));
        pvOptions = new OptionsPickerBuilder(getActivity(), new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                String tx = options1Items.get(options1).getName();
                tvName.setText(tx);
                companyCode = options1Items.get(options1).getCode();
            }
        }).setCancelText("cancel").setSubmitText("confirm").build();
        pvOptions.setPicker(options1Items, null, null);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_search:
                String no = etNum.getText().toString();
                if(TextUtils.isEmpty(no)){
                    Toast.makeText(getActivity(),"请输入快递单号",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(companyCode)){
                    Toast.makeText(getActivity(),"请选择快递公司",Toast.LENGTH_SHORT).show();
                    return;
                }
                ((HomeActivity)getActivity()). showProgressDialog(getActivity(),"loading...");
                OkHttpUtils.get()
                        .url(Constant.BASE_URL + "?no=" + no + "&type=" + companyCode)
                        .addHeader("Authorization","APPCODE 93190485d495463ebae70fd84c4ac8f8")
                        .build()
                        .execute(new ListExpressCallback()
                        {
                            @Override
                            public void onError(Call call, Exception e, int id) {
                                ((HomeActivity)getActivity()).hidProgressDialog();
                                Toast.makeText(getActivity(),e.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onResponse(ExpressInfo response, int id) {
                                ((HomeActivity)getActivity()).hidProgressDialog();
                                Log.e("111",response.toString());
                                if (response.getCode().equals("OK")){
                                    Intent intent = new Intent(getActivity(), SearchActivity.class);
                                    intent.putExtra("list", (Serializable) response.getList());
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                }else{
                                   Toast.makeText(getActivity(),response.getMsg(),Toast.LENGTH_SHORT).show();
                                }
                            }


                        });


                break;
            case R.id.ll_company:
                pvOptions.show();
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        OkHttpUtils.getInstance().cancelTag(this);
    }
}
