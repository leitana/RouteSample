package com.boco.routesample.entity;

import com.boco.bmdp.core.pojo.common.BaseBo;

import java.io.Serializable;

/**
 * Created by 11300 on 2018/4/23.
 */

public class TrackPointInfo extends BaseBo implements Serializable{
    private String id;
    private String trackId;
    private String longitudeBaidu;
    private String latitudeBaidu;
    private String pointType;
    private String uploadDate;
    private String fileId;
    private String remark;
    private String upSiteUserId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTrackId() {
        return trackId;
    }

    public void setTrackId(String trackId) {
        this.trackId = trackId;
    }

    public String getLongitudeBaidu() {
        return longitudeBaidu;
    }

    public void setLongitudeBaidu(String longitudeBaidu) {
        this.longitudeBaidu = longitudeBaidu;
    }

    public String getLatitudeBaidu() {
        return latitudeBaidu;
    }

    public void setLatitudeBaidu(String latitudeBaidu) {
        this.latitudeBaidu = latitudeBaidu;
    }

    public String getPointType() {
        return pointType;
    }

    public void setPointType(String pointType) {
        this.pointType = pointType;
    }

    public String getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(String uploadDate) {
        this.uploadDate = uploadDate;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getUpSiteUserId() {
        return upSiteUserId;
    }

    public void setUpSiteUserId(String upSiteUserId) {
        this.upSiteUserId = upSiteUserId;
    }
}
