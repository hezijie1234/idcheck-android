package com.huiyu.tech.zhongxing.ui.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.huiyu.tech.zhongxing.Constants;
import com.huiyu.tech.zhongxing.R;
import com.huiyu.tech.zhongxing.api.ApiImpl;
import com.huiyu.tech.zhongxing.api.OnResponseListener;
import com.huiyu.tech.zhongxing.btutil.BTCardReader;
import com.huiyu.tech.zhongxing.btutil.BTDeviceFinder;
import com.huiyu.tech.zhongxing.btutil.DeviceRecoder;
import com.huiyu.tech.zhongxing.btutil.IDCardReadTask;
import com.huiyu.tech.zhongxing.models.RecognitionModel;
import com.huiyu.tech.zhongxing.ui.ZZBaseActivity;
import com.huiyu.tech.zhongxing.utils.Base64Util;
import com.huiyu.tech.zhongxing.utils.CustomToast;
import com.huiyu.tech.zhongxing.utils.ImageUtils;
import com.huiyu.tech.zhongxing.utils.LogUtils;
import com.huiyu.tech.zhongxing.utils.SharedPrefUtils;
import com.wang.avi.AVLoadingIndicatorView;

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
import java.util.ArrayList;

/**
 * 人像识别
 */
public class FaceRecognitionActivity1 extends ZZBaseActivity implements View.OnClickListener, CameraBridgeViewBase.CvCameraViewListener2, OnResponseListener {
    private CascadeClassifier mJavaDetector;
    private float mRelativeFaceSize = 0.2f;
    private int mAbsoluteFaceSize = 0;
    private Mat mRgba;
    private static final Scalar FACE_RECT_COLOR = new Scalar(0, 255, 0, 255);
    private int num = 0;
    private boolean hasImage = false;

    private JavaCameraView faceRecSurfaceView;
    private TextView faceRecIntroText;
    private RelativeLayout faceRecCardInfoLay;
    private TextView faceRecTvName;
    private TextView faceRecTvSex;
    private TextView faceRecTvNation;
    private TextView faceRecTvYear;
    private TextView faceRecTvMonth;
    private TextView faceRecTvDay;
    private TextView faceRecTvAddr;
    private TextView faceRecTvIdcard;
    private ImageView faceRecIvHead;
    private LinearLayout faceRecConfirm;

    private AlertDialog mBTDevGetDailog;
    private ArrayAdapter<String> mBTDevListViewAdapter;
    private BTDeviceFinder mBtDevFinder;
    private String mUserSelectedBTDevMac = "";
    private Dialog warnDialog;
    private Dialog passDialog;

