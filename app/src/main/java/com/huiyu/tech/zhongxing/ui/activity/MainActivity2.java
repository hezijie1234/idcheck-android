package com.huiyu.tech.zhongxing.ui.activity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.huiyu.tech.zhongxing.Constants;
import com.huiyu.tech.zhongxing.R;
import com.huiyu.tech.zhongxing.api.ApiImpl;
import com.huiyu.tech.zhongxing.api.OnResponseListener;
import com.huiyu.tech.zhongxing.models.UserModel;
import com.huiyu.tech.zhongxing.models.WarningNumModel;
import com.huiyu.tech.zhongxing.ui.ZZBaseActivity;
import com.huiyu.tech.zhongxing.utils.CustomToast;
import com.huiyu.tech.zhongxing.utils.DataUtils;
import com.huiyu.tech.zhongxing.utils.SharedPrefUtils;
import com.huiyu.tech.zhongxing.widget.CircleImageView;
import com.huiyu.tech.zhongxing.widget.NotificationClickReceiver;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.Date;

public class MainActivity2 extends ZZBaseActivity implements View.OnClickListener,OnResponseListener {
    private GridLayout gridLayout;
    private RelativeLayout relativeLayout1;
    private RelativeLayout relativeLayout2;
    private RelativeLayout relativeLayout3;
    private RelativeLayout relativeLayout4;
    private RelativeLayout relativeLayout5;
    private RelativeLayout relativeLayout6;
    private RelativeLayout relativeLayout7;
    private RelativeLayout relativeLayout8;
    private RelativeLayout relativeLayout9;
    private CircleImageView imageView;
    private TextView name;
    private TextView no;
    private String date ;
    private Handler handler;
    private TextView mDotNum;
    private LocalBroadcastManager manager;
    private NotificationManager notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        String lastDate = SharedPrefUtils.getString(this, "lastDate", "");
        if(TextUtils.isEmpty(lastDate)){
            date = "";
        }else {
            date = lastDate;
        }
        Log.e("111", "onCreate: "+date );
        manager = LocalBroadcastManager.getInstance(this);
        manager.registerReceiver(receiver,new IntentFilter("picSuccess"));
        initBase();
        initView();
    }

    /**
     * if(user != null){
     intent.putExtra("photo",user.getPhone());
     intent.putExtra("no",user.getNo());
     intent.putExtra("userName",user.getName());
     }
     */
    private void initBase() {
        notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        handler = new Handler();
        showProgressDialog();
        ApiImpl.getInstance().getUserInfo(this);
        ApiImpl.getInstance().getWarningNum(SharedPrefUtils.getString(this,Constants.SHARE_KEY.USER_ID,""),date,this);
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            ApiImpl.getInstance().getUserInfo(MainActivity2.this);
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("111", "onDestroy: MainActivity2结束" );
        manager.unregisterReceiver(receiver);
        if(handler != null){
            handler.removeCallbacks(runnable);
        }
    }

    private void initView() {
        mDotNum = (TextView) findViewById(R.id.item_recent_hotdot);
        relativeLayout1 = (RelativeLayout) findViewById(R.id.relative1);
        relativeLayout2 = (RelativeLayout) findViewById(R.id.relative2);
        relativeLayout3 = (RelativeLayout) findViewById(R.id.relative3);
        relativeLayout4 = (RelativeLayout) findViewById(R.id.relative4);
        relativeLayout5 = (RelativeLayout) findViewById(R.id.relative5);
        relativeLayout6 = (RelativeLayout) findViewById(R.id.relative6);
        relativeLayout7 = (RelativeLayout) findViewById(R.id.relative7);
        relativeLayout8 = (RelativeLayout) findViewById(R.id.relative8);
        relativeLayout9 = (RelativeLayout) findViewById(R.id.relative9);
        imageView = (CircleImageView) findViewById(R.id.head_image);
        name = (TextView) findViewById(R.id.name);
        no = (TextView) findViewById(R.id.no);
        relativeLayout1.setOnClickListener(this);
        relativeLayout2.setOnClickListener(this);
        relativeLayout3.setOnClickListener(this);
        relativeLayout4.setOnClickListener(this);
        relativeLayout5.setOnClickListener(this);
        relativeLayout6.setOnClickListener(this);
        relativeLayout7.setOnClickListener(this);
        relativeLayout8.setOnClickListener(this);
        relativeLayout9.setOnClickListener(this);
        Intent intent = getIntent();
        String photo = intent.getStringExtra("photo");
        String no = intent.getStringExtra("no");
        String userName = intent.getStringExtra("userName");
//        name.setText(userName);
//        this.no.setText(no);
//        Log.e("111", "initView: "+photo );
//        Picasso.with(this).load(Constants.IMG_HOST + photo)
//                .placeholder(R.mipmap.icon_default_head)
//                .error(R.mipmap.icon_default_head)
//                .fit()
//                .into(imageView);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.relative1:
                startActivity(new Intent(MainActivity2.this,FaceRecognitionActivity.class));
                break;
            case R.id.relative2:
                startActivity(new Intent(MainActivity2.this,SuspectScanActivity.class));
                break;
            case R.id.relative3:
                startActivity(new Intent(MainActivity2.this,AddSuspectActivity.class));
                break;
            case R.id.relative4:
                mDotNum.setVisibility(View.GONE);
                notificationManager.cancel(1001);
                startActivity(new Intent(MainActivity2.this,WarningDealActivity.class));
                break;
            case R.id.relative5:
                startActivity(new Intent(MainActivity2.this,HistoryWarningsActivity.class));
                break;
            case R.id.relative6:
                startActivity(new Intent(MainActivity2.this,EmergencyActivity.class));
                break;
            case R.id.relative7:
                startActivity(new Intent(MainActivity2.this,NoticeActivity.class));
                break;
            case R.id.relative8:
                startActivity(new Intent(MainActivity2.this,ContactsActivity.class));
                break;
            case R.id.relative9:
//                startActivity(new Intent(MainActivity2.this,ModifyPwdActivity.class));
                startActivity(new Intent(MainActivity2.this,SettingActivity.class));
                break;
//            case R.id.relative10:
//                break;
//            case R.id.relative11:
//
//                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        String lastDate = SharedPrefUtils.getString(this, "lastDate", "");
        if(TextUtils.isEmpty(lastDate)){
            date = "";
        }else {
            date = lastDate;
        }
        Log.e("111", "onResume: "+date );
        if(SharedPrefUtils.getInt(this, "news", 0) == 0){
            mDotNum.setVisibility(View.GONE);
        }
    }

    @Override
    public void onAPISuccess(String flag, JSONObject json) {
        Gson gons = new Gson();
        hideProgressDialog();
        if(ApiImpl.GET_USER_INFO.equals(flag)){
            UserModel.UserBean userModel = JSON.parseObject(json.optString("d"), UserModel.UserBean.class);
            name.setText(userModel.getName());
            no.setText(userModel.getNo());
            Picasso.with(this).load(userModel.getPhoto())
                    .placeholder(R.mipmap.icon_default_head)
                    .error(R.mipmap.icon_default_head)
                    .fit()
                    .into(imageView);
        }else if(flag.equals(ApiImpl.WARN_NUM)){
            WarningNumModel warningNumModel = gons.fromJson(json.toString(), WarningNumModel.class);
            String lastDate = SharedPrefUtils.getString(this, "lastDate", "");
            if(TextUtils.isEmpty(lastDate)){
                SharedPrefUtils.setString(this,"lastDate",warningNumModel.getD().getTime());
                date = warningNumModel.getD().getTime();
            }else {
                Date last = DataUtils.string2Data(lastDate);
                Date now = DataUtils.string2Data(warningNumModel.getD().getTime());
                if(last != null){
                    if(now.after(last)){
                        SharedPrefUtils.setString(this,"lastDate",warningNumModel.getD().getTime());
                        date = warningNumModel.getD().getTime();
                    }
                }
            }
            int count = warningNumModel.getD().getCount();
            int news = SharedPrefUtils.getInt(this, "news", 0);
            if( count> 0){
                SharedPrefUtils.setInt(this,"news",news + count);
                sendNotivication(count + news);
                mDotNum.setVisibility(View.VISIBLE);
                mDotNum.setText(count + news + "");
            }else if (news > 0){
                mDotNum.setVisibility(View.VISIBLE);
                mDotNum.setText(news + "");
            }else {
                mDotNum.setVisibility(View.GONE);
            }
            Log.e("111", "onAPISuccess:新消息数量 "+warningNumModel.getD().getCount() );
            getData();
        }
    }
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            Log.e("111", "run: 请求网络时的时间" + date );
            ApiImpl.getInstance().getWarningNum(SharedPrefUtils.getString(MainActivity2.this,Constants.SHARE_KEY.USER_ID,""),date,MainActivity2.this);
        }
    };
    public void getData(){
        handler.postDelayed(runnable,5000);
    }

    @Override
    public void onAPIError(String flag, int code, String error) {
        hideProgressDialog();
        getData();
        CustomToast.showToast(this, error);
    }

    private void sendNotivication(int count) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.icon_logo);
        builder.setLargeIcon(bitmap);
        builder.setSmallIcon(android.R.drawable.ic_dialog_info);
        builder.setWhen(System.currentTimeMillis());
        builder.setTicker("（您有"+count+"条新警情）");
        builder.setContentTitle("（您有"+count+"条新警情）");
        builder.setContentText("点击查看");
        builder.setDefaults(Notification.DEFAULT_ALL);
        builder.setAutoCancel(true);
