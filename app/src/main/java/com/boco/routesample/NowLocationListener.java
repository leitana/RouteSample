package com.boco.routesample;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Polyline;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.boco.routesample.entity.TrackPointInfo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static android.content.Context.SENSOR_SERVICE;

/**
 * Created by 11300 on 2018/4/11.
 */

public class NowLocationListener implements BDLocationListener, SensorEventListener {

    private MapView mMapView = null;
    private BaiduMap mBaiduMap = null;
    private boolean isFirstLoc = true;
    private Context context;
    private LocationClient mLocClient;


    private SensorManager mSensorManager;

    private Double lastX = 0.0;//角度
    private int mCurrentDirection = 0;//方向
    private double mCurrentLat = 0.0;
    private double mCurrentLon = 0.0;
    private MyLocationData locData;
    float mCurrentZoom = 18f;//默认地图缩放比例值

    private boolean isRoute = false;

    //起点图标
    BitmapDescriptor startBD = BitmapDescriptorFactory.fromResource(R.mipmap.ic_start_point);
    //终点图标
    BitmapDescriptor finishBD = BitmapDescriptorFactory.fromResource(R.mipmap.ic_end_point);
    //关键点图标
    BitmapDescriptor keyPointBD = BitmapDescriptorFactory.fromResource(R.mipmap.ic_key_point
    );

    List<LatLng> points = new ArrayList<LatLng>();//位置点集合
    Polyline mPolyline;//运动轨迹图层
    LatLng last = new LatLng(0, 0);//上一个定位点

    List<LatLng> keyPoints = new ArrayList<>();//关键点集合

    MapStatus.Builder builder;

    List<TrackPointInfo> tpList = new ArrayList<>();

    public NowLocationListener(Context context, MapView mMapView) {
        this.mMapView = mMapView;
        mBaiduMap = mMapView.getMap();
        mBaiduMap.setMyLocationEnabled(true);
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);//设置地图类型
        mBaiduMap.setMyLocationConfiguration(new MyLocationConfiguration(MyLocationConfiguration.LocationMode.FOLLOWING, true, null));
        MapStatusUpdate u = MapStatusUpdateFactory.zoomTo(18);
        mBaiduMap.animateMapStatus(u);

        this.context = context;

        /**
         * 添加地图缩放状态变化监听，当手动放大或缩小地图时，拿到缩放后的比例，然后获取到下次定位，
         *  给地图重新设置缩放比例，否则地图会重新回到默认的mCurrentZoom缩放比例
         */
        mBaiduMap.setOnMapStatusChangeListener(new BaiduMap.OnMapStatusChangeListener() {

            @Override
            public void onMapStatusChangeStart(MapStatus arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onMapStatusChangeStart(MapStatus mapStatus, int i) {

            }

            @Override
            public void onMapStatusChangeFinish(MapStatus arg0) {
                mCurrentZoom = arg0.zoom;
            }

            @Override
            public void onMapStatusChange(MapStatus arg0) {
                // TODO Auto-generated method stub

            }
        });

        mSensorManager = (SensorManager) context.getSystemService(SENSOR_SERVICE);// 获取传感器管理服务
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_UI);

        // 定位初始化
        mLocClient = new LocationClient(context);
        mLocClient.registerLocationListener(this);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);// 打开gps
        option.setNeedDeviceDirect(true);
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);
        mLocClient.setLocOption(option);
        mLocClient.start();
    }

    public void Location() {
        MapStatus.Builder builder = new MapStatus.Builder();
        //设置缩放中心点；缩放比例；
        LatLng ll = new LatLng(mCurrentLat, mCurrentLon);
        builder.target(ll).zoom(18.0f);
        //给地图设置状态
        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
    }

    public void startRoute() {
        isRoute = true;
    }

