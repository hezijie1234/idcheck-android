package com.huiyu.tech.zhongxing.ui.activity;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.huiyu.tech.zhongxing.R;
import com.huiyu.tech.zhongxing.ui.ZZBaseActivity;
import com.huiyu.tech.zhongxing.ui.fragment.ContactsFragment;
import com.huiyu.tech.zhongxing.ui.fragment.HomeFragment;

public class WarningDealActivity extends ZZBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warning_deal);
        showBackView();
        showTitleView("待处理警情");
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        HomeFragment fragment = new HomeFragment();
        supportFragmentManager.beginTransaction().add(R.id.activity_contacts_frame,fragment).commit();
    }
}
