package com.huiyu.tech.zhongxing.ui.activity;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.alibaba.fastjson.JSON;
import com.huiyu.tech.zhongxing.Constants;
import com.huiyu.tech.zhongxing.R;
import com.huiyu.tech.zhongxing.api.ApiImpl;
import com.huiyu.tech.zhongxing.api.OkHttpManager;
import com.huiyu.tech.zhongxing.api.OnResponseListener;
import com.huiyu.tech.zhongxing.api.okdownload.OkDownloadEnqueueListener;
import com.huiyu.tech.zhongxing.api.okdownload.OkDownloadError;
import com.huiyu.tech.zhongxing.models.VersionModel;
import com.huiyu.tech.zhongxing.ui.ZZBaseActivity;
import com.huiyu.tech.zhongxing.ui.adapter.MainAdapter;
import com.huiyu.tech.zhongxing.ui.fragment.ContactsFragment;
import com.huiyu.tech.zhongxing.ui.fragment.HomeFragment;
import com.huiyu.tech.zhongxing.ui.fragment.MessageFragment;
import com.huiyu.tech.zhongxing.ui.fragment.UserFragment;
import com.huiyu.tech.zhongxing.utils.CommonUtils;
import com.huiyu.tech.zhongxing.utils.CustomToast;
import com.huiyu.tech.zhongxing.utils.FileUtil;
import com.huiyu.tech.zhongxing.utils.SharedPrefUtils;
import com.huiyu.tech.zhongxing.widget.FixedViewPager;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends ZZBaseActivity implements RadioGroup.OnCheckedChangeListener, ViewPager.OnPageChangeListener, OnResponseListener {

    private FixedViewPager pager;
    private RadioGroup rgTab;
    private RadioButton rbHome;
    private RadioButton rbContacts;
    private RadioButton rbNotice;
    private RadioButton rbUser;

    private Fragment homeFragment;
    private Fragment contactsFragment;
    private Fragment messageFragment;
    private Fragment userFragment;

    private FragmentManager mFm;
    private MainAdapter mAdapter;
    private List<Fragment> datas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initView();
        initData();

    }

    private void initView(){
        pager = (FixedViewPager) findViewById(R.id.pager);
        rgTab = (RadioGroup) findViewById(R.id.rg_tab);
        rbHome = (RadioButton) findViewById(R.id.rb_home);
        rbContacts = (RadioButton) findViewById(R.id.rb_contacts);
        rbNotice = (RadioButton) findViewById(R.id.rb_notice);
        rbUser = (RadioButton) findViewById(R.id.rb_user);
    }

    private void initData(){
        mFm = getSupportFragmentManager();
        datas = new ArrayList<Fragment>();
        homeFragment = new HomeFragment();
        contactsFragment = new ContactsFragment();
        messageFragment = new MessageFragment();
        userFragment = new UserFragment();

        datas.add(homeFragment);
        datas.add(contactsFragment);
        datas.add(messageFragment);
        datas.add(userFragment);
        mAdapter = new MainAdapter(mFm, datas);
        rgTab.setOnCheckedChangeListener(this);
        pager.setAdapter(mAdapter);
        pager.addOnPageChangeListener(this);
        pager.setOffscreenPageLimit(datas.size());
        rgTab.check(R.id.rb_home);

        isCheckVersion();
    }

    private void isCheckVersion(){
        ApiImpl.getInstance().checkVersion(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
        switch (checkedId) {
            case R.id.rb_home:
                pager.setCurrentItem(0);
                break;
            case R.id.rb_contacts:
                pager.setCurrentItem(1);
                break;
            case R.id.rb_notice:
                pager.setCurrentItem(2);
                break;
            case R.id.rb_user:
                pager.setCurrentItem(3);
                break;
            default:
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 0:
                rbHome.setChecked(true);
                break;
            case 1:
                rbContacts.setChecked(true);
                break;
            case 2:
                rbNotice.setChecked(true);
                break;
            case 3:
                rbUser.setChecked(true);
                break;
            default:
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onAPISuccess(String flag, JSONObject json) {
        VersionModel versionModel = JSON.parseObject(json.optString("d"), VersionModel.class);
        String verName = versionModel.getVersionName();
        final String verUrl = ApiImpl.HOST+versionModel.getFile();
        final int verCode = versionModel.getVersionCode();
        int check = SharedPrefUtils.getInt(this, Constants.SHARE_KEY.VERSION_CODE,0);
        if (verCode > check && verCode > CommonUtils.getVersionCode(this)) {
            new AlertDialog.Builder(this)
                    .setMessage("有新版本" + verName + "，是否立即更新？取消则当前版本不再提示")
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            SharedPrefUtils.setInt(HomeActivity.this, Constants.SHARE_KEY.VERSION_CODE,verCode);
                        }
                    })
                    .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            downloadNewVersion(verUrl);
                        }
                    }).create().show();
        }
    }

    @Override
    public void onAPIError(String flag, int code, String error) {
        hideProgressDialog();
    }

    private String newVersionName;

    private void downloadNewVersion(String url) {
        if(TextUtils.isEmpty(url)){
            CustomToast.showToast(this, "下载地址错误！");
            return;
        }

//        String path[] = url.split("/");
        if (FileUtil.avaiableSDCard()) {
            File folder = new File(Constants.PROJECT_FOLDER_PATH);
            if (!folder.exists())
                folder.mkdirs();

            String name = url.substring(url.lastIndexOf("/") + 1, url.length());
            newVersionName = Constants.PROJECT_FOLDER_PATH + name;
        } else {
            CustomToast.showToast(this, "SD卡错误！");
            return;
        }

        final OkHttpManager okHttpManager = OkHttpManager.getInstance();

        okHttpManager.download(url, newVersionName, new OkDownloadEnqueueListener() {

            private int lastProgress;

            @Override
            public void onStart(int id) {
                sendNotivication(0);
                handler.sendEmptyMessage(MSG_DOWNLOAD_START);
            }

            @Override
            public void onProgress(int progress, long cacheSize, long totalSize) {
                if (progress != lastProgress) {
                    sendNotivication(progress);
                    lastProgress = progress;
                }
            }

            @Override
            public void onRestart() {

            }

            @Override
            public void onPause() {

            }

            @Override
            public void onFinish() {
                handler.sendEmptyMessage(MSG_DOWNLOAD_STOP);
                sendNotivication(100);
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(OkDownloadError error) {
                Message message = handler.obtainMessage();
                message.what = SHOW_TOAST;
                message.obj = error.getMessage();
                handler.sendMessage(message);
            }
        });
    }

    private void sendNotivication(int prograess) {
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.icon_logo);
        builder.setLargeIcon(bitmap);
        builder.setSmallIcon(android.R.drawable.ic_dialog_info);
        builder.setWhen(System.currentTimeMillis());
        builder.setTicker("安装包下载中……");
        builder.setContentTitle(getString(R.string.text_download_title));
        if (prograess < 100) {
            builder.setContentText(getString(R.string.text_downloading, prograess + "%"));
        } else {
            builder.setContentText(getString(R.string.text_download_over));
            builder.setDefaults(Notification.DEFAULT_ALL);
        }
        builder.setAutoCancel(true);
//        Intent installIntent = new Intent(Intent.ACTION_VIEW);
//        installIntent.setDataAndType(Uri.fromFile(new File(newVersionName))
//                , "application/vnd.android.package-archive");
//
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, installIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//        builder.setContentIntent(pendingIntent);

        int notifyId = 1000;
        manager.notify(notifyId, builder.build());
    }

    private final int MSG_DOWNLOAD_START = 0x1001;
    private final int MSG_DOWNLOAD_STOP = 0x1002;
    private final int SHOW_TOAST = 0x1003;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_DOWNLOAD_START:
//                    showDialog();
                    break;
                case MSG_DOWNLOAD_STOP:
                    installApk();
                    break;
                case SHOW_TOAST:
                    CustomToast.showToast(HomeActivity.this, (String) msg.obj);
                    break;
                default:
                    break;
            }
        }
    };

    private void installApk() {
        Intent installIntent = new Intent(Intent.ACTION_VIEW);
        installIntent.setDataAndType(Uri.fromFile(new File(newVersionName))
                , "application/vnd.android.package-archive");
        startActivity(installIntent);
    }
}
