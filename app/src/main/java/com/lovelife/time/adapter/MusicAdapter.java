package com.lovelife.time.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lovelife.time.R;
import com.lovelife.time.bean.MusicInfo;
import com.lovelife.time.view.activity.MainActivity;
import com.lovelife.time.view.activity.MusicActivity;
import com.lovelife.time.view.activity.MusicPlayActivity;

import java.util.List;

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.MusicHolder> {

    private List<MusicInfo> mList;
    private Context mContent;
    private onClickInterface mOnItemClickListener;

    public MusicAdapter(List<MusicInfo> mList, Context mContent) {
        this.mList = mList;
        this.mContent = mContent;
    }

    @NonNull
    @Override
    public MusicHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContent).inflate(R.layout.layout_adapter_music_item,null,false);
        return new MusicHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MusicHolder holder, final int position) {
        int playState = mList.get(position).getPlayState();
        holder.tv_music_title.setText(mList.get(position).getTitle());
        holder.tv_music_msg.setText(mList.get(position).getArtist());
        if (playState == 0){
            holder.iv_music_isPlaying.setBackgroundResource(R.mipmap.playing_d);
            holder.tv_music_msg.setTextColor(Color.BLACK);
            holder.tv_music_title.setTextColor(Color.BLACK);
        }else if (playState == 1){
            holder.iv_music_isPlaying.setBackgroundResource(R.mipmap.playing);
            holder.tv_music_msg.setTextColor(Color.rgb(56,250,41));
            holder.tv_music_title.setTextColor(Color.rgb(56,250,41));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null){
                    mOnItemClickListener.onItemClickListener(mList.get(position),holder.tv_music_title,position);
                }
            }
        });
        holder.iv_right_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null){
                    mOnItemClickListener.onItemRightMenu(mList.get(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class MusicHolder extends RecyclerView.ViewHolder{

        public TextView tv_music_title;
        public TextView tv_music_msg;
        public ImageView iv_right_menu;
        public ImageView iv_music_isPlaying;

        public MusicHolder(View itemView) {
            super(itemView);
            tv_music_title = itemView.findViewById(R.id.tv_music_title);
            tv_music_msg = itemView.findViewById(R.id.tv_music_msg);
            iv_right_menu = itemView.findViewById(R.id.iv_music_right_menu);
            iv_music_isPlaying = itemView.findViewById(R.id.iv_music_isPlaying);
        }
    }
   public interface  onClickInterface{
        void onItemClickListener(MusicInfo musicInfo,View mTitleView,int index);
        void onItemRightMenu(MusicInfo musicInfo);
    }
    public void setOnItemClickListener(onClickInterface onClickInterface){
        this.mOnItemClickListener = onClickInterface;
    }

}
