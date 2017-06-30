package com.huiyu.tech.zhongxing.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.huiyu.tech.zhongxing.Constants;
import com.huiyu.tech.zhongxing.R;
import com.huiyu.tech.zhongxing.api.ApiImpl;
import com.huiyu.tech.zhongxing.api.OkHttpManager;
import com.huiyu.tech.zhongxing.api.OnResponseListener;
import com.huiyu.tech.zhongxing.models.UserModel;
import com.huiyu.tech.zhongxing.ui.ZZBaseActivity;
import com.huiyu.tech.zhongxing.utils.CommonUtils;
import com.huiyu.tech.zhongxing.utils.CustomToast;
import com.huiyu.tech.zhongxing.utils.SharedPrefUtils;
import com.huiyu.tech.zhongxing.widget.ClearEditText;
import com.tbruyelle.rxpermissions.RxPermissions;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;

import rx.functions.Action1;

public class LoginActivity extends ZZBaseActivity implements View.OnClickListener, OnResponseListener {

    private ClearEditText etAccount;
    private ClearEditText etPwd;
    private TextView tvLogin;
    private RelativeLayout tvFaceLogin;
    private CheckBox checkBox;
    private TextView findPwd;
    private String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.READ_PHONE_STATE};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*//不显示程序的标题栏
        requestWindowFeature( Window.FEATURE_NO_TITLE );

        //不显示系统的标题栏
        getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN );*/

        setContentView(R.layout.activity_login);
        showTitleView(getResources().getString(R.string.text_short_name));
        new RxPermissions(this).request(permissions).subscribe(new Action1<Boolean>() {
            @Override
            public void call(Boolean granted) {
                if (granted) {
                    if (OkHttpManager.getInstance().hasCookie()) {
                        startActivity(new Intent(LoginActivity.this, MainActivity2.class));
                        finish();
                    } else {
                        initView();
                        createUserHeadFile();
                    }
                } else {
                    CustomToast.showToast(LoginActivity.this,"请先打开应用所需的权限");
                    finish();
                }
            }
        });


    }

    private void initView() {

        etAccount = (ClearEditText) findViewById(R.id.et_account);
        etPwd = (ClearEditText) findViewById(R.id.et_pwd);
        tvLogin = (TextView) findViewById(R.id.tv_login);
        tvFaceLogin = (RelativeLayout) findViewById(R.id.tv_face_login);
        checkBox = (CheckBox) findViewById(R.id.activity_login_checkbox);
        findPwd = (TextView) findViewById(R.id.activity_login_find);
        etAccount.setText(SharedPrefUtils.getString(this, Constants.SHARE_KEY.KEY_ACCOUNT, ""));
        checkBox.setChecked(SharedPrefUtils.getBoolean(this,"checked",false));
        etPwd.setText(SharedPrefUtils.getString(this,etAccount.getText().toString(),""));
        tvLogin.setOnClickListener(this);
        tvFaceLogin.setOnClickListener(this);
        findPwd.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_login:
                if (TextUtils.isEmpty(etAccount.getText().toString())) {
                    CustomToast.showToast(this, "您输入的账号不正确");
                    return;
                }
                if (TextUtils.isEmpty(etPwd.getText().toString())) {
                    CustomToast.showToast(this, "您输入的密码不正确");
                    return;
                }
                if(checkBox.isChecked()){
                    SharedPrefUtils.setString(this,etAccount.getText().toString(),etPwd.getText().toString());
                }else{
                    SharedPrefUtils.setString(this,etAccount.getText().toString(),"");
                }
                SharedPrefUtils.setBoolean(this,"checked",checkBox.isChecked());
                showProgressDialog(true);
                String deviceId = null;
                boolean first = SharedPrefUtils.getBoolean(this, Constants.SHARE_KEY.FIRST_LOGIN, true);
                deviceId = CommonUtils.getDeviceId(this);
                ApiImpl.getInstance().doLogin(etAccount.getText().toString(), etPwd.getText().toString(), deviceId, this);
                break;
            case R.id.tv_face_login:
                toFaceLogin();
            case R.id.activity_login_find :
//                startActivity(new Intent(this,MainActivity2.class));
                break;
        }
    }

    @Override
    public void onAPISuccess(String flag, JSONObject json) {
        hideProgressDialog();
        switch (flag) {
            case ApiImpl.DO_LOGIN:
                UserModel userModel = JSON.parseObject(json.optString("d"), UserModel.class);
                SharedPrefUtils.setString(this, Constants.SHARE_KEY.KEY_ACCOUNT, userModel.getUser().getNo());
                SharedPrefUtils.setString(this,Constants.SHARE_KEY.USER_ID,userModel.getUser().getId());
                SharedPrefUtils.setString(this, Constants.SHARE_KEY.TYPE, userModel.getAlarmright());
                SharedPrefUtils.setBoolean(this, Constants.SHARE_KEY.FIRST_LOGIN, false);
//                OkDownloadRequest.Builder builder = new OkDownloadRequest.Builder()
//                        .filePath(Constants.USER_HEAD)
//                        .url(Constants.IMG_HOST + userModel.getUser().getPhoto());
//
//                OkDownloadManager.getInstance(this).enqueue(builder.build(), new OkDownloadEnqueueListener() {
//                    @Override
//                    public void onStart(int id) {
//                        LogUtils.e("===onStart====");
//                    }
//
//                    @Override
//                    public void onProgress(int progress, long cacheSize, long totalSize) {
//
//                    }
//
//                    @Override
//                    public void onRestart() {
//
//                    }
//
//                    @Override
//                    public void onPause() {
//
//                    }
//
//                    @Override
//                    public void onFinish() {
//                        LogUtils.e("===onFinish====");
//                    }
//
//                    @Override
//                    public void onCancel() {
//
//                    }
//
//                    @Override
//                    public void onError(OkDownloadError error) {
//                        LogUtils.e("===error===="+error.toString());
//                    }
//                });

                Intent intent = new Intent(this, MainActivity2.class);
                if(userModel != null){
                    UserModel.UserBean user = userModel.getUser();
                    if(user != null){
                        intent.putExtra("photo",user.getPhone());
                        intent.putExtra("no",user.getNo());
                        intent.putExtra("userName",user.getName());
                    }
                }
                startActivity(intent);
                finish();
                break;
        }
    }

    @Override
    public void onAPIError(String flag, int code, String error) {
        hideProgressDialog();
        CustomToast.showToast(this, error);
    }

    /**
     * 跳转到人脸登录界面
     */
    private void toFaceLogin() {
//        File file = new File(Constants.USER_HEAD);
//        if (!file.exists()){
//            CustomToast.showLongToast(this,"未获取到本地警员人脸信息，请用账号登录！");
//            return;
//        }
        startActivity(new Intent(this, FaceLoginActivity.class));
        finish();
        //                startActivity(new Intent(this,ComparePicActivity.class));
    }

    /**
     * 初始化必备的目录
     */
    private void createUserHeadFile() {
        File file = new File(Constants.PICS);
        if (!file.exists()) {
            file.mkdirs();
        }
        file = new File(Constants.CASH);
        if (!file.exists()) {
            file.mkdirs();
        }
        file = new File(Constants.USER_HEAD);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
