package com.boco.routesample;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.boco.routesample.utils.ImageLoaderUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 11300 on 2018/4/17.
 */

public class PreviewActivity extends Activity{
    private Context mContext = PreviewActivity.this;

    private List<String> imageList = new ArrayList<>();
    private int index;

    private ViewPager viewPager;
    private ImageBrowseAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
        initData();
        viewPager = findViewById(R.id.viewpager);
        mAdapter = new ImageBrowseAdapter();
        viewPager.setAdapter(mAdapter);
        viewPager.setCurrentItem(index);
    }

    private void initData() {
        if (getIntent() != null) {
            imageList = (List<String>) getIntent().getSerializableExtra("imageList");
            index = getIntent().getIntExtra("index", 0);
        }
    }

    private class ImageBrowseAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return imageList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = new ImageView(mContext);
            ImageLoaderUtils.display(mContext, imageView, imageList.get(position));
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
//            super.destroyItem(container, position, object);
            ((ViewGroup) container).removeView((View) object);
            object = null;
        }
    }

}
