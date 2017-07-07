package com.huiyu.tech.zhongxing.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

import com.alibaba.fastjson.JSON;
import com.huiyu.tech.zhongxing.Constants;
import com.huiyu.tech.zhongxing.R;
import com.huiyu.tech.zhongxing.api.ApiImpl;
import com.huiyu.tech.zhongxing.api.OnResponseListener;
import com.huiyu.tech.zhongxing.models.CheckDetailModel;
import com.huiyu.tech.zhongxing.models.NoticeDetailModel;
import com.huiyu.tech.zhongxing.ui.ZZBaseActivity;
import com.huiyu.tech.zhongxing.utils.CustomToast;
import com.huiyu.tech.zhongxing.utils.LogUtils;

import org.json.JSONObject;

/**
 * 公告详情
 */
public class NoticeDetailActivity extends ZZBaseActivity implements OnResponseListener {

    private WebView wv;

    private Intent intent;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_detail);

        initBase();
        initView();
        initData();
    }

    private void initBase() {
        intent = getIntent();
        if (intent.hasExtra(Constants.INTENT_KEY.KEY_ID)) {
            id = intent.getStringExtra(Constants.INTENT_KEY.KEY_ID);
        }
    }

    private void initView() {
        showBackView();
        showTitleView(getResources().getString(R.string.title_detail));

        wv = (WebView) findViewById(R.id.wv);
    }

    private void initData(){
        showProgressDialog(true);
        ApiImpl.getInstance().getNoticeDetail(id,this);
    }

    @Override
    public void onAPISuccess(String flag, JSONObject json) {
        hideProgressDialog();
        LogUtils.i("json="+json);
        NoticeDetailModel noticeDetailModel = JSON.parseObject(json.optString("d"), NoticeDetailModel.class);
        String content =
                "<html><head><meta name=\"viewport\" content=\"target-densitydpi=device-dpi, width=device-width, initial-scale=1, user-scalable=no, minimum-scale=1.0, maximum-scale=1.0\"></head><body>"
                        + noticeDetailModel.getContent().replaceAll("<img","<img style='width:100%;'") + "</body></html>";
        wv.loadDataWithBaseURL(ApiImpl.DOMIN, content, "text/html", "utf-8", null);
    }

    @Override
    public void onAPIError(String flag, int code, String error) {
        hideProgressDialog();
        CustomToast.showToast(this,error);
    }
}
