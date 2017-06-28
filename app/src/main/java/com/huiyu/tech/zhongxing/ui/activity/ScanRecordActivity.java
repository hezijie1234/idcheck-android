package com.huiyu.tech.zhongxing.ui.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
import com.huiyu.tech.zhongxing.utils.CustomToast;
import com.huiyu.tech.zhongxing.utils.SharedPrefUtils;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.huiyu.tech.zhongxing.api.ApiImpl.BLACK_LIST;
import static com.huiyu.tech.zhongxing.api.ApiImpl.RECONG_SCAN_RECORD;

public class ScanRecordActivity extends ZZBaseActivity implements OnResponseListener {
    private PullToRefreshListView mListView;
    private int pageNo = 1;
    private List<SuspectRecordModel.DBean> mSusList;
    private List<RecongRecordModel.DBean> mRecongList;
    private RecordAdapter mSusAdapter;
    private int recordType;
    private int max_page;
    private RecongRecordAdapter mRecongAdapter;
    private ArrayList<BlackRecordModel.DBean> mBlackList;
    private BlackRecordAdapter mBlackAdapter;

    private RelativeLayout relSearch;
    private TextView start_time;
    private TextView end_time;
    private Button search;
    private String beginDate;
    private String endDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_record);
        showBackView();
        showRightViewOne(R.mipmap.ic_search, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSearchView();
            }
        });

        Intent intent = getIntent();
        String type = intent.getStringExtra("type");
        if (type.equals("suspect")) {
            //加载扫描记录
            showTitleView("人证扫描记录");
            recordType = 2;
            initView(2);
            initData(2);

        } else if (type.equals("recog")) {
            recordType = 1;
            initView(1);
            //加载核验记录
            showTitleView("人证核验记录");
            initData(1);
        } else if (type.equals("module")) {
            showTitleView("模板采集记录");
            recordType = 3;
            initView(3);
            initData(3);

        }
    }

    private void showSearchView() {
        if (relSearch.getVisibility() == View.VISIBLE) {
            relSearch.setVisibility(View.GONE);
            beginDate = "";
            endDate = "";
        } else {
            relSearch.setVisibility(View.VISIBLE);
            start_time.setText("");
            end_time.setText("");
        }
    }

    private void initView(int i) {
        relSearch = (RelativeLayout) findViewById(R.id.relSearch);
        start_time = (TextView) findViewById(R.id.start_time);
        end_time = (TextView) findViewById(R.id.end_time);
        search = (Button) findViewById(R.id.search);
        mListView = (PullToRefreshListView) findViewById(R.id.lv_info);
        mListView.setMode(PullToRefreshBase.Mode.BOTH);
        mListView.setPullToRefreshOverScrollEnabled(false);
        ILoadingLayout startLabels = mListView.getLoadingLayoutProxy();
        startLabels.setPullLabel("加载更多");// 刚下拉时，显示的提示
        startLabels.setRefreshingLabel("正在加载...");// 刷新时
        startLabels.setReleaseLabel("松开载入更多");// 下来达到一定距离时，显示的提示
        switch (recordType) {
            case 1:
                mRecongList = new ArrayList<>();
                mRecongAdapter = new RecongRecordAdapter(this, mRecongList);
                mListView.setAdapter(mRecongAdapter);
                break;
            case 2:
                Log.e("111", "initView: 设置适配器");
                mSusList = new ArrayList<>();
                mSusAdapter = new RecordAdapter(this, mSusList);
                mListView.setAdapter(mSusAdapter);
                break;
            case 3:
                Log.e("111", "initView: 模板记录空间初始化");
                mBlackList = new ArrayList<>();
                mBlackAdapter = new BlackRecordAdapter(this, mBlackList);
                mListView.setAdapter(mBlackAdapter);
                break;
        }
        mListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                switch (recordType) {
                    case 1:
                        pageNo = 1;
                        loadData(recordType);
                        break;
                    case 2:
                        pageNo = 1;
                        loadData(recordType);
                        break;
                    case 3:
                        pageNo = 1;
                        loadData(recordType);
                        break;
                }
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                pageNo++;
                loadData(recordType);
            }
        });
        start_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(start_time);
            }
        });
        end_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(end_time);
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                beginDate = start_time.getText().toString();
                endDate = end_time.getText().toString();
                if (TextUtils.isEmpty(beginDate)) {
                    CustomToast.showToast(ScanRecordActivity.this, "请输入开始时间");
                    return;
                }
                if (TextUtils.isEmpty(endDate)) {
                    CustomToast.showToast(ScanRecordActivity.this, "请输入结束时间");
                    return;
                }
                if (endDate.compareTo(beginDate) < 0) {
                    CustomToast.showToast(ScanRecordActivity.this, "结束时间不能小于开始时间");
                    return;
                }
                pageNo = 1;
                loadData(recordType);
            }
        });
    }

    private void showDatePickerDialog(final TextView result) {
        Calendar calendar = Calendar.getInstance();
        if (TextUtils.isEmpty(result.getText())) {
            calendar.setTime(new Date());
        } else {
            try {
                calendar.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(result.getText().toString()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String date = year + "-" + (month < 10 ? "0" : "") + (month + 1) + "-" + (dayOfMonth < 10 ? "0" : "") + dayOfMonth;
                result.setText(date);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void initData(int num) {
        switch (num) {
            case 1:
                loadData(1);
                break;
            case 2:

                Log.e("111", "initData: " + SharedPrefUtils.getString(this, Constants.SHARE_KEY.USER_ID, ""));
                loadData(2);
                break;
            case 3:
                loadData(3);
                break;
        }
    }

    @Override
    public void onAPISuccess(String flag, JSONObject json) {
        Log.e("111", "onAPISuccess: " + json);
        mListView.onRefreshComplete();
        Gson gson = new Gson();
        if (flag.equals(RECONG_SCAN_RECORD)) {
            if (json.optInt("c") == 0) {
                if (recordType == 2) {
                    Log.e("111", "onAPISuccess: " + recordType);
                    SuspectRecordModel record = gson.fromJson(json.toString(), SuspectRecordModel.class);
                    Log.e("111", "onAPISuccess: " + record.getD().size());
                    if (pageNo == 1) {
                        mSusList.clear();
                    }
                    if(record.getD()!=null&&record.getD().size()>0){
                        mSusList.addAll(record.getD());
                    }
                    Log.e("111", "onAPISuccess: " + mSusList.size());
                    mSusAdapter.notifyDataSetChanged();
                } else if (recordType == 1) {
                    RecongRecordModel recongRecordModel = gson.fromJson(json.toString(), RecongRecordModel.class);
                    if (pageNo <= 1) {
                        mRecongList.clear();
                    }
                    mRecongList.addAll(recongRecordModel.getD());
                    mRecongAdapter.notifyDataSetChanged();
                }
            }
        } else if (flag.equals(BLACK_LIST)) {
            if (recordType == 3) {
                BlackRecordModel blackRecordModel = gson.fromJson(json.toString(), BlackRecordModel.class);
                if (pageNo <= 1) {
                    mBlackList.clear();
                }
                if(blackRecordModel.getD()!=null&&blackRecordModel.getD().size()>0){
                    mBlackList.addAll(blackRecordModel.getD());
                }
                mBlackAdapter.notifyDataSetChanged();
            }
        }
    }

    public void loadData(int i) {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", SharedPrefUtils.getString(this, Constants.SHARE_KEY.USER_ID, ""));
        params.put("pageNo", pageNo);
        params.put("beginDate", beginDate);
        params.put("endDate", endDate);
        if (i == 1 || i == 2) {
            params.put("type", recordType);
            ApiImpl.getInstance().getRecongRecord(params, this);
        } else if (i == 3) {
            ApiImpl.getInstance().getModelRecord(params, this);
        }
    }

    @Override
    public void onAPIError(String flag, int code, String error) {
        mListView.onRefreshComplete();
        Log.e("111", "onAPIError: 记录获取失败" + code);
    }

}
