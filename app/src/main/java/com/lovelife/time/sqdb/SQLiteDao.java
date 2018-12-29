package com.lovelife.time.sqdb;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.lovelife.time.bean.MusicInfo;

import java.util.ArrayList;
import java.util.List;

public class SQLiteDao {
    private Context context;
    private final SQDbHelper helper;
    private final SQLiteDatabase m_sqLite;

    public SQLiteDao(Context context) {
        this.context = context;
        helper = new SQDbHelper(context);
        m_sqLite = helper.getWritableDatabase();
    }
    public void createTab(){

        if (!tableIsExist(SQManager.SQLITE_MC_MSG)) {
            m_sqLite.execSQL("create table " + SQManager.SQLITE_MC_MSG
                    + " (id Integer primary key autoincrement, name VARCHAR, path VARCHAR, " +
                    " album VARCHAR, artist VARCHAR, size VARCHAR, duration VARCHAR, pinyin VARCHAR, time VARCHAR, title VARCHAR,mid VARCHAR);");
        }
    }

    /**
     * 判断表是否存在
     * @param tableName
     * @return
     */
    public boolean tableIsExist(String tableName) {
        boolean result = false;
        if (tableName == null) {
            return false;
        }
        Cursor cursor = null;
        try {
            String sql = "select count(*) as c from sqlite_master where type ='table' and name ='"
                    + tableName.trim() + "' ";
            cursor = m_sqLite.rawQuery(sql, null);

            if (cursor.moveToNext()) {
                int count = cursor.getInt(0);
                if (count > 0) {
                    result = true;
                }
            } else {
                result = false;
            }

            cursor.close();
        } catch (Exception e) {
            Log.e("tableIsExist", e.getMessage());
        }
        return result;
    }

    /**
     * 添加音乐信息到数据库中
     * @param info
     */
    public void insertMusicMsg(MusicInfo info) {
        if (m_sqLite.isOpen()) {
            ContentValues cv = new ContentValues();
            cv.put("name", info.getName().toString().trim());
            cv.put("path", info.getPath());
            cv.put("album", info.getAlbum().toString().trim());
            cv.put("artist", info.getArtist().toString().trim());
            cv.put("size", info.getSize());
            cv.put("duration ", info.getDuration());
            cv.put("pinyin", info.getPinyin());
            cv.put("time", info.getTime());
            cv.put("title", info.getTitle().toString().trim());
            cv.put("mid",info.getMid());
            m_sqLite.insert(SQManager.SQLITE_MC_MSG, null, cv);
        }
    }

    /**
     * 根据路径删除数据中的音乐
     * @param id
     */
    public void deleteMusic(String id) {
        if (m_sqLite.isOpen()) {
            m_sqLite.execSQL("DELETE FROM " + SQManager.SQLITE_MC_MSG  + " where mid = " + id);
        }
    }

    /**
     * 查询数据库中的所有信息
     * @param tableName
     * @return
     */
    public List<MusicInfo> getMusicAll(String tableName) {
        Cursor cs;
        List<MusicInfo> beanContact = new ArrayList<>();
        if (m_sqLite.isOpen()) {
            cs = m_sqLite.rawQuery("select * from " + tableName, null);
            if (cs != null) {
                while (cs.moveToNext()) {
                    MusicInfo listBean = new MusicInfo();
                    listBean.setName(cs.getString(cs.getColumnIndex("name")));
                    listBean.setPath(cs.getString(cs.getColumnIndex("path")));
                    listBean.setAlbum(cs.getString(cs.getColumnIndex("album")));
                    listBean.setArtist(cs.getString(cs.getColumnIndex("artist")));
                    listBean.setSize(cs.getString(cs.getColumnIndex("size")));
                    listBean.setTitle(cs.getString(cs.getColumnIndex("title")));
                    listBean.setDuration(cs.getString(cs.getColumnIndex("duration")));
                    listBean.setPinyin(cs.getString(cs.getColumnIndex("pinyin")));
                    listBean.setTime(cs.getString(cs.getColumnIndex("time")));
                    listBean.setTitle(cs.getString(cs.getColumnIndex("title")));
                    listBean.setMid(cs.getString(cs.getColumnIndex("mid")));
                    beanContact.add(listBean);
                }
                cs.close();
            }
        }
        return beanContact;
    }

    /**
     * 删除表中的所有数据
     * @param tableName
     */
    public void deleteTab(String tableName){
        Cursor cs;
        if (m_sqLite.isOpen()){
            m_sqLite.execSQL("DELETE FROM " + tableName );
        }
    }

}
