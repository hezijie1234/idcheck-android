package com.huiyu.tech.zhongxing.ui.activity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.huiyu.tech.zhongxing.Constants;
import com.huiyu.tech.zhongxing.R;
import com.huiyu.tech.zhongxing.api.ApiImpl;
import com.huiyu.tech.zhongxing.api.OnResponseListener;
import com.huiyu.tech.zhongxing.models.CheckInfo;
import com.huiyu.tech.zhongxing.models.EmergencyNoticeModel;
import com.huiyu.tech.zhongxing.models.NoticeModel;
import com.huiyu.tech.zhongxing.models.WarningDealModel;
import com.huiyu.tech.zhongxing.ui.ZZBaseActivity;
import com.huiyu.tech.zhongxing.ui.adapter.CheckInfoAdapter;
import com.huiyu.tech.zhongxing.ui.adapter.HistoryWarnAdapter;
import com.huiyu.tech.zhongxing.utils.CustomToast;
import com.huiyu.tech.zhongxing.utils.DataUtils;
import com.huiyu.tech.zhongxing.utils.LogUtils;
import com.huiyu.tech.zhongxing.utils.TimeRenderUtils;
import com.huiyu.tech.zhongxing.widget.ClearEditText;

import org.json.JSONObject;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 历史警情
 */
public class HistoryWarningsActivity extends ZZBaseActivity implements PullToRefreshBase.OnRefreshListener2, AdapterView.OnItemClickListener, OnResponseListener {

//    private ClearEditText etSearch;
    private PullToRefreshListView lvInfo;

    private HistoryWarnAdapter checkInfoAdapter;
    private LinearLayout startLinear;
    private LinearLayout endLinear;
    private TextView startText;
    private TextView endText;

    private int max_page;
    private int page = 1;
    private static final int REFRESH_SIZE = 10;
    private Button searchBtn;
    private InputMethodManager inputmanger;
    private List<WarningDealModel.DBean.ListBean> allItems = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_warnings);
        inputmanger = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        initView();
        initData();
        listener();
    }

    private void listener() {
        startLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(startText);
            }
        });
        endLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(endText);
            }
        });
    }

    private void initView(){
        showBackView();
        showTitleView(getResources().getString(R.string.title_history_emergency));
        startLinear = (LinearLayout) findViewById(R.id.start_linear);
        endLinear = (LinearLayout) findViewById(R.id.end_linear);
        startText = (TextView) findViewById(R.id.start_time);
        endText = (TextView) findViewById(R.id.end_time);
        searchBtn = (Button) findViewById(R.id.search_btn);
//        etSearch = (ClearEditText) findViewById(R.id.et_search);
        lvInfo = (PullToRefreshListView) findViewById(R.id.lv_info);
        lvInfo.setMode(PullToRefreshBase.Mode.BOTH);
        lvInfo.setPullToRefreshOverScrollEnabled(false);
        lvInfo.setOnRefreshListener(this);
        ILoadingLayout startLabels = lvInfo.getLoadingLayoutProxy();
        startLabels.setPullLabel("加载更多");// 刚下拉时，显示的提示
        startLabels.setRefreshingLabel("正在加载...");// 刷新时
        startLabels.setReleaseLabel("松开载入更多");// 下来达到一定距离时，显示的提示
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeFilter(1);
            }
        });
//        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
//                if (actionId == EditorInfo.IME_ACTION_SEARCH
//                        ||(keyEvent != null && keyEvent.getKeyCode()== KeyEvent.KEYCODE_ENTER)) {
//                    //do something;
//                    inputmanger.hideSoftInputFromWindow(etSearch.getWindowToken(), 0);
//                    if(TextUtils.isEmpty(etSearch.getText().toString())){
//                        return true;
//                    }
//                    Intent intent = new Intent(HistoryWarningsActivity.this,SearchResultActivity.class);
//                    intent.putExtra("keyword",etSearch.getText().toString());
//                    startActivity(intent);
//                    return true;
//                }
//                return false;
//            }
//        });
        checkInfoAdapter = new HistoryWarnAdapter(this);
        lvInfo.setAdapter(checkInfoAdapter);
        lvInfo.setOnItemClickListener(this);
    }
    public void timeFilter(int type){
        String startStr = startText.getText().toString();
        Log.e("111", "onClick: 开始时间"+startStr );
        String endStr = endText.getText().toString();
        Log.e("111", "onClick:结束时间 "+startStr );
        long now = System.currentTimeMillis();
        String nowTime = TimeRenderUtils.getDate("yyyy-MM-dd HH:mm:ss", now);
        if(startStr.equals("开始时间")){
            startStr = "2017-1-1 00:00:01";
        }else {
            startStr += " 00:00:01";
        }
        if(endStr.equals("结束时间")){
            endStr = nowTime;
        }else {
            endStr += " 23:59:59" ;
        }
        Date date1 = DataUtils.string2Data(startStr);
        Date date2 = DataUtils.string2Data(endStr);
        if(date1 != null && date2 != null){
            if(date1.after(date2)){
                Toast.makeText(this, "开始时间不能大于结束时间", Toast.LENGTH_SHORT).show();
                return;
            }
            List<WarningDealModel.DBean.ListBean> newItems = new ArrayList<>();
            if(allItems != null && allItems.size() > 0){
                for(WarningDealModel.DBean.ListBean listBean : allItems){
                    String createDate = listBean.getCreateDate();
                    //当从网络请求数据时，久
                    if(TextUtils.isEmpty(createDate) && type == 2){
                        newItems.add(listBean);
                        continue;
                    }
                    Date itemDate = DataUtils.string2Data(createDate);
                    if(itemDate == null){
                        continue;
                    }
                    if(itemDate.after(date1) && itemDate.before(date2)){
                        newItems.add(listBean);
                    }
                }
                checkInfoAdapter.setItems(newItems);
            }
        }
    }
    private void initData(){
        page = 1;
        loadData();
    }

    private void loadData(){
        showProgressDialog(true);
        ApiImpl.getInstance().searchAlarmList(null,""+page,""+REFRESH_SIZE,"1",null,this);
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
        Intent intent = new Intent(this,HandleResultActivity.class);
        intent.putExtra(Constants.INTENT_KEY.KEY_ID, (Serializable) checkInfoAdapter.getItems().get(position - 1).getId());
        startActivity(intent);
    }

    @Override
    public void onAPISuccess(String flag, JSONObject json) {
        Gson gson = new Gson();
        Log.e("111", "onAPISuccess: "+json );
        hideProgressDialog();
        lvInfo.onRefreshComplete();
        WarningDealModel checkInfo = gson.fromJson(json.toString(),WarningDealModel.class);
        max_page = checkInfo.getD().getMax_page();
        if(checkInfo.getD().getList() != null && checkInfo.getD().getList().size() > 0){
            if (page > 1) {
                allItems.addAll(checkInfo.getD().getList());
                timeFilter(2);
            } else {
                allItems.clear();
                allItems.addAll(checkInfo.getD().getList());
                timeFilter(2);
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
        new DatePickerDialog(this, R.style.ThemeDialog,new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String date = year + "-" + (month < 10 ? "0" : "") + (month + 1) + "-" + (dayOfMonth < 10 ? "0" : "") + dayOfMonth;
                result.setText(date);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }
}
