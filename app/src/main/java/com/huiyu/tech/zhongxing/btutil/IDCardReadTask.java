package com.huiyu.tech.zhongxing.btutil;


import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.util.Log;

import com.huiyu.tech.zhongxing.R;


public class IDCardReadTask {

    public static final int Status_read_successful = 0;
    public static final int Status_no_card = 1;
    public static final int Status_device_disconnected = 2;
    public static final int Status_is_reading = 3;
    public static final int Status_findCard_successful = 4;
    public static final int Status_lunch_task_successful = 5;
    public static final int Status_check_device_not_ready = 6;
    public static final int Status_no_action = 7;
    public static final int Status_findCard_error = 8;
    public static final int Status_error_open_device_failed = 10;
    public static final int Status_error_check_device_status = 11;
    public static final int Status_error_select_card_failed = 12;
    public static final int Status_error_read_failed = 13;
    public static final int Status_error_mac_format_incorrect = 14;

    private ReadTask mReadTask = null;
    private boolean  bLoopRead = false;
    private String mUserSelectedBTDevMac = null;

    private IDCardReadTask(){}

    private static IDCardReadTask instance = null;
    public static IDCardReadTask getReadTask() {
        if (instance == null) {
            instance = new IDCardReadTask();
        }
        return instance;
    }

    public void setCallback(ReaderTaskListener l){
        this.listener = l;
    }

    private boolean checkMacAddressFormat(String mac){
        if(null == mac){
            return false;
        }
        return mac.matches("^([0-9a-fA-F]{2})(([:][0-9a-fA-F]{2}){5})$");
    }

    public int readCard(String macAddress, boolean isLoopRead){
        mUserSelectedBTDevMac = macAddress;
        boolean ret = checkMacAddressFormat(mUserSelectedBTDevMac);
        if(!ret){
            return Status_error_mac_format_incorrect;
        }

        if(null == mReadTask || mReadTask.isCancelled()){
            bLoopRead = isLoopRead;
            mReadTask = new ReadTask();
            mReadTask.execute();
            initTestData();
            return Status_lunch_task_successful;
        }else{
            return Status_is_reading;
        }
    }

    public void stopLoopRead() {
        bLoopRead = false;
    }

    private class ReadTask extends AsyncTask<String, Integer, BTCardReader.Info> {

        private static final String TAG = "IDCardReadTask";
        private int mStatus = Status_no_action;
        private BTCardReader.Info info = null;
        private long mSignalReadingStartTime = 0;
        private long mSignalReadingEndTime = 0;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected BTCardReader.Info doInBackground(String... params) {
            Log.i(TAG, "... start to read card in background");
            mSignalReadingStartTime = 0;
            mSignalReadingEndTime = 0;

            // Get blueTooth device and open it
            BTCardReader mBtCardReader = BTCardReader.getReader();
            BluetoothDevice dev = BTDeviceFinder.getDevice(mUserSelectedBTDevMac);
            int ret = mBtCardReader.openDevice(dev);
            if (ret != 0) {
                mStatus = Status_error_open_device_failed;
                // open device failed, return directly.
                return null;
            }

            final int readerType = mBtCardReader.getReaderModal();
            Log.i(TAG, "reader type:" + readerType);

            // check device status
            ret = mBtCardReader.checkDeviceStatus();
            if (ret != 0) {
                mStatus = Status_check_device_not_ready;
            }else{
                // start to read card
                do {
                    ret = mBtCardReader.findCard();
                    if(0 == ret){
                        String cardNo = "";
                        if(readerType > BTCardReader.ReaderType_230){
                            cardNo = testReadIDCardCardID(mBtCardReader);
                        }
                        mStatus = Status_findCard_successful;
                        // find card success. notice UI clean idcardInfo
                        publishProgress(1);

                        mSignalReadingStartTime = System.currentTimeMillis();

                        ret = mBtCardReader.selectCard();
                        if(0 == ret){
                            info = mBtCardReader.readCard();
                            if (null != info) { // read card successful
                                info.cardNo = cardNo;
                                mStatus = Status_read_successful;
                                mSignalReadingEndTime = System.currentTimeMillis();
                            } else {
                                mStatus = Status_error_read_failed;
                            }

                        }else{
                            mStatus = Status_error_select_card_failed;
                        }
                    }else if(-15 == ret){
                        mStatus = Status_no_card;
                    }else if(-14 == ret){
                        mStatus = Status_device_disconnected;
                        break; //the device is disconnected. break read loop
                    }else {
                        mStatus = Status_findCard_error;
                    }

                    if(bLoopRead){
                        publishProgress(0);

                        if(Status_read_successful == mStatus){
                            long startTime = System.currentTimeMillis();
                            do{
                                ret = mBtCardReader.readIDCardAppendInfo();
                                long currentTime = System.currentTimeMillis();
                                if( (!bLoopRead) || (ret < 0) || (currentTime - startTime) > 5000){
                                    break;
                                }
                                SystemClock.sleep(100);
                            }while(true);
                        }
                    }

                    SystemClock.sleep(1000);
                    if (!bLoopRead) {
                        break;
                    }
                } while (true);
            }

            // You must close device if you open device successful
            mBtCardReader.closeDevice();
            return info;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            if (values[0] == 0) {
                Log.i(TAG, "... callback : " + mStatus);
                if(Status_read_successful == mStatus){
                    mSignalReadingTime = Math.abs(mSignalReadingEndTime - mSignalReadingStartTime);
                }else{
                    mSignalReadingTime = 0;
                }
                mSignalReadingStartTime = 0;
                mSignalReadingEndTime = 0;
                setTestData(mStatus);

                if(listener != null){
                    listener.readResult(info, mStatus);
                }
            } else if(values[0] == 1 && listener != null){
                listener.readResult(null, mStatus);
            }

        }

