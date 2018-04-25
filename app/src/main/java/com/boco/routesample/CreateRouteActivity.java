package com.boco.routesample;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.boco.bmdp.core.pojo.common.CommMsgResponse;
import com.boco.routesample.entity.TrackInfo;
import com.boco.routesample.entity.TrackPointInfo;
import com.boco.routesample.entity.TrackRequest;
import com.boco.routesample.service.RetrofitCreateHelper;
import com.boco.routesample.service.ServiceInterface;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CreateRouteActivity extends AppCompatActivity {

    private Context mContext = CreateRouteActivity.this;

    private MapView mMapView;
    private ImageView iv_locate;
    private TextView tv_info;

    private NowLocationListener myListener;
    private LinearLayout ll_start;
    private TextView tv_start;
    private ImageView iv_start;
    private LinearLayout ll_submit;
    private LinearLayout ll_key;

    private List<TrackPointInfo> tpList = new ArrayList<>();

    private List<TrackInfo> trackInfoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMapView = (MapView) findViewById(R.id.bmapView);
        tv_info = (TextView) findViewById(R.id.tv_info);
        iv_locate = (ImageView) findViewById(R.id.iv_locate);
        ll_start = (LinearLayout) findViewById(R.id.ll_start);
        tv_start = (TextView) findViewById(R.id.tv_start);
        iv_start = (ImageView) findViewById(R.id.iv_start);
        ll_submit = (LinearLayout) findViewById(R.id.submit);
        ll_key = (LinearLayout) findViewById(R.id.ll_key);

        initMap();
        initAction();
        myListener.Location();
        getSiteTrackUnfinish();
    }

    private void initMap() {
        mMapView.showZoomControls(true);

        myListener = new NowLocationListener(mContext, mMapView);
    }

    private void initAction() {
        iv_locate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myListener.Location();
            }
        });
        ll_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tv_start.getText().toString().equals("开始")) {//开始
                    tv_start.setText("暂停");
                    iv_start.setImageResource(R.mipmap.ic_pause);
                    myListener.startRoute();
                } else {//暂停
                    tv_start.setText("开始");
                    iv_start.setImageResource(R.mipmap.ic_start);
                    myListener.pausePoint();
                }
            }
        });
        ll_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(CreateRouteActivity.this, PointActivity.class);
//                startActivity(intent);
//                Intent intent = new Intent(CreateRouteActivity.this, HistoryRouteActivity.class);
//                startActivity(intent);
                myListener.submitPoint();
                reportSiteTrack();
            }
        });

        ll_key.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myListener.keyPoint();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onStop() {

        super.onStop();
    }

    @Override
    protected void onDestroy() {
        mMapView.onPause();
        myListener.stopLocation();
        super.onDestroy();
    }


    private void reportSiteTrack(){
        tpList = myListener.getTpList();
        TrackInfo trackInfo = new TrackInfo();
        trackInfo.setIsComplete("0");//是否完成轨迹上报0=未完成  1=完成
        trackInfo.setTrackId("888888");//轨迹id
        trackInfo.setTrackType("");//轨迹类型
        trackInfo.setBusinessType("");//业务类型
        trackInfo.setBusinessSys("");//业务系统
        trackInfo.setSheetNo("");//工单号
        trackInfo.setSheetTheme("");//工单主题
        trackInfo.setResType("");//资源类型
        trackInfo.setProfession("");//专业
        trackInfo.setResName("");//资源名称
        trackInfo.setResId("");//资源ID
        trackInfo.setUpSiteUserId("18380448172");//上站人员账号Id
        trackInfo.setUpSiteUser("");//上站人员账号
        trackInfo.setUpSiteUserName("");//上站人员姓名
        trackInfo.setUpSiteUserTeam("");//上站人员班组
        trackInfo.setUpSiteUserCompany("");//上站人员所属地市级公司
        trackInfo.setRegionId("");//地市
        trackInfo.setCityId("");//区县
        trackInfo.setInsertTime("");//插入时间
        trackInfo.setStartLongitudeBaidu(tpList.get(0).getLongitudeBaidu());//百度起点经度
        trackInfo.setStartLatitudeBaidu(tpList.get(0).getLatitudeBaidu());//百度起点纬度
        trackInfo.setEndLongitudeBaidu(tpList.get(tpList.size()-1).getLongitudeBaidu());//百度终点经度
        trackInfo.setEndLatitudeBaidu(tpList.get(tpList.size()-1).getLatitudeBaidu());//百度终点纬度
        trackInfo.setTargetLongitudeBaidu("104.12183");//百度目标经度假设万象城
        trackInfo.setTargetLatitudeBaidu("30.655712");//百度目标纬度
        trackInfo.setDistance(distance()+"");//打点结束时距离目的站点距离（米）
        trackInfo.setTpList(tpList);//轨迹详情
        tv_info.setText("正在上传轨迹");
        RetrofitCreateHelper.createApi(ServiceInterface.class, ServiceInterface.URL)
                .reportSiteTrack(trackInfo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String strResponse) {
                        CommMsgResponse commMsgResponse = JSON.parseObject(strResponse, new TypeReference<CommMsgResponse>() {});
                        if (commMsgResponse.isServiceFlag()) {
                            tv_info.setText("上传轨迹成功");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("----------",e.getMessage());
                        tv_info.setText("上传失败");
                    }

                    @Override
                    public void onComplete() {
//                        tv_info.setText("上传完成");
                    }
                });
    }

    private double distance() {
        LatLng targetLatLng = new LatLng(30.655712, 104.12183);
        LatLng endLatLng = new LatLng(Double.parseDouble(tpList.get(tpList.size()-1).getLatitudeBaidu()),
                Double.parseDouble(tpList.get(tpList.size()-1).getLongitudeBaidu()));
        return DistanceUtil.getDistance(targetLatLng, endLatLng);
    }

    //查询未完成的轨迹上报
    private void getSiteTrackUnfinish() {
        TrackRequest request = new TrackRequest();
        request.setUpSiteUserId("18380448172");
        RetrofitCreateHelper.createApi(ServiceInterface.class, ServiceInterface.URL)
                .getSiteTrackUnfinish(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String strResponse) {
//                        if (commMsgResponse.isServiceFlag()) {
//                            if(commMsgResponse.getDataList()!=null && commMsgResponse.getDataList().size() > 0) {
////                                trackInfo = new TrackInfo();
////                                trackInfo = (TrackInfo) commMsgResponse.getDataList().get(0);
//                            } else {
//                                tv_info.setText("没有未完成轨迹");
//                            }
//                        }
                        CommMsgResponse commMsgResponse = JSON.parseObject(strResponse, new TypeReference<CommMsgResponse>() {});
                        if (commMsgResponse.isServiceFlag()) {
                            if(commMsgResponse.getDataList()!=null && commMsgResponse.getDataList().size() > 0) {
                                JSONObject jsonObject = JSONObject.parseObject(strResponse);
                                String dataList = jsonObject.get("dataList").toString();
                                trackInfoList = JSON.parseObject(dataList, new TypeReference<List<TrackInfo>>() {});

                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        tv_info.setText("未完成轨迹查询失败");
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

}
