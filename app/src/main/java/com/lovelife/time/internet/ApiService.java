package com.lovelife.time.internet;


import com.lovelife.time.bean.BaseCallModel;
import com.lovelife.time.bean.HomeCarBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface ApiService {

    @GET("carowner/getCarArchivesEntityListByCarIdForAPP")
    Observable<BaseCallModel<HomeCarBean>> getCarData(@Query("carId") int carId);

}