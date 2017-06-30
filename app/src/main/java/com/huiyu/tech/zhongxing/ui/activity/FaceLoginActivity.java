package com.huiyu.tech.zhongxing.ui.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.huiyu.tech.zhongxing.Constants;
import com.huiyu.tech.zhongxing.R;
import com.huiyu.tech.zhongxing.api.ApiImpl;
import com.huiyu.tech.zhongxing.api.OnResponseListener;
import com.huiyu.tech.zhongxing.models.UserModel;
import com.huiyu.tech.zhongxing.ui.ZZBaseActivity;
import com.huiyu.tech.zhongxing.utils.Base64Util;
import com.huiyu.tech.zhongxing.utils.CommonUtils;
import com.huiyu.tech.zhongxing.utils.CustomToast;
import com.huiyu.tech.zhongxing.utils.ImageUtils;
import com.huiyu.tech.zhongxing.utils.LogUtils;
import com.huiyu.tech.zhongxing.utils.SharedPrefUtils;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;
import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.JavaCameraView;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

public class FaceLoginActivity extends ZZBaseActivity implements CameraBridgeViewBase.CvCameraViewListener2, OnResponseListener {

    private JavaCameraView faceLoginSurfaceView;
    private LinearLayout faceLoginUserLay;
    private ImageView faceLoginImage;
    private TextView faceLoginNameText;
    private TextView faceLoginIdText;
    private TextView faceLoginConfirm;

    private CascadeClassifier mJavaDetector;
    private float mRelativeFaceSize = 0.2f;
    private int mAbsoluteFaceSize = 0;
    private Mat mRgba;
    private static final Scalar FACE_RECT_COLOR = new Scalar(0, 255, 0, 255);
    private int num = 0;
    private Mat headMat;

    private boolean isFinished = false;
    private boolean isRequesting = false;

    private UserModel.UserBean user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_face_login);
        initView();
    }

    private void initView() {
        ImageButton backView = showBackView();
        showTitleView(getResources().getString(R.string.btn_face_login));
        faceLoginIdText = (TextView) findViewById(R.id.tv_no);
        faceLoginNameText = (TextView) findViewById(R.id.tv_person);
        backView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });
        bindView();
        faceLoginSurfaceView.setCvCameraViewListener(this);
        faceLoginSurfaceView.setCameraIndex(CameraBridgeViewBase.CAMERA_ID_FRONT);
//        faceLoginSurfaceView.setCameraIndex(CameraBridgeViewBase.CAMERA_ID_BACK);
//        faceLoginSurfaceView.enableFpsMeter();
        faceLoginConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(user!=null){
                    showProgressDialog();
                    ApiImpl.getInstance().faceLoginConfirm(user.getNo(), FaceLoginActivity.this);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        back();
    }

    private void back() {
        startActivity(new Intent(this,LoginActivity.class));
        finish();
    }

    private void bindView() {
        faceLoginSurfaceView = (JavaCameraView) findViewById(R.id.face_login_surface_view);
        faceLoginUserLay = (LinearLayout) findViewById(R.id.face_login_user_lay);
        faceLoginImage = (ImageView) findViewById(R.id.face_login_image);
        faceLoginConfirm = (TextView) findViewById(R.id.face_login_confirm);
    }


    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS: {
                    LogUtils.i("OpenCV loaded successfully");
                    try {
                        // load cascade file from application resources
                        InputStream is = getResources().openRawResource(R.raw.lbpcascade_frontalface);
                        File cascadeDir = getDir("cascade", Context.MODE_PRIVATE);
                        File mCascadeFile = new File(cascadeDir, "lbpcascade_frontalface.xml");
                        FileOutputStream os = new FileOutputStream(mCascadeFile);

                        byte[] buffer = new byte[4096];
                        int bytesRead;
                        while ((bytesRead = is.read(buffer)) != -1) {
                            os.write(buffer, 0, bytesRead);
                        }
                        is.close();
                        os.close();

                        mJavaDetector = new CascadeClassifier(mCascadeFile.getAbsolutePath());
                        if (mJavaDetector.empty()) {
                            LogUtils.i("Failed to load cascade classifier");
                            mJavaDetector = null;
                        } else {
                            LogUtils.i("Loaded cascade classifier from " + mCascadeFile.getAbsolutePath());
                        }
                        cascadeDir.delete();
                        //getLocalHeadMat();
                    } catch (IOException e) {
                        e.printStackTrace();
                        LogUtils.i("Failed to load cascade. Exception thrown: " + e);
                    }

                    faceLoginSurfaceView.enableView();
                }
                break;
                default: {
                    super.onManagerConnected(status);
                }
                break;
            }
        }
    };


    @Override
    public void onPause() {
        super.onPause();
        if (faceLoginSurfaceView != null) {
            faceLoginSurfaceView.disableView();
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        if (!OpenCVLoader.initDebug()) {
            LogUtils.i("Internal OpenCV library not found. Using OpenCV Manager for initialization");
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_2_0, this, mLoaderCallback);
        } else {
            LogUtils.i("OpenCV library found inside package. Using it!");
            mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }
    }

    public void onDestroy() {
        super.onDestroy();
        if (faceLoginSurfaceView != null) {
            faceLoginSurfaceView.disableView();
        }
    }

    public void onCameraViewStarted(int width, int height) {
        mRgba = new Mat();
    }

    public void onCameraViewStopped() {
        mRgba.release();
    }

    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
        mRgba = inputFrame.rgba();
        //使用前置摄像头时旋转90度
        Mat rotateMat = Imgproc.getRotationMatrix2D(new Point(mRgba.cols() / 2, mRgba.rows() / 2), 90, 1);
        Imgproc.warpAffine(mRgba, mRgba,rotateMat, mRgba.size());
