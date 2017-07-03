package com.huiyu.tech.zhongxing.widget;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.huiyu.tech.zhongxing.ui.activity.WarningDealActivity;

/**
 * Created by Administrator on 2017-07-03.
 */

public class NotificationClickReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent newIntent = new Intent(context, WarningDealActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(newIntent);
    }
}
