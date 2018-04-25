package com.boco.routesample.service;

import com.boco.routesample.entity.TrackInfo;
import com.boco.routesample.entity.TrackRequest;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by 11300 on 2018/4/19.
 */

public interface ServiceInterface {
//    String URL = "http://192.168.22.5:8088/bmdp/rest/";//内网
    String URL = "http://182.18.57.8:8080/bmdpnew/rest/";

    /**
     * 查询基站历史轨迹
     * @param trackRequest
     * @return
     */
    @POST("ISiteTrackUploadSrv/getSiteTrackHis")
    Observable<String> getSiteTrackHis(@Body TrackRequest trackRequest);

    /**
     * 轨迹上报
     * @param trackInfo
     * @return
     */
    @POST("ISiteTrackUploadSrv/reportSiteTrack")
    Observable<String> reportSiteTrack(@Body TrackInfo trackInfo);

    /**
     * 查询未完成的轨迹
     * @param trackRequest
     * @return
     */
    @POST("ISiteTrackUploadSrv/getSiteTrackUnfinish")
    Observable<String> getSiteTrackUnfinish(@Body TrackRequest trackRequest);

    /**
     * 删除未完成的轨迹
     * @param trackRequest
     * @return
     */
    @POST("ISiteTrackUploadSrv/delTrack")
    Observable<String> delTrack(@Body TrackRequest trackRequest);
}
