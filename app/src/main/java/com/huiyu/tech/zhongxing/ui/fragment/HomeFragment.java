package com.huiyu.tech.zhongxing.ui.fragment;


import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.huiyu.tech.zhongxing.Constants;
import com.huiyu.tech.zhongxing.R;
import com.huiyu.tech.zhongxing.api.ApiImpl;
import com.huiyu.tech.zhongxing.api.OnResponseListener;
import com.huiyu.tech.zhongxing.models.CheckInfo;
import com.huiyu.tech.zhongxing.ui.BaseFragment;
import com.huiyu.tech.zhongxing.ui.activity.FaceRecognitionActivity;
import com.huiyu.tech.zhongxing.ui.activity.HistoryWarningsActivity;
import com.huiyu.tech.zhongxing.ui.activity.HomeActivity;
import com.huiyu.tech.zhongxing.ui.activity.SuspectScanActivity;
import com.huiyu.tech.zhongxing.ui.activity.WarningDetailActivity;
import com.huiyu.tech.zhongxing.ui.adapter.CheckInfoAdapter;
import com.huiyu.tech.zhongxing.utils.CustomToast;
import com.huiyu.tech.zhongxing.utils.LogUtils;
import com.huiyu.tech.zhongxing.utils.SharedPrefUtils;