        @Override
        protected void onPostExecute(BTCardReader.Info info) {
            super.onPostExecute(info);
            Log.i(TAG, "... read task finish, status is " + mStatus);

            mSignalReadingTime = Math.abs(mSignalReadingEndTime - mSignalReadingStartTime);
            mSignalReadingStartTime = 0;
            mSignalReadingEndTime = 0;

            setTestData(mStatus);
            if (listener != null) {
                listener.readResult(info, mStatus);
            }
            cancel(false);
            // some phone can't get cancel status. eg:HuaWei Y500, so set null to mReadTask
            mReadTask = null;

        }

        private String testReadIDCardCardID(BTCardReader reader){
            String cardID = "";
            byte[] cardNo = new byte[16];
            int ret = reader.getIDCardCardID(cardNo);
            if(ret < 0) {
                Log.e(TAG, "read card no failed:" + ret);
            }else{
                cardID = String.format("%02x%02x%02x%02x%02x%02x%02x%02x", cardNo[0], cardNo[1], cardNo[2], cardNo[3], cardNo[4], cardNo[5], cardNo[6], cardNo[7]);
                Log.e(TAG, "IDCard No:" + cardID);
            }
            return cardID.toUpperCase();
        }
    }

    private ReaderTaskListener listener = null;
    public interface ReaderTaskListener {
        public void readResult(BTCardReader.Info info, int status);
    }

    private long mCountsReadSucc = 0;
    private long mCountsReadFail = 0;
    private long mTestStratTime = 0;
    private long mSignalReadingTime = 0;
    private void initTestData(){
        mCountsReadSucc = 0;
        mCountsReadFail = 0;
        mTestStratTime = System.currentTimeMillis();
    }

    private void setTestData(int status){
        if(status == Status_read_successful){
            mCountsReadSucc++;
        } else if(status == Status_error_read_failed){
            mCountsReadFail++;
        }
    }

    public String getTestInfo(Context ctx){
        long testTime = System.currentTimeMillis() - mTestStratTime;
        long mSecond  = Math.abs(testTime)%1000;
        testTime = Math.abs(testTime)/1000;
        String testTimeStr = testTime/3600 + "h " + (testTime%3600)/60 + "m " + (testTime%60) + "s " + mSecond + "ms";

        String testInfoStr = ctx.getResources().getString(R.string.testInfoStr);
        String signalReadingTime = "" + mSignalReadingTime/1000 + "." + mSignalReadingTime%1000 + "s";
        testInfoStr = String.format(testInfoStr, testTimeStr, ""+mCountsReadSucc, ""+mCountsReadFail, signalReadingTime);
        return testInfoStr;
    }
}
