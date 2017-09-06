package com.huiyu.tech.zhongxing.ui;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.huiyu.tech.zhongxing.R;
import com.huiyu.tech.zhongxing.utils.LogUtils;
import com.huiyu.tech.zhongxing.widget.DialogUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class BaseFragment extends Fragment {

    protected View view;

    private boolean isFirstResume = true;
    private boolean isFirstVisible = true;
    private Dialog progressDialog;

    public BaseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_base, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        LogUtils.i("base onAttach");
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtils.i("base onResume");
        if (isFirstResume) {
            //如果是第一次resume只需要让程序正常执行即可
            isFirstResume = false;
            return;
        }
        //当不是第一次resume时，fragment重新可见了，那时数据不需要重新加载
        if (getUserVisibleHint()) {
            onUserVisible(false);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        LogUtils.i("base setUserVisibleHint+isVisibleToUser="+isVisibleToUser);
        if(isVisibleToUser){
            setUserVisible();
        }
    }

    private void setUserVisible() {
        LogUtils.i("base setUserVisible");
        if (isFirstVisible) {
            isFirstVisible = false;
            onUserVisible(true);
        } else {
            onUserVisible(false);
        }
    }

    /**
     * fragment可见（切换回来或者onResume）
     */
    public void onUserVisible(boolean firstVisible) {
    }

    /**
     * 设置左上角按键
     * @return
     */
    protected ImageButton showLeftView(int resId, View.OnClickListener listener) {
        ImageButton mIbBack = (ImageButton) view.findViewById(R.id.back);
        if (mIbBack != null) {
            mIbBack.setVisibility(View.VISIBLE);
            mIbBack.setImageResource(resId);
            mIbBack.setOnClickListener(listener);
        }
        return mIbBack;
    }

    /**
     * 设置右侧按键一
     * @return
     */
    protected ImageButton showRightViewOne(int resId, View.OnClickListener listener) {
        ImageButton mIbBack = (ImageButton) view.findViewById(R.id.btn_action_one);
        if (mIbBack != null) {
            mIbBack.setVisibility(View.VISIBLE);
            mIbBack.setImageResource(resId);
            mIbBack.setOnClickListener(listener);
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
        TextView mTvTitle = (TextView) view.findViewById(R.id.title);
        if (mTvTitle != null) {
            mTvTitle.setText(title);
        }
        return mTvTitle;
    }

    /**
     * 设置右侧文字按钮
     * @param str
     * @param listener
     * @return
     */
    protected TextView showTextRightAction(String str, View.OnClickListener listener) {
        TextView mTvAction = (TextView) view.findViewById(R.id.tv_action);
        if (mTvAction != null) {
            mTvAction.setVisibility(View.VISIBLE);
            mTvAction.setText(str);
            mTvAction.setOnClickListener(listener);
        }
        return mTvAction;
    }

    /**
     * 初始等待框
     */
    private void initProgressDialog() {
        progressDialog = DialogUtils.createLoadingDialog(getActivity(), "");
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
        if (progressDialog == null && !isRemoving()) {
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

}