//    public void stopRoute() {
//        isRoute = false;
//    }

    public void stopLocation() {
        mSensorManager.unregisterListener(this);
        mLocClient.unRegisterLocationListener(this); //注销掉监听
        mLocClient.stop(); //停止定位
    }

    public void keyPoint(){
        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String uploadDate = format.format(selectedDate.getTime());
        TrackPointInfo trackPointInfo = new TrackPointInfo();
        trackPointInfo.setTrackId("888888");//轨迹id
        trackPointInfo.setLongitudeBaidu(mCurrentLon + "");
        trackPointInfo.setLatitudeBaidu(mCurrentLat + "");
        trackPointInfo.setPointType("3");
        trackPointInfo.setUploadDate(uploadDate);
        trackPointInfo.setUpSiteUserId("18380448172");
        tpList.add(trackPointInfo);
        LatLng latLng = new LatLng(mCurrentLat, mCurrentLon);
        keyPoints.add(latLng);
        MarkerOptions keyPoint = new MarkerOptions();// 地图标记覆盖物参数配置类
        keyPoint.position(latLng);// 覆盖物位置点
        keyPoint.icon(keyPointBD);// 设置覆盖物图片
        mBaiduMap.addOverlay(keyPoint); // 在地图上添加此图层
    }

    //暂停点
    public void pausePoint() {
        isRoute = false;
        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String uploadDate = format.format(selectedDate.getTime());
        TrackPointInfo trackPointInfo = new TrackPointInfo();
        trackPointInfo.setTrackId("888888");//轨迹id
        trackPointInfo.setLongitudeBaidu(mCurrentLon + "");
        trackPointInfo.setLatitudeBaidu(mCurrentLat + "");
        trackPointInfo.setPointType("6");
        trackPointInfo.setUploadDate(uploadDate);
        trackPointInfo.setUpSiteUserId("18380448172");
        tpList.add(trackPointInfo);
    }

    //提交
    public void submitPoint() {
        isRoute = false;
        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String uploadDate = format.format(selectedDate.getTime());
        TrackPointInfo trackPointInfo = new TrackPointInfo();
        trackPointInfo.setTrackId("888888");//轨迹id
        trackPointInfo.setLongitudeBaidu(mCurrentLon + "");
        trackPointInfo.setLatitudeBaidu(mCurrentLat + "");
        trackPointInfo.setPointType("2");
        trackPointInfo.setUploadDate(uploadDate);
        trackPointInfo.setUpSiteUserId("18380448172");
        tpList.add(trackPointInfo);
    }

    public List<TrackPointInfo> getTpList(){
        return tpList;
    }


    @Override
    public void onReceiveLocation(BDLocation location) {
        //注意这里只接受gps点，需要在室外定位。
//        if (location.getLocType() == BDLocation.TypeGpsLocation) {

//            info.setText("GPS信号弱，请稍后...");

        if (isRoute) {
            if (isFirstLoc) {//首次定位
                //第一个点很重要，决定了轨迹的效果，gps刚开始返回的一些点精度不高，尽量选一个精度相对较高的起始点
                LatLng ll = null;

                ll = getMostAccuracyLocation(location);
                if (ll == null) {
                    return;
                }
                isFirstLoc = false;
                points.add(ll);//加入集合
                last = ll;

                //显示当前定位点，缩放地图
                locateAndZoom(location, ll);

                //标记起点图层位置
                MarkerOptions oStart = new MarkerOptions();// 地图标记覆盖物参数配置类
                oStart.position(points.get(0));// 覆盖物位置点，第一个点为起点
                oStart.icon(startBD);// 设置覆盖物图片
                mBaiduMap.addOverlay(oStart); // 在地图上添加此图层

                //标记终点位置
                MarkerOptions target = new MarkerOptions();// 地图标记覆盖物参数配置类
                LatLng targetLatLng = new LatLng(30.655712, 104.12183);
                target.position(targetLatLng);// 覆盖物位置点，第一个点为起点
                target.icon(finishBD);// 设置覆盖物图片
                mBaiduMap.addOverlay(target); // 在地图上添加此图层

                TrackPointInfo trackPointInfo = new TrackPointInfo();
                trackPointInfo.setTrackId("888888");//轨迹id
                trackPointInfo.setLongitudeBaidu(last.longitude + "");
                trackPointInfo.setLatitudeBaidu(last.latitude + "");
                trackPointInfo.setPointType("1");//先默认全部为普通点
                trackPointInfo.setUploadDate(location.getTime());
                trackPointInfo.setUpSiteUserId("18380448172");
                tpList.add(trackPointInfo);

//                progressBarRl.setVisibility(View.GONE);

                return;//画轨迹最少得2个点，首地定位到这里就可以返回了
            }

            //从第二个点开始
            LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
            //sdk回调gps位置的频率是1秒1个，位置点太近动态画在图上不是很明显，可以设置点之间距离大于为5米才添加到集合中
            if (DistanceUtil.getDistance(last, ll) < 50) {
                locateAndZoom(location, ll);
                return;
            }

            points.add(ll);//如果要运动完成后画整个轨迹，位置点都在这个集合中

            last = ll;

            TrackPointInfo trackPointInfo = new TrackPointInfo();
            trackPointInfo.setTrackId("888888");//轨迹id
            trackPointInfo.setLongitudeBaidu(last.longitude + "");
            trackPointInfo.setLatitudeBaidu(last.latitude + "");
            trackPointInfo.setPointType("5");
            trackPointInfo.setUploadDate(location.getTime());
            trackPointInfo.setUpSiteUserId("18380448172");
            tpList.add(trackPointInfo);

            //显示当前定位点，缩放地图
            locateAndZoom(location, ll);

            //清除上一次轨迹，避免重叠绘画
            mMapView.getMap().clear();

            //起始点图层也会被清除，重新绘画
            MarkerOptions oStart = new MarkerOptions();
            oStart.position(points.get(0));
            oStart.icon(startBD);
            mBaiduMap.addOverlay(oStart);

            //终点位置重新绘画
            MarkerOptions target = new MarkerOptions();// 地图标记覆盖物参数配置类
            LatLng targetLatLng = new LatLng(30.655712, 104.12183);
            target.position(targetLatLng);// 覆盖物位置点，第一个点为起点
            target.icon(finishBD);// 设置覆盖物图片
            mBaiduMap.addOverlay(target); // 在地图上添加此图层

            //关键点图层也被清除，重新绘画
            for (LatLng keyPoint : keyPoints) {
                MarkerOptions markerOptions = new MarkerOptions();// 地图标记覆盖物参数配置类
                markerOptions.position(keyPoint);// 覆盖物位置点，第一个点为起点
                markerOptions.icon(keyPointBD);// 设置覆盖物图片
                mBaiduMap.addOverlay(markerOptions); // 在地图上添加此图层
            }

            //将points集合中的点绘制轨迹线条图层，显示在地图上
            OverlayOptions ooPolyline = new PolylineOptions().width(13).color(0xAAFF0000).points(points);
            mPolyline = (Polyline) mBaiduMap.addOverlay(ooPolyline);
        } else {
            LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
            locateAndZoom(location, ll);
        }
//        }
    }

    private void locateAndZoom(final BDLocation location, LatLng ll) {
        mCurrentLat = location.getLatitude();
        mCurrentLon = location.getLongitude();
        locData = new MyLocationData.Builder().accuracy(0)
                // 此处设置开发者获取到的方向信息，顺时针0-360
                .direction(mCurrentDirection).latitude(location.getLatitude())
                .longitude(location.getLongitude()).build();
        mBaiduMap.setMyLocationData(locData);

        builder = new MapStatus.Builder();
        builder.target(ll).zoom(mCurrentZoom);
        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
    }

    /**
     * 首次定位很重要，选一个精度相对较高的起始点
     * 注意：如果一直显示gps信号弱，说明过滤的标准过高了，
     * 你可以将location.getRadius()>25中的过滤半径调大，比如>40，
     * 并且将连续5个点之间的距离DistanceUtil.getDistance(last, ll ) > 5也调大一点，比如>10，
     * 这里不是固定死的，你可以根据你的需求调整，如果你的轨迹刚开始效果不是很好，你可以将半径调小，两点之间距离也调小，
     * gps的精度半径一般是10-50米
     */
    private LatLng getMostAccuracyLocation(BDLocation location) {

        if (location.getRadius() > 40) {//gps位置精度大于40米的点直接弃用
            return null;
        }

        LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());

        if (DistanceUtil.getDistance(last, ll) > 10) {
            last = ll;
            points.clear();//有任意连续两点位置大于10，重新取点
            return null;
        }
        points.add(ll);
        last = ll;
        //有5个连续的点之间的距离小于10，认为gps已稳定，以最新的点为起始点
        if (points.size() >= 5) {
            points.clear();
            return ll;
        }
        return null;
    }


    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        //每次方向改变，重新给地图设置定位数据，用上一次onReceiveLocation得到的经纬度、精度
        double x = sensorEvent.values[SensorManager.DATA_X];
        if (Math.abs(x - lastX) > 1.0) {// 方向改变大于1度才设置，以免地图上的箭头转动过于频繁
            mCurrentDirection = (int) x;
            locData = new MyLocationData.Builder().accuracy(0)
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(mCurrentDirection).latitude(mCurrentLat).longitude(mCurrentLon).build();
            mBaiduMap.setMyLocationData(locData);

        }
        lastX = x;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    /**
     * 判断两个点的距离是否大于50
     * @param start
     * @param end
     * @return
     */
//    public boolean getDistance(LatLng start,LatLng end){
//        double lat1 = (Math.PI/180)*start.latitude;
//        double lat2 = (Math.PI/180)*end.latitude;
//
//        double lon1 = (Math.PI/180)*start.longitude;
//        double lon2 = (Math.PI/180)*end.longitude;
//
//        //地球半径
//        double R = 6371;
//
//        //两点间距离 km，如果想要米的话，结果*1000
//        double d =  Math.acos(Math.sin(lat1)*Math.sin(lat2)+Math.cos(lat1)*Math.cos(lat2)*Math.cos(lon2-lon1))*R;
//        if (d * 1000 > 50) {
//            return true;
//        } else {
//            return false;
//        }
//    }
}
