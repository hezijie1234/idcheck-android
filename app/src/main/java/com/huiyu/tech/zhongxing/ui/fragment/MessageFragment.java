package com.huiyu.tech.zhongxing.ui.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.huiyu.tech.zhongxing.R;
import com.huiyu.tech.zhongxing.ui.BaseFragment;
import com.huiyu.tech.zhongxing.ui.adapter.TitlePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/2.
 */

public class MessageFragment extends BaseFragment {

    private   TabLayout tabs;
    private   ViewPager viewPager;

    private TitlePagerAdapter pagerAdapter;
    private List<Fragment> fragments = new ArrayList<>();
    private String[] titles = {"公告消息","紧急布控"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_message, container, false);
        initView();
        return view;
    }

    private void initView() {
        showTitleView(getResources().getString(R.string.text_notice));
        TabLayout tabs = (TabLayout) view.findViewById(R.id.tabs);
        viewPager = (ViewPager) view.findViewById(R.id.viewPager);

        fragments.add(new NoticeFragment());
        fragments.add(new EmergencyNoticeFragment());
        pagerAdapter = new TitlePagerAdapter(getChildFragmentManager(),fragments,titles);
        viewPager.setAdapter(pagerAdapter);
        tabs.setupWithViewPager(viewPager);
    }
}
