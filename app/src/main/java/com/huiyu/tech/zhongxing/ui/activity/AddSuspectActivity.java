package com.huiyu.tech.zhongxing.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.huiyu.tech.zhongxing.Constants;
import com.huiyu.tech.zhongxing.R;
import com.huiyu.tech.zhongxing.api.ApiImpl;
import com.huiyu.tech.zhongxing.api.LoadingListener;
import com.huiyu.tech.zhongxing.api.OnResponseListener;
import com.huiyu.tech.zhongxing.models.BlackTypeModel;
import com.huiyu.tech.zhongxing.models.CheckInfo;
import com.huiyu.tech.zhongxing.ui.ZZBaseActivity;
import com.huiyu.tech.zhongxing.utils.Base64Util;
import com.huiyu.tech.zhongxing.utils.CustomToast;
import com.huiyu.tech.zhongxing.utils.ImageUtils;

import org.json.JSONObject;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.imgproc.Imgproc;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AddSuspectActivity extends ZZBaseActivity implements OnResponseListener {

//    private Button mButton;
//    private ImageView imageView;
//    private EditText nameEdit;
//    private EditText idEdit;
//    private Button mSenndBtn;
//    private File imageFile;
//    private RadioGroup radioGroup;
    private  List<BlackTypeModel.DBean> list;
    private List<String> mTypeList;
    private String value;
    private String imageString = "";
    private List<String> mImageString;

    private ImageView firstImage;
    private ImageView secondImage;
    private EditText mNameEdit;
    private EditText mIdCardEdit;
    private Spinner spinner;
    private TextView mSendText;
    private String firstImageString;
    private String secondImageString;
    private ArrayAdapter<String> adapter;
    private ImageView mSpinnerImage;
    private LinearLayout mSpinnerLinear;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_suspect);
        initData();
        initView();
        listener();
    }

    private void initData() {
        mImageString = new ArrayList<>();
        list = new ArrayList<>();
        mTypeList = new ArrayList<>();
        mTypeList.add("请选择模板类型");
        //获取黑名单类型
        showProgressDialog();
        ApiImpl.getInstance().getBlackType(this);
    }

    private void listener() {
        firstImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Mat rotateMat = Imgproc.getRotationMatrix2D(new Point(), -90, 1);
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,1);
            }
        });
        secondImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,2);
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mSpinnerImage.setImageResource(R.mipmap.addsuspect_2);
                if(list != null && list.size() > 0){
                    if(position > 0){
                        BlackTypeModel.DBean dBean = list.get(position - 1);
                        value = dBean.getValue();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mSpinnerLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(spinner.isShown()){
                    mSpinnerImage.setImageResource(R.mipmap.addsuspect_1);
                }else {
                    mSpinnerImage.setImageResource(R.mipmap.addsuspect_2);
                }
            }
        });


        mSendText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!TextUtils.isEmpty(firstImageString) && !TextUtils.isEmpty(secondImageString)){
                        imageString = firstImageString +"|" + secondImageString;
                    }
                if(TextUtils.isEmpty(imageString)){
                    Toast.makeText(AddSuspectActivity.this, "图片不可为空", Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(mNameEdit.getText().toString())){
                    Toast.makeText(AddSuspectActivity.this, "名称不能为空", Toast.LENGTH_SHORT).show();
                }else  if(TextUtils.isEmpty(value)){
                    Toast.makeText(AddSuspectActivity.this, "请选择嫌犯类型", Toast.LENGTH_SHORT).show();
                }else {
                    showProgressDialog();
                    ApiImpl.getInstance().templatePicSend(mIdCardEdit.getText().toString(),imageString,mNameEdit.getText().toString(),value,AddSuspectActivity.this);
                    mImageString.clear();
                }
            }
        });
