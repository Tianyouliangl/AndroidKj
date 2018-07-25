package com.kj.app.internet;


import com.kj.app.bean.BaseCallModel;
import com.kj.app.bean.HomeCarBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface ApiService {

    @GET("carowner/getCarArchivesEntityListByCarIdForAPP")
    Observable<BaseCallModel<HomeCarBean>> getCarData(@Query("carId") int carId);

}