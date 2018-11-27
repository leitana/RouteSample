package com.boco.routesample;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.baidu.mapapi.map.MapView;
import com.boco.routesample.entity.TrackRequest;
import com.boco.routesample.service.RetrofitCreateHelper;
import com.boco.routesample.service.ServiceInterface;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by 11300 on 2018/4/18.
 */

public class HistoryRouteActivity extends AppCompatActivity {

    private Context mContext = HistoryRouteActivity.this;
    private MapView mMapView;
    private HistoryLocationListener myListener;
    private ImageView iv_locate;
    private MaterialSpinner standardSpinner;
    private MaterialSpinner distanceSpinner;

    private List<String> standardList;
    private List<String> distanceList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        mMapView = (MapView) findViewById(R.id.bmapView);
        iv_locate = (ImageView) findViewById(R.id.iv_locate);
        standardSpinner = (MaterialSpinner) findViewById(R.id.sp_standard);
        distanceSpinner = (MaterialSpinner) findViewById(R.id.sp_distance);
        initMap();
        initView();
//        getSiteTrackHis();
    }

    private void initView() {
        standardList = new ArrayList<>();
        distanceList = new ArrayList<>();

        standardList.add("标准轨迹");
        standardList.add("非标准轨迹");
        standardList.add("全部");

        distanceList.add("全部");
        distanceList.add("0.5km");
        distanceList.add("1.0km");
        distanceList.add("2.0km");

        standardSpinner.setItems(standardList);
        distanceSpinner.setItems(distanceList);

        standardSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {

            }
        });
        distanceSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {

            }
        });

        iv_locate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myListener.Location();
            }
        });
    }

    private void initMap() {
        mMapView.showZoomControls(false);

        myListener = new HistoryLocationListener(mContext, mMapView);
    }

    private void getSiteTrackHis() {
        TrackRequest request = new TrackRequest();
        request.setTrackType("1");
        request.setResName("测试13");
        RetrofitCreateHelper.createApi(ServiceInterface.class, ServiceInterface.URL)
                .getSiteTrackHis(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String commMsgResponse) {
                        Log.d("---------",commMsgResponse.toString());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("---------",e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


}
