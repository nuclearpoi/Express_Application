package com.bs.express.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bmob.express.R;
import com.bs.express.bean.Comment;
import com.bs.express.bean.OrderBean;
import com.bumptech.glide.Glide;
import java.util.List;


public class CommentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    public static final int ITEM_TYPE_HEADER = 0;
    public static final int ITEM_TYPE_CONTENT = 1;
    private Context mContext;
    private List<Comment> mDataList;
    private LayoutInflater mLayoutInflater;
    private int mHeaderCount  =1;
    private OrderBean order ;

    public CommentAdapter(Context mContext, OrderBean order, List<Comment> mDataList){
        this.mContext=mContext;
        this.mDataList=mDataList;
        this.order=order;
        mLayoutInflater= LayoutInflater.from(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType ==ITEM_TYPE_HEADER) {
            return new TopViewHolder(mLayoutInflater.inflate(R.layout.top, parent, false));
        } else if (viewType == ITEM_TYPE_CONTENT) {
            return  new ViewHolder(mLayoutInflater.inflate(R.layout.comment_item, parent, false));
        }
        return null;
    }

    //将数据绑定到控件
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof TopViewHolder) {
            if (null==order) {
                return;
            }
            final TopViewHolder vh= (TopViewHolder) holder;
            vh.tv_name.setText(order.getUserbean().getUsername());
            if (order.getUserbean().getIcon()==null){
                vh.iv_pic.setImageResource(R.drawable.icon_boy);
            }else{
                Glide.with(mContext).load(order.getUserbean().getIcon()).error(R.drawable.icon_boy).into(vh.iv_pic);
            }
            vh.time.setText(order.getCreatedAt());
            vh.tv_time_money.setText("fee:"+order.getMoney()+"\ndeadline:"+order.getTime());

            vh.tv_title.setText(order.getTitle());
            vh.tv_address.setText("address:"+order.getAddress());


            vh.iv_call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (TextUtils.isEmpty(order.getPhone())){
                        return;
                    }
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + order.getPhone()));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(intent);
                }
            });

        } else if (holder instanceof ViewHolder) {
            final Comment commentItem=mDataList.get(position-1);
            if (null==commentItem) {
                return;
            }
            ViewHolder vh= (ViewHolder) holder;
            vh.name.setText(commentItem.user.getUsername());
            vh.content.setText(commentItem.content);
            if (commentItem.user.getIcon()==null){
                vh.avatarView.setImageResource(R.drawable.icon_boy);
            }else{
                Glide.with(mContext).load(commentItem.user.getIcon()).error(R.drawable.icon_boy).into(vh.avatarView);
            }

            vh.timeView.setText(commentItem.getCreatedAt());
        }



    }
    @Override
    public int getItemCount() {
        return mHeaderCount + mDataList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView avatarView ;
        TextView name;
        TextView content;
        TextView timeView;
        public ViewHolder(View itemView) {
            super(itemView);
            avatarView = itemView.findViewById(R.id.avatarView);
            name = itemView.findViewById(R.id.name);
            content= itemView.findViewById(R.id.content);
            timeView=  itemView.findViewById(R.id.timeView);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mHeaderCount != 0 && position < mHeaderCount) {
            //头部View
            return ITEM_TYPE_HEADER;
        }  else {
            //内容View
            return ITEM_TYPE_CONTENT;
        }
    }

    public static class TopViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_call;
        ImageView iv_pic;
         TextView tv_name ;
         TextView tv_time_money ;
         TextView tv_address ;
         TextView time ;
         TextView tv_title ;
        public TopViewHolder(View itemView) {
            super(itemView);
            iv_pic=  itemView.findViewById(R.id.iv_pic);
            iv_call=  itemView.findViewById(R.id.iv_call);
            tv_name=  itemView.findViewById(R.id.tv_name);
            tv_title=  itemView.findViewById(R.id.tv_title);
            tv_address=  itemView.findViewById(R.id.tv_address);
            tv_time_money=  itemView.findViewById(R.id.tv_time_money);
            time=  itemView.findViewById(R.id.tv_time);
        }
    }
}