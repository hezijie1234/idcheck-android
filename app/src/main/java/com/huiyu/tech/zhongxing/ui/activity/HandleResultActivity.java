package com.huiyu.tech.zhongxing.ui.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.alibaba.fastjson.JSON;
import com.huiyu.tech.zhongxing.Constants;
import com.huiyu.tech.zhongxing.R;
import com.huiyu.tech.zhongxing.api.ApiImpl;
import com.huiyu.tech.zhongxing.api.OnResponseListener;
import com.huiyu.tech.zhongxing.models.ResultPageModel;
import com.huiyu.tech.zhongxing.ui.ZZBaseActivity;
import com.huiyu.tech.zhongxing.ui.adapter.GridPicAdapter2;
import com.huiyu.tech.zhongxing.utils.CustomToast;
import com.huiyu.tech.zhongxing.utils.LogUtils;
import com.huiyu.tech.zhongxing.widget.MyGridView;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
//import java.util.Observable;

/**
 * 警情处理结果
 */
public class HandleResultActivity extends ZZBaseActivity implements View.OnClickListener, OnResponseListener {

    private MyGridView gvPic;
    private RelativeLayout layoutContainer;
    private VideoView vv;
    private ImageView ivStop;
    private TextView tvState;
    private TextView tvDesc;
    private TextView tvName;
    private TextView tvPhone;

    private GridPicAdapter2 gridPicAdapter;

    private Intent intent;
    private String id;
    private ImageView mVideoBackground;
    //是否是第一次点击播放
    private boolean isFirst = true;
    private ResultPageModel resultPageModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handle_result);

        initBase();
        initView();
        initData();
        listener();
    }

    /**
     * created by hezijie at 2017/3/30
     */
    private void listener() {
        //设置gridview中图片点击放大监听。
        gvPic.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                List<ResultPageModel.ImagesBean> items = gridPicAdapter.getItems();
                String imagefile = items.get(i).getImagefile();
                Intent intent = new Intent(HandleResultActivity.this,LargeMapActivity.class);
                intent.putExtra("imageurl",imagefile);
                LogUtils.e(imagefile);
                startActivity(intent);
            }
        });
    }

    private void initBase(){
        intent = getIntent();
        if(intent.hasExtra(Constants.INTENT_KEY.KEY_ID)){
            id = intent.getStringExtra(Constants.INTENT_KEY.KEY_ID);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void initView() {
        showBackView();
        showTitleView("警情处理详情");

        gvPic = (MyGridView) findViewById(R.id.gv_pic);
        layoutContainer = (RelativeLayout) findViewById(R.id.layout_container);
        vv = (VideoView) findViewById(R.id.vv);
        ivStop = (ImageView) findViewById(R.id.iv_stop);
        tvState = (TextView) findViewById(R.id.tv_state);
        tvDesc = (TextView) findViewById(R.id.tv_desc);
        tvName = (TextView) findViewById(R.id.tv_name);
        tvPhone = (TextView) findViewById(R.id.tv_phone);
        mVideoBackground = (ImageView) findViewById(R.id.video_background_iv);
        ivStop.setOnClickListener(this);
    }

    private void initData(){
        gridPicAdapter = new GridPicAdapter2(this);
        gvPic.setAdapter(gridPicAdapter);

        showProgressDialog(true);
        ApiImpl.getInstance().getHandlerResult(id,this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.iv_stop:
                ivStop.setVisibility(View.GONE);
                //如果是第一次点击播放，就将显示的第一帧图片隐藏。
                if(isFirst){
                    isFirst = false;

                    mVideoBackground.setVisibility(View.GONE);
                }
                vv.start();
                break;
        }
    }

    @Override
    public void onAPISuccess(String flag, JSONObject json) {
        hideProgressDialog();
        resultPageModel = JSON.parseObject(json.optString("d"), ResultPageModel.class);
        tvDesc.setText(resultPageModel.getAlarmret().getComments());
        tvName.setText(resultPageModel.getRecuser());
        tvPhone.setText(resultPageModel.getRecphone());
        if(resultPageModel.getImages() != null && resultPageModel.getImages().size() > 0){
            gridPicAdapter.setItems(resultPageModel.getImages());
        }
        String videofile = resultPageModel.getAlarmret().getVideofile();
        String thumbnail = resultPageModel.getAlarmret().getVideoThumbnail();
        //在视屏没有播放之前，显示第一帧图片
        Picasso.with(this).load(ApiImpl.DOMIN +thumbnail)
                .placeholder(R.mipmap.jz_11)
                .error(R.mipmap.jz_11)
                .fit()
                .into(mVideoBackground);
        if(!TextUtils.isEmpty(videofile)){
            layoutContainer.setVisibility(View.VISIBLE);
            initVideo(ApiImpl.DOMIN+videofile.replaceAll("\\\\","/"));
        }
    }

    private void initVideo(String url) {
        Uri uri = Uri.parse(url);
        vv.setMediaController(new MediaController(this));
        vv.setVideoURI(uri);
        createVideoThumbnail(uri.getPath());
    }

    @Override
    public void onAPIError(String flag, int code, String error) {
        hideProgressDialog();
        CustomToast.showToast(this,error);
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    private void createVideoThumbnail(final String path) {
        Observable<Bitmap> observable = Observable.create(new Observable.OnSubscribe<Bitmap>() {
            @Override
            public void call(Subscriber<? super Bitmap> subscriber) {
                Bitmap bitmap = null;
                MediaMetadataRetriever retriever = new MediaMetadataRetriever();
                int kind = MediaStore.Video.Thumbnails.MINI_KIND;
                if (Build.VERSION.SDK_INT >= 14) {
                    try{
                        retriever.setDataSource(path, new HashMap<String, String>());
                    }catch (Exception e){
                        retriever.setDataSource(path);
                    }
                } else {
                    retriever.setDataSource(path);
                }
                bitmap = retriever.getFrameAtTime(0);
                subscriber.onNext(bitmap);
                retriever.release();
            }
        });
        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.computation())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Action1<Bitmap>() {
                    @Override
                    public void call(Bitmap bitmap) {
                        //设置封面
//                        LogUtils.i("observable bitmap");
                        vv.setBackgroundDrawable(new BitmapDrawable(bitmap));
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
//                        LogUtils.i("observable throwable");
                        vv.setBackgroundResource(0);
                    }
                });
    }
}
