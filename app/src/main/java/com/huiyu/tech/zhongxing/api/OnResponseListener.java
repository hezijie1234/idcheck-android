package com.huiyu.tech.zhongxing.api;

import org.json.JSONObject;

/**
 * Created by ml on 2016/9/2.
 */
public interface OnResponseListener {
    void onAPISuccess(String flag, JSONObject json);

    void onAPIError(String flag, int code, String error);

}