//        Intent clickIntent = new Intent(this, NotificationBroadcastReceiver.class);
//        clickIntent.setAction("notification_clicked");
//        clickIntent.putExtra(NotificationBroadcastReceiver.TYPE, "-1");
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,0,clickIntent,PendingIntent.FLAG_ONE_SHOT);
//
//        Intent intentCancel = new Intent(this, NotificationBroadcastReceiver.class);
//        intentCancel.setAction("notification_cancelled");
//        intentCancel.putExtra(NotificationBroadcastReceiver.TYPE, "-1");
//        PendingIntent pendingIntentCancel = PendingIntent.getBroadcast(this, 0, intentCancel, PendingIntent.FLAG_ONE_SHOT);
        Intent installIntent = new Intent(this, WarningDealActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, installIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
//        builder.setDeleteIntent(pendingIntentCancel);
        int notifyId = 1001;
        notificationManager.notify(notifyId, builder.build());
    }

    public class NotificationBroadcastReceiver extends BroadcastReceiver {

        public static final String TYPE = "type"; //这个type是为了Notification更新信息的，这个不明白的朋友可以去搜搜，很多

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            int type = intent.getIntExtra(TYPE, -1);
            Log.e("111", "onReceive: "+intent.getAction() );
            if (type != -1) {
                NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.cancel(type);
            }

            if (action.equals("notification_clicked")) {
                //处理点击事件
                Log.e("111", "onReceive: 处理点击时间" );
                if(mDotNum != null){
                    mDotNum.setText(0 + "");
                }
                Intent newIntent = new Intent(context, WarningDealActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(newIntent);
            }

            if (action.equals("notification_cancelled")) {
                //处理滑动清除和点击删除事件
                Log.e("111", "onReceive: 处理删除时间" );
            }
        }
    }
}
