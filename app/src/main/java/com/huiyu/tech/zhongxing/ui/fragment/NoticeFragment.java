package com.huiyu.tech.zhongxing.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.huiyu.tech.zhongxing.models.NoticeModel;
import com.huiyu.tech.zhongxing.ui.BaseFragment;
import com.huiyu.tech.zhongxing.ui.activity.NoticeDetailActivity;
import com.huiyu.tech.zhongxing.ui.adapter.NoticeAdapter;
import com.huiyu.tech.zhongxing.utils.CustomToast;
import com.huiyu.tech.zhongxing.utils.LogUtils;

import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 */
public class NoticeFragment extends BaseFragment implements PullToRefreshBase.OnRefreshListener2, AdapterView.OnItemClickListener, OnResponseListener {

    private PullToRefreshListView lvInfo;

    private int max_page;
    private int page = 1;
    private static final int REFRESH_SIZE = 20;

    private NoticeAdapter noticeAdapter;

    public NoticeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_notice, container, false);
        initView();
        //initData();
        return view;
    }

    private void initView(){
        //showTitleView(getResources().getString(R.string.title_notice));

        lvInfo = (PullToRefreshListView) view.findViewById(R.id.lv_info);
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
        ApiImpl.getInstance().getNoticeList(""+page,""+REFRESH_SIZE,this);
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
                noticeAdapter.addItems(noticeModel.getList());
            } else {
                noticeAdapter.setItems(noticeModel.getList());
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
        CustomToast.showToast(getActivity(),error);
    }

}
