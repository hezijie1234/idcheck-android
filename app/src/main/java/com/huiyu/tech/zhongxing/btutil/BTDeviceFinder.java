package com.huiyu.tech.zhongxing.btutil;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

public class BTDeviceFinder {

    private static final String TAG = "Routon.BTDevFinder";
    private Activity mActivityInstance = null;
    private BluetoothAdapter mBtDevAdapter = null;
    private boolean mFlagAdapterTrunOnByMe = false;

    private static BTDeviceFinder instance = null;

    public static BTDeviceFinder getFinder() {
        return instance;
    }

    public static BTDeviceFinder getFinder(Activity a) {
        if (null == instance) {
            instance = new BTDeviceFinder(a);
        }
        return instance;
    }

    private BTDeviceFinder(Activity a) {
        mActivityInstance = a;
        mBtDevAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    /**
     * @return -1: no adapter <br>
     *         -2: has adapter but the Adapter is not opened.<br>
     *          0: has adapter and the Adapter is opened.
     */
    public int checkBTAdapter() {
        if (null == mBtDevAdapter) { // no bluetooth adapter;
            return -1;
        }

        if (!mBtDevAdapter.isEnabled()) {
            return -2;
        }
        return 0;
    }

    public void openBTAdapter(){
        if (!mBtDevAdapter.isEnabled()) {
            mFlagAdapterTrunOnByMe = true;

            // open adapter directly, no asking
            // return mBtDevAdapter.enable();
            // ask user to open adapter
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            //intent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
            mActivityInstance.startActivity(intent);
        }
    }

    private Intent mRegisteredBroadCast = null;
    public int searchtBTDevice() {
        int ret = checkBTAdapter();
        if(ret != 0){
            return ret;
        }

        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothDevice.ACTION_FOUND);
        filter.setPriority(Integer.MAX_VALUE);
        mRegisteredBroadCast = mActivityInstance.registerReceiver(btDevFoundReceiver, filter);

        if (mBtDevAdapter.isDiscovering()) {
            mBtDevAdapter.cancelDiscovery();
        }
        mBtDevAdapter.startDiscovery();
        return 0;
    }

    public int cancleSearchDev() {
        if (null != mBtDevAdapter && mBtDevAdapter.isDiscovering()) {
            mBtDevAdapter.cancelDiscovery();
        }

        if(mRegisteredBroadCast != null){
            mActivityInstance.unregisterReceiver(btDevFoundReceiver);
        }
        return 0;
    }

    private BroadcastReceiver btDevFoundReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.i(TAG, "action:" + action);
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if (listener != null) {
                    listener.onDevieFound(device);
                }
                Log.i(TAG, "device:" + device.getName());
            }
        }
    };

    public static BluetoothDevice getDevice(String address)
            throws IllegalArgumentException {
        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        if (adapter != null) {
            return adapter.getRemoteDevice(address);
        } else {
            return null;
        }
    }

    private OnDeviceFoundListener listener = null;

    public void setClientListener(OnDeviceFoundListener listener) {
        this.listener = listener;
    }

    public interface OnDeviceFoundListener {
        public void onDevieFound(BluetoothDevice device);
    }

    /**
     * if the Adapter turn on by me, you can choose to turn off it, <br>
     * class instance has recorded the action to turn on adapter, <br>
     * only to turn off adapter while has a record.
     * 
     * @return true:turn off successful<br>
     *         false:failed
     */
    public boolean turnOffAdapter() {
        return mFlagAdapterTrunOnByMe && mBtDevAdapter.disable();
    }

}
