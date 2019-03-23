package com.bs.express.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bmob.express.R;
import com.bs.express.bean.Address;

import java.util.List;


public class NoteListAdapter extends RecyclerView.Adapter<NoteListAdapter.ViewHolder> {
    private Context context;
    private List<Address> mAddresses;
    public NoteListAdapter(Context context, List<Address> addresses) {
        this.context = context;
        this.mAddresses = addresses;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_note, parent,false);
        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Address address = mAddresses.get(position);

        holder.mTvName.setText("name:"+ address.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener!=null){
                    listener.setOnItemClickListener(position);
                }
            }
        });

        holder.mtvphone.setText("tel:"+address.getPhone());
        holder.mtvaddress.setText("address:"+address.getAddress());
    }

    @Override
    public int getItemCount() {
        return mAddresses.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView mTvName;
        TextView mtvphone;
        TextView mtvaddress;
        public ViewHolder(View itemView) {
            super(itemView);
            mTvName = itemView.findViewById(R.id.mTvName);
            mtvphone = itemView.findViewById(R.id.mtvphone);
            mtvaddress = itemView.findViewById(R.id.mtvaddress);
        }
    }
    private ItemClickListener listener;
    public void setOnItemClickListener(ItemClickListener listener ){
        this.listener = listener;
    }
    public interface ItemClickListener{
        void setOnItemClickListener(int position);
        void setOnItemLongClickListener(int position);
    }
}
