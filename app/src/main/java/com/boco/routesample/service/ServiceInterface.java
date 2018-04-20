package com.boco.routesample.service;

import com.boco.bmdp.core.pojo.common.CommMsgResponse;
import com.boco.routesample.entity.TrackRequest;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by 11300 on 2018/4/19.
 */

public interface ServiceInterface {
    String URL = "http://182.18.57.8:8080/bmdpnew/rest/";

    @POST("ISiteTrackUploadSrv/getSiteTrackHis")
    Observable<CommMsgResponse> getSiteTrackHis(@Body TrackRequest trackRequest);
}
