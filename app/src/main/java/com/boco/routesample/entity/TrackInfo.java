package com.boco.routesample.entity;

import com.boco.bmdp.core.pojo.common.CommMsgRequest;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 11300 on 2018/4/23.
 */

public class TrackInfo extends CommMsgRequest implements Serializable {
    private String id;
    private String isComplete;
    private String trackId;
    private String trackType;
    private String businessType;
    private String businessSys;
    private String sheetNo;
    private String sheetTheme;
    private String resType;
    private String profession;
    private String resName;
    private String resId;
    private String upSiteUserId;
    private String upSiteUser;
    private String upSiteUserName;
    private String upSiteUserTeam;
    private String upSiteUserCompany;
    private String regionId;
    private String cityId;
    private String insertTime;
    private String startLongitudeBaidu;
    private String startLatitudeBaidu;
    private String endLongitudeBaidu;
    private String endLatitudeBaidu;
    private String targetLongitudeBaidu;
    private String targetLatitudeBaidu;
    private String distance;
    private List<TrackPointInfo> tpList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIsComplete() {
        return isComplete;
    }

    public void setIsComplete(String isComplete) {
        this.isComplete = isComplete;
    }

    public String getTrackId() {
        return trackId;
    }

    public void setTrackId(String trackId) {
        this.trackId = trackId;
    }

    public String getTrackType() {
        return trackType;
    }

    public void setTrackType(String trackType) {
        this.trackType = trackType;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getBusinessSys() {
        return businessSys;
    }

    public void setBusinessSys(String businessSys) {
        this.businessSys = businessSys;
    }

    public String getSheetNo() {
        return sheetNo;
    }

    public void setSheetNo(String sheetNo) {
        this.sheetNo = sheetNo;
    }

    public String getSheetTheme() {
        return sheetTheme;
    }

    public void setSheetTheme(String sheetTheme) {
        this.sheetTheme = sheetTheme;
    }

    public String getResType() {
        return resType;
    }

    public void setResType(String resType) {
        this.resType = resType;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getResName() {
        return resName;
    }

    public void setResName(String resName) {
        this.resName = resName;
    }

    public String getResId() {
        return resId;
    }

    public void setResId(String resId) {
        this.resId = resId;
    }

    public String getUpSiteUserId() {
        return upSiteUserId;
    }

    public void setUpSiteUserId(String upSiteUserId) {
        this.upSiteUserId = upSiteUserId;
    }

    public String getUpSiteUser() {
        return upSiteUser;
    }

    public void setUpSiteUser(String upSiteUser) {
        this.upSiteUser = upSiteUser;
    }

    public String getUpSiteUserName() {
        return upSiteUserName;
    }

    public void setUpSiteUserName(String upSiteUserName) {
        this.upSiteUserName = upSiteUserName;
    }

    public String getUpSiteUserTeam() {
        return upSiteUserTeam;
    }

    public void setUpSiteUserTeam(String upSiteUserTeam) {
        this.upSiteUserTeam = upSiteUserTeam;
    }

    public String getUpSiteUserCompany() {
        return upSiteUserCompany;
    }

    public void setUpSiteUserCompany(String upSiteUserCompany) {
        this.upSiteUserCompany = upSiteUserCompany;
    }

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(String insertTime) {
        this.insertTime = insertTime;
    }

    public String getStartLongitudeBaidu() {
        return startLongitudeBaidu;
    }

    public void setStartLongitudeBaidu(String startLongitudeBaidu) {
        this.startLongitudeBaidu = startLongitudeBaidu;
    }

    public String getStartLatitudeBaidu() {
        return startLatitudeBaidu;
    }

    public void setStartLatitudeBaidu(String startLatitudeBaidu) {
        this.startLatitudeBaidu = startLatitudeBaidu;
    }

    public String getEndLongitudeBaidu() {
        return endLongitudeBaidu;
    }

    public void setEndLongitudeBaidu(String endLongitudeBaidu) {
        this.endLongitudeBaidu = endLongitudeBaidu;
    }

    public String getEndLatitudeBaidu() {
        return endLatitudeBaidu;
    }

    public void setEndLatitudeBaidu(String endLatitudeBaidu) {
        this.endLatitudeBaidu = endLatitudeBaidu;
    }

    public String getTargetLongitudeBaidu() {
        return targetLongitudeBaidu;
    }

    public void setTargetLongitudeBaidu(String targetLongitudeBaidu) {
        this.targetLongitudeBaidu = targetLongitudeBaidu;
    }

    public String getTargetLatitudeBaidu() {
        return targetLatitudeBaidu;
    }

    public void setTargetLatitudeBaidu(String targetLatitudeBaidu) {
        this.targetLatitudeBaidu = targetLatitudeBaidu;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public List<TrackPointInfo> getTpList() {
        return tpList;
    }

    public void setTpList(List<TrackPointInfo> tpList) {
        this.tpList = tpList;
    }
}
