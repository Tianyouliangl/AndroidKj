package com.lovelife.time.internet;


import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Url;

/**
 * Created by fucp on 2018-5-23.
 * Description : 文件上传
 */

public interface ApiFile {

   // String UPLOAD_FILE_URL = Constant.BASE_FILE_URL + "upload/file";

    @POST
    Observable<ResponseBody> uploadFile(@Url String url, @Body MultipartBody body);

}