//        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                BlackTypeModel.DBean dBean = list.get(checkedId - 1);
//                value = dBean.getValue();
//            }
//        });
    }

    private void initView() {
        showBackView();
        showTitleView("模板采集");
        showTextRightAction("采集记录", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddSuspectActivity.this,ScanRecordActivity.class);
                intent.putExtra("type","module");
                startActivity(intent);
            }
        });
        firstImage = (ImageView) findViewById(R.id.camera_image);
        secondImage = (ImageView) findViewById(R.id.idcard_image);
        mNameEdit = (EditText) findViewById(R.id.name_edit);
        mIdCardEdit = (EditText) findViewById(R.id.idCard_edit);
        spinner = (Spinner) findViewById(R.id.spinner);
        mSpinnerLinear = (LinearLayout) findViewById(R.id.spinner_linear);
        mSendText = (TextView) findViewById(R.id.send);
        mSpinnerImage = (ImageView) findViewById(R.id.spinner_image);
//        spinner.setDropDownVerticalOffset(70);
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, mTypeList);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 1:
                /**
                 * 通过这种方法取出的拍摄会默认压缩，因为如果相机的像素比较高拍摄出来的图会比较高清，
                 * 如果图太大会造成内存溢出（OOM），因此此种方法会默认给图片进行压缩
                 */
                if(data != null) {
                    Bundle bundle = data.getExtras();
                    Bitmap bitmap = (Bitmap) bundle.get("data");
                    firstImageString = Base64Util.bitmapToBase64(bitmap);
                    firstImage.setImageBitmap(bitmap);
                }
                break;
            case 2:
                if(data != null) {
                    Bundle bundle = data.getExtras();
                    Bitmap bitmap = (Bitmap) bundle.get("data");
                    secondImageString = Base64Util.bitmapToBase64(bitmap);
                    secondImage.setImageBitmap(bitmap);
                }
                break;
        }
    }


    @Override
    public void onAPISuccess(String flag, JSONObject json) {
        hideProgressDialog();
        if(flag.equals(ApiImpl.TEMPLATE_UPLOAD)){
            firstImageString = "";
            secondImageString = "";
            firstImage.setImageResource(R.mipmap.muban);
            secondImage.setImageResource(R.mipmap.muban);
            mNameEdit.setText("");
            mIdCardEdit.setText("");
            imageString = "";
            if(json.optInt("c") == 0){
                Toast.makeText(this, "上传成功", Toast.LENGTH_SHORT).show();
            }
        }
        if(flag.equals(ApiImpl.BLACK_TYPE)){
            Gson gson = new Gson();
            if(json.optInt("c") == 0){
                BlackTypeModel blackTypeModel = gson.fromJson(json.toString(), BlackTypeModel.class);
                list =  blackTypeModel.getD();
                if(null != list && list.size() > 0){

                    for (int i = 0; i < list.size(); i++) {
//                        RadioButton radioButton = new RadioButton(this);
//                        radioButton.setText(list.get(i).getLabel());
//                        radioButton.setPadding(80,0,0,0);
//                        radioGroup.addView(radioButton, LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                        mTypeList.add(list.get(i).getLabel());
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        }
    }

    @Override
    public void onAPIError(String flag, int code, String error) {
        hideProgressDialog();
        CustomToast.showToast(this,error);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(hideSoftByEditViewIds() == null || hideSoftByEditViewIds().length == 0){
            return super.dispatchTouchEvent(event);
        }
        View view = getCurrentFocus();
        if(isFocusEditText(view,hideSoftByEditViewIds())){
            hideInputForce(this);
        }
        return super.onTouchEvent(event);
    }
    public int[] hideSoftByEditViewIds(){
        return new int[]{R.id.name_edit,R.id.idCard_edit};
    }
    public boolean isFocusEditText(View view,int[] ids){
        for (int i = 0; i < ids.length; i++) {
            if(view.getId() == ids[i]){
                return true;
            }
        }
        return false;
    }
    public void hideInputForce(Activity activity){
        if(null == activity || activity.getCurrentFocus() == null){
            return;
        }
        ((InputMethodManager)activity.getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
