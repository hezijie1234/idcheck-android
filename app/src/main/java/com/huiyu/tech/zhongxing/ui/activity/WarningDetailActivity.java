package com.huiyu.tech.zhongxing.ui.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.VideoView;

import com.alibaba.fastjson.JSON;
import com.huiyu.tech.zhongxing.Constants;
import com.huiyu.tech.zhongxing.R;
import com.huiyu.tech.zhongxing.api.ApiImpl;
import com.huiyu.tech.zhongxing.api.OnResponseListener;
import com.huiyu.tech.zhongxing.models.CheckDetailModel;
import com.huiyu.tech.zhongxing.models.CheckInfo;
import com.huiyu.tech.zhongxing.ui.ZZBaseActivity;
import com.huiyu.tech.zhongxing.utils.CustomToast;
import com.huiyu.tech.zhongxing.utils.LogUtils;
import com.huiyu.tech.zhongxing.utils.SharedPrefUtils;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * 警情详情
 */
public class WarningDetailActivity extends ZZBaseActivity implements View.OnClickListener, OnResponseListener {

    private LinearLayout llIdentity;
    private ImageView ivIdcard;
    private ImageView ivRightNow;
    private TextView tvName1;
    private TextView tvType1;
    private TextView tvPos;
    private TextView tvTime1;
    private TextView tvStartPos;
    private TextView tvEndPos;
    private TextView tvRailwayNum;
    private TextView tvSiteNum;
    private LinearLayout layoutTicket1;
    private LinearLayout layoutTicket2;
    private TextView tvStartPos2;
    private TextView tvEndPos2;
    private TextView tvRailwayNum2;
    private TextView tvSiteNum2;
    private TextView tvSexy;
    private TextView tvNation;
    private TextView tvIdcard1;
    private TextView tvAddr1;
    private TextView tvPeriod;
    private RelativeLayout layoutContainer;
    private VideoView videoView;
    private ImageView ivStop;
    private RelativeLayout layout1;
    private RelativeLayout layoutDo;
    private TextView tvDo;
    private RelativeLayout layoutHistory;
    private TextView tvPrevious;
    private TextView tvSuggestion;
    private TextView tvNext;

    /**
     * 身份证信息
     */
    private TextView tvType;
    private TextView tvTime;
    private TextView tvIdcard;
    private TextView tvSuspectType;
    private TextView tvName;
    private TextView tvSex;
    private TextView tvZu;
    private TextView tvYear;
    private TextView tvMonth;
    private TextView tvDay;
    private TextView tvAddr;
    private TextView tvCard;
    private ImageView ivPic;
    private ImageView ivHead;

    private Intent intent;
    private String id;
    private boolean hasHandle;

