package com.huiyu.tech.zhongxing.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.huiyu.tech.zhongxing.Constants;
import com.huiyu.tech.zhongxing.R;
import com.huiyu.tech.zhongxing.api.ApiImpl;
import com.huiyu.tech.zhongxing.api.OnResponseListener;
import com.huiyu.tech.zhongxing.models.ImageInfo;
import com.huiyu.tech.zhongxing.models.SuspectInfo;
import com.huiyu.tech.zhongxing.ui.ZZBaseActivity;
import com.huiyu.tech.zhongxing.ui.adapter.SuspectListAdapter;
import com.huiyu.tech.zhongxing.utils.Base64Util;
import com.huiyu.tech.zhongxing.utils.ImageUtils;
import com.huiyu.tech.zhongxing.utils.LogUtils;

import org.json.JSONObject;
import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.JavaCameraView;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SuspectScanActivity extends ZZBaseActivity implements CameraBridgeViewBase.CvCameraViewListener2, OnResponseListener {

    private static final Scalar FACE_RECT_COLOR = new Scalar(0, 255, 0, 255);
    private JavaCameraView cameraView;
    private RecyclerView list;
    private Mat mRgba;
    private int mAbsoluteFaceSize = 0;
    private float mRelativeFaceSize = 0.1f;
    private int num;

    private SuspectListAdapter adapter;
    private List<SuspectInfo> data = new ArrayList<>();

    private boolean isRequesting = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_suspect_scan);
        showBackView();
        showTitleView("嫌犯扫描");
        showTextRightAction("扫描记录", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SuspectScanActivity.this,ScanRecordActivity.class);
                intent.putExtra("type","suspect");
                startActivity(intent);
            }
        });
