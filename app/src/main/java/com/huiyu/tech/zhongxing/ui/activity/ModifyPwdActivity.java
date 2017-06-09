package com.huiyu.tech.zhongxing.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.huiyu.tech.zhongxing.R;
import com.huiyu.tech.zhongxing.api.ApiImpl;
import com.huiyu.tech.zhongxing.api.OnResponseListener;
import com.huiyu.tech.zhongxing.ui.ZZBaseActivity;
import com.huiyu.tech.zhongxing.utils.CustomToast;
import com.huiyu.tech.zhongxing.widget.ClearEditText;

import org.json.JSONObject;

public class ModifyPwdActivity extends ZZBaseActivity implements View.OnClickListener, OnResponseListener {

    private ClearEditText etOldpwd;
    private ClearEditText etNewpwd;
    private ClearEditText etNewpwd2;
    private TextView tvConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_pwd);

        initView();
    }

    private void initView(){
        showBackView();
        showTitleView(getResources().getString(R.string.text_modify_pwd));

        etOldpwd = (ClearEditText) findViewById(R.id.et_oldpwd);
        etNewpwd = (ClearEditText) findViewById(R.id.et_newpwd);
        etNewpwd2 = (ClearEditText) findViewById(R.id.et_newpwd2);
        tvConfirm = (TextView) findViewById(R.id.tv_confirm);

        tvConfirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.tv_confirm:
                if(TextUtils.isEmpty(etOldpwd.getText().toString())){
                    CustomToast.showToast(this,"请输入原密码");
                    etOldpwd.requestFocus();
                    return;
                }
                if(TextUtils.isEmpty(etNewpwd.getText().toString())){
                    CustomToast.showToast(this,"请输入新密码");
                    etNewpwd.requestFocus();
                    return;
                }
                if(TextUtils.isEmpty(etNewpwd2.getText().toString())){
                    CustomToast.showToast(this,"请确认新密码");
                    etNewpwd2.requestFocus();
                    return;
                }
                if(!etNewpwd.getText().toString().equals(etNewpwd2.getText().toString())){
                    CustomToast.showToast(this,"两次密码输入不一致");
                    return;
                }
                showProgressDialog(true);
                ApiImpl.getInstance().modifyPwd(etOldpwd.getText().toString(),etNewpwd.getText().toString(),this);
                break;
        }
    }

    @Override
    public void onAPISuccess(String flag, JSONObject json) {
        hideProgressDialog();
        CustomToast.showToast(this,"密码修改成功");
        finish();
    }

    @Override
    public void onAPIError(String flag, int code, String error) {
        hideProgressDialog();
        CustomToast.showToast(this,error);
    }
}
