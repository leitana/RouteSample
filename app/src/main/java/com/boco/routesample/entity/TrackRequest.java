package com.boco.routesample.entity;

import com.boco.bmdp.core.pojo.common.CommMsgRequest;

import java.io.Serializable;

/**
 * Created by 11300 on 2018/4/19.
 */

public class TrackRequest extends CommMsgRequest implements Serializable{
    private String longitude;//gps精度
    private String latitude;//gps纬度
    private String trackType;//轨迹类型 0=普通 1=标准 2=所有【普通，标准】
    private String distance;//距离
    private String resName;//资源名称

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getTrackType() {
        return trackType;
    }

    public void setTrackType(String trackType) {
        this.trackType = trackType;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getResName() {
        return resName;
    }

    public void setResName(String resName) {
        this.resName = resName;
    }
}
