package com.kj.app.contract;

import android.content.Context;

import com.kj.app.base.BaseContract;
import com.kj.app.bean.MusicInfo;

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

    }
}
