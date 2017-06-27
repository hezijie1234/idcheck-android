package com.huiyu.tech.zhongxing.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.RelativeLayout;

import com.huiyu.tech.zhongxing.R;

public class MainActivity2 extends AppCompatActivity implements View.OnClickListener {
    private GridLayout gridLayout;
    private RelativeLayout relativeLayout1;
    private RelativeLayout relativeLayout2;
    private RelativeLayout relativeLayout3;
    private RelativeLayout relativeLayout4;
    private RelativeLayout relativeLayout5;
    private RelativeLayout relativeLayout6;
    private RelativeLayout relativeLayout7;
    private RelativeLayout relativeLayout8;
    private RelativeLayout relativeLayout9;
    private RelativeLayout relativeLayout10;
    private RelativeLayout relativeLayout11;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        initView();
    }

    private void initView() {

        relativeLayout1 = (RelativeLayout) findViewById(R.id.relative1);
        relativeLayout2 = (RelativeLayout) findViewById(R.id.relative2);
        relativeLayout3 = (RelativeLayout) findViewById(R.id.relative3);
        relativeLayout4 = (RelativeLayout) findViewById(R.id.relative4);
        relativeLayout5 = (RelativeLayout) findViewById(R.id.relative5);
        relativeLayout6 = (RelativeLayout) findViewById(R.id.relative6);
        relativeLayout7 = (RelativeLayout) findViewById(R.id.relative7);
        relativeLayout8 = (RelativeLayout) findViewById(R.id.relative8);
        relativeLayout9 = (RelativeLayout) findViewById(R.id.relative9);
        relativeLayout10 = (RelativeLayout) findViewById(R.id.relative10);
        relativeLayout11 = (RelativeLayout) findViewById(R.id.relative11);
        relativeLayout1.setOnClickListener(this);
        relativeLayout2.setOnClickListener(this);
        relativeLayout3.setOnClickListener(this);
        relativeLayout4.setOnClickListener(this);
        relativeLayout5.setOnClickListener(this);
        relativeLayout6.setOnClickListener(this);
        relativeLayout7.setOnClickListener(this);
        relativeLayout8.setOnClickListener(this);
        relativeLayout9.setOnClickListener(this);
        relativeLayout10.setOnClickListener(this);
        relativeLayout11.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.relative1:
                startActivity(new Intent(MainActivity2.this,FaceRecognitionActivity.class));
                break;
            case R.id.relative2:
                startActivity(new Intent(MainActivity2.this,SuspectScanActivity.class));
                break;
            case R.id.relative3:
                startActivity(new Intent(MainActivity2.this,AddSuspectActivity.class));
                break;
            case R.id.relative4:

                break;
            case R.id.relative5:
                startActivity(new Intent(MainActivity2.this,HistoryWarningsActivity.class));
                break;
            case R.id.relative6:
                startActivity(new Intent(MainActivity2.this,EmergencyActivity.class));
                break;
            case R.id.relative7:
                startActivity(new Intent(MainActivity2.this,NoticeActivity.class));
                break;
            case R.id.relative8:
                startActivity(new Intent(MainActivity2.this,ContactsActivity.class));
                break;
            case R.id.relative9:
                startActivity(new Intent(MainActivity2.this,ModifyPwdActivity.class));
                break;
            case R.id.relative10:
                break;
            case R.id.relative11:
                startActivity(new Intent(MainActivity2.this,SettingActivity.class));
                break;
        }
    }
}
