package com.boco.routesample;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.baidu.mapapi.map.MapView;
import com.boco.bmdp.core.pojo.common.CommMsgResponse;
import com.boco.routesample.entity.TrackRequest;
import com.boco.routesample.service.RetrofitCreateHelper;
import com.boco.routesample.service.ServiceInterface;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        mMapView = (MapView) findViewById(R.id.bmapView);
        iv_locate = (ImageView) findViewById(R.id.iv_locate);
        initMap();
        initAction();
//        getSiteTrackHis();
    }

    private void initAction() {
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
                .subscribe(new Observer<CommMsgResponse>() {

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(CommMsgResponse commMsgResponse) {
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
