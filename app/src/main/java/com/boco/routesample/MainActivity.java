package com.boco.routesample;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;

public class MainActivity extends AppCompatActivity {

    private Context mContext = MainActivity.this;

    private MapView mMapView;
    private ImageView iv_locate;

    private LocationListener myListener;
    private LinearLayout ll_start;
    private TextView tv_start;
    private ImageView iv_start;
    private LinearLayout ll_submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMapView = findViewById(R.id.bmapView);
        iv_locate = findViewById(R.id.iv_locate);
        ll_start = findViewById(R.id.ll_start);
        tv_start = findViewById(R.id.tv_start);
        iv_start = findViewById(R.id.iv_start);
        ll_submit = findViewById(R.id.submit);

        initMap();
        initAction();
        myListener.Location();
    }

    private void initMap() {
        mMapView.showZoomControls(false);

        myListener = new LocationListener(mContext, mMapView);
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

                } else {//暂停
                    tv_start.setText("开始");
                    iv_start.setImageResource(R.mipmap.ic_start);
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onStop() {
        mMapView.onPause();
        myListener.stopLocation();
        super.onStop();
    }
}
