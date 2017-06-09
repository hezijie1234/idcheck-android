package com.huiyu.tech.zhongxing.utils;

import android.content.Context;
import android.widget.Toast;

public class CustomToast {

	private static Toast mToast;
	public static void showToast(Context context, String msg){
		if(mToast != null){
			mToast.setText(msg);
		}else{
			mToast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
		}
		mToast.show();
	}

	public static void showToast(Context context, String msg, int duration){
		if(mToast != null){
			mToast.setText(msg);
		}else{
			mToast = Toast.makeText(context, msg, duration);
		}
		mToast.show();
	}

	public static void showToast(Context context, int id){
		if(mToast != null){
			mToast.setText(id);
		}else{
			mToast = Toast.makeText(context, id, Toast.LENGTH_SHORT);
		}
		mToast.show();
	}

	public static void showLongToast(Context context, String msg){
		if(mToast != null){
			mToast.setText(msg);
		}else{
			mToast = Toast.makeText(context, msg, Toast.LENGTH_LONG);
		}
		mToast.show();
	}
}
