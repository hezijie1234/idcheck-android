package com.huiyu.tech.zhongxing.ui.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.huiyu.tech.zhongxing.Constants;
import com.huiyu.tech.zhongxing.R;
import com.huiyu.tech.zhongxing.api.ApiImpl;
import com.huiyu.tech.zhongxing.api.LoadingListener;
import com.huiyu.tech.zhongxing.models.HandlerResultModel;
import com.huiyu.tech.zhongxing.ui.ZZBaseActivity;
import com.huiyu.tech.zhongxing.ui.adapter.AddPicAdapter;
import com.huiyu.tech.zhongxing.utils.CustomToast;
import com.huiyu.tech.zhongxing.utils.FileUtil;
import com.huiyu.tech.zhongxing.utils.ImageUtils;
import com.huiyu.tech.zhongxing.utils.LogUtils;
import com.huiyu.tech.zhongxing.widget.MyGridView;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import me.nereo.multi_image_selector.MultiImageSelector;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * 警情处理界面
 */
public class EmergencyHandleActivity extends ZZBaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener, LoadingListener {

    public static final int MAXNUM = 5;
    private MyGridView gvPic;
    private RelativeLayout layoutVideo;
    private ImageView ivVideo;
    private ImageView ivDelete;
    private ImageButton ivAddVideo;
    private EditText etDesc;
    private TextView tvUndo;
    private TextView tvCommit;

    private AddPicAdapter addPicAdapter;

    private static final int REQUEST_STORAGE_READ_ACCESS_PERMISSION = 101;
    // 存放图片路径的list
    private ArrayList<String> mSelectPath;
    private List<Uri> bitmaps;

    private File file;//添加的附件

    private Intent intent;
    private String id;

    private String resultId;
    private ProgressDialog progressDialog;
    private int imageCount;


    // 使用系统当前日期加以调整作为照片的名称
    private String getPhotoFileName() {
        return "img_" + System.currentTimeMillis() + ".jpg";
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    int pos = msg.arg1;
                    mSelectPath.remove(pos);
                    bitmaps.remove(pos);
                    addPicAdapter.getItems().remove(pos);
                    if (!isContainDelete()) {
                        Uri delete = Uri.parse("delete");
                        bitmaps.add(delete);
                        addPicAdapter.getItems().add(delete);
                    }
                    addPicAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };

    private boolean isContainDelete() {
        boolean containDelete = false;
        for (Uri uri : bitmaps) {
            if (uri.getPath().equals("delete")) {
                containDelete = true;
                break;
            }
        }
        return containDelete;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_handle);

