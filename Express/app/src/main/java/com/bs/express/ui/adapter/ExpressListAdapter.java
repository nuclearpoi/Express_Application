package com.bs.express.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bmob.express.R;
import com.bs.express.bean.ExpressInfo;

import java.util.List;


public class ExpressListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<ExpressInfo.ListEntity> mDataList;//动态数组承载数据使用
    private LayoutInflater mLayoutInflater;

    public ExpressListAdapter(Context mContext, List<ExpressInfo.ListEntity> mDataList){
        this.mDataList=mDataList;
        mLayoutInflater= LayoutInflater.from(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v=mLayoutInflater.from(parent.getContext()).inflate(R.layout.item_express,parent,false);
        return new ExpressListAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        final ExpressInfo.ListEntity entity=mDataList.get(position);
        if (null==entity)
            return;
        ExpressListAdapter.ViewHolder viewHolder= (ExpressListAdapter.ViewHolder) holder;
        viewHolder.tv_title.setText(entity.getContent());
        viewHolder.tv_detail.setText(entity.getTime());



    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    //找到控件，将其初始化
    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView img;
        TextView tv_title;
        TextView tv_detail;
        public ViewHolder(View itemView) {
            super(itemView);
            tv_title= (TextView) itemView.findViewById(R.id.tv_title);
            tv_detail= (TextView) itemView.findViewById(R.id.tv_detail);
        }
    }

}