import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends BaseFragment implements View.OnClickListener,PullToRefreshBase.OnRefreshListener2, AdapterView.OnItemClickListener, OnResponseListener {

    private LinearLayout layoutFaceRecognize;
    private LinearLayout layoutEmergency;
    private PullToRefreshListView lvInfo;
    private TextView tvNewNum;

    private CheckInfoAdapter checkInfoAdapter;

    private int max_page;
    private int page = 1;
    private static final int REFRESH_SIZE = 10;

    private static final int REFRESH = 101;

    private String serialno;
    private int newCount;

    private String type;

    public HomeFragment() {
        // Required empty public constructor
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch(msg.what){
                case REFRESH:
                    initData();
                    LogUtils.i("REFRESH");
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
    public void onUserVisible(boolean firstVisible) {
        super.onUserVisible(firstVisible);
        if(!firstVisible){
            LogUtils.i("firstVisible22");
//            handler.sendEmptyMessage(REFRESH);
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        LogUtils.i("isVisibleToUserxxx="+isVisibleToUser);
        if(isVisibleToUser){
            handler.sendEmptyMessage(REFRESH);
        }else{
            handler.removeCallbacks(runnable);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        type = SharedPrefUtils.getString(getActivity(), Constants.SHARE_KEY.TYPE, "0");
        initView();
//        initData();
        return view;
    }

    private void initView(){
        showTitleView(getResources().getString(R.string.title_fragment_home));
        showRightViewOne(R.mipmap.zte_23,this);

        layoutFaceRecognize = (LinearLayout) view.findViewById(R.id.layout_face_recognize);
        layoutEmergency = (LinearLayout) view.findViewById(R.id.layout_emergency);
        lvInfo = (PullToRefreshListView) view.findViewById(R.id.lv_info);
        tvNewNum = (TextView) view.findViewById(R.id.tv_new_num);

        tvNewNum.setVisibility(View.INVISIBLE);

        lvInfo.setMode(PullToRefreshBase.Mode.BOTH);
        lvInfo.setPullToRefreshOverScrollEnabled(false);
        lvInfo.setOnRefreshListener(this);
        ILoadingLayout startLabels = lvInfo.getLoadingLayoutProxy();
        startLabels.setPullLabel("加载更多");// 刚下拉时，显示的提示
        startLabels.setRefreshingLabel("正在加载...");// 刷新时
        startLabels.setReleaseLabel("松开载入更多");// 下来达到一定距离时，显示的提示
        checkInfoAdapter = new CheckInfoAdapter(getActivity());
        lvInfo.setAdapter(checkInfoAdapter);

        layoutFaceRecognize.setOnClickListener(this);
        layoutEmergency.setOnClickListener(this);
        lvInfo.setOnItemClickListener(this);
    }

//    @Override
//    public void onStop() {
//        super.onStop();
//        if(!type.equals("0")){
//            handler.removeCallbacks(runnable);
//        }
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        if(!type.equals("0")){
//            handler.sendEmptyMessage(REFRESH);
//        }
//    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(handler != null){
            handler.removeCallbacks(runnable);
        }
    }

    private void initData(){
        page = 1;
        loadData();
    }

    private void loadData(){
        if(!type.equals("0")){
            showProgressDialog(true);
            LogUtils.i("serialno2="+serialno);
            ApiImpl.getInstance().searchAlarmList(null,""+page,""+REFRESH_SIZE,"0",serialno,this);
        }
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btn_action_one:
                startActivity(new Intent(getActivity(),HistoryWarningsActivity.class));
                break;
            case R.id.layout_face_recognize:
                startActivity(new Intent(getActivity(),FaceRecognitionActivity.class));
                break;
            case R.id.layout_emergency:
                //startActivity(new Intent(getActivity(),EmergencyNoticeActivity.class));
                startActivity(new Intent(getActivity(), SuspectScanActivity.class));
                break;
        }
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        handler.removeCallbacks(runnable);
        page = 1;
        loadData();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        handler.removeCallbacks(runnable);
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK){
            switch (requestCode){
                case Constants.REQUEST_REFRESH_LIST:
                    initData();
                    break;
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        int pos = position - lvInfo.getRefreshableView().getHeaderViewsCount();
        Intent intent = new Intent(getActivity(),WarningDetailActivity.class);
        intent.putExtra("info",checkInfoAdapter.getItem(pos));
        intent.putExtra("has_handle",false);
        startActivityForResult(intent,Constants.REQUEST_REFRESH_LIST);
    }

    @Override
    public void onAPISuccess(String flag, JSONObject json) {
        hideProgressDialog();
        handler.postDelayed(runnable,30000);
        lvInfo.onRefreshComplete();
        LogUtils.i("json="+json);
        CheckInfo checkInfo = JSON.parseObject(json.optString("d"), CheckInfo.class);
        max_page = checkInfo.getMax_page();
        if(!TextUtils.isEmpty(checkInfo.getListcount())){
            newCount = Integer.parseInt(checkInfo.getListcount());
            if(newCount > 0){
                tvNewNum.setVisibility(View.VISIBLE);
                tvNewNum.setText("（您有"+newCount+"条新警情）");
                sendNotivication(newCount);
            }else{
                tvNewNum.setVisibility(View.GONE);
            }
        }
        if(checkInfo.getList() != null && checkInfo.getList().size() > 0){
            if (page > 1) {
                checkInfoAdapter.addItems(checkInfo.getList());
            } else {
                checkInfoAdapter.setItems(checkInfo.getList());
            }
            serialno = checkInfoAdapter.getItem(0).getSerialno();
            LogUtils.i("serialno1="+serialno);
        }else{
            checkInfoAdapter.clearItems();
            CustomToast.showToast(getActivity(),"暂无数据");
        }
    }

    @Override
    public void onAPIError(String flag, int code, String error) {
        lvInfo.onRefreshComplete();
        hideProgressDialog();
        handler.postDelayed(runnable,30000);
        CustomToast.showToast(getActivity(),error);
    }

    private void sendNotivication(int count) {
        NotificationManager manager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity());
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.icon_logo);
        builder.setLargeIcon(bitmap);
        builder.setSmallIcon(android.R.drawable.ic_dialog_info);
        builder.setWhen(System.currentTimeMillis());
        builder.setTicker("（您有"+count+"条新警情）");
        builder.setContentTitle("（您有"+count+"条新警情）");
        builder.setContentText("点击查看");
        builder.setDefaults(Notification.DEFAULT_ALL);
        builder.setAutoCancel(true);
        Intent installIntent = new Intent(Intent.ACTION_VIEW);
        installIntent.setClass(getActivity(), HomeActivity.class);
        installIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);

        PendingIntent pendingIntent = PendingIntent.getActivity(getActivity(), 0, installIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);

        int notifyId = 1001;
        manager.notify(notifyId, builder.build());
    }
}
