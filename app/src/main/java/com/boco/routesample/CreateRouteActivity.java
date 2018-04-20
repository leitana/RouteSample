package com.boco.routesample;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.mapapi.map.MapView;

public class CreateRouteActivity extends AppCompatActivity {

    private Context mContext = CreateRouteActivity.this;

    private MapView mMapView;
    private ImageView iv_locate;

    private NowLocationListener myListener;
    private LinearLayout ll_start;
    private TextView tv_start;
    private ImageView iv_start;
    private LinearLayout ll_submit;
    private LinearLayout ll_key;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMapView = (MapView) findViewById(R.id.bmapView);
        iv_locate = (ImageView) findViewById(R.id.iv_locate);
        ll_start = (LinearLayout) findViewById(R.id.ll_start);
        tv_start = (TextView) findViewById(R.id.tv_start);
        iv_start = (ImageView) findViewById(R.id.iv_start);
        ll_submit = (LinearLayout) findViewById(R.id.submit);
        ll_key = (LinearLayout) findViewById(R.id.ll_key);

        initMap();
        initAction();
        myListener.Location();
    }

    private void initMap() {
        mMapView.showZoomControls(false);

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
                    myListener.stopRoute();
                }
            }
        });
        ll_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(CreateRouteActivity.this, PointActivity.class);
//                startActivity(intent);
                Intent intent = new Intent(CreateRouteActivity.this, HistoryRouteActivity.class);
                startActivity(intent);
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


}
