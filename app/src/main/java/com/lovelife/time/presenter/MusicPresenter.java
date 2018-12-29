package com.lovelife.time.presenter;

import android.content.Context;

import android.database.Cursor;
import android.provider.MediaStore;

import com.lovelife.time.app.DHApplication;
import com.lovelife.time.base.BasePresenter;
import com.lovelife.time.bean.MusicInfo;
import com.lovelife.time.contract.MusicContract;
import com.lovelife.time.sqdb.SQManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class MusicPresenter extends BasePresenter<MusicContract.MusicView> implements MusicContract.MusicPresenter {

    @Inject
    public MusicPresenter() {
    }

    @Override
    public void getMusic(Context context) {
        DHApplication.getDao().deleteTab(SQManager.SQLITE_MC_MSG);
        List<MusicInfo> infos = getMp3Infos(context);
        for (int i=0;i<infos.size();i++){
            DHApplication.getDao().insertMusicMsg(infos.get(i));
        }
        List<MusicInfo> musicAll = DHApplication.getDao().getMusicAll(SQManager.SQLITE_MC_MSG);
        mView.MusicMsg(musicAll);
    }

    /**
     * 查询数据库中所有的歌曲信息
     */
    @Override
    public void getMusicDbAll() {
        if (DHApplication.getDao().tableIsExist(SQManager.SQLITE_MC_MSG)){
            mView.MusicMsg(DHApplication.getDao().getMusicAll(SQManager.SQLITE_MC_MSG));
        }
    }

    /**
     * 删除歌曲
     * @param name  歌曲路径
     */
    @Override
    public void deleteMusic(String id,String name,boolean isChecked) {
        if (DHApplication.getDao().tableIsExist(SQManager.SQLITE_MC_MSG)){
            if (isChecked){
                boolean b = deleteBendi(name);
                if (!b){
                    mView.deleteError("文件不存在");
                }else {
                    mView.deleteSuccess("删除成功");
                }
                DHApplication.getDao().deleteMusic(id);
                mView.MusicMsg(DHApplication.getDao().getMusicAll(SQManager.SQLITE_MC_MSG));
            }else {
                DHApplication.getDao().deleteMusic(id);
                mView.deleteSuccess("删除成功");
                mView.MusicMsg(DHApplication.getDao().getMusicAll(SQManager.SQLITE_MC_MSG));
            }
        }else {
            mView.deleteError("删除失败");
        }
    }

    @Override
    public boolean getIsNull(String path) {
        File mf = new File(path);
        if(!mf.exists()){
            mView.deleteError("文件不存在!");
            return false;
        }
        return true;
    }

    private boolean deleteBendi(String path) {
        File mf = new File(path);
        if(mf.exists()){
            mf.delete();
            return true;
        }else {
           return  false;
        }
    }


    public static List<MusicInfo> getMp3Infos(Context context) {
        Cursor cursor = context.getContentResolver().query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null,
                MediaStore.Audio.Media.DEFAULT_SORT_ORDER);

        List<MusicInfo> musicInfos = new ArrayList<MusicInfo>();
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToNext();
            MusicInfo musicInfo = new MusicInfo();
            long id = cursor.getLong(cursor
                    .getColumnIndex(MediaStore.Audio.Media._ID));	//音乐id
            String title = cursor.getString((cursor
                    .getColumnIndex(MediaStore.Audio.Media.TITLE))); // 音乐标题
            String artist = cursor.getString(cursor
                    .getColumnIndex(MediaStore.Audio.Media.ARTIST)); // 艺术家
            String album = cursor.getString(cursor
                    .getColumnIndex(MediaStore.Audio.Media.ALBUM));	//专辑
            String displayName = cursor.getString(cursor
                    .getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));
            long albumId = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));
            long duration = cursor.getLong(cursor
                    .getColumnIndex(MediaStore.Audio.Media.DURATION)); // 时长
            long size = cursor.getLong(cursor
                    .getColumnIndex(MediaStore.Audio.Media.SIZE)); // 文件大小
            String url = cursor.getString(cursor
                    .getColumnIndex(MediaStore.Audio.Media.DATA)); // 文件路径
            int isMusic = cursor.getInt(cursor
                    .getColumnIndex(MediaStore.Audio.Media.IS_MUSIC)); // 是否为音乐
            if (isMusic != 0 && duration >= 30000) { // 只把音乐添加到集合当中
                musicInfo.setName(displayName);
                musicInfo.setTitle(title);
                musicInfo.setArtist(artist);
                musicInfo.setAlbum(album);
                musicInfo.setDuration(String.valueOf(duration));
                musicInfo.setSize(String.valueOf(size));
                musicInfo.setPath(url);
                musicInfo.setMid(String.valueOf(id));
                musicInfos.add(musicInfo);
            }
        }
        return musicInfos;
    }
}
