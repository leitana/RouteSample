package com.boco.routesample;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;

import com.boco.routesample.recoder.AudioRecoderDialog;
import com.boco.routesample.recoder.AudioRecoderUtils;
import com.boco.routesample.utils.GetImagePathUtil;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 11300 on 2018/4/17.
 */

public class PointActivity extends AppCompatActivity implements View.OnTouchListener, AudioRecoderUtils.OnAudioStatusUpdateListener{
    private static final int REQUEST_CODE_CHOOSE = 1;

    private Context mContext = PointActivity.this;

    private GridView gridView;
    private EditText editText;
    private ImageView iv_voice;

    private List<String> mSelected;
    private GridAdapter mAdapter;

    private AudioRecoderDialog recoderDialog;
    private AudioRecoderUtils recoderUtils;
    private long downT;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pop_submit);

        gridView = (GridView) findViewById(R.id.gv_picture);
        iv_voice = (ImageView) findViewById(R.id.iv_voice);
        iv_voice.setOnTouchListener(this);

        recoderDialog = new AudioRecoderDialog(this);
        recoderDialog.setShowAlpha(0.98f);

//        recoderUtils = new AudioRecoderUtils(new File(Environment.getExternalStorageDirectory() + "/recoder.amr"));
        recoderUtils = new AudioRecoderUtils();
        recoderUtils.setOnAudioStatusUpdateListener(this);

        mSelected = new ArrayList<>();
        mAdapter = new GridAdapter(this, mSelected);

        gridView.setAdapter(mAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == mSelected.size()) {
                    Matisse.from(PointActivity.this)
                            .choose(MimeType.allOf()) // 选择 mime 的类型
                            .countable(true)
                            .maxSelectable(5 - mSelected.size()) // 图片选择的最多数量
                            .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                            .thumbnailScale(0.85f) // 缩略图的比例
                            .imageEngine(new GlideEngine()) // 使用的图片加载引擎
                            .forResult(REQUEST_CODE_CHOOSE); // 设置作为标记的请求码

                } else {
                    Intent intent = new Intent(PointActivity.this, PreviewActivity.class);
                    intent.putExtra("index", position);
                    intent.putExtra("imageList", (Serializable) mSelected);
                    startActivity(intent);
                }
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            if (Matisse.obtainResult(data).size() > 0) {
                for (Uri uri : Matisse.obtainResult(data)) {
                    mSelected.add(GetImagePathUtil.getPathFromUri(mContext, uri));
                }
            }
            mAdapter = new GridAdapter(this, mSelected);
            gridView.setAdapter(mAdapter);
//            Log.d("Matisse", "mSelected: " + mSelected);
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                String time=System.currentTimeMillis() + "";
                recoderUtils.startRecord(new File(Environment.getExternalStorageDirectory() + "/" + time + ".amr"));
                downT = System.currentTimeMillis();
                recoderDialog.showAtLocation(v, Gravity.CENTER, 0, 0);
                iv_voice.setImageResource(R.mipmap.ic_voice_green);
                return true;
            case MotionEvent.ACTION_UP:
                long current = System.currentTimeMillis();
                if (current - downT > 2000){
                    recoderUtils.stopRecord();
                    recoderDialog.dismiss();
                } else {
                    recoderUtils.cancelRecord();
                    recoderDialog.dismiss();
                }
                iv_voice.setImageResource(R.mipmap.ic_voice);
                return true;
        }
        return false;
    }

    @Override
    public void onUpdate(double db) {
        if(null != recoderDialog) {
            int level = (int) db;
            recoderDialog.setLevel((int)db);
            recoderDialog.setTime(System.currentTimeMillis() - downT);
        }
    }
}
