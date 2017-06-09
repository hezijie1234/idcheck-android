package com.huiyu.tech.zhongxing.btutil;

import android.app.Activity;
import android.content.SharedPreferences;

public class DeviceRecoder {

	public static void recodeMac(Activity a, String name, String mac) {
		SharedPreferences mSP = a.getPreferences(Activity.MODE_PRIVATE);
		SharedPreferences.Editor spEd = mSP.edit();
		spEd.putString("name", name);
		spEd.putString("mac", mac);
		spEd.commit();
	}

	public static String[] getMac(Activity a) {
		SharedPreferences mSP = a.getPreferences(Activity.MODE_PRIVATE);
		String devinfo [] = new String[2];
		devinfo[0] = mSP.getString("name", "");
		devinfo[1] = mSP.getString("mac", "请选取一个设备");
		return devinfo;
	}

}
