package com.kj.app.bean;

import java.io.Serializable;

public class MusicInfo implements Serializable{
    /**歌曲名*/
    private String name;
    /**路径*/
    private String path;
    /**所属专辑*/
    private String album;
    /**艺术家(作者)*/
    private String artist;
    /**文件大小*/
    private String size;
    /**时长*/
    private String duration;
    /**歌曲名的拼音，用于字母排序*/
    private String pinyin;
    private String time;
    private String title;
    private String mid;
    private int state; // 播放状态 1 : 播放  0 : 暂停     4 : 上一首  9 : 下一首   -1 : 重置  -2 :  准备中  -3 : 播放完毕
    private int playState = 0;

    public int getPlayState() {
        return playState;
    }

    public void setPlayState(int playState) {
        this.playState = playState;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }
}
