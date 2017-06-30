package com.huiyu.tech.zhongxing.ui.activity;

import android.content.Intent;
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
import com.huiyu.tech.zhongxing.ui.ZZBaseActivity;
import com.huiyu.tech.zhongxing.ui.adapter.CheckInfoAdapter;
import com.huiyu.tech.zhongxing.ui.adapter.HistoryWarnAdapter;
import com.huiyu.tech.zhongxing.utils.CustomToast;
import com.huiyu.tech.zhongxing.utils.LogUtils;

import org.json.JSONObject;

import java.io.Serializable;

public class SearchResultActivity extends ZZBaseActivity implements PullToRefreshBase.OnRefreshListener2, AdapterView.OnItemClickListener, OnResponseListener {

    private PullToRefreshListView lvInfo;

    private HistoryWarnAdapter checkInfoAdapter;

    private int max_page;
    private int page = 1;
    private static final int REFRESH_SIZE = 10;

    private Intent intent;
    private String keyWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        initBase();
        initView();
        initData();
    }

    private void initBase(){
        intent = getIntent();
        if(intent.hasExtra("keyword")){
            keyWord = intent.getStringExtra("keyword");
        }
    }

    private void initView(){
        showBackView();
        showTitleView("搜索结果");

        lvInfo = (PullToRefreshListView) findViewById(R.id.lv_info);

        lvInfo.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
        lvInfo.setPullToRefreshOverScrollEnabled(false);
        lvInfo.setOnRefreshListener(this);
        ILoadingLayout startLabels = lvInfo.getLoadingLayoutProxy();
        startLabels.setPullLabel("加载更多");// 刚下拉时，显示的提示
        startLabels.setRefreshingLabel("正在加载...");// 刷新时
        startLabels.setReleaseLabel("松开载入更多");// 下来达到一定距离时，显示的提示

        checkInfoAdapter = new HistoryWarnAdapter(this);
        lvInfo.setAdapter(checkInfoAdapter);
        lvInfo.setOnItemClickListener(this);
    }

    private void initData(){
        page = 1;
        loadData();
    }

    private void loadData(){
        showProgressDialog(true);
        ApiImpl.getInstance().searchAlarmList(keyWord,""+page,""+REFRESH_SIZE,"1",null,this);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            switch(requestCode){
                case Constants.REQUEST_NEXT:
                    int state = data.getIntExtra("state",0);
                    int index = 0;
                    if(state == 0){
                        index = pos + 1;
                    }else{
                        index = pos - 1;
                    }
                    if(index >= checkInfoAdapter.getCount()){
                        CustomToast.showToast(this,"没有下一条了");
                        index = pos;
                    }
                    if(index < 0){
                        CustomToast.showToast(this,"没有上一条了");
                        index = pos;
                    }
                    Intent intent = new Intent(this,WarningDetailActivity.class);
                    intent.putExtra(Constants.INTENT_KEY.KEY_ID,checkInfoAdapter.getItem(index).getId());
                    intent.putExtra("has_handle",true);
                    startActivityForResult(intent,Constants.REQUEST_NEXT);
                    pos = index;
                    break;
            }
        }
    }

    private int pos;

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        pos = position - lvInfo.getRefreshableView().getHeaderViewsCount();
        Intent intent = new Intent(this,WarningDetailActivity.class);
        intent.putExtra(Constants.INTENT_KEY.POSITION,pos);
        intent.putExtra(Constants.INTENT_KEY.LIST, (Serializable) checkInfoAdapter.getItems());
        intent.putExtra("has_handle",true);
        startActivity(intent);
    }

    @Override
    public void onAPISuccess(String flag, JSONObject json) {
        hideProgressDialog();
        lvInfo.onRefreshComplete();
        LogUtils.i("json="+json);
        CheckInfo checkInfo = JSON.parseObject(json.optString("d"), CheckInfo.class);
        max_page = checkInfo.getMax_page();
        if(checkInfo.getList() != null && checkInfo.getList().size() > 0){
            if (page > 1) {
//                checkInfoAdapter.addItems(checkInfo.getList());
            } else {
//                checkInfoAdapter.setItems(checkInfo.getList());
            }
        }else{
            checkInfoAdapter.clearItems();
            CustomToast.showToast(this,"暂无数据");
        }
    }

    @Override
    public void onAPIError(String flag, int code, String error) {
        hideProgressDialog();
        CustomToast.showToast(this,error);
    }
}