//        px2dp();
        initView();

    }

    public void px2dp(){
        float density = getResources().getDisplayMetrics().density;
        Log.e("111", "density: " + density );
        Log.e("111", "dp: "+(int)(600/density + 0.5f));
    }

    private void initView() {
        cameraView = (JavaCameraView) findViewById(R.id.face_rec_surface_view);
        list = (RecyclerView) findViewById(R.id.list);
        cameraView.setCvCameraViewListener(this);
        cameraView.setCameraIndex(CameraBridgeViewBase.CAMERA_ID_BACK);
        adapter = new SuspectListAdapter(data);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(adapter);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (cameraView != null) {
            cameraView.disableView();
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
        if (cameraView != null) {
            cameraView.disableView();
        }
    }


    @Override
    public void onCameraViewStarted(int width, int height) {
        mRgba = new Mat();
    }

    @Override
    public void onCameraViewStopped() {
        mRgba.release();
    }

    @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
        mRgba = inputFrame.rgba();
        int face = 0;
        //使用后置摄像头时旋转-90度
        Mat rotateMat = Imgproc.getRotationMatrix2D(new Point(mRgba.cols() / 2, mRgba.rows() / 2), -90, 1);
        Imgproc.warpAffine(mRgba, mRgba, rotateMat, mRgba.size());

        if (mAbsoluteFaceSize == 0) {
            int height = mRgba.rows();
            if (Math.round(height * mRelativeFaceSize) > 0) {
                mAbsoluteFaceSize = Math.round(height * mRelativeFaceSize);
            }
            Log.e("111", "onCameraFrame: " + mAbsoluteFaceSize );
        }
        //需要时可以使用灰度图来检测人脸。
//        Imgproc.cvtColor(mRgba, mRgba, Imgproc.COLOR_RGBA2GRAY, 3);
        MatOfRect faces = new MatOfRect();
        if (mJavaDetector != null) {
            mJavaDetector.detectMultiScale(mRgba, faces, 1.1, 2, 2,
                    new Size(mAbsoluteFaceSize, mAbsoluteFaceSize), new Size());
        }

        Rect[] facesArray = faces.toArray();
//
        if (facesArray.length > 0) {
            num++;
            if (num % Constants.FACE_SUCCESS_COUNT == 0 && !isRequesting) {
                Bitmap bmp = null;
                List<ImageInfo> list = new ArrayList<>();
                for (int i = 0; i < facesArray.length; i++) {
                    try {
                        final Mat mat = getDefaultCompareSize(mRgba.submat(getImageRect(mRgba.size(), facesArray[i])));
//                        LogUtils.i("==截图==");
                        //解决截取图片脸显示为蓝色的问题 http://blog.csdn.net/yang_xian521/article/details/7010475
                        //Imgproc.cvtColor(mat, mat, Imgproc.COLOR_RGBA2BGR, 3);
                        if(facesArray[i].height < 200 && face == 0){
                            face = 1;
                            cameraView.setZoomAdd();
                            cameraView.handleZoom(true,cameraView.mCamera);
                        } 
                        if(facesArray[i].height > 400 && face == 0){
                            face = 1;
                            cameraView.setZoomReduce();
                            cameraView.handleZoom(false,cameraView.mCamera);
                        }
                        bmp = Bitmap.createBitmap(mat.cols(), mat.rows(), Bitmap.Config.ARGB_8888);
                        Utils.matToBitmap(mat, bmp);
//                        String tempFile = FileUtil.getTempFile();
//                        Imgcodecs.imwrite(tempFile,mat);
                        //ZTEUtils.getFaceFeature(mat);
//                        Observable.create(new Observable.OnSubscribe<byte[]>() {
//                            @Override
//                            public void call(Subscriber<? super byte[]> subscriber) {
//                                subscriber.onNext(ZTEUtils.getFaceFeature(mat));
//                            }
//                        }).subscribeOn(Schedulers.computation()).subscribe(new Action1<byte[]>() {
//                            @Override
//                            public void call(byte[] bytes) {
//                                if(bytes!=null){
//                                    Log.i("face","上传特征值");
//                                }
//                            }
//                        });

                        bmp = ImageUtils.compressImage(bmp);
                        ImageInfo info = new ImageInfo();
                        info.imageName = new Date().getTime() + "";
                        info.imageStr = Base64Util.bitmapToBase64(bmp);
                        list.add(info);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                ApiImpl.getInstance().sendFaceScan(JSON.toJSONString(list), this);
                isRequesting = true;
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        showProgressDialog();
//                    }
//                });
            }

            for (int i = 0; i < facesArray.length; i++) {
                Imgproc.rectangle(mRgba, facesArray[i].tl(), facesArray[i].br(), FACE_RECT_COLOR, 3);
            }
        } else {
            num = 0;
        }

        //画出外框
//        Imgproc.rectangle(mRgba, new Point(0,0), new Point(mRgba.cols(),mRgba.rows()), OUTER_RECT_COLOR, 3);
        return mRgba;
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

    /**
     * 根据整张图和人脸尺寸，获取一个修正的人脸图片大小
     *
     * @param big   大图尺寸
     * @param small 人脸图
     * @return 新尺寸
     */
    private Rect getImageRect(Size big, Rect small) {
        Log.e("111", "getImageRect: " + small.width );
        Log.e("111", "getImageRect: " + small.height );
        Point point = small.tl();
        int width = (int) (small.width * 1.5);
        int height = (int) (small.height * 1.5);
        int x = (int) (point.x - small.width * 0.5 / 2);
        int y = (int) (point.y - small.height * 0.5 / 2);
        if (x < 0) {
            x = 0;
        }
        if (y < 0) {
            y = 0;
        }
        if (x + width > big.width) {
            width = (int) big.width - x;
        }
        if (y + height > big.height) {
            height = (int) big.height - y;
        }
        return new Rect(x, y, width, height);
    }

    private CascadeClassifier mJavaDetector;

    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS: {
//                    LogUtils.i("OpenCV loaded successfully");
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
//                            LogUtils.i("Failed to load cascade classifier");
                            mJavaDetector = null;
                        } else {
//                            LogUtils.i("Loaded cascade classifier from " + mCascadeFile.getAbsolutePath());
                        }
                        cascadeDir.delete();
                    } catch (IOException e) {
                        e.printStackTrace();
//                        LogUtils.i("Failed to load cascade. Exception thrown: " + e);
                    }

                    cameraView.enableView();
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
    public void onAPISuccess(String flag, JSONObject json) {
        isRequesting = false;
//        hideProgressDialog();
        if (json != null) {
            LogUtils.i(json.toString());
            if (json.optInt("c") == 0 && !TextUtils.isEmpty(json.optString("d"))) {
                List<SuspectInfo> infos = JSON.parseObject(json.optString("d"), new TypeReference<List<SuspectInfo>>() {
                });
                if (infos.size() > 0) {
                    int size = data.size();
//                    LogUtils.i("size---" + infos.size());
                    data.addAll(infos);
                    adapter.notifyItemRangeChanged(size, infos.size());
                    list.scrollToPosition(data.size() - 1);
                }
            }
        }

    }

    @Override
    public void onAPIError(String flag, int code, String error) {
        isRequesting = false;
//        hideProgressDialog();
    }
}
