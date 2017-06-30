package com.huiyu.tech.zhongxing.ui.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.IsoDep;
import android.nfc.tech.MifareClassic;
import android.nfc.tech.Ndef;
import android.nfc.tech.NfcA;
import android.nfc.tech.NfcB;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ZTEUtils;
import com.alibaba.fastjson.JSON;
import com.huiyu.tech.zhongxing.Constants;
import com.huiyu.tech.zhongxing.R;
import com.huiyu.tech.zhongxing.api.ApiImpl;
import com.huiyu.tech.zhongxing.api.OnResponseListener;
import com.huiyu.tech.zhongxing.btutil.BTCardReader;
import com.huiyu.tech.zhongxing.btutil.BTDeviceFinder;
import com.huiyu.tech.zhongxing.btutil.DeviceRecoder;
import com.huiyu.tech.zhongxing.btutil.IDCardReadTask;
import com.huiyu.tech.zhongxing.models.IdCardModule;
import com.huiyu.tech.zhongxing.models.RecognitionModel;
import com.huiyu.tech.zhongxing.ui.ZZBaseActivity;
import com.huiyu.tech.zhongxing.utils.Base64Util;
import com.huiyu.tech.zhongxing.utils.CustomToast;
import com.huiyu.tech.zhongxing.utils.ImageUtils;
import com.huiyu.tech.zhongxing.utils.LogUtils;
import com.huiyu.tech.zhongxing.utils.SharedPrefUtils;

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
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import cc.lotuscard.LotusCardDriver;
import cc.lotuscard.TwoIdInfoParam;

/**
 * 人像识别
 */
public class FaceRecognitionActivity extends ZZBaseActivity implements View.OnClickListener, CameraBridgeViewBase.CvCameraViewListener2, OnResponseListener {
    private CascadeClassifier mJavaDetector;
    private float mRelativeFaceSize = 0.2f;
    private int mAbsoluteFaceSize = 0;
    private Mat mRgba;
    private static final Scalar FACE_RECT_COLOR = new Scalar(0, 255, 0, 255);
    private int num = 0;
    private boolean hasImage = true;

    private JavaCameraView faceRecSurfaceView;
    private TextView tvScore;
    private TextView tvTime;
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

    private NfcAdapter m_NfcAdpater;
    private PendingIntent pendingIntent;
    private IntentFilter[] mFilters;
    private String[][] mTechLists;
    private LotusCardDriver mLotusCardDriver;
    private long m_nDeviceHandle = -1;
    private FrameLayout frameLayout;
    private byte[] feature1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        m_NfcAdpater = NfcAdapter.getDefaultAdapter(this);
        if (m_NfcAdpater == null) {
            Toast.makeText(this, "手机不支持NFC", Toast.LENGTH_SHORT)
                    .show();
            // finish();
            // return;
        } else if (!m_NfcAdpater.isEnabled()) {
            Toast.makeText(this, "请先打开NFC功能",
                    Toast.LENGTH_SHORT).show();
            // finish();
            // return;
        }
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_face_recognition);

        initView();

        initNFC();
    }

    private void initNFC() {
        pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this,
                getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        IntentFilter ndef = new IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED);
        ndef.addCategory("*/*");
        mFilters = new IntentFilter[] { ndef };// 过滤器
        mTechLists = new String[][] {
                new String[] { MifareClassic.class.getName() },
                new String[] { NfcB.class.getName() },
                new String[] { IsoDep.class.getName() },
                new String[] { NfcA.class.getName() } };// 允许扫描的标签类型
        mLotusCardDriver = new LotusCardDriver();
    }

    private void initView() {
        showBackView();
        showTitleView(getResources().getString(R.string.title_face_login));
        showTextRightAction(getResources().getString(R.string.idcard_record), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FaceRecognitionActivity.this,ScanRecordActivity.class);
                intent.putExtra("type","recog");
                startActivity(intent);
            }
        });
        bindView();
        faceRecSurfaceView.setCvCameraViewListener(this);
//        faceRecSurfaceView.setCameraIndex(CameraBridgeViewBase.CAMERA_ID_FRONT);
        faceRecSurfaceView.setCameraIndex(CameraBridgeViewBase.CAMERA_ID_BACK);
