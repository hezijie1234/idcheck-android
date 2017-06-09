package com.huiyu.tech.zhongxing.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.alibaba.fastjson.JSON;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.huiyu.tech.zhongxing.Constants;
import com.huiyu.tech.zhongxing.R;
import com.huiyu.tech.zhongxing.api.ApiImpl;
import com.huiyu.tech.zhongxing.api.OnResponseListener;
import com.huiyu.tech.zhongxing.models.CheckInfo;
import com.huiyu.tech.zhongxing.models.EmergencyNoticeModel;
import com.huiyu.tech.zhongxing.ui.ZZBaseActivity;
import com.huiyu.tech.zhongxing.ui.adapter.CheckInfoAdapter;
import com.huiyu.tech.zhongxing.ui.adapter.EmergencyNoticeAdapter;
import com.huiyu.tech.zhongxing.utils.CustomToast;
import com.huiyu.tech.zhongxing.utils.LogUtils;

import org.json.JSONObject;

/**
 * 紧急布控
 */
public class EmergencyNoticeActivity extends ZZBaseActivity implements PullToRefreshBase.OnRefreshListener2, AdapterView.OnItemClickListener, OnResponseListener {

    private PullToRefreshListView lvInfo;

    private EmergencyNoticeAdapter emergencyNoticeAdapter;

    private int max_page;
    private int page = 1;
    private static final int REFRESH_SIZE = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_notice);

        initView();
        initData();
    }

    private void initView() {
        showBackView();
        showTitleView(getResources().getString(R.string.title_emergency_notice));

        lvInfo = (PullToRefreshListView) findViewById(R.id.lv_info);
        lvInfo.setMode(PullToRefreshBase.Mode.BOTH);
        lvInfo.setPullToRefreshOverScrollEnabled(false);
        lvInfo.setOnRefreshListener(this);
        ILoadingLayout startLabels = lvInfo.getLoadingLayoutProxy();
        startLabels.setPullLabel("加载更多");// 刚下拉时，显示的提示
        startLabels.setRefreshingLabel("正在加载...");// 刷新时
        startLabels.setReleaseLabel("松开载入更多");// 下来达到一定距离时，显示的提示
        emergencyNoticeAdapter = new EmergencyNoticeAdapter(this);
        lvInfo.setAdapter(emergencyNoticeAdapter);
        lvInfo.setOnItemClickListener(this);
    }

    private void initData(){
        page = 1;
        loadData();
    }

    private void loadData(){
        showProgressDialog(true);
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
            CustomToast.showToast(this,"没有更多信息了");
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        int pos = position - lvInfo.getRefreshableView().getHeaderViewsCount();
        Intent intent = new Intent(this,EmergencyNoticeDetailActivity.class);
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
            CustomToast.showToast(this,"暂无数据");
        }
    }

    @Override
    public void onAPIError(String flag, int code, String error) {
        hideProgressDialog();
        CustomToast.showToast(this,error);
    }
}