    private String type;
    private ScrollView scrollView;
    private int position;
    private CheckInfo.ListBean infoBean;
    private List<CheckInfo.ListBean> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warning_detail);

        initBase();
        initView();
        initData();
    }

    private void initBase() {
        type = SharedPrefUtils.getString(this, Constants.SHARE_KEY.TYPE, "0");
        intent = getIntent();
        if (intent.hasExtra("info")) {
            infoBean = (CheckInfo.ListBean) intent.getSerializableExtra("info");
            id = infoBean.getId();
        } else {
            position = intent.getIntExtra(Constants.INTENT_KEY.POSITION, 0);
            list = (List<CheckInfo.ListBean>) intent.getSerializableExtra(Constants.INTENT_KEY.LIST);
            id = list.get(position).getId();
        }
        hasHandle = intent.getBooleanExtra("has_handle", false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void initView() {
        showBackView();
        showTitleView(getResources().getString(R.string.title_emergency_detail));

        scrollView = (ScrollView) findViewById(R.id.scrollView);
        ivIdcard = (ImageView) findViewById(R.id.iv_idcard);
        ivRightNow = (ImageView) findViewById(R.id.iv_right_now);
        tvName1 = (TextView) findViewById(R.id.tv_name1);
        tvType1 = (TextView) findViewById(R.id.tv_type1);
        tvPos = (TextView) findViewById(R.id.tv_pos);
        tvTime1 = (TextView) findViewById(R.id.tv_time1);
        tvStartPos = (TextView) findViewById(R.id.tv_start_pos);
        tvEndPos = (TextView) findViewById(R.id.tv_end_pos);
        tvRailwayNum = (TextView) findViewById(R.id.tv_railway_num);
        tvSiteNum = (TextView) findViewById(R.id.tv_site_num);
        layoutTicket1 = (LinearLayout) findViewById(R.id.layout_ticket1);
        layoutTicket2 = (LinearLayout) findViewById(R.id.layout_ticket2);
        tvStartPos2 = (TextView) findViewById(R.id.tv_start_pos2);
        tvEndPos2 = (TextView) findViewById(R.id.tv_end_pos2);
        tvRailwayNum2 = (TextView) findViewById(R.id.tv_railway_num2);
        tvSiteNum2 = (TextView) findViewById(R.id.tv_site_num2);
        tvSexy = (TextView) findViewById(R.id.tv_sexy);
        tvNation = (TextView) findViewById(R.id.tv_nation);
        tvIdcard1 = (TextView) findViewById(R.id.tv_idcard1);
        tvAddr1 = (TextView) findViewById(R.id.tv_addr1);
        tvPeriod = (TextView) findViewById(R.id.tv_period);
        layoutContainer = (RelativeLayout) findViewById(R.id.layout_container);
        videoView = (VideoView) findViewById(R.id.vv);
        ivStop = (ImageView) findViewById(R.id.iv_stop);
        layout1 = (RelativeLayout) findViewById(R.id.layout1);
        layoutDo = (RelativeLayout) findViewById(R.id.layout_do);
        tvDo = (TextView) findViewById(R.id.tv_do);
        layoutHistory = (RelativeLayout) findViewById(R.id.layout_history);
        tvPrevious = (TextView) findViewById(R.id.tv_previous);
        tvSuggestion = (TextView) findViewById(R.id.tv_suggestion);
        tvNext = (TextView) findViewById(R.id.tv_next);


        llIdentity = (LinearLayout) findViewById(R.id.llIdentity);
        tvType = (TextView) findViewById(R.id.tv_type);
        tvTime = (TextView) findViewById(R.id.tv_time);
        tvIdcard = (TextView) findViewById(R.id.tv_idcard);
        tvSuspectType = (TextView) findViewById(R.id.tvSuspectType);
        tvName = (TextView) findViewById(R.id.tv_name);
        tvSex = (TextView) findViewById(R.id.tv_sex);
        tvZu = (TextView) findViewById(R.id.tv_zu);
        tvYear = (TextView) findViewById(R.id.tv_year);
        tvMonth = (TextView) findViewById(R.id.tv_month);
        tvDay = (TextView) findViewById(R.id.tv_day);
        tvAddr = (TextView) findViewById(R.id.tv_addr);
        tvCard = (TextView) findViewById(R.id.tv_card);
        ivPic = (ImageView) findViewById(R.id.iv_pic);
        ivHead = (ImageView) findViewById(R.id.iv_head);

        layoutTicket2.setVisibility(View.GONE);

        if (hasHandle) {
            layoutDo.setVisibility(View.GONE);
            layoutHistory.setVisibility(View.VISIBLE);
        } else {
            layoutHistory.setVisibility(View.GONE);
            if (type.equals("2")) {
                layoutDo.setVisibility(View.VISIBLE);
            }
        }

        tvPrevious.setOnClickListener(this);
        tvNext.setOnClickListener(this);
        ivStop.setOnClickListener(this);
        tvDo.setOnClickListener(this);
        tvSuggestion.setOnClickListener(this);
    }

    private void initData() {
        showProgressDialog(true);
        ApiImpl.getInstance().getCheckDetail(id, this);
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
                    try {
                        retriever.setDataSource(path, new HashMap<String, String>());
                    } catch (Exception e) {
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
//        observable.observeOn(AndroidSchedulers.mainThread())
//                .subscribeOn(Schedulers.computation())
//                .unsubscribeOn(Schedulers.io())
//                .subscribe(new Action1<Bitmap>() {
//                    @Override
//                    public void call(Bitmap bitmap) {
//                        //设置封面
//                        videoView.setBackgroundDrawable(new BitmapDrawable(bitmap));
//                    }
//                });
        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.computation())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Action1<Bitmap>() {
                    @Override
                    public void call(Bitmap bitmap) {
                        //设置封面
                        LogUtils.i("observable bitmap");
                        videoView.setBackgroundDrawable(new BitmapDrawable(bitmap));
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        LogUtils.i("observable throwable");
                        videoView.setBackgroundResource(0);
                    }
                });
    }

    private void initPlayMedia(String url) {
        Uri uri = Uri.parse(Environment.getExternalStorageDirectory() + "/test.mp4");
        videoView.setMediaController(new MediaController(this));
        videoView.setVideoURI(uri);
        createVideoThumbnail(uri.getPath());
//        videoView.start();
//        videoView.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                videoView.pause();
//            }
//        },500);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case Constants.REQUEST_HANDLER_OVER:
                    setResult(RESULT_OK);
                    finish();
                    break;
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_stop:
                ivStop.setVisibility(View.GONE);
                videoView.setBackgroundResource(0);
                videoView.start();
                break;
            case R.id.tv_do:
                Intent intent = new Intent(this, EmergencyHandleActivity.class);
                intent.putExtra(Constants.INTENT_KEY.KEY_ID, id);
                startActivityForResult(intent, Constants.REQUEST_HANDLER_OVER);
                break;
            case R.id.tv_suggestion:
                Intent intent2 = new Intent(this, HandleResultActivity.class);
                intent2.putExtra(Constants.INTENT_KEY.KEY_ID, id);
                startActivity(intent2);
                break;
            case R.id.tv_next:
//                Intent intentNext = new Intent();
//                intentNext.putExtra("state", 0);
//                setResult(RESULT_OK, intentNext);
//                finish();
                if(position==list.size()-1){
                    CustomToast.showToast(this,"没有下一条了");
                }else{
                    id = list.get(++position).getId();
                    initData();
                }
                break;
            case R.id.tv_previous:
//                Intent intentPrevious = new Intent();
//                intentPrevious.putExtra("state", 1);
//                setResult(RESULT_OK, intentPrevious);
//                finish();
                if(position==0){
                    CustomToast.showToast(this,"没有上一条了");
                }else{
                    id = list.get(--position).getId();
                    initData();
                }
                break;
        }
    }

    @Override
    public void onAPISuccess(String flag, JSONObject json) {
        hideProgressDialog();
        scrollView.setVisibility(View.VISIBLE);
        LogUtils.i("json=" + json);
        CheckDetailModel checkDetailModel = JSON.parseObject(json.optString("d"), CheckDetailModel.class);
        //initPlayMedia(checkDetailModel.getAlarm().);
        id = checkDetailModel.getAlarm().getId();
        if (checkDetailModel.getAlarm().getIdcardImg() != null) {
            Picasso.with(this).load(ApiImpl.DOMIN + checkDetailModel.getAlarm().getIdcardImg().getPath())
                    .placeholder(R.mipmap.id_03)
                    .error(R.mipmap.id_03)
                    .fit()
                    .into(ivIdcard);
        }
        if (checkDetailModel.getAlarm().getFrontImg() != null) {
            Picasso.with(this).load(ApiImpl.DOMIN + checkDetailModel.getAlarm().getFrontImg().getPath())
                    .placeholder(R.mipmap.jz_07)
                    .error(R.mipmap.jz_07)
                    .fit()
                    .into(ivRightNow);
        }
        tvName1.setText(checkDetailModel.getPerson().getUserName());
        tvType1.setText(checkDetailModel.getSuspecttype());
        tvTime1.setText(checkDetailModel.getAlarm().getCheckinTime());
        tvPos.setText(checkDetailModel.getAlarm().getStationName() + " " + checkDetailModel.getAlarm().getPlaceNo() + "号进站口");
        if (checkDetailModel.getTicket() != null && checkDetailModel.getTicket().size() > 0) {
            tvStartPos.setText(checkDetailModel.getTicket().get(0).getStartAddress());
            tvEndPos.setText(checkDetailModel.getTicket().get(0).getEndAddress());
            tvRailwayNum.setText("车次：" + checkDetailModel.getTicket().get(0).getTrainNo());
            tvSiteNum.setText("座位：" + checkDetailModel.getTicket().get(0).getTrainSeat());
            if (checkDetailModel.getTicket().size() > 1) {
                layoutTicket2.setVisibility(View.VISIBLE);
                tvStartPos2.setText(checkDetailModel.getTicket().get(1).getStartAddress());
                tvEndPos2.setText(checkDetailModel.getTicket().get(1).getEndAddress());
                tvRailwayNum2.setText("车次：" + checkDetailModel.getTicket().get(1).getTrainNo());
                tvSiteNum2.setText("座位：" + checkDetailModel.getTicket().get(1).getTrainSeat());
            }
        } else {
            layoutTicket1.setVisibility(View.GONE);
        }
        tvSexy.setText("性别：" + (checkDetailModel.getPerson().getSex().equals("male") ? "男" : "女"));
        tvNation.setText("民族：" + checkDetailModel.getPerson().getNation());
        tvIdcard1.setText("身份证号：" + checkDetailModel.getPerson().getIdcard());
        tvAddr1.setText("住址：" + checkDetailModel.getPerson().getAddress());
        tvPeriod.setText("有效期限：" + checkDetailModel.getPerson().getValidateDate());
    }

    @Override
    public void onAPIError(String flag, int code, String error) {
        hideProgressDialog();
        CustomToast.showToast(this, error);
        if(code==4004){//没有车票信息
            llIdentity.setVisibility(View.VISIBLE);
            showIdentityInfo();
        }
    }

    private void showIdentityInfo() {
        CheckInfo.ListBean model = null ;
        if(infoBean==null){
            model  = list.get(position);
        }else{
            model = infoBean;
        }
        if(model.getIdcardImg() != null){
            Picasso.with(this).load(ApiImpl.DOMIN +model.getIdcardImg().getPath())
                    .placeholder(R.mipmap.id_03)
                    .error(R.mipmap.id_03)
                    .fit()
                    .into(ivHead);
        }else{
            Picasso.with(this).load(ApiImpl.DOMIN )
                    .placeholder(R.mipmap.id_03)
                    .error(R.mipmap.id_03)
                    .fit()
                    .into(ivHead);
        }
        tvType.setText(model.getUserName()+" "+model.getStationName() +" "+model.getPlaceNo()+"进站口");
        tvTime.setText(model.getCreateDate());
        tvIdcard.setText("身份证：" + model.getIdcard());
        tvSuspectType.setText(model.getAlarmType());
        tvName.setText(model.getUserName());
        tvZu.setText(model.getNation());
        if(!TextUtils.isEmpty(model.getIdcard())&&model.getIdcard().length() == 18){
            tvYear.setText(model.getIdcard().substring(6,10));
            tvMonth.setText(model.getIdcard().substring(10,12));
            tvDay.setText(model.getIdcard().substring(12,14));
        }
        tvAddr.setText(model.getAddress());
        tvCard.setText(model.getIdcard());
        tvSex.setText("male".equals(model.getSex()) ? "男" : "女");
//        ImageUtils.setImage(context, model.getAddress(),holder.ivPic,R.mipmap.jz_07);
        if(model.getFrontImg() != null){
            Picasso.with(this).load(ApiImpl.DOMIN +model.getFrontImg().getPath())
                    .placeholder(R.mipmap.jz_07)
                    .error(R.mipmap.jz_07)
                    .fit()
                    .into(ivPic);
        }else{
            Picasso.with(this).load(Constants.IMG_HOST  )
                    .placeholder(R.mipmap.jz_07)
                    .error(R.mipmap.jz_07)
                    .fit()
                    .into(ivPic);
        }
    }
}
