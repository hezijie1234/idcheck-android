package com.huiyu.tech.zhongxing.ui.activity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
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
        handler = new Handler();
        showProgressDialog();
        ApiImpl.getInstance().getUserInfo(this);
        ApiImpl.getInstance().getWarningNum(SharedPrefUtils.getString(this,Constants.SHARE_KEY.USER_ID,""),date,this);
    }

    private void initView() {

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
            Log.e("111.", "onAPISuccess: "+json );
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
            if( count> 0){
                sendNotivication(count);
            }
            Log.e("111", "onAPISuccess: "+warningNumModel.getD().getCount() );
            getData();
        }
    }

    public void getData(){
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ApiImpl.getInstance().getWarningNum(SharedPrefUtils.getString(MainActivity2.this,Constants.SHARE_KEY.USER_ID,""),date,MainActivity2.this);
                Log.e("111", "run: " + "请求网络" );
            }
        },10000);
    }

    @Override
    public void onAPIError(String flag, int code, String error) {
        hideProgressDialog();
        getData();
        CustomToast.showToast(this, error);
    }

    private void sendNotivication(int count) {
        NotificationManager manager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
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
        Intent installIntent = new Intent(Intent.ACTION_VIEW);
        installIntent.setClass(this, WarningDealActivity.class);
        installIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, installIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        int notifyId = 1001;
        manager.notify(notifyId, builder.build());
    }
}
