package com.huiyu.tech.zhongxing.widget;

import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.Toast;


import com.huiyu.tech.zhongxing.ui.activity.MainActivity2;


/**
 * Created by Administrator on 2017-07-11.
 */

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class KeepLiveService extends JobService {
    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("111", "onCreate:service " );
        startJobSheduler();
    }
    public void startJobSheduler() {
        try {
            JobInfo.Builder builder = new JobInfo.Builder(1, new ComponentName(getPackageName(), KeepLiveService.class.getName()));
            builder.setPeriodic(5);
            builder.setPersisted(true);
            JobScheduler jobScheduler = (JobScheduler) this.getSystemService(Context.JOB_SCHEDULER_SERVICE);
            jobScheduler.schedule(builder.build());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public boolean onStartJob(JobParameters params) {
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }
}
