package com.huiyu.tech.zhongxing.ui.fragment;


import android.Manifest;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.huiyu.tech.zhongxing.Constants;
import com.huiyu.tech.zhongxing.R;
import com.huiyu.tech.zhongxing.api.ApiImpl;
import com.huiyu.tech.zhongxing.api.OkHttpManager;
import com.huiyu.tech.zhongxing.api.OnResponseListener;
import com.huiyu.tech.zhongxing.api.okdownload.OkDownloadEnqueueListener;
import com.huiyu.tech.zhongxing.api.okdownload.OkDownloadError;
import com.huiyu.tech.zhongxing.models.UserModel;
import com.huiyu.tech.zhongxing.models.VersionModel;
import com.huiyu.tech.zhongxing.ui.BaseFragment;
import com.huiyu.tech.zhongxing.ui.activity.AddSuspectActivity;
import com.huiyu.tech.zhongxing.ui.activity.LoginActivity;
import com.huiyu.tech.zhongxing.ui.activity.ModifyPwdActivity;
import com.huiyu.tech.zhongxing.utils.CommonUtils;
import com.huiyu.tech.zhongxing.utils.CustomToast;
import com.huiyu.tech.zhongxing.utils.FileUtil;
import com.huiyu.tech.zhongxing.utils.LogUtils;
import com.huiyu.tech.zhongxing.utils.MimeUtils;
import com.huiyu.tech.zhongxing.widget.CircleImageView;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;