        initBase();
        initView();
        initData();
    }

    private void initBase() {
        intent = getIntent();
        if (intent.hasExtra(Constants.INTENT_KEY.KEY_ID)) {
            id = intent.getStringExtra(Constants.INTENT_KEY.KEY_ID);
        }
        bitmaps = new ArrayList<>();
    }

    private void initView() {
        showBackView();
        showTitleView(getResources().getString(R.string.title_handle_emergency));

        gvPic = (MyGridView) findViewById(R.id.gv_pic);
        layoutVideo = (RelativeLayout) findViewById(R.id.layout_video);
        ivDelete = (ImageView) findViewById(R.id.iv_delete);
        ivVideo = (ImageView) findViewById(R.id.iv_video);
        ivAddVideo = (ImageButton) findViewById(R.id.iv_add_video);
        etDesc = (EditText) findViewById(R.id.et_desc);
        tvUndo = (TextView) findViewById(R.id.tv_undo);
        tvCommit = (TextView) findViewById(R.id.tv_commit);

        gvPic.setOnItemClickListener(this);

        ivAddVideo.setOnClickListener(this);
        ivDelete.setOnClickListener(this);
        tvCommit.setOnClickListener(this);
    }

    private void initData() {
        addPicAdapter = new AddPicAdapter(this, handler);
        gvPic.setAdapter(addPicAdapter);

        initAddPic();
    }

    private void initAddPic() {
        bitmaps.add(Uri.parse("delete"));
        addPicAdapter.setItems(bitmaps);
    }

    private void pickImage() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN // Permission was added in API Level 16
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE,
                    getString(R.string.mis_permission_rationale),
                    REQUEST_STORAGE_READ_ACCESS_PERMISSION);
        } else {
            MultiImageSelector selector = MultiImageSelector.create(this);
            selector.showCamera(true);
            selector.count(MAXNUM);
            selector.multi();

            selector.origin(mSelectPath);
            selector.start(this, Constants.REQUEST_PHOTO_SELECTED);
        }
    }

    private void requestPermission(final String permission, String rationale, final int requestCode) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
            new AlertDialog.Builder(this)
                    .setTitle(R.string.mis_permission_dialog_title)
                    .setMessage(rationale)
                    .setPositiveButton(R.string.mis_permission_dialog_ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(EmergencyHandleActivity.this, new String[]{permission}, requestCode);
                        }
                    })
                    .setNegativeButton(R.string.mis_permission_dialog_cancel, null)
                    .create().show();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{permission}, requestCode);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_STORAGE_READ_ACCESS_PERMISSION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                pickImage();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    private void createVideoThumbnail(final String path) {
        Observable<Bitmap> observable = Observable.create(new Observable.OnSubscribe<Bitmap>() {
            @Override
            public void call(Subscriber<? super Bitmap> subscriber) {
                Bitmap bitmap = null;
                MediaMetadataRetriever retriever = new MediaMetadataRetriever();
                int kind = MediaStore.Video.Thumbnails.MINI_KIND;
                if (Build.VERSION.SDK_INT >= 14) {
                    try {
                        retriever.setDataSource(path, new HashMap<String, String>());
                    } catch (Exception e) {
                        retriever.setDataSource(path);
                    }
                } else {
                    retriever.setDataSource(path);
                }
                bitmap = retriever.getFrameAtTime(0);
                subscriber.onNext(bitmap);
                retriever.release();
            }
        });
//        observable.observeOn(Schedulers.io())
//                .subscribeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Action1<Bitmap>() {
//                    @Override
//                    public void call(Bitmap bitmap) {
//                        //设置封面
//                        ivVideo.setBackgroundDrawable(new BitmapDrawable(bitmap));
//                    }
//                });
        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.computation())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Action1<Bitmap>() {
                    @Override
                    public void call(Bitmap bitmap) {
                        //设置封面
                        ivVideo.setBackgroundDrawable(new BitmapDrawable(bitmap));
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        LogUtils.i("observable throwable");
                        ivVideo.setBackgroundResource(0);
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case Constants.REQUEST_FILE_SELECTED:
                    if (data != null) {
                        Uri uri = data.getData();
                        file = new File(FileUtil.getPath(this, uri));
//                        file = new File(uri.getPath());
                        LogUtils.i("file=" + file.length());
                        if (file.length() > 50 * 1024 * 1024L) {
                            CustomToast.showToast(this, "您选择的文件过大，请选择小于50MB的文件上传");
                            return;
                        }
                        layoutVideo.setVisibility(View.VISIBLE);
                        createVideoThumbnail(file.getPath());
                    }
                    break;
                case Constants.REQUEST_PHOTO_SELECTED:
                    mSelectPath = data.getStringArrayListExtra(MultiImageSelector.EXTRA_RESULT);
//                StringBuilder sb = new StringBuilder();
                    bitmaps.clear();
                    for (String p : mSelectPath) {
//                    sb.append(p);
//                    sb.append("\n");
//                    ImageModel imageModel = new ImageModel();
//                    imageModel.setId();
                        Uri uri = Uri.fromFile(new File(p));
                        bitmaps.add(uri);
                    }
                    if (bitmaps.size() < MAXNUM) {
                        bitmaps.add(Uri.parse("delete"));
                    }
                    addPicAdapter.setItems(bitmaps);
                    break;
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_add_video:
                if (!FileUtil.avaiableSDCard()) {
                    CustomToast.showToast(this, "没有检测到存储卡！");
                    return;
                }
                Intent intentFile = new Intent(Intent.ACTION_GET_CONTENT);
                intentFile.setType("video/mp4");//设置类型，我这里是任意类型，任意后缀的可以这样写。
                intentFile.addCategory(Intent.CATEGORY_OPENABLE);
                try {
                    startActivityForResult(Intent.createChooser(intentFile, "请选择一个要上传的文件"),
                            Constants.REQUEST_FILE_SELECTED);
                } catch (android.content.ActivityNotFoundException ex) {
                    // Potentially direct the user to the Market with a Dialog
                    CustomToast.showToast(this, "请安装文件管理器");
                }
                break;
            case R.id.iv_delete:
                layoutVideo.setVisibility(View.GONE);
                file = null;
                break;
            case R.id.tv_commit:
                if (bitmaps.size() <= 1 && file == null) {
                    CustomToast.showToast(this, "处理图片或视频必须上传一个");
                    return;
                }
                imageCount = isContainDelete() ? bitmaps.size() - 1 : bitmaps.size();
                if (file == null) {
                    showProgressDialog();
                } else {
                    showVideoProgress();
                }
                ApiImpl.getInstance().handlerEmergency(id, file, etDesc.getText().toString(), this);
                break;
        }
    }

    public void showVideoProgress() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setMax(100);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        LogUtils.i("position =" + position);
        if (position == addPicAdapter.getCount() - 1&&bitmaps.get(position).getPath().equals("delete")) {
            pickImage();
        }
    }

    @Override
    public void onAPISuccess(String flag, JSONObject json) {
        if (file == null && imageCount == 0) {
            hideProgressDialog();
        }
        switch (flag) {
            case ApiImpl.HANDLE_EMERGENCY:
                if(imageCount==0){
                    doFinish();
                }else{
                    HandlerResultModel handlerResultModel = JSON.parseObject(json.optString("d"), HandlerResultModel.class);
                    resultId = handlerResultModel.getAlarmretid();
                    if (bitmaps != null && bitmaps.size() > 0) {
                        for (int i = 0; i < imageCount; i++) {
                            File file = ImageUtils.Bitmap2File(ImageUtils.compressImageFromFile(bitmaps.get(i).getPath()), getPhotoFileName(), 90);
                            ApiImpl.getInstance().handlerEmergencyPic(resultId, file, this);
                        }
                    }
                }
                break;
            case ApiImpl.HANDLE_EMERGENCY_PIC:
                count++;
                if (progressDialog != null) {
                    progressDialog.setProgress(progressDialog.getProgress() + count);
                }
                if (count == imageCount) {
                    if (progressDialog != null){
                        progressDialog.dismiss();
                    }else{
                        hideProgressDialog();
                    }
                    doFinish();
                }
                break;
        }
    }

    private void doFinish() {
        CustomToast.showToast(this, "上传成功");
        setResult(RESULT_OK);
        finish();
    }

    private int count;

    @Override
    public void onAPIError(String flag, int code, String error) {
        hideProgressDialog();
        if(progressDialog!=null&&progressDialog.isShowing())progressDialog.dismiss();
        CustomToast.showToast(this, error);
    }

    @Override
    public void onLoading(long total, long progress) {
        int p = (int) (((float)progress / total - (float) imageCount / 100) * 100);
        progressDialog.setProgress(p);
        if (p == 100) {
            progressDialog.dismiss();
        }
    }
}
