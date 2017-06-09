package com.huiyu.tech.zhongxing.btutil;

import android.bluetooth.BluetoothDevice;
import android.graphics.Bitmap;

import com.Routon.iDRBtLib.iDRBtDev;
import com.Routon.iDRBtLib.iDRBtDev.SecondIDInfo;

public class BTCardReader {

    private iDRBtDev mBTDevice = null;
    private SecondIDInfo sIDInfo = null;

    private BTCardReader() {
        mBTDevice = new iDRBtDev();
    }

    private static BTCardReader instance = null;
    public static BTCardReader getReader() {
        if (null == instance) {
            instance = new BTCardReader();
        }
        return instance;
    }

    /*0*/ public static final int ReaderType_Unknown = iDRBtDev.READER_TYPE_unknown;
    /*1*/ public static final int ReaderType_230 = iDRBtDev.READER_TYPE_230;
    /*2*/ public static final int ReaderType_240 = iDRBtDev.READER_TYPE_240;
    /*3*/ public static final int ReaderType_260 = iDRBtDev.READER_TYPE_260;
    
    public int openDevice(BluetoothDevice device) {
        int ret = 0;
        if(!mBTDevice.isConnected()){
            mBTDevice.closeDevice();
            ret = mBTDevice.openDevice(device);
        }
        return ret;
    }

    public void closeDevice() {
        mBTDevice.closeDevice();
    }

    public int getReaderModal(){
        return mBTDevice.GetDeviceModel();
    }
    
    public int checkDeviceStatus() {
        return mBTDevice.GetSamStaus();
    }

    public int findCard() {
        return mBTDevice.Authenticate();
    }

    public int selectCard() {
        return 0;
    }

    public Info readCard() {
        sIDInfo = mBTDevice.new SecondIDInfo();
        final int ret = mBTDevice.ReadBaseMsg(sIDInfo);
        Info info = null;
        if (ret == 0) {
            info = new Info();
            info.name = sIDInfo.name;
            info.sex = sIDInfo.gender;
            info.nation = sIDInfo.folk;
            info.birthday = sIDInfo.birthday;

            info.address = sIDInfo.address;
            info.idNum = sIDInfo.id;
            info.author = sIDInfo.agency;
            info.validDateStart = sIDInfo.expireStart;
            info.validDateEnd = sIDInfo.expireEnd;
            info.image = sIDInfo.photo;
        }
        
        return info;
    }

    public SecondIDInfo getSecondIDInfo(){
        return sIDInfo;
    }
    
    public class Info {
        public String name;
        public String sex;
        public String nation;
        public String birthday;
        public String year;
        public String month;
        public String day;
        public String address;
        public String idNum;
        public String author;
        public String validDateStart;
        public String validDateEnd;
        public Bitmap image;
        public String cardNo;

        @Override
        public String toString() {
            return "Info{" +
                    "name='" + name + '\'' +
                    ", sex='" + sex + '\'' +
                    ", nation='" + nation + '\'' +
                    ", birthday='" + birthday + '\'' +
                    ", year='" + year + '\'' +
                    ", month='" + month + '\'' +
                    ", day='" + day + '\'' +
                    ", address='" + address + '\'' +
                    ", idNum='" + idNum + '\'' +
                    ", author='" + author + '\'' +
                    ", validDateStart='" + validDateStart + '\'' +
                    ", validDateEnd='" + validDateEnd + '\'' +
                    ", image=" + image +
                    ", cardNo='" + cardNo + '\'' +
                    '}';
        }
    }

    public int readIDCardAppendInfo(){
        iDRBtDev.MoreAddrInfo more = mBTDevice.new MoreAddrInfo();
        int ret = mBTDevice.GetNewAppMsg(more);
        return ret;
    }

    public int getIDCardCardID(byte []data){
        return mBTDevice.getIDCardCID(data);
    }
    
    public int typeAFindCard(){
        return mBTDevice.typeAFindCard();
    }

    public int setBeepLed(boolean beepOn, boolean ledOn, int duration){
        return mBTDevice.BeepLed(beepOn, ledOn, duration);
    }
    public int typeAReadCID(byte[] data){
        return mBTDevice.typeAReadCID(data);
    }

    public int typeAReadBlock(byte sectorNo, byte blockNo, byte encyType, byte[] encyKey, byte[] data){
        return mBTDevice.typeAReadBlock(sectorNo, blockNo, encyType, encyKey, data);
    }
    
    public int typeAWriteBlock(byte sectorNo, byte blockNo, byte encyType, byte[] encyKey, byte[] data){
        return mBTDevice.typeAWriteBlock(sectorNo, blockNo, encyType, encyKey, data);
    }
}