    private RecognitionModel recognitionModel;
    private AVLoadingIndicatorView loadingView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_face_recognition1);

        initView();
    }

    private void initView() {
        showBackView();
        showTitleView(getResources().getString(R.string.title_face_login));
        bindView();

        faceRecSurfaceView.setCvCameraViewListener(this);
//        faceRecSurfaceView.setCameraIndex(CameraBridgeViewBase.CAMERA_ID_FRONT);
        faceRecSurfaceView.setCameraIndex(CameraBridgeViewBase.CAMERA_ID_BACK);
//        faceRecSurfaceView.enableFpsMeter();

        recognitionModel = new RecognitionModel();
        initBt();
    }

    /**
     * 获取控件
     */
    private void bindView() {
        faceRecSurfaceView = (JavaCameraView) findViewById(R.id.face_rec_surface_view);
        faceRecIntroText = (TextView) findViewById(R.id.face_rec_intro_text);
        faceRecCardInfoLay = (RelativeLayout) findViewById(R.id.face_rec_card_info_lay);
        faceRecTvName = (TextView) findViewById(R.id.face_rec_tv_name);
        faceRecTvSex = (TextView) findViewById(R.id.face_rec_tv_sex);
        faceRecTvNation = (TextView) findViewById(R.id.face_rec_tv_nation);
        faceRecTvYear = (TextView) findViewById(R.id.face_rec_tv_year);
        faceRecTvMonth = (TextView) findViewById(R.id.face_rec_tv_month);
        faceRecTvDay = (TextView) findViewById(R.id.face_rec_tv_day);
        faceRecTvAddr = (TextView) findViewById(R.id.face_rec_tv_addr);
        faceRecTvIdcard = (TextView) findViewById(R.id.face_rec_tv_idcard);
        faceRecIvHead = (ImageView) findViewById(R.id.face_rec_iv_head);
        faceRecConfirm = (LinearLayout) findViewById(R.id.face_rec_confirm);
        loadingView = (AVLoadingIndicatorView) findViewById(R.id.loadingView);
        faceRecConfirm.setOnClickListener(this);
    }

    private void initBt() {

        // 获取蓝牙设备查找类的实例
        mBtDevFinder = BTDeviceFinder.getFinder(this);
        mBtDevFinder.setClientListener(mBtDevFoundListener);
        mBtDevFinder.checkBTAdapter();
        mBtDevFinder.openBTAdapter(); // 如果没有打开蓝牙设备，系统 会弹出窗子询问

        // 创建一个对话框 并嵌入一个ListView，用来显示被发现的蓝牙设备
        ListView deviceList = new ListView(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(this, AlertDialog.THEME_HOLO_LIGHT);
        builder.setTitle("蓝牙设备列表");
        builder.setView(deviceList);
        mBTDevGetDailog = builder.create();
        mBTDevGetDailog.setOnShowListener(mBTDevListViewDailogShow);
        mBTDevGetDailog.setOnDismissListener(mBTDevListViewDailogDismiss);

        mBTDevListViewAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        deviceList.setAdapter(mBTDevListViewAdapter);
        deviceList.setOnItemClickListener(mListViewClickListener);

        // 设置读卡回调
        IDCardReadTask.getReadTask().setCallback(mReaderTaskListener);

        mUserSelectedBTDevMac = SharedPrefUtils.getString(this, Constants.SHARE_KEY.BLUETOOTH_MAC, "");
        readIDCard();
    }

    private ArrayList<String> btDevAddressList = new ArrayList<String>();
    private BTDeviceFinder.OnDeviceFoundListener mBtDevFoundListener = new BTDeviceFinder.OnDeviceFoundListener() {

        @Override
        public void onDevieFound(BluetoothDevice device) {
            String devAddress = device.getAddress();
            if (devAddress != null && !devAddress.isEmpty()) {
                if (!btDevAddressList.contains(devAddress)) {
                    btDevAddressList.add(devAddress);
                    mBTDevListViewAdapter.add(device.getName() + "\n" + devAddress);
                }
            }
        }
    };

    private IDCardReadTask.ReaderTaskListener mReaderTaskListener = new IDCardReadTask.ReaderTaskListener() {

        @Override
        public void readResult(BTCardReader.Info info, int status) {


            LogUtils.e("==========status=========" + status);
            if (info != null && status == IDCardReadTask.Status_read_successful) {
                LogUtils.e("==========info=========" + info.toString());
                showProgress(false);
                showUserInfo(info);
                /*i.name = info.name;
                i.sex = info.sex;
                i.nation = info.nation;
                i.year = info.birthday.substring(0, 4);
                i.month = info.birthday.substring(4, 6);
                i.day = info.birthday.substring(6, 8);

                i.address = info.address;
                i.idNum = info.idNum;
                i.author = info.author;
                i.validDate = info.validDateStart + " - " + info.validDateEnd;
                i.image = info.image;
                i.cardNo= info.cardNo;*/

            }

            Resources resource = getResources();
            String strStatus = "";
            switch (status) {
                case IDCardReadTask.Status_findCard_successful: {
                    strStatus += resource.getString(R.string.NoteStrFindCard);
                    break;
                }
                case IDCardReadTask.Status_read_successful: {
                    strStatus += resource.getString(R.string.NoteStrReadSuccess);
                    break;
                }
                case IDCardReadTask.Status_device_disconnected: {
                    showProgress(false);
                    mBTDevGetDailog.show();
                    strStatus += resource.getString(R.string.NoteStrDevIsDisconnect);
                    mUserSelectedBTDevMac = "";
                    break;
                }
                case IDCardReadTask.Status_no_card: {
                    showProgress(false);
                    strStatus += resource.getString(R.string.NoteStrNotFoundCard);
                    break;
                }
                case IDCardReadTask.Status_findCard_error: {
                    showProgress(false);
                    strStatus += resource.getString(R.string.NoteStrFindCardFailed);
                    break;
                }
                case IDCardReadTask.Status_error_open_device_failed: {
                    showProgress(false);
                    strStatus += resource.getString(R.string.NoteStrOpenDevFailed);
                    mUserSelectedBTDevMac = "";
                    break;
                }
                case IDCardReadTask.Status_check_device_not_ready: {
                    showProgress(false);
                    strStatus += resource.getString(R.string.NoteStrDevNotReady);
                    mUserSelectedBTDevMac = "";
                    break;
                }
                case IDCardReadTask.Status_error_select_card_failed: {
                    showProgress(false);
                    strStatus += resource.getString(R.string.NoteStrSelectCardFailed);
                    break;
                }
                case IDCardReadTask.Status_error_read_failed: {
                    showProgress(false);
                    strStatus += resource.getString(R.string.NoteStrReadCardFailed);
                    break;
                }

                default:
                    break;
            }

            CustomToast.showToast(FaceRecognitionActivity1.this, strStatus);
        }
    };

    private AdapterView.OnItemClickListener mListViewClickListener = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            mBTDevGetDailog.dismiss();
            String itemText = mBTDevListViewAdapter.getItem(position);
            String devinfo[] = itemText.split("\n");
            if (devinfo.length == 2 && devinfo[1].matches("^([0-9a-fA-F]{2})(([:][0-9a-fA-F]{2}){5})$")) {
                mUserSelectedBTDevMac = devinfo[1];
                /**把选择的mac地址存到sp，下次进来就直接使用**/
                SharedPrefUtils.setString(FaceRecognitionActivity1.this, Constants.SHARE_KEY.BLUETOOTH_MAC, mUserSelectedBTDevMac);

                DeviceRecoder.recodeMac(FaceRecognitionActivity1.this, devinfo[0], mUserSelectedBTDevMac);
                readIDCard();
            } else {
                mBtDevFinder.openBTAdapter();
            }

        }
    };

    private DialogInterface.OnShowListener mBTDevListViewDailogShow = new DialogInterface.OnShowListener() {

        @Override
        public void onShow(DialogInterface dialog) {
            btDevAddressList.clear();
            mBTDevListViewAdapter.clear();
            int ret = mBtDevFinder.searchtBTDevice();
            if (ret < 0) {
                String notice = "";
                Resources resource = getResources();
                if (-1 == ret) {
                    notice = resource.getString(R.string.NoteStrNotFounddBTAdapter);
                } else if (-2 == ret) {
                    notice = resource.getString(R.string.NoteStrOpenBTPlease);
                }

                mBTDevListViewAdapter.add(notice);
            }
        }
    };

    private DialogInterface.OnDismissListener mBTDevListViewDailogDismiss = new DialogInterface.OnDismissListener() {

        @Override
        public void onDismiss(DialogInterface dialog) {
            mBtDevFinder.cancleSearchDev();
        }
    };

    /**
     * 读取身份证
     */
    private void readIDCard() {
        LogUtils.e("=====readIDCard=====");
        showProgress(true);
        faceRecIntroText.setVisibility(View.VISIBLE);
        faceRecCardInfoLay.setVisibility(View.INVISIBLE);

        final IDCardReadTask readTask = IDCardReadTask.getReadTask();
        int status = IDCardReadTask.Status_no_action;
        status = readTask.readCard(mUserSelectedBTDevMac, false);
        Resources resource = getResources();
        String strStatus = "value:" + status + "  description:";
        LogUtils.e("=====strStatus=====" + strStatus);
        switch (status) {
            case IDCardReadTask.Status_error_mac_format_incorrect: {
                strStatus += resource.getString(R.string.NoteStrMacAddressFormatError);
                //mac地址错误，需要先连接设备
                mBTDevGetDailog.show();
                break;
            }
            case IDCardReadTask.Status_lunch_task_successful: {
                strStatus += resource.getString(R.string.NoteStrStartToReadCard);
                break;
            }
            case IDCardReadTask.Status_is_reading: {
                strStatus += resource.getString(R.string.NoteStrReadingCard);
                break;
            }
            default:
                break;
        }
    }

    private void showProgress(boolean show) {
        loadingView.setVisibility(show?View.VISIBLE:View.GONE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.face_rec_confirm:
                readIDCard();
                hasImage = false;
                break;
            default:
                break;
        }

    }

    private void showUserInfo(BTCardReader.Info info) {
        faceRecCardInfoLay.setVisibility(View.VISIBLE);
//        private TextView faceRecTvName;
//        private TextView faceRecTvSex;
//        private TextView faceRecTvNation;
//        private TextView faceRecTvYear;
//        private TextView faceRecTvMonth;
//        private TextView faceRecTvDay;
//        private TextView faceRecTvAddr;
//        private TextView faceRecTvIdcard;
//        private ImageView faceRecIvHead;
        /*i.name = info.name;
                i.sex = info.sex;
                i.nation = info.nation;
                i.year = info.birthday.substring(0, 4);
                i.month = info.birthday.substring(4, 6);
                i.day = info.birthday.substring(6, 8);

                i.address = info.address;
                i.idNum = info.idNum;
                i.author = info.author;
                i.validDate = info.validDateStart + " - " + info.validDateEnd;
                i.image = info.image;
                i.cardNo= info.cardNo;*/
        faceRecTvName.setText(info.name);
        faceRecTvSex.setText(info.sex);
        faceRecTvNation.setText(info.nation);
        faceRecTvYear.setText(info.birthday.substring(0, 4));
        faceRecTvMonth.setText(info.birthday.substring(4, 6));
        faceRecTvDay.setText(info.birthday.substring(6, 8));
        faceRecTvAddr.setText(info.address);
        faceRecTvIdcard.setText(info.idNum);
        faceRecIvHead.setImageBitmap(info.image);

        /**
         * idCard : 420103198604153710
         * idName : 江纯
         * idSex : 1
         * idEntityType : 1
         * idBirthday : 1986-04-15
         * idNation : 汉
         * idAddress : 湖北省武汉市江汉区万松园小区
         * idValidateDate : 2015-12-28 - 2035-12-28
         * idLicenceAuthority : 武汉市公安局江汉分局
         */

        RecognitionModel.IdcardBeanBean idcardBeanBean = new RecognitionModel.IdcardBeanBean();
        idcardBeanBean.setIdCard(info.idNum);
        idcardBeanBean.setIdName(info.name);
        idcardBeanBean.setIdSex(info.sex);
        idcardBeanBean.setIdEntityType("1");
        idcardBeanBean.setIdBirthday(info.birthday);
        idcardBeanBean.setIdNation(info.nation);
        idcardBeanBean.setIdAddress(info.address);
        idcardBeanBean.setIdValidateDate(info.validDateStart + " - " + info.validDateEnd);
        idcardBeanBean.setIdLicenceAuthority(info.author);
        recognitionModel.setIdcardBean(idcardBeanBean);

        /**
         * imgName : 身份证
         * imgStr : ************
         * imgType : 1
         */
        RecognitionModel.IdCardImageBean imageBean = new RecognitionModel.IdCardImageBean();
        imageBean.setImgType("1");
        imageBean.setImgName(info.idNum);
        imageBean.setImgStr(Base64Util.bitmapToBase64(info.image));
        recognitionModel.setIdCardImage(imageBean);

        sendFaceToServer();


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
                    } catch (IOException e) {
                        e.printStackTrace();
                        LogUtils.i("Failed to load cascade. Exception thrown: " + e);
                    }

                    faceRecSurfaceView.enableView();
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
        if (faceRecSurfaceView != null) {
            faceRecSurfaceView.disableView();
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

        String devinfo[] = DeviceRecoder.getMac(this);
        mUserSelectedBTDevMac = devinfo[1];
    }

    public void onDestroy() {
        super.onDestroy();
        if (faceRecSurfaceView != null) {
            faceRecSurfaceView.disableView();
        }
        mBtDevFinder.turnOffAdapter();
    }

    public void onCameraViewStarted(int width, int height) {
        mRgba = new Mat();
    }

    public void onCameraViewStopped() {
        mRgba.release();
    }

    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
        mRgba = inputFrame.rgba();
        //使用后置摄像头时旋转-90度
        Mat rotateMat = Imgproc.getRotationMatrix2D(new Point(mRgba.cols() / 2, mRgba.rows() / 2), -90, 1);
        Imgproc.warpAffine(mRgba, mRgba, rotateMat, mRgba.size());
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
//        LogUtils.i("识别人脸---"+facesArray.length);
//
        if (facesArray.length > 0 && !hasImage) {
            num++;
            if (num % Constants.FACE_SUCCESS_COUNT == 0) {
                try {
                    //0 <= roi.x && 0 <= roi.width && roi.x + roi.width <= m.cols && 0 <= roi.y && 0 <= roi.height && roi.y + roi.height <= m.rows in function cv::Mat::Mat(const cv::Mat&, const Rect&)
                    Mat mat = getDefaultCompareSize(mRgba.submat(getImageRect(mRgba.size(), facesArray[0])));
//                compare(mat,headMat);
                    LogUtils.i("==截图==");
                    //解决截取图片脸显示为蓝色的问题 http://blog.csdn.net/yang_xian521/article/details/7010475
                    //Imgproc.cvtColor(mat, mat, Imgproc.COLOR_RGBA2BGR, 3);
                    Imgcodecs.imwrite(Constants.CASH_IMG, mat);
                    Bitmap bitmap = BitmapFactory.decodeFile(Constants.CASH_IMG);
                    bitmap = ImageUtils.compressImage(bitmap);
//                ImageUtils.saveBitmapLocal(bitmap, Constants.CASH_IMG, 80);
                    hasImage = true;

                    /**
                     * imgName : 时间戳
                     * imgStr : ****************
                     * imgType : 2
                     */

                    RecognitionModel.PhoneImageBean phoneImage = new RecognitionModel.PhoneImageBean();
                    phoneImage.setImgName(String.valueOf(System.currentTimeMillis()));
                    phoneImage.setImgStr(Base64Util.bitmapToBase64(bitmap));
                    phoneImage.setImgType("2");
                    recognitionModel.setPhoneImage(phoneImage);
                    sendFaceToServer();
                } catch (Exception e) {
                    e.printStackTrace();
                    num =0;
                }
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

    /**
     * 显示人证不符合的对话框
     */
    private void showFailDialog(){
        if (warnDialog == null) {
            warnDialog = new Dialog(this,R.style.zzDialog);
            View view = LayoutInflater.from(this).inflate(R.layout.dialog_recognition_warn_lay,null);
            warnDialog.setContentView(view);
        }
        if (!warnDialog.isShowing()){
            warnDialog.show();
        }
    }

    /**
     * 显示识别成功的dialog
     */
    private void showPassDialog(){
        if (passDialog == null) {
            passDialog = new Dialog(this,R.style.zzDialog);
            View view = LayoutInflater.from(this).inflate(R.layout.dialog_recognition_success_lay,null);
            passDialog.setContentView(view);
        }
        if (!passDialog.isShowing()){
            passDialog.show();
        }

    }

    /**
     * 发送用户信息到后台进行验证
     */
    private void sendFaceToServer() {
        if (recognitionModel != null
                && recognitionModel.getIdcardBean() != null
                && recognitionModel.getIdCardImage() != null
                && recognitionModel.getPhoneImage() != null) {
            ApiImpl.getInstance().sendFaceDetect(JSON.toJSONString(recognitionModel), this);
            //上传完后清除本地数据
            recognitionModel.setPhoneImage(null);
            recognitionModel.setIdCardImage(null);
            recognitionModel.setIdcardBean(null);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    showProgressDialog();
                }
            });
        }
    }

    @Override
    public void onAPISuccess(String flag, JSONObject json) {
        hideProgressDialog();
        if (flag.equals(ApiImpl.FACE_DETECT)){
            if (json.optInt("c") == 0){
//                /*{"returnTime":"2017-02-27 15:24:51","is_success":"1"}*/
                if (json.optJSONObject("d").optString("is_success").equals("1")){
                    showPassDialog();
                }else {
                    showFailDialog();
                }
            }else {
                CustomToast.showToast(this,json.optString("m"));
            }
        }
    }

    @Override
    public void onAPIError(String flag, int code, String error) {
        hideProgressDialog();
        CustomToast.showToast(this,error);
    }
}
