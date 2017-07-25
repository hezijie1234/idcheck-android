package com.huiyu.tech.zhongxing.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.huiyu.tech.zhongxing.R;
import com.huiyu.tech.zhongxing.api.ApiImpl;
import com.huiyu.tech.zhongxing.utils.CommonUtils;
import com.squareup.picasso.Picasso;

public class LargeMapActivity extends AppCompatActivity {
    private ImageView mLargeImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_large_map);
        initView();
    }
    private void initView() {
        mLargeImage = (ImageView) findViewById(R.id.activity_largemap);
        Intent intent = getIntent();
//        ViewGroup.LayoutParams layoutParams = mLargeImage.getLayoutParams();
//        layoutParams.height = CommonUtils.getScreenWidth(this);
//        mLargeImage.setLayoutParams(layoutParams);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        mLargeImage.setLayoutParams(layoutParams);
        String imageurl = intent.getStringExtra("imageurl");

        Picasso.with(this).load(imageurl)
                .placeholder(R.mipmap.jz_11)
                .error(R.mipmap.jz_11)
                .into(mLargeImage);
        mLargeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
