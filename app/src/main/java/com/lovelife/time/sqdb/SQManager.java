package com.lovelife.time.sqdb;

import com.lovelife.time.app.DHApplication;
import com.lovelife.time.utlis.SPUtils;
import com.lovelife.time.weight.SPKey;

public class SQManager {

    /**
     * 数据库名字
     */
    // 音乐
    public static  String SQLITE_MC_MSG =  "music_msg_" + getNameSub();

    // user
    public static String SQLITE_USER_INFO = "user_info_" + getNameSub();

    public static String getNameSub(){
        //43F331753E61315FE3B8818C484963C5
        String user_id =SPUtils.getInstance(DHApplication.getContext()).getString(SPKey.USER_ID);
        String open_id = null;
        if (user_id.length() > 30){
            open_id = user_id.substring(24,28);
        }

        return open_id;
    }

}
