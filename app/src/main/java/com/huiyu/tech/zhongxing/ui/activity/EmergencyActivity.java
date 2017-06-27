package com.huiyu.tech.zhongxing.ui.activity;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.huiyu.tech.zhongxing.R;
import com.huiyu.tech.zhongxing.ui.ZZBaseActivity;
import com.huiyu.tech.zhongxing.ui.fragment.ContactsFragment;
import com.huiyu.tech.zhongxing.ui.fragment.EmergencyNoticeFragment;

public class EmergencyActivity extends ZZBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency);
        setContentView(R.layout.activity_contacts);
        showBackView();
        showTitleView("警情下达");
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        EmergencyNoticeFragment fragment = new EmergencyNoticeFragment();
        supportFragmentManager.beginTransaction().add(R.id.activity_contacts_frame,fragment).commit();
    }
}
