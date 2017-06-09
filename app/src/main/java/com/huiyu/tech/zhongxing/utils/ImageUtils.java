package com.huiyu.tech.zhongxing.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.renderscript.Allocation;
import android.renderscript.RenderScript;
import android.widget.ImageView;

import com.huiyu.tech.zhongxing.Constants;
import com.huiyu.tech.zhongxing.R;
import com.huiyu.tech.zhongxing.api.ApiImpl;
import com.squareup.picasso.Picasso;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by ml on 2016/7/26.
 */
public class ImageUtils {
    public final static int DEFAULT_HEAD_RESOURCE = R.mipmap.jz_07;
    public final static int DEFAULT__GROUP_HEAD_RESOURCE = R.drawable.loading_big_bg;
    public final static int DEFAULT_PIC_RESOURCE = R.drawable.loading_big_bg;

    /**
     * 加载图片
     * @param context
     * @param url
     * @param image
     * @param def
     */
    public static void setImage(Context context, String url, ImageView image, int def){
        LogUtils.i("url="+ ApiImpl.HOST + url);
        Picasso.with(context).load(ApiImpl.HOST + "/"+url)
                .placeholder(def)
                .error(def)
                .fit()
                .into(image);
    }

    /**
     * 加载图片
     * @param context
     * @param url
     * @param image
     */
    public static void setImage(Context context, Uri url, ImageView image){
        Picasso.with(context).load(url)
                .placeholder(DEFAULT_HEAD_RESOURCE)
                .error(DEFAULT_HEAD_RESOURCE)
                .fit()
                .into(image);
    }

    /**
     * 加载头像
     * @param context
     * @param url
     * @param image
     */
    public static void setHeadImage(Context context, String url, ImageView image){
        setImage(context, url, image, DEFAULT_HEAD_RESOURCE);
    }
    /**
     * 加载头像
     * @param context
     * @param url
     * @param image
     */
    public static void setListImage(Context context, String url, ImageView image){
        setImage(context,url,image,DEFAULT_HEAD_RESOURCE);
    }

    /**
     * 从文件读取bitmap，大小转为1280*960尺寸（微信比例）
     *
     * 返回bitmap
     */
    public static Bitmap compressImageFromFile(String srcPath) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        newOpts.inJustDecodeBounds = true;// 只读边,不读内容
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);

        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        float hh = 640f;//
        float ww = 960f;//
        int be = 1;
        if (w > h && w > ww) {
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0){
            be = 1;
        }
        newOpts.inSampleSize = be;// 设置采样率

        newOpts.inPreferredConfig = Bitmap.Config.RGB_565;// 默认ARGB_8888 ,可不设

        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        // return compressBmpFromBmp(bitmap);//原来的方法调用了这个方法企图进行二次压缩
        // 其实是无效的,大家尽管尝试
        return bitmap;
    }


    /**
     * @param image
     * @return
     */
    public static Bitmap compressImage(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while ( baos.toByteArray().length / 1024 > 20) {  //循环判断如果压缩后图片是否大于20kb,大于继续压缩
            LogUtils.i("=====压缩后大小===="+baos.toByteArray().length/1024);
            baos.reset();//重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;//每次都减少10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        return BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
    }

    /**
     * 将Bitmap转换成File
     * @param bm
     * @param fileName
     * @return
     */
    public static File Bitmap2File(Bitmap bm, String fileName, int quality) {
        File f = new File(Constants.PICS);
        if (!f.exists()) {
            f.mkdirs();
        }
        File file=new File(Constants.PICS,fileName);//将要保存图片的路径
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bm.compress(Bitmap.CompressFormat.JPEG, quality, bos);
            bos.flush();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    /**
     * 保存bitmap到本地
     * @param bm bitmap
     * @param path 路径
     * @param quality 比例
     * @return
     */
    public static File saveBitmapLocal(Bitmap bm, String path, int quality){
        File f = new File(path.substring(path.lastIndexOf(".")));
        if (!f.exists()) {
            f.mkdirs();
        }
        File file = new File(path);//将要保存图片的路径
        if (!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bm.compress(Bitmap.CompressFormat.JPEG, quality, bos);
            bos.flush();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }


    /**
     * 裁剪图片
     *
     * @param activity activity
     * @param uri      地址
     * @param outputX  宽度像素
     * @param outputY  高度像素
     */
    public static void cropImage(Activity activity, Uri uri, int outputX, int outputY, boolean isLarge) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            String url = FileUtil.getPath(activity, uri);
            intent.setDataAndType(Uri.fromFile(new File(url)), "image/*");
        } else {
            intent.setDataAndType(uri, "image/*");
        }
        // crop为true是设置在开启的intent中设置显示的view可以剪裁
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX,outputY 是剪裁图片的宽高
        intent.putExtra("outputX", isLarge ? outputX : 160);
        intent.putExtra("outputY", isLarge ? outputX : 160);

        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true);
        intent.putExtra("return-data", true);
        activity.startActivityForResult(intent, Constants.REQUEST_PHOTO_CROP);
    }

    public static Bitmap blurBitmap(Context context, Bitmap bitmap){

        //Let's create an empty bitmap with the same size of the bitmap we want to blur
        Bitmap outBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);

        //Instantiate a new Renderscript
        RenderScript rs = RenderScript.create(context);

        //Create an Intrinsic Blur Script using the Renderscript
//        ScriptIntrinsicBlur blurScript = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));

        //Create the Allocations (in/out) with the Renderscript and the in/out bitmaps
        Allocation allIn = Allocation.createFromBitmap(rs, bitmap);
        Allocation allOut = Allocation.createFromBitmap(rs, outBitmap);

        //Set the radius of the blur
//        blurScript.setRadius(25.f);
//
//        //Perform the Renderscript
//        blurScript.setInput(allIn);
//        blurScript.forEach(allOut);

        //Copy the final bitmap created by the out Allocation to the outBitmap
        allOut.copyTo(outBitmap);

        //recycle the original bitmap
        bitmap.recycle();

        //After finishing everything, we destroy the Renderscript.
        rs.destroy();

        return outBitmap;
    }
}