import me.nereo.multi_image_selector.MultiImageSelector;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserFragment extends BaseFragment implements View.OnClickListener, OnResponseListener {

    private static final int REQUEST_IMAGE = 2;
    private static final int REQUEST_CODE_SYSTEM_CROP = 3;
    private static final int REQUEST_STORAGE_READ_ACCESS_PERMISSION = 101;

    private LinearLayout layoutHead;
    private CircleImageView ivHead;
    private TextView tvName;
    private TextView tvVersion;
    private RelativeLayout layoutModifypwd;
    private RelativeLayout layoutCheckVersion;
    private TextView tvLogout;
    private RelativeLayout addSuspect;

    // 存放图片路径的list
    private ArrayList<String> mSelectPath;
    private Uri avatarUri;


    public UserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_user, container, false);
        showProgressDialog(true);
        ApiImpl.getInstance().getUserInfo(this);
        initView();
        initData();
        return view;
    }

    @Override
    public void onUserVisible(boolean firstVisible) {
        super.onUserVisible(firstVisible);
        if (firstVisible) {

        }
    }

    private void initView() {
        layoutHead = (LinearLayout) view.findViewById(R.id.layout_head);
        ivHead = (CircleImageView) view.findViewById(R.id.iv_head);
        tvName = (TextView) view.findViewById(R.id.tv_name);
        tvVersion = (TextView) view.findViewById(R.id.tv_version);
        layoutModifypwd = (RelativeLayout) view.findViewById(R.id.layout_modifypwd);
        layoutCheckVersion = (RelativeLayout) view.findViewById(R.id.layout_check_version);
        tvLogout = (TextView) view.findViewById(R.id.tv_logout);
        addSuspect = (RelativeLayout) view.findViewById(R.id.fragment_user_addsuspect);
        tvVersion.setText("当前版本" + CommonUtils.getVersionName(getActivity()));
        layoutHead.setOnClickListener(this);
        layoutModifypwd.setOnClickListener(this);
        layoutCheckVersion.setOnClickListener(this);
        tvLogout.setOnClickListener(this);
        addSuspect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddSuspectActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layout_head:
                pickImage();
                break;
            case R.id.layout_modifypwd:
                startActivity(new Intent(getActivity(), ModifyPwdActivity.class));
                break;
            case R.id.layout_check_version:
                showProgressDialog(true);
                ApiImpl.getInstance().checkVersion(this);
                break;
            case R.id.tv_logout:
                new AlertDialog.Builder(getActivity()).setMessage("您确定要退出当前账号吗？").setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        showProgressDialog(true);
                        ApiImpl.getInstance().doLogout(UserFragment.this);
                    }
                }).create().show();
                break;
        }
    }

    private void pickImage() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN // Permission was added in API Level 16
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE,
                    getString(R.string.mis_permission_rationale),
                    REQUEST_STORAGE_READ_ACCESS_PERMISSION);
        } else {
            int maxNum = 9;
            MultiImageSelector selector = MultiImageSelector.create(getActivity());
            selector.showCamera(true);
            selector.count(maxNum);
            selector.single();

            selector.origin(mSelectPath);
            selector.start(this, REQUEST_IMAGE);
        }
    }

    private void requestPermission(final String permission, String rationale, final int requestCode) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), permission)) {
            new AlertDialog.Builder(getActivity())
                    .setTitle(R.string.mis_permission_dialog_title)
                    .setMessage(rationale)
                    .setPositiveButton(R.string.mis_permission_dialog_ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(getActivity(), new String[]{permission}, requestCode);
                        }
                    })
                    .setNegativeButton(R.string.mis_permission_dialog_cancel, null)
                    .create().show();
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{permission}, requestCode);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_STORAGE_READ_ACCESS_PERMISSION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                pickImage();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    /**
     * 进入图片裁剪页面
     *
     * @param filePath 文件路径
     */
    private void startSystemCropActivity(String filePath) {
        try {
            Intent intent = new Intent("com.android.camera.action.CROP");
            //获取文件file的MIME类型
            String type = MimeUtils.getMIMEType(new File(filePath));
            //设置intent的data和Type属性。
            intent.setDataAndType(Uri.fromFile(new File(filePath)), type);
            intent.putExtra("crop", "true");
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            intent.putExtra("outputX", 160);
            intent.putExtra("outputY", 160);
            intent.putExtra("scale", true);
            intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
            intent.putExtra("return-data", false);
            long time = Calendar.getInstance().getTimeInMillis();
            String fileName = Constants.PROJECT_FOLDER_PATH + "head_" + time + ".jpg";
            avatarUri = Uri.fromFile(new File(fileName));
            intent.putExtra(MediaStore.EXTRA_OUTPUT, avatarUri);
            startActivityForResult(intent, REQUEST_CODE_SYSTEM_CROP);
        } catch (ActivityNotFoundException e) {
            LogUtils.i("exception:" + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                mSelectPath = data.getStringArrayListExtra(MultiImageSelector.EXTRA_RESULT);
                StringBuilder sb = new StringBuilder();
                for (String p : mSelectPath) {
                    sb.append(p);
                    sb.append("\n");
                }
                LogUtils.i(sb.toString());
                startSystemCropActivity(sb.toString().trim());
            }
        }
        if (REQUEST_CODE_SYSTEM_CROP == requestCode) {
            if (Activity.RESULT_OK == resultCode) {
                if (avatarUri != null) {
                    String path = FileUtil.getPath(getActivity(), avatarUri);
                    if (path != null) {
                        showProgressDialog(true);
                        ApiImpl.getInstance().updateHead(new File(path), this);
                    }
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onAPISuccess(String flag, JSONObject json) {
        hideProgressDialog();
        switch (flag) {
            case ApiImpl.GET_USER_INFO:
                UserModel.UserBean userModel = JSON.parseObject(json.optString("d"), UserModel.UserBean.class);
                tvName.setText(userModel.getName());
                Picasso.with(getActivity()).load(Constants.IMG_HOST + userModel.getPhoto())
                        .placeholder(R.mipmap.icon_default_head)
                        .error(R.mipmap.icon_default_head)
                        .fit()
                        .into(ivHead);
                break;
            case ApiImpl.UPDATE_HEAD:
                CustomToast.showToast(getActivity(), "上传成功！");
                String head = json.optString("d");
                Picasso.with(getActivity()).load(Constants.IMG_HOST + head)
                        .placeholder(R.mipmap.icon_default_head)
                        .error(R.mipmap.icon_default_head)
                        .fit()
                        .into(ivHead);
                break;
            case ApiImpl.DO_LOGOUT:
                OkHttpManager.getInstance().deleteCookie();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                getActivity().finish();
                break;
            case ApiImpl.CHECK_VERSION:
                VersionModel versionModel = JSON.parseObject(json.optString("d"), VersionModel.class);
                final String verUrl = ApiImpl.APK_DOWNLOAD + versionModel.getFile();
                String verName = versionModel.getVersionName();
                if (versionModel.getVersionCode() > CommonUtils.getVersionCode(getActivity())) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage("有新版本" + verName + "，是否立即更新？");
                    builder.setNegativeButton("取消", null)
                            .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    downloadNewVersion(verUrl);
                                }
                            });
                    builder.show();
                } else {
                    CustomToast.showToast(getActivity(), "当前已是最新版本！");
                }
                break;
        }
    }

    @Override
    public void onAPIError(String flag, int code, String error) {
        hideProgressDialog();
        CustomToast.showToast(getActivity(), error);

    }

    private String newVersionName;

    private void downloadNewVersion(String url) {
        if (TextUtils.isEmpty(url)) {
            CustomToast.showToast(getActivity(), "下载地址错误！");
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
            CustomToast.showToast(getActivity(), "SD卡错误！");
            return;
        }

        final OkHttpManager okHttpManager = OkHttpManager.getInstance();

        okHttpManager.download(url, newVersionName, new OkDownloadEnqueueListener() {

            private int lastProgress;

            @Override
            public void onStart(int id) {
                LogUtils.i("download+id" + id);
                sendNotivication(0);
                handler.sendEmptyMessage(MSG_DOWNLOAD_START);
            }

            @Override
            public void onProgress(int progress, long cacheSize, long totalSize) {
                LogUtils.i("download+progress" + progress);
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
                LogUtils.i("download+100" + 100);
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
        NotificationManager manager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity());
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
//        PendingIntent pendingIntent = PendingIntent.getActivity(getActivity(), 0, installIntent, PendingIntent.FLAG_UPDATE_CURRENT);
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
                    CustomToast.showToast(getActivity(), (String) msg.obj);
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
