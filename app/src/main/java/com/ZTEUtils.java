package com;

import android.os.Environment;
import android.util.Log;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

/**
 * Created by Administrator on 2017/5/6.
 */

public class ZTEUtils {

    private static final String TAG = "FaceFeature";
    public static final String PATH = Environment.getExternalStorageDirectory() + "/Jingwutong";

    static {
        //加载模型
        ZTEFace.loadModel(PATH + "/d.dat", PATH + "/a.dat", PATH + "/db.dat", PATH + "/p.dat");
    }

    /**
     * 获取图片特征值
     *
     * @param src
     * @return
     */
    public static byte[] getFaceFeature(Mat src) {
        long start = System.currentTimeMillis();
        //转化为YUV数据
        Imgproc.cvtColor(src, src, Imgproc.COLOR_RGBA2YUV_I420);
        int length = (int) (src.total() * src.elemSize());
        byte[] bytes = new byte[length];
        src.get(0, 0, bytes);
        //人脸检测 输入 rgb8或YUV
        byte[] ret = ZTEFace.detectFace(bytes, src.width(), src.height());
        //人脸信息转换
        ZTEFace.FaceInfo info = ZTEFace.getFaceInfo(ret);
        if (info.info[0] != null) {
            byte[] fea = getFea(bytes, info.info[0], src.width(), src.height());
            long end = System.currentTimeMillis();
            Log.i(TAG, "耗时：" + (end - start) + " ms");
            return fea;
        }
        Log.i(TAG, "特征值提取失败");
        return null;
    }


    private static byte[] getFea(byte[] bb, ZTEFace.FacePointInfo facePointInfo, int width, int height) {
        int eyeLeftX = (int) facePointInfo.ptEyeLeft.x;
        int eyeLeftY = (int) facePointInfo.ptEyeLeft.y;
        int eyeRightX = (int) facePointInfo.ptEyeRight.x;
        int eyeRightY = (int) facePointInfo.ptEyeRight.y;
        int eyeNoseX = (int) facePointInfo.ptNose.x;
        int eyeNoseY = (int) facePointInfo.ptNose.y;
        int mouthLeftX = (int) facePointInfo.ptMouthLeft.x;
        int mouthLeftY = (int) facePointInfo.ptMouthLeft.y;
        int mouthRightX = (int) facePointInfo.ptMouthRight.x;
        int mouthRightY = (int) facePointInfo.ptMouthRight.y;
        //特征提取
        return ZTEFace.getFea(bb, width, height, eyeLeftX, eyeLeftY, eyeRightX, eyeRightY, eyeNoseX, eyeNoseY, mouthLeftX, mouthLeftY, mouthRightX, mouthRightY);
    }


    /**
     * 人脸比对
     *
     * @param fea1
     * @param fea2
     * @return 结果分数
     */
    public static double feaCompare(byte[] fea1, byte[] fea2) {
        float result = ZTEFace.feaCompare(fea1, fea2);
        Log.i(TAG, "比对结果：" + Math.sqrt(result) * 10);
        return Math.sqrt(result) * 10;
    }


}
