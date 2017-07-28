package com.huiyu.tech.zhongxing.ui.activity;

import android.support.v4.app.FragmentManager;
import android.os.Bundle;

import com.huiyu.tech.zhongxing.R;
import com.huiyu.tech.zhongxing.ui.ZZBaseActivity;
import com.huiyu.tech.zhongxing.ui.fragment.UserFragment;

public class SettingActivity extends ZZBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        showBackView();
        showTitleView("个人设置");
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        UserFragment fragment = new UserFragment();
        supportFragmentManager.beginTransaction().add(R.id.activity_contacts_frame,fragment).commit();
    }
}
