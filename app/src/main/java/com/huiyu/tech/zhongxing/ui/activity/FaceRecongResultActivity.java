package com.huiyu.tech.zhongxing.ui.activity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.huiyu.tech.zhongxing.Constants;
import com.huiyu.tech.zhongxing.R;
import com.huiyu.tech.zhongxing.btutil.BTCardReader;
import com.huiyu.tech.zhongxing.models.IdCardModule;
import com.huiyu.tech.zhongxing.ui.ZZBaseActivity;

public class FaceRecongResultActivity extends ZZBaseActivity {
    private TextView resultText;
    private ImageView mCameraImage;
    private ImageView mIdCardImage;
    private TextView mCardName;
    private TextView mSex;
    private TextView mNation;
    private TextView mYearText;
    private TextView mMonthText;
    private TextView mDayText;
    private TextView mAddressText;
    private TextView mId;
    private ImageView mIdCardImageView;
    private Button goToBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_face_recong_result);
        initView();
        initData();
    }

    private void initData() {
        Intent intent = getIntent();
        boolean isSuccess = intent.getBooleanExtra("isSuccess",false);
        IdCardModule info = intent.getParcelableExtra("info");
        Log.e("111", "initData: "+info );
        if(isSuccess){
            resultText.setText("【人证相符】");
            resultText.setTextColor(getResources().getColor(R.color.recon_success));
        }else {
            resultText.setText("【人证不符】");
            resultText.setTextColor(getResources().getColor(R.color.recon_unsuccess));
        }
        if(info != null){
            mIdCardImage.setImageBitmap(info.image);
            mIdCardImageView.setImageBitmap(info.image);
            mCardName.setText(info.name);
            mSex.setText(info.sex);
            mNation.setText(info.nation);
            mYearText.setText(info.year);
            mMonthText.setText(info.month);
            mDayText.setText(info.day);
            mAddressText.setText(info.address);
            mId.setText(info.cardNo);
            mCameraImage.setImageBitmap(BitmapFactory.decodeFile(Constants.CASH_IMG));
        }
    }

    private void initView() {
        showBackView();
        showTitleView("人证核验结果");
        resultText = (TextView) findViewById(R.id.result_tv);
        mCameraImage = (ImageView) findViewById(R.id.camera_image);
        mIdCardImage = (ImageView) findViewById(R.id.idcard_image);
        mCardName = (TextView) findViewById(R.id.face_rec_tv_name);
        mSex = (TextView) findViewById(R.id.face_rec_tv_sex);
        mNation = (TextView) findViewById(R.id.face_rec_tv_nation);
        mYearText = (TextView) findViewById(R.id.face_rec_tv_year);
        mMonthText = (TextView) findViewById(R.id.face_rec_tv_month);
        mDayText = (TextView) findViewById(R.id.face_rec_tv_day);
        mAddressText = (TextView) findViewById(R.id.face_rec_tv_addr);
        mId = (TextView) findViewById(R.id.face_rec_tv_idcard);
        mIdCardImageView = (ImageView) findViewById(R.id.face_rec_iv_head);
        goToBack = (Button) findViewById(R.id.go_back);
        goToBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
