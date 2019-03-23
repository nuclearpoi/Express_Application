package com.bs.express.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bmob.express.R;
import com.bs.express.bean.OrderBean;

import java.util.List;


public class OrderListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<OrderBean> mDataList;//动态数组承载数据使用
    private LayoutInflater mLayoutInflater;

    public OrderListAdapter(Context mContext, List<OrderBean> mDataList){
        this.mDataList=mDataList;
        mLayoutInflater= LayoutInflater.from(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v=mLayoutInflater.from(parent.getContext()).inflate(R.layout.item_order,parent,false);
        return new OrderListAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        final OrderBean entity=mDataList.get(position);
        if (null==entity)
            return;
        OrderListAdapter.ViewHolder viewHolder= (OrderListAdapter.ViewHolder) holder;
        viewHolder.tv_title.setText("address:"+entity.getAddress());
        viewHolder.tv_detail.setText("fee:"+entity.getMoney());
        if (entity.getStatus().equals("1")) {
            viewHolder.tv_status.setText("wait");
        }else  if (entity.getStatus().equals("3")) {
            viewHolder.tv_status.setText("over");
        }else  if (entity.getStatus().equals("2")) {
            viewHolder.tv_status.setText("accepted");
        }
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener!=null){
                    listener.setOnItemClickListener(position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    //找到控件，将其初始化
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_title;
        TextView tv_detail;
        TextView tv_status;
        public ViewHolder(View itemView) {
            super(itemView);
            tv_title=  itemView.findViewById(R.id.tv_title);
            tv_detail=  itemView.findViewById(R.id.tv_detail);
            tv_status=  itemView.findViewById(R.id.tv_status);
        }
    }
    private ItemClickListener listener;
    public void setOnItemClickListener(ItemClickListener listener ){
        this.listener = listener;
    }
    public interface ItemClickListener{
        void setOnItemClickListener(int position);
    }
}