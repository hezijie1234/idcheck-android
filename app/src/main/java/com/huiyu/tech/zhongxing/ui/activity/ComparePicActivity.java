package com.huiyu.tech.zhongxing.ui.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.huiyu.tech.zhongxing.R;
import com.huiyu.tech.zhongxing.ui.ZZBaseActivity;
import com.huiyu.tech.zhongxing.utils.LogUtils;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfFloat;
import org.opencv.core.MatOfInt;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.util.Arrays;

/**
 * =============================================================
 * Copyright (c) 2015-2017 huiyu, Inc.
 * All rights reserved.
 * <p>
 * 文件名：     ComparePicActivity
 * 作者：       songwei
 * 创建日期：   2017-02-21
 * 版本：       V1.0
 * *============================================================
 */

public class ComparePicActivity extends ZZBaseActivity implements View.OnClickListener{
    public static final String TAG = "OpenCv_compare";
    private Bitmap mBitmap1,mBitmap2;
    private ImageView mIv_ImageView1,mIv_ImageView2;
    private Button mBtn_compare;
    private BaseLoaderCallback callback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            super.onManagerConnected(status);
            switch (status) {
                case BaseLoaderCallback.SUCCESS:

                    break;

                default:
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare_lay
        );
        init();
    }

    public void init(){
        mBitmap1 = BitmapFactory.decodeResource(getResources(), R.mipmap.b);
        mBitmap2 = BitmapFactory.decodeResource(getResources(), R.mipmap.c);
        mIv_ImageView1 = (ImageView)findViewById(R.id.iv_img1);
        mIv_ImageView2 = (ImageView)findViewById(R.id.iv_img2);
        mBtn_compare = (Button)findViewById(R.id.btn_compare);
        mIv_ImageView1.setImageBitmap(mBitmap1);
        mIv_ImageView2.setImageBitmap(mBitmap2);
        mBtn_compare.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Mat mat1 = new Mat();
        Mat mat2 = new Mat();
        Mat mat11 = new Mat();
        Mat mat22 = new Mat();
        Utils.bitmapToMat(mBitmap1, mat1);
        Utils.bitmapToMat(mBitmap2, mat2);
        Imgproc.cvtColor(mat1, mat11, Imgproc.COLOR_BGR2GRAY);
        Imgproc.cvtColor(mat2, mat22, Imgproc.COLOR_BGR2GRAY);
        comPareHist(mat11, mat22);
    }

    private Mat                  mMat0;
    private MatOfInt             mChannels[];
    private MatOfInt             mHistSize;
    private int                  mHistSizeNum = 25;
    private MatOfFloat           mRanges;
    private Scalar               mColorsRGB[];
    private Point                mP1;
    private Point                mP2;
    private float                mBuff[];


    public Mat procSrc2GrayJni(Mat srcMat,int type) {
        Mat grayMat = new Mat();
        Imgproc.cvtColor(srcMat, grayMat, type);//转换为灰度图
        // Imgproc.HoughCircles(rgbMat, gray,Imgproc.CV_HOUGH_GRADIENT, 1, 18);
        // //霍夫变换找圆
        mChannels = new MatOfInt[] { new MatOfInt(0), new MatOfInt(1), new MatOfInt(2) };
        mBuff = new float[mHistSizeNum];
        mHistSize = new MatOfInt(mHistSizeNum);
        mRanges = new MatOfFloat(0f, 256f);
        mMat0  = new Mat();
        mColorsRGB = new Scalar[] { new Scalar(200, 0, 0, 255), new Scalar(0, 200, 0, 255), new Scalar(0, 0, 200, 255) };
        mP1 = new Point();
        mP2 = new Point();



        Mat rgba = srcMat;
        Size sizeRgba = rgba.size();
        Mat hist = new Mat(); //转换直方图进行绘制
        int thikness = (int) (sizeRgba.width / (mHistSizeNum + 10) / 5);
        if(thikness > 5) thikness = 5;
        int offset = (int) ((sizeRgba.width - (5*mHistSizeNum + 4*10)*thikness)/2);
        // RGB
        for(int c=0; c<3; c++) {
            Imgproc.calcHist(Arrays.asList(rgba), mChannels[c], mMat0, hist, mHistSize, mRanges);
            Core.normalize(hist, hist, sizeRgba.height/2, 0, Core.NORM_INF);
            hist.get(0, 0, mBuff);
            for(int h=0; h<mHistSizeNum; h++) {
                mP1.x = mP2.x = offset + (c * (mHistSizeNum + 10) + h) * thikness;
                mP1.y = sizeRgba.height-1;
                mP2.y = mP1.y - 2 - (int)mBuff[h];
//                Core.line(rgba, mP1, mP2, mColorsRGB[c], thikness);
                Imgproc.line(rgba, mP1, mP2, mColorsRGB[c], thikness);
            }
        }

        return rgba;
    }
    /**
     * 比较来个矩阵的相似度
     * @param srcMat
     * @param desMat
     */
    public void comPareHist(Mat srcMat,Mat desMat){

        srcMat.convertTo(srcMat, CvType.CV_32F);
        desMat.convertTo(desMat, CvType.CV_32F);
        double target = Imgproc.compareHist(srcMat, desMat, Imgproc.CV_COMP_CORREL);
        Log.e(TAG, "相似度 ：   ==" + target);
        Toast.makeText(this, "相似度 ：   ==" + target, Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (!OpenCVLoader.initDebug()) {
            LogUtils.i( "Internal OpenCV library not found. Using OpenCV Manager for initialization");
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_2_0, this, callback);
        } else {
            LogUtils.i( "OpenCV library found inside package. Using it!");
            callback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }
    }
}
