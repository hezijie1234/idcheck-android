package com.huiyu.tech.zhongxing.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.alibaba.fastjson.JSON;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.huiyu.tech.zhongxing.Constants;
import com.huiyu.tech.zhongxing.R;
import com.huiyu.tech.zhongxing.api.ApiImpl;
import com.huiyu.tech.zhongxing.api.OnResponseListener;
import com.huiyu.tech.zhongxing.models.EmergencyNoticeModel;
import com.huiyu.tech.zhongxing.ui.BaseFragment;
import com.huiyu.tech.zhongxing.ui.activity.EmergencyNoticeDetailActivity;
import com.huiyu.tech.zhongxing.ui.adapter.EmergencyNoticeAdapter;
import com.huiyu.tech.zhongxing.utils.CustomToast;
import com.huiyu.tech.zhongxing.utils.LogUtils;

import org.json.JSONObject;

/**
 * Created by Administrator on 2017/3/2.
 */

public class EmergencyNoticeFragment extends BaseFragment implements PullToRefreshBase.OnRefreshListener2, AdapterView.OnItemClickListener, OnResponseListener {

    private PullToRefreshListView lvInfo;

    private EmergencyNoticeAdapter emergencyNoticeAdapter;

    private int max_page;
    private int page = 1;
    private static final int REFRESH_SIZE = 20;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_emergency_notice, container, false);
        initView();
        return view;
    }

    private void initView() {
        lvInfo = (PullToRefreshListView) view.findViewById(R.id.lv_info);
        lvInfo.setMode(PullToRefreshBase.Mode.BOTH);
        lvInfo.setPullToRefreshOverScrollEnabled(false);
        lvInfo.setOnRefreshListener(this);
        ILoadingLayout startLabels = lvInfo.getLoadingLayoutProxy();
        startLabels.setPullLabel("加载更多");// 刚下拉时，显示的提示
        startLabels.setRefreshingLabel("正在加载...");// 刷新时
        startLabels.setReleaseLabel("松开载入更多");// 下来达到一定距离时，显示的提示
        emergencyNoticeAdapter = new EmergencyNoticeAdapter(getActivity());
        lvInfo.setAdapter(emergencyNoticeAdapter);
        lvInfo.setOnItemClickListener(this);
    }

    @Override
    public void onUserVisible(boolean firstVisible) {
        if(firstVisible)initData();
    }

    private void initData(){
        page = 1;
        loadData();
    }

    private void loadData(){
        //showProgressDialog(true);
        ApiImpl.getInstance().getEmergencyNoticeList(""+page,""+REFRESH_SIZE,this);
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        page = 1;
        loadData();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        if(page < max_page){
            page ++;
            loadData();
        }else{
            lvInfo.postDelayed(new Runnable() {
                @Override
                public void run() {
                    lvInfo.onRefreshComplete();
                }
            }, 1000);
            CustomToast.showToast(getActivity(),"没有更多信息了");
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        int pos = position - lvInfo.getRefreshableView().getHeaderViewsCount();
        Intent intent = new Intent(getActivity(),EmergencyNoticeDetailActivity.class);
        intent.putExtra(Constants.INTENT_KEY.KEY_ID,emergencyNoticeAdapter.getItem(pos).getId());
        startActivity(intent);
    }

    @Override
    public void onAPISuccess(String flag, JSONObject json) {
        hideProgressDialog();
        lvInfo.onRefreshComplete();
        LogUtils.i("json="+json);
        EmergencyNoticeModel emergencyNoticeModel = JSON.parseObject(json.optString("d"), EmergencyNoticeModel.class);
        max_page = emergencyNoticeModel.getMax_page();
        if(emergencyNoticeModel.getList() != null && emergencyNoticeModel.getList().size() > 0){
            if (page > 1) {
                emergencyNoticeAdapter.addItems(emergencyNoticeModel.getList());
            } else {
                emergencyNoticeAdapter.setItems(emergencyNoticeModel.getList());
            }
        }else{
            emergencyNoticeAdapter.clearItems();
            CustomToast.showToast(getActivity(),"暂无数据");
        }
    }

    @Override
    public void onAPIError(String flag, int code, String error) {
        lvInfo.onRefreshComplete();
        hideProgressDialog();
        CustomToast.showToast(getActivity(),error);
    }
}
