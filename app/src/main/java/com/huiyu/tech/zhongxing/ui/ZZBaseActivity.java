package com.huiyu.tech.zhongxing.ui;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.huiyu.tech.zhongxing.R;
import com.huiyu.tech.zhongxing.widget.DialogUtils;

public class ZZBaseActivity extends AppCompatActivity {

    private Dialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * 初始等待框
     */
    private void initProgressDialog() {
        progressDialog = DialogUtils.createLoadingDialog(this, "");
        progressDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode,
                                 KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    dialog.dismiss();
                    return true;
                }
                return false;
            }
        });
    }



    /**
     * 显示等待框
     */
    public void showProgressDialog() {
        showProgressDialog(false);
    }

    /**
     * 显示等待框
     *
     * @param isModel 是否为模态
     */
    public void showProgressDialog(boolean isModel) {
        if (progressDialog == null && !isFinishing()) {
            this.initProgressDialog();
        }

        if (progressDialog != null && !progressDialog.isShowing()) {
            progressDialog.setCanceledOnTouchOutside(!isModel);
            progressDialog.show();
        }
    }

    /**
     * 隐藏等待框
     */
    public void hideProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    /**
     * 设置返回
     *
     * @return
     */
    protected ImageButton showBackView() {
        ImageButton mIbBack = (ImageButton) findViewById(R.id.back);
        if (mIbBack != null) {
            mIbBack.setVisibility(View.VISIBLE);
            mIbBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
        return mIbBack;
    }

    /**
     * 设置标题
     *
     * @param title
     * @return
     */
    protected TextView showTitleView(String title) {
        TextView mTvTitle = (TextView) findViewById(R.id.title);
        if (mTvTitle != null) {
            mTvTitle.setText(title);
        }
        return mTvTitle;
    }

    /**
     * 设置右侧文字按钮
     *
     * @param str
     * @param listener
     * @return
     */
    protected TextView showTextRightAction(String str, View.OnClickListener listener) {
        TextView mTvAction = (TextView) findViewById(R.id.tv_action);
        if (mTvAction != null) {
            mTvAction.setVisibility(View.VISIBLE);
            mTvAction.setText(str);
            mTvAction.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
            mTvAction.getPaint().setAntiAlias(true);
            mTvAction.setOnClickListener(listener);
        }
        return mTvAction;
    }

    /**
     * 设置右侧按键一
     *
     * @return
     */
    protected ImageButton showRightViewOne(int resId, View.OnClickListener listener) {
        ImageButton mIbBack = (ImageButton) findViewById(R.id.btn_action_one);
        if (mIbBack != null) {
            mIbBack.setVisibility(View.VISIBLE);
            mIbBack.setImageResource(resId);
            mIbBack.setOnClickListener(listener);
        }
        return mIbBack;
    }

    /**
     * 设置右侧按键一
     *
     * @return
     */
    protected ImageButton showRightViewTwo(int resId, View.OnClickListener listener) {
        ImageButton mIbBack = (ImageButton) findViewById(R.id.btn_action_two);
        if (mIbBack != null) {
            mIbBack.setVisibility(View.VISIBLE);
            mIbBack.setImageResource(resId);
            mIbBack.setOnClickListener(listener);
        }
        return mIbBack;
    }
}
