package com.lovelife.time.sqdb;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.lovelife.time.bean.MusicInfo;
import com.lovelife.time.bean.UserInfoBean;

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
        /**
         * ret : 0
         * msg :
         * is_lost : 0
         * nickname : 今日就到这吧.
         * gender : 男
         * province : 河北
         * city : 邯郸
         * year : 1994
         * constellation :
         * figureurl_qq_1 : http://thirdqq.qlogo.cn/qqapp/1105602574/43F331753E61315FE3B8818C484963C5/40
         * is_yellow_vip : 0
         * vip : 0
         * yellow_vip_level : 0
         * level : 0
         * is_yellow_year_vip : 0
         */
        if (!tableIsExist(SQManager.SQLITE_USER_INFO)) {
            m_sqLite.execSQL("create table " + SQManager.SQLITE_USER_INFO
                    + " (id Integer primary key autoincrement, ret Integer, msg VARCHAR, " +
                    " is_lost Integer, nickname VARCHAR, gender VARCHAR, province VARCHAR, city VARCHAR, year VARCHAR," +
                    " constellation VARCHAR,figureurl_qq_1 VARCHAR,is_yellow_vip VARCHAR,vip VARCHAR,yellow_vip_level VARCHAR," +
                    "level VARCHAR,is_yellow_year_vip VARCHAR);");
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
        if (m_sqLite.isOpen()){
            m_sqLite.execSQL("DELETE FROM " + tableName );
        }
    }
    /**添加用户信息
     * ret : 0
     * msg :
     * is_lost : 0
     * nickname : 今日就到这吧.
     * gender : 男
     * province : 河北
     * city : 邯郸
     * year : 1994
     * constellation :
     * figureurl_qq_1 : http://thirdqq.qlogo.cn/qqapp/1105602574/43F331753E61315FE3B8818C484963C5/40
     * is_yellow_vip : 0
     * vip : 0
     * yellow_vip_level : 0
     * level : 0
     * is_yellow_year_vip : 0
     */
    public void insetUserInfo(UserInfoBean user){
        if (m_sqLite.isOpen()) {
            ContentValues cv = new ContentValues();
            cv.put("ret", user.getRet());
            cv.put("msg", user.getMsg());
            cv.put("is_lost",user.getIs_lost());
            cv.put("nickname", user.getNickname());
            cv.put("gender", user.getGender());
            cv.put("province ", user.getProvince());
            cv.put("city", user.getCity());
            cv.put("year", user.getYear());
            cv.put("constellation", user.getConstellation());
            cv.put("figureurl_qq_1",user.getFigureurl_qq_1());
            cv.put("is_yellow_vip ", user.getIs_yellow_vip());
            cv.put("vip", user.getVip());
            cv.put("yellow_vip_level", user.getYellow_vip_level());
            cv.put("level", user.getLevel());
            cv.put("is_yellow_year_vip",user.getIs_yellow_year_vip());
            m_sqLite.insert(SQManager.SQLITE_USER_INFO, null, cv);
        }
    }
    /**查询用户信息
     * ret : 0
     * msg :
     * is_lost : 0
     * nickname : 今日就到这吧.
     * gender : 男
     * province : 河北
     * city : 邯郸
     * year : 1994
     * constellation :
     * figureurl_qq_1 : http://thirdqq.qlogo.cn/qqapp/1105602574/43F331753E61315FE3B8818C484963C5/40
     * is_yellow_vip : 0
     * vip : 0
     * yellow_vip_level : 0
     * level : 0
     * is_yellow_year_vip : 0
     */
    public UserInfoBean getUserInfo() {
        Cursor cs;
        UserInfoBean listBean = null;
        if (m_sqLite.isOpen()) {
            cs = m_sqLite.rawQuery("select * from " + SQManager.SQLITE_USER_INFO, null);
            if (cs != null) {
                while (cs.moveToNext()) {
                    listBean = new UserInfoBean();
                    listBean.setRet(cs.getInt(cs.getColumnIndex("ret")));
                    listBean.setMsg(cs.getString(cs.getColumnIndex("msg")));
                    listBean.setIs_lost(cs.getInt(cs.getColumnIndex("is_lost")));
                    listBean.setNickname(cs.getString(cs.getColumnIndex("nickname")));
                    listBean.setGender(cs.getString(cs.getColumnIndex("gender")));
                    listBean.setProvince(cs.getString(cs.getColumnIndex("province")));
                    listBean.setCity(cs.getString(cs.getColumnIndex("city")));
                    listBean.setYear(cs.getString(cs.getColumnIndex("year")));
                    listBean.setConstellation(cs.getString(cs.getColumnIndex("constellation")));
                    listBean.setFigureurl_qq_1(cs.getString(cs.getColumnIndex("figureurl_qq_1")));
                    listBean.setIs_yellow_vip(cs.getString(cs.getColumnIndex("is_yellow_vip")));
                    listBean.setVip(cs.getString(cs.getColumnIndex("vip")));
                    listBean.setYellow_vip_level(cs.getString(cs.getColumnIndex("yellow_vip_level")));
                    listBean.setLevel(cs.getString(cs.getColumnIndex("level")));
                    listBean.setIs_yellow_year_vip(cs.getString(cs.getColumnIndex("is_yellow_year_vip")));
                }
                cs.close();
            }
        }
        return listBean;
    }

}
