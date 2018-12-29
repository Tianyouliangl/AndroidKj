package com.lovelife.time.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lovelife.time.R;
import com.lovelife.time.bean.ThisType;

import java.util.List;

public class HomeMeAdapter extends RecyclerView.Adapter<HomeMeAdapter.HomeMeHolder>{

    private Context mContext;
    private List<ThisType> mList;
    private onItemClickInterface mOnClickItem;

    public HomeMeAdapter(Context mContext, List<ThisType> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }
    public interface onItemClickInterface{
        void onClickItem(String title);
    }

    @NonNull
    @Override
    public HomeMeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_adapter_home_me_item,null,false);
         return new HomeMeHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeMeHolder holder, final int position) {
        holder.iv_home_me.setImageResource(mList.get(position).getImage());
        holder.tv_title.setText(mList.get(position).getTitle());
        holder.tv_size.setText(mList.get(position).getSize() > 0 ? mList.get(position).getSize()+"": "");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnClickItem != null){
                    mOnClickItem.onClickItem(mList.get(position).getTitle());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class HomeMeHolder extends RecyclerView.ViewHolder{

        public ImageView iv_home_me;
        public TextView tv_title;
        public TextView tv_size;

        public HomeMeHolder(View itemView) {
            super(itemView);
            iv_home_me = itemView.findViewById(R.id.iv_home_me);
            tv_title = itemView.findViewById(R.id.tv_home_me_title);
            tv_size = itemView.findViewById(R.id.tv_home_me_size);
        }
    }
    public void setOnClickItemListener(onItemClickInterface clickInterface){
        this.mOnClickItem = clickInterface;
    }
}