//        faceRecSurfaceView.enableFpsMeter();

        recognitionModel = new RecognitionModel();
//        initBt();
        BTCardReader read = new BTCardReader();
        mInfo = new IdCardModule();
    }

    /**
     * 获取控件
     */
    private void bindView() {
        faceRecSurfaceView = (JavaCameraView) findViewById(R.id.face_rec_surface_view);
        tvScore = (TextView) findViewById(R.id.tvScore);
        tvTime = (TextView) findViewById(R.id.tvTime);
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
        frameLayout = (FrameLayout) findViewById(R.id.frame);
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
        AlertDialog.Builder builder = new AlertDialog.Builder(this, android.app.AlertDialog.THEME_HOLO_LIGHT);
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
                Log.e("111", "readResult: "+info );
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

            CustomToast.showToast(FaceRecognitionActivity.this, strStatus);
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
                SharedPrefUtils.setString(FaceRecognitionActivity.this, Constants.SHARE_KEY.BLUETOOTH_MAC, mUserSelectedBTDevMac);

                DeviceRecoder.recodeMac(FaceRecognitionActivity.this, devinfo[0], mUserSelectedBTDevMac);
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
        //loadingView.setVisibility(show?View.VISIBLE:View.GONE);
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
    private IdCardModule mInfo ;
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

//        String devinfo[] = DeviceRecoder.getMac(this);
//        mUserSelectedBTDevMac = devinfo[1];
        m_NfcAdpater.enableForegroundDispatch(this, pendingIntent, mFilters,
                mTechLists);
    }

    public void onDestroy() {
        super.onDestroy();
        if (faceRecSurfaceView != null) {
            faceRecSurfaceView.disableView();
        }
       // mBtDevFinder.turnOffAdapter();
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
        //在人脸检测之前将每张图片转换成灰度图，测试是否能提高人脸检测效率
//        Imgproc.cvtColor(mRgba, mRgba, Imgproc.COLOR_RGBA2GRAY, 3);
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
                    Imgproc.cvtColor(mat, mat, Imgproc.COLOR_RGBA2BGR, 3);
                    Imgcodecs.imwrite(Constants.CASH_IMG, mat);
                    Bitmap bitmap = BitmapFactory.decodeFile(Constants.CASH_IMG);
                    bitmap = ImageUtils.compressImage(bitmap);
//                ImageUtils.saveBitmapLocal(bitmap, Constants.CASH_IMG, 80);
                    hasImage = true;
//                    /**
//                     * imgName : 时间戳
//                     * imgStr : ****************
//                     * imgType : 2
//                     */
//
                    RecognitionModel.PhoneImageBean phoneImage = new RecognitionModel.PhoneImageBean();
                    phoneImage.setImgName(String.valueOf(System.currentTimeMillis()));
                    phoneImage.setImgStr(Base64Util.bitmapToBase64(bitmap));
                    phoneImage.setImgType("2");
                    recognitionModel.setPhoneImage(phoneImage);
                    sendFaceToServer();
                    final long start =System.currentTimeMillis();
                    byte[] feature2 = ZTEUtils.getFaceFeature(mat);
                    final double result = ZTEUtils.feaCompare(feature1, feature2);
                    final long end =System.currentTimeMillis();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tvScore.setText(result+"");
                            tvTime.setText((end-start)+"ms");
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    num = 0;
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
            Log.e("111", "sendFaceToServer: 发送给服务器" );
            ApiImpl.getInstance().sendFaceDetect(JSON.toJSONString(recognitionModel), this);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    showProgressDialog();
                }
            });
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        recognitionModel.setPhoneImage(null);
    }

    @Override
    public void onAPISuccess(String flag, JSONObject json) {
        hideProgressDialog();
        if (flag.equals(ApiImpl.FACE_DETECT)){
            if (json.optInt("c") == 0){
//                /*{"returnTime":"2017-02-27 15:24:51","is_success":"1"}*/
                if (json.optJSONObject("d").optString("is_success").equals("1")){
                    //上传完后清除本地数据
                    recognitionModel.setPhoneImage(null);
                    recognitionModel.setIdCardImage(null);
                    recognitionModel.setIdcardBean(null);
                    Intent intent = new Intent(this,FaceRecongResultActivity.class);
                    intent.putExtra("isSuccess",true);
                    frameLayout.setVisibility(View.GONE);
                    Log.e("111", "onAPISuccess: "+mInfo );
                    intent.putExtra("info",mInfo);
                    startActivity(intent);
//                    showPassDialog();
                }else {
                    hasImage = false;
//                    showFailDialog();
                    Intent intent = new Intent(this,FaceRecongResultActivity.class);
                    intent.putExtra("isSuccess",false);
                    frameLayout.setVisibility(View.VISIBLE);
                    intent.putExtra("info",mInfo);
                    startActivity(intent);
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
        hasImage = false;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        // TODO Auto-generated method stub
        super.onNewIntent(intent);
        boolean bResult = false;
        boolean bWlDecodeResult = false;
        String temp;
        byte[] arrCardId = { (byte) 0x00, (byte) 0x36, (byte) 0x00,
                (byte) 0x00, (byte) 0x08 };
        byte[] arrFirstFile = { (byte) 0x00, (byte) 0xA4, (byte) 0x00,
                (byte) 0x00, (byte) 0x02, (byte) 0x60, (byte) 0x02 };
        byte[] arrReadFirstFile = { (byte) 0x80, (byte) 0xB0, (byte) 0x00,
                (byte) 0x00, (byte) 0x20 };
        // m_NfcIntent = intent;
        String strWriteText = "";
        if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(intent.getAction())) {
            Tag tagFromIntent = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            NfcB nfcbId = NfcB.get(tagFromIntent);
            Ndef ndef = Ndef.get(tagFromIntent);
            IsoDep iso = IsoDep.get(tagFromIntent);
            if (nfcbId != null) {
                faceRecCardInfoLay.setVisibility(View.INVISIBLE);
                try {
                    nfcbId.connect();
                    //if (nfcbId.isConnected())
                        //AddLog("connect");
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                if (nfcbId.isConnected()) {
                    // try {
                    // byte[] arrResult =nfcbId.getApplicationData();
                    // byte[] arrResult =nfcbId.getProtocolInfo();
                    // byte[] arrResult = nfcbId.transceive(arrCardId);
                    // AddLog("Read Id:" + arrResult.length + " " +
                    // convertArray2String(arrResult));
                    // arrResult = nfcbId.transceive(arrFirstFile);
                    // AddLog("Select FirstFile:" + arrResult.length + " " +
                    // convertArray2String(arrResult));
                    //
                    // arrResult = nfcbId.transceive(arrReadFirstFile);
                    // AddLog("Read FirstFile:" + arrResult.length + " " +
                    // convertArray2String(arrResult));
                    TwoIdInfoParam tTwoIdInfo = new TwoIdInfoParam();
                    if (m_nDeviceHandle == -1) {
                        m_nDeviceHandle = mLotusCardDriver.OpenDevice("", 0, 0,
                                0, 0,// 使用内部默认超时设置
                                true);

                    }
                    bResult = mLotusCardDriver.GetTwoIdInfoByMcuServer(nfcbId,
                            m_nDeviceHandle, "120.24.249.49", "username",
                            "password", tTwoIdInfo,2);
//					bResult = mLotusCardDriver.GetTwoIdInfoByMcuServer(nfcbId,
//							m_nDeviceHandle, "192.168.1.52", "username",
//							"password", tTwoIdInfo, 2);
                    if (!bResult) {
                        frameLayout.setVisibility(View.GONE);
                        CustomToast.showToast(this,"身份证信息获取失败");
                        //AddLog("Call GetTwoIdInfoByMcuServer Error! ErrorCode:" + mLotusCardDriver.GetTwoIdErrorCode(m_nDeviceHandle));
                        return;
                    }
                    //AddLog("Call GetTwoIdInfoByMcuServer Ok!");
                    //处理照片
                    if(0x00 == tTwoIdInfo.unTwoIdPhotoJpegLength)
                    {
                        bWlDecodeResult = mLotusCardDriver.WlDecodeByServer(m_nDeviceHandle, "120.24.249.49", tTwoIdInfo);
                        if (!bWlDecodeResult) {
                            CustomToast.showToast(this,"身份证头像信息获取失败");
                            //AddLog("Call WlDecodeByServer Error! ");
                        }
                        else
                        {
                            //AddLog("Call WlDecodeByServer Ok!");
                        }

                    }
                    if (true == bResult) {
                        frameLayout.setVisibility(View.VISIBLE);
                        faceRecCardInfoLay.setVisibility(View.VISIBLE);
                        // 姓名
                        try {
                            RecognitionModel.IdcardBeanBean idcardBeanBean = new RecognitionModel.IdcardBeanBean();
                            RecognitionModel.IdCardImageBean imageBean = new RecognitionModel.IdCardImageBean();
                            temp = new String(tTwoIdInfo.arrTwoIdName, 0, 30,
                                    "UTF-16LE").trim();
                            if (temp.equals("")) {
                                //AddLog("数据为空");
                                return;
                            }
                            idcardBeanBean.setIdName(temp);
                            faceRecTvName.setText(temp);
                            mInfo.setName(temp);

                            // 性别
                            temp = new String(tTwoIdInfo.arrTwoIdSex, 0, 2,
                                    "UTF-16LE").trim();
                            if (temp.equals("1"))
                                temp = "男";
                            else
                                temp = "女";
                            idcardBeanBean.setIdSex(temp);
                            faceRecTvSex.setText(temp);
                            mInfo.setSex(temp);
                            // 民族
                            temp = new String(tTwoIdInfo.arrTwoIdNation, 0, 4,
                                    "UTF-16LE").trim();
                            try {
                                int code = Integer.parseInt(temp.toString());
                                temp = decodeNation(code);
                            } catch (Exception e) {
                                temp = "";
                            }
                            idcardBeanBean.setIdNation(temp);
                            faceRecTvNation.setText( temp);
                            mInfo.setNation(temp);
                            // 出生日期
                            temp = new String(tTwoIdInfo.arrTwoIdBirthday, 0,
                                    16, "UTF-16LE").trim();
                            idcardBeanBean.setIdBirthday(temp);
                            faceRecTvYear.setText(temp.substring(0,4));
                            mInfo.setYear(temp.substring(0,4));
                            faceRecTvMonth.setText(temp.substring(4,6));
                            mInfo.setMonth(temp.substring(4,6));
                            faceRecTvDay.setText(temp.substring(6,8));
                            mInfo.setDay(temp.substring(6,8));
                            // 住址
                            temp = new String(tTwoIdInfo.arrTwoIdAddress, 0,
                                    70, "UTF-16LE").trim();
                            idcardBeanBean.setIdAddress(temp);
                            faceRecTvAddr.setText(temp);
                            mInfo.setAddress(temp);
                            // 身份证号码
                            temp = new String(tTwoIdInfo.arrTwoIdNo, 0, 36,
                                    "UTF-16LE").trim();
                            imageBean.setImgName(temp);
                            idcardBeanBean.setIdCard(temp);
                            faceRecTvIdcard.setText( temp);
                            mInfo.setCardNo(temp);
                            // 签发机关
                            temp = new String(
                                    tTwoIdInfo.arrTwoIdSignedDepartment, 0, 30,
                                    "UTF-16LE").trim();
                            idcardBeanBean.setIdLicenceAuthority(temp);
                            //AddLog("签发机关:" + temp);
                            // 有效期起始日期
                            temp = new String(
                                    tTwoIdInfo.arrTwoIdValidityPeriodBegin, 0,
                                    16, "UTF-16LE").trim();
                            String validateDate = temp;
                            //AddLog("有效期起始日期:" + temp);
                            // 有效期截止日期 UNICODE YYYYMMDD 有效期为长期时存储“长期”
                            temp = new String(
                                    tTwoIdInfo.arrTwoIdValidityPeriodEnd, 0,
                                    16, "UTF-16LE").trim();
                            //AddLog("有效期截止日期:" + temp);
                            validateDate+=" - "+temp;
                            idcardBeanBean.setIdValidateDate(validateDate);
                            if (tTwoIdInfo.unTwoIdPhotoJpegLength > 0) {
                                Bitmap photo = BitmapFactory.decodeByteArray(
                                        tTwoIdInfo.arrTwoIdPhotoJpeg, 0,
                                        tTwoIdInfo.unTwoIdPhotoJpegLength);
                                imageBean.setImgStr(Base64Util.bitmapToBase64(photo));
                                faceRecIvHead.setImageBitmap(photo);
                                mInfo.setImage(photo);
                                Mat mat = new Mat();
                                Utils.bitmapToMat(photo,mat);
                               feature1 = ZTEUtils.getFaceFeature(mat);
                            }

                            idcardBeanBean.setIdEntityType("1");
                            recognitionModel.setIdcardBean(idcardBeanBean);



                            imageBean.setImgType("1");

                            recognitionModel.setIdCardImage(imageBean);

                            hasImage= false;
                            sendFaceToServer();

                        } catch (UnsupportedEncodingException e1) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        }

                    } else {
                        frameLayout.setVisibility(View.GONE);
                        CustomToast.showToast(this,"身份证信息获取失败");
                        //AddLog("GetTwoIdInfoByMcuServer执行失败");
                    }

                    // } catch (IOException e) {
                    // // TODO Auto-generated catch block
                    // e.printStackTrace();
                    // AddLog(e.getMessage());
                    // }
                }else{
                    frameLayout.setVisibility(View.GONE);
                    CustomToast.showToast(this,"连接失败");
                }
            }
        }
    }



    private String decodeNation(int code) {
        String nation;
        switch (code) {
            case 1:
                nation = "汉";
                break;
            case 2:
                nation = "蒙古";
                break;
            case 3:
                nation = "回";
                break;
            case 4:
                nation = "藏";
                break;
            case 5:
                nation = "维吾尔";
                break;
            case 6:
                nation = "苗";
                break;
            case 7:
                nation = "彝";
                break;
            case 8:
                nation = "壮";
                break;
            case 9:
                nation = "布依";
                break;
            case 10:
                nation = "朝鲜";
                break;
            case 11:
                nation = "满";
                break;
            case 12:
                nation = "侗";
                break;
            case 13:
                nation = "瑶";
                break;
            case 14:
                nation = "白";
                break;
            case 15:
                nation = "土家";
                break;
            case 16:
                nation = "哈尼";
                break;
            case 17:
                nation = "哈萨克";
                break;
            case 18:
                nation = "傣";
                break;
            case 19:
                nation = "黎";
                break;
            case 20:
                nation = "傈僳";
                break;
            case 21:
                nation = "佤";
                break;
            case 22:
                nation = "畲";
                break;
            case 23:
                nation = "高山";
                break;
            case 24:
                nation = "拉祜";
                break;
            case 25:
                nation = "水";
                break;
            case 26:
                nation = "东乡";
                break;
            case 27:
                nation = "纳西";
                break;
            case 28:
                nation = "景颇";
                break;
            case 29:
                nation = "柯尔克孜";
                break;
            case 30:
                nation = "土";
                break;
            case 31:
                nation = "达斡尔";
                break;
            case 32:
                nation = "仫佬";
                break;
            case 33:
                nation = "羌";
                break;
            case 34:
                nation = "布朗";
                break;
            case 35:
                nation = "撒拉";
                break;
            case 36:
                nation = "毛南";
                break;
            case 37:
                nation = "仡佬";
                break;
            case 38:
                nation = "锡伯";
                break;
            case 39:
                nation = "阿昌";
                break;
            case 40:
                nation = "普米";
                break;
            case 41:
                nation = "塔吉克";
                break;
            case 42:
                nation = "怒";
                break;
            case 43:
                nation = "乌孜别克";
                break;
            case 44:
                nation = "俄罗斯";
                break;
            case 45:
                nation = "鄂温克";
                break;
            case 46:
                nation = "德昂";
                break;
            case 47:
                nation = "保安";
                break;
            case 48:
                nation = "裕固";
                break;
            case 49:
                nation = "京";
                break;
            case 50:
                nation = "塔塔尔";
                break;
            case 51:
                nation = "独龙";
                break;
            case 52:
                nation = "鄂伦春";
                break;
            case 53:
                nation = "赫哲";
                break;
            case 54:
                nation = "门巴";
                break;
            case 55:
                nation = "珞巴";
                break;
            case 56:
                nation = "基诺";
                break;
            case 97:
                nation = "其他";
                break;
            case 98:
                nation = "外国血统中国籍人士";
                break;
            default:
                nation = "";
        }

        return nation;
    }



}
