package com.huiyu.tech.zhongxing.ui.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.huiyu.tech.zhongxing.Constants;
import com.huiyu.tech.zhongxing.R;
import com.huiyu.tech.zhongxing.api.ApiImpl;
import com.huiyu.tech.zhongxing.api.OnResponseListener;
import com.huiyu.tech.zhongxing.models.BlackRecordModel;
import com.huiyu.tech.zhongxing.models.RecongRecordModel;
import com.huiyu.tech.zhongxing.models.SuspectRecordModel;
import com.huiyu.tech.zhongxing.ui.ZZBaseActivity;
import com.huiyu.tech.zhongxing.ui.adapter.BlackRecordAdapter;
import com.huiyu.tech.zhongxing.ui.adapter.RecongRecordAdapter;
import com.huiyu.tech.zhongxing.ui.adapter.RecordAdapter;
import com.huiyu.tech.zhongxing.utils.LogUtils;
import com.huiyu.tech.zhongxing.utils.SharedPrefUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.huiyu.tech.zhongxing.api.ApiImpl.BLACK_LIST;
import static com.huiyu.tech.zhongxing.api.ApiImpl.RECONG_SCAN_RECORD;

public class ScanRecordActivity extends ZZBaseActivity implements OnResponseListener {
    private PullToRefreshListView mListView;
    private int pageNo = 1;
    private List<SuspectRecordModel.DBean> mSusList;
    private List<RecongRecordModel.DBean> mRecongList;
    private RecordAdapter mSusAdapter;
    private int recordType;
    private int max_page ;
    private RecongRecordAdapter mRecongAdapter;
    private ArrayList<BlackRecordModel.DBean> mBlackList;
    private BlackRecordAdapter mBlackAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_record);
        showBackView();

        Intent intent = getIntent();
        String type = intent.getStringExtra("type");
        if(type.equals("suspect")){
            //加载扫描记录
            showTitleView("人证扫描记录");
            recordType = 2;
            initView(2);
            initData(2);

        }else if (type.equals("recog")){
            recordType = 1;
            initView(1);
            //加载核验记录
            showTitleView("人证核验记录");
            initData(1);
        }else if(type.equals("module")){
            showTitleView("模板采集记录");
            recordType = 3;
            initView(3);
            initData(3);

        }
    }

    private void initView(int i) {
        mListView = (PullToRefreshListView) findViewById(R.id.lv_info);
        mListView.setMode(PullToRefreshBase.Mode.BOTH);
        mListView.setPullToRefreshOverScrollEnabled(false);
        ILoadingLayout startLabels = mListView.getLoadingLayoutProxy();
        startLabels.setPullLabel("加载更多");// 刚下拉时，显示的提示
        startLabels.setRefreshingLabel("正在加载...");// 刷新时
        startLabels.setReleaseLabel("松开载入更多");// 下来达到一定距离时，显示的提示
        switch(recordType){
            case 1 :
                mRecongList = new ArrayList<>();
                mRecongAdapter = new RecongRecordAdapter(this,mRecongList);
                mListView.setAdapter(mRecongAdapter);
                break;
            case 2 :
                Log.e("111", "initView: 设置适配器" );
                mSusList = new ArrayList<>();
                mSusAdapter = new RecordAdapter(this,mSusList);
                mListView.setAdapter(mSusAdapter);
                break;
            case 3 :
                Log.e("111", "initView: 模板记录空间初始化" );
                mBlackList = new ArrayList<>();
                mBlackAdapter = new BlackRecordAdapter(this,mBlackList);
                mListView.setAdapter(mBlackAdapter);
                break;
        }
        mListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                switch (recordType){
                    case 1 :
                        pageNo = 1;
                        loadData(recordType);
                        break;
                    case 2 :
                        pageNo = 1;
                        loadData(recordType);
                        break;
                    case 3 :
                        pageNo = 1;
                        loadData(recordType);
                        break;
                }
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                pageNo ++;
                loadData(recordType);
            }
        });


    }

    private void initData(int num) {
        switch(num){
            case 1 :
                loadData(1);
                break;
            case 2 :

                Log.e("111", "initData: "+SharedPrefUtils.getString(this, Constants.SHARE_KEY.USER_ID,"") );
                loadData(2);
                break;
            case 3 :
                loadData(3);
                break;
        }
    }

    @Override
    public void onAPISuccess(String flag, JSONObject json) {
        Log.e("111", "onAPISuccess: "+json );
        mListView.onRefreshComplete();
        Gson gson = new Gson();
        if(flag.equals(RECONG_SCAN_RECORD)){
            if(json.optInt("c") == 0){
                if(recordType == 2){
                    Log.e("111", "onAPISuccess: "+recordType );
                    SuspectRecordModel record = gson.fromJson(json.toString(), SuspectRecordModel.class);
                    Log.e("111", "onAPISuccess: "+record.getD().size() );
                    if(pageNo > 1){
                        mSusList.addAll(record.getD());
                    }else {
                        mSusList.clear();
                        mSusList.addAll(record.getD());
                    }
                    Log.e("111", "onAPISuccess: "+mSusList.size() );
                    mSusAdapter.notifyDataSetChanged();
                }else if(recordType == 1){
                    RecongRecordModel recongRecordModel = gson.fromJson(json.toString(), RecongRecordModel.class);
                    if(pageNo <= 1){
                        mRecongList.clear();
                    }
                    mRecongList.addAll(recongRecordModel.getD());
                    mRecongAdapter.notifyDataSetChanged();
                }
            }
        }else if (flag.equals(BLACK_LIST)){
            if (recordType == 3){
                BlackRecordModel blackRecordModel = gson.fromJson(json.toString(), BlackRecordModel.class);
                if (pageNo <= 1){
                    mBlackList.clear();
                }
                mBlackList.addAll(blackRecordModel.getD());
                mBlackAdapter.notifyDataSetChanged();
            }
        }
    }

    public void loadData(int i){
        if(i == 2){
            ApiImpl.getInstance().getRecongRecord(SharedPrefUtils.getString(this, Constants.SHARE_KEY.USER_ID,""),pageNo,recordType,this);
        }else if (i == 1){
            ApiImpl.getInstance().getRecongRecord(SharedPrefUtils.getString(this, Constants.SHARE_KEY.USER_ID,""),pageNo,recordType,this);
        }else if (i == 3){
            ApiImpl.getInstance().getModelRecord(SharedPrefUtils.getString(this,Constants.SHARE_KEY.USER_ID,""),pageNo,this);
        }
    }

    @Override
    public void onAPIError(String flag, int code, String error) {
        mListView.onRefreshComplete();
        Log.e("111", "onAPIError: 记录获取失败"+code );
    }

}
