package com.lovelife.time.contract;

import android.content.Context;

import com.lovelife.time.base.BaseContract;
import com.lovelife.time.bean.MusicInfo;

import java.util.List;

public interface MusicContract {

    interface MusicView extends BaseContract.BaseView{
        void MusicMsg(List<MusicInfo> list);
        void deleteSuccess(String msg);
        void deleteError(String msg);
    }
    interface MusicPresenter extends BaseContract.BasePresenter<MusicView>{
        void  getMusic(Context context);
        void  getMusicDbAll();
        void  deleteMusic(String id,String path,boolean isChecked);
        boolean  getIsNull(String path);

    }
}