//        Core.transpose(mRgba,mRgba);
//        Core.flip(mRgba,mRgba,-1);

        if (mAbsoluteFaceSize == 0) {
            int height = mRgba.rows();
            if (Math.round(height * mRelativeFaceSize) > 0) {
                mAbsoluteFaceSize = Math.round(height * mRelativeFaceSize);
            }
        }

        MatOfRect faces = new MatOfRect();
        if (mJavaDetector != null) {
            mJavaDetector.detectMultiScale(mRgba, faces, 1.1, 2, 2,
                    new Size(mAbsoluteFaceSize, mAbsoluteFaceSize), new Size());
        }

        Rect[] facesArray = faces.toArray();

        if (facesArray.length > 0 && !isFinished && !isRequesting) {
            num++;
            if (num % Constants.FACE_SUCCESS_COUNT == 0) {
                isRequesting = true;
                Mat mat = getDefaultCompareSize(mRgba.submat(facesArray[0]));
//                compare(mat,headMat);
                Imgproc.cvtColor(mat, mat, Imgproc.COLOR_RGBA2BGR, 3);
                Imgcodecs.imwrite(Constants.CASH_IMG, mat);
                Bitmap bitmap = BitmapFactory.decodeFile(Constants.CASH_IMG);
                bitmap = ImageUtils.compressImage(bitmap);
                //ImageUtils.saveBitmapLocal(bitmap,Constants.CASH_IMG,80);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showProgressDialog();
                    }
                });
                ApiImpl.getInstance().faceLogin(CommonUtils.getDeviceId(this), new Date().getTime() + "", Base64Util.bitmapToBase64(bitmap), this);
            }
            Imgproc.rectangle(mRgba, facesArray[0].tl(), facesArray[0].br(), FACE_RECT_COLOR, 3);
        } else {
            num = 0;
        }
        //画出外框
//        Imgproc.rectangle(mRgba, new Point(0,0), new Point(mRgba.cols(),mRgba.rows()), OUTER_RECT_COLOR, 3);
        return mRgba;

    }

    /**
     * 获取本地警员的头像信息
     */
    private void getLocalHeadMat() {
        MatOfRect faces = new MatOfRect();
        Mat fullMat = Imgcodecs.imread(Constants.USER_HEAD);
        if (mJavaDetector != null) {
            mJavaDetector.detectMultiScale(fullMat, faces, 1.1, 2, 2,
                    new Size(mAbsoluteFaceSize, mAbsoluteFaceSize), new Size());
        }
        Rect[] facesArray = faces.toArray();
        if (facesArray.length > 0) {
            Mat head = fullMat.submat(facesArray[0]);
            headMat = getDefaultCompareSize(head);
            Imgcodecs.imwrite(Constants.CASH + "songwei.jpeg", headMat);
        } else {
            CustomToast.showToast(this, "未获取到本地警员人脸信息，请用账号登录！");
            finish();
        }
    }

    /**
     * 获取相同尺寸的头像
     *
     * @param mat 传入的mat
     * @return Mat
     */
    private Mat getDefaultCompareSize(Mat mat) {
        Mat result = new Mat();
        Size size = new Size(Constants.COMPARE_SIZE, Constants.COMPARE_SIZE);
        Imgproc.resize(mat, result, size);
        return result;
    }

    @Override
    public void onAPISuccess(String flag, JSONObject json) {
        hideProgressDialog();
        if(ApiImpl.FACE_LOGIN_CONFIRM.equals(flag)){
            Intent intent = new Intent(FaceLoginActivity.this, MainActivity2.class);
            if(user != null){
                intent.putExtra("photo",user.getPhoto());
                intent.putExtra("no",user.getNo());
                intent.putExtra("userName",user.getName());
            }
            startActivity(intent);
            finish();
        }else{
            isFinished = true;
            isRequesting = false;
            faceLoginUserLay.setVisibility(View.VISIBLE);
            Log.e("111", "onAPISuccess: "+json );
            UserModel userModel = JSON.parseObject(json.optString("d"), UserModel.class);
            user = userModel.getUser();
            faceLoginNameText.setText("警员:"+user.getName());
            faceLoginIdText.setText("警号:"+user.getNo());
            SharedPrefUtils.setString(this, Constants.SHARE_KEY.KEY_ACCOUNT, user.getNo());
            SharedPrefUtils.setString(this, Constants.SHARE_KEY.TYPE, userModel.getAlarmright());
            SharedPrefUtils.setString(this,Constants.SHARE_KEY.USER_ID,userModel.getUser().getId());
            SharedPrefUtils.setBoolean(this, Constants.SHARE_KEY.FIRST_LOGIN, false);
            showInfo(user);
        }
    }

    private void showInfo(UserModel.UserBean user) {
        ImageUtils.setHeadImage(this, user.getPhoto(), faceLoginImage);
        Picasso.with(this).load(user.getPhoto())
                .placeholder(R.mipmap.id_03)
                .error(R.mipmap.id_03)
                .fit()
                .into(faceLoginImage);
        faceLoginConfirm.setEnabled(true);
    }

    @Override
    public void onAPIError(String flag, int code, String error) {
        hideProgressDialog();
        showWarnDialog(error);
    }

    private Dialog warnDialog;

    public void showWarnDialog(String text) {
        warnDialog = new Dialog(this, R.style.zzDialog);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_recognition_warn_lay, null);
        if (!TextUtils.isEmpty(text)) {
            TextView tvInfo = (TextView) view.findViewById(R.id.tvInfo);
            tvInfo.setText(text);
        }
        warnDialog.setContentView(view);
        warnDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                isRequesting = false;
            }
        });
        if (!warnDialog.isShowing()) {
            warnDialog.show();
        }
    }

}
