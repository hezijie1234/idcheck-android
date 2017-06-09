package com.huiyu.tech.zhongxing.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.huiyu.tech.zhongxing.Constants;
import com.huiyu.tech.zhongxing.R;
import com.huiyu.tech.zhongxing.api.OnResponseListener;
import com.huiyu.tech.zhongxing.ui.ZZBaseActivity;
import com.huiyu.tech.zhongxing.ui.adapter.CheckInfoAdapter;
import com.huiyu.tech.zhongxing.utils.CustomToast;
import com.huiyu.tech.zhongxing.utils.LogUtils;
import com.huiyu.tech.zhongxing.utils.SharedPrefUtils;
import com.huiyu.tech.zhongxing.widget.ActionItem;
import com.huiyu.tech.zhongxing.widget.TitlePopup;

import org.json.JSONObject;

public class MainActivity extends ZZBaseActivity implements View.OnClickListener, OnResponseListener,PullToRefreshBase.OnRefreshListener2 {

    private ImageButton back;
    private TextView tvName;
    private TextView tvAddr;
    private TextView tvWork;
    private ListView lvInfo;

    private CheckInfoAdapter checkInfoAdapter;

    private boolean isWork;
    private static final int REFRESH = 101;

    private Intent intent;

    private TitlePopup titlePopup;

    private void initTitlePopup() {
        titlePopup = new TitlePopup(this, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        titlePopup.addAction(new ActionItem(this, R.string.text_exit,R.mipmap.zn_12));
        titlePopup.setItemOnClickListener(new TitlePopup.OnItemOnClickListener() {
            @Override
            public void onItemClick(ActionItem item, int position) {
                SharedPrefUtils.setString(MainActivity.this, Constants.SHARE_KEY.KEY_SESSIONID,"");
                finish();
            }
        });
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch(msg.what){
                case REFRESH:
//                    loadData();
                    LogUtils.i("REFRESH");
                    handler.postDelayed(runnable,3000);
                    break;
            }
        }
    };

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            handler.sendEmptyMessage(REFRESH);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initBase();
        initView();
        initData();
    }

    private void initBase(){
        intent = getIntent();
    }

    private void initView(){
        back = (ImageButton) findViewById(R.id.back);
        back.setImageResource(R.mipmap.zn_03);
        back.setVisibility(View.VISIBLE);
        showTitleView(getResources().getString(R.string.text_login_title));
        tvName = (TextView) findViewById(R.id.tv_name);
        tvAddr = (TextView) findViewById(R.id.tv_addr);
        tvWork = (TextView) findViewById(R.id.tv_work);
        lvInfo = (ListView) findViewById(R.id.lv_info);

//        lvInfo.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
//        lvInfo.setPullToRefreshOverScrollEnabled(false);
//        lvInfo.setOnRefreshListener(this);
//        ILoadingLayout startLabels = lvInfo.getLoadingLayoutProxy();
//        startLabels.setPullLabel("加载更多");// 刚下拉时，显示的提示
//        startLabels.setRefreshingLabel("正在加载...");// 刷新时
//        startLabels.setReleaseLabel("松开载入更多");// 下来达到一定距离时，显示的提示
        checkInfoAdapter = new CheckInfoAdapter(this);
        lvInfo.setAdapter(checkInfoAdapter);
        tvWork.setOnClickListener(this);
        back.setOnClickListener(this);

        initTitlePopup();
    }

    private void initData(){
        loadData();
    }

    private void loadData(){
        if(!TextUtils.isEmpty(SharedPrefUtils.getString(this, Constants.SHARE_KEY.KEY_SESSIONID,""))){
//            showProgressDialog(true);
//            ApiImpl.getInstance().getCheckInfo(SharedPrefUtils.getString(this, Constants.SHARE_KEY.KEY_SESSIONID,""),this);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        handler.removeCallbacks(runnable);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.back:
                titlePopup.show(back);
                break;
            case R.id.tv_work:
                if(!isWork){
                    isWork = true;
                    tvWork.setText(getResources().getString(R.string.btn_offwork));
                    tvWork.setBackgroundResource(R.drawable.bg_action_solidyellow);
                    handler.sendEmptyMessage(REFRESH);
                }else{
                    isWork = false;
                    tvWork.setText(getResources().getString(R.string.btn_work));
                    tvWork.setBackgroundResource(R.drawable.bg_action_solidblue);
                    handler.removeCallbacks(runnable);
                }
                break;
        }
    }

    @Override
    public void onAPISuccess(String flag, JSONObject json) {
        hideProgressDialog();
//        List<CheckInfo> checkInfoList = JSON.parseArray(json,CheckInfo.class);
//        LogUtils.i("size="+checkInfoList.size());
//        LogUtils.i("size="+(checkInfoList != null));
//        if(checkInfoList != null && checkInfoList.size() > 0){
//            checkInfoAdapter.setItems(checkInfoList);
//        }
    }

    @Override
    public void onAPIError(String flag, int code, String error) {
        hideProgressDialog();
        CustomToast.showToast(this,error);
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {

    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {

    }
}
