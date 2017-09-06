package com.huiyu.tech.zhongxing.ui.fragment;


import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.huiyu.tech.zhongxing.Constants;
import com.huiyu.tech.zhongxing.R;
import com.huiyu.tech.zhongxing.api.ApiImpl;
import com.huiyu.tech.zhongxing.api.OnResponseListener;
import com.huiyu.tech.zhongxing.models.NoticeModel;
import com.huiyu.tech.zhongxing.ui.BaseFragment;
import com.huiyu.tech.zhongxing.ui.activity.NoticeDetailActivity;
import com.huiyu.tech.zhongxing.ui.adapter.NoticeAdapter;
import com.huiyu.tech.zhongxing.utils.CustomToast;
import com.huiyu.tech.zhongxing.utils.DataUtils;
import com.huiyu.tech.zhongxing.utils.LogUtils;
import com.huiyu.tech.zhongxing.utils.SharedPrefUtils;
import com.huiyu.tech.zhongxing.utils.TimeRenderUtils;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.huiyu.tech.zhongxing.Constants.SHARE_KEY.USER_ID;

/**
 * A simple {@link Fragment} subclass.
 */
public class NoticeFragment extends BaseFragment implements PullToRefreshBase.OnRefreshListener2, AdapterView.OnItemClickListener, OnResponseListener {

    private PullToRefreshListView lvInfo;
    private LinearLayout startLinear;
    private LinearLayout endLinear;
    private TextView startText;
    private TextView endText;
    private int max_page;
    private int page = 1;
    private static final int REFRESH_SIZE = 20;
    private Context context;
    private List<NoticeModel.ListBean> allItems = new ArrayList<>();
    private NoticeAdapter noticeAdapter;
    private Button searchBtn;
    public NoticeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_notice, container, false);
        initView();
        initData();
        return view;
    }

    private void initView(){
        //showTitleView(getResources().getString(R.string.title_notice));
        lvInfo = (PullToRefreshListView) view.findViewById(R.id.lv_info);
        startLinear = (LinearLayout) view.findViewById(R.id.start_linear);
        endLinear = (LinearLayout) view.findViewById(R.id.end_linear);
        startText = (TextView) view.findViewById(R.id.start_time);
        endText = (TextView) view.findViewById(R.id.end_time);
        searchBtn = (Button) view.findViewById(R.id.search_btn);
        lvInfo.setMode(PullToRefreshBase.Mode.BOTH);
        lvInfo.setPullToRefreshOverScrollEnabled(false);
        lvInfo.setOnRefreshListener(this);
        ILoadingLayout startLabels = lvInfo.getLoadingLayoutProxy();
        startLabels.setPullLabel("加载更多");// 刚下拉时，显示的提示
        startLabels.setRefreshingLabel("正在加载...");// 刷新时
        startLabels.setReleaseLabel("松开载入更多");// 下来达到一定距离时，显示的提示
        noticeAdapter = new NoticeAdapter(getActivity());
        lvInfo.setAdapter(noticeAdapter);
        lvInfo.setOnItemClickListener(this);
        startLinear.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
            @Override
            public void onClick(View v) {
                showDatePickerDialog(startText);
            }
        });
        endLinear.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
            @Override
            public void onClick(View v) {
                showDatePickerDialog(endText);
            }
        });
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeFilter();
            }
        });
    }

    public void timeFilter(){
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
                Toast.makeText(context, "开始时间不能大于结束时间", Toast.LENGTH_SHORT).show();
                return;
            }
            List<NoticeModel.ListBean> newItems = new ArrayList<NoticeModel.ListBean>();
            if(allItems != null && allItems.size() > 0){
                for(NoticeModel.ListBean listBean : allItems){
                    String createDate = listBean.getCreateDate();
                    if(createDate == null){
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
                noticeAdapter.setItems(newItems);
            }
        }

    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
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
        new DatePickerDialog(context,R.style.ThemeDialog,new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String date = year + "-" + (month < 10 ? "0" : "") + (month + 1) + "-" + (dayOfMonth < 10 ? "0" : "") + dayOfMonth;
                result.setText(date);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    @Override
    public void onUserVisible(boolean firstVisible) {

    }

    private void initData(){
        page = 1;
        loadData();
    }

    private void loadData(){
        //showProgressDialog(true);
        ApiImpl.getInstance().getNoticeList(SharedPrefUtils.getString(context,USER_ID,""),""+page,""+REFRESH_SIZE,this);
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
        Intent intent = new Intent(getActivity(),NoticeDetailActivity.class);
        intent.putExtra(Constants.INTENT_KEY.KEY_ID,noticeAdapter.getItem(pos).getId());
        startActivity(intent);
    }

    @Override
    public void onAPISuccess(String flag, JSONObject json) {
        hideProgressDialog();
        lvInfo.onRefreshComplete();
        LogUtils.i("json="+json);
        NoticeModel noticeModel = JSON.parseObject(json.optString("d"), NoticeModel.class);
        max_page = noticeModel.getMax_page();
        if(noticeModel.getList() != null && noticeModel.getList().size() > 0){
            if (page > 1) {
                allItems.addAll(noticeModel.getList());
                //刷新时根据时间段筛选数据，
                timeFilter();
            } else {
                allItems.clear();
                allItems.addAll(noticeModel.getList());
                timeFilter();
            }
        }else{
            noticeAdapter.clearItems();
            CustomToast.showToast(getActivity(),"暂无数据");
        }
    }

    @Override
    public void onAPIError(String flag, int code, String error) {
        lvInfo.onRefreshComplete();
        hideProgressDialog();
//        CustomToast.showToast(getActivity(),error);
    }

}
