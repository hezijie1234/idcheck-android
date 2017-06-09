package com.huiyu.tech.zhongxing.models;

/**
 * Created by ml on 2016/12/22.
 */
public class HandlerResultModel {

    /**
     * id : 14a77f19f3f941ae8c95954f50612ec1
     * isNewRecord : false
     * createDate : 2016-08-24 12:27:31
     * updateDate : 2016-12-22 11:41:47
     * checkLogId : ab1e21126580401d9dcdb202baecbafb
     * userName : 王南杰
     * idcard : 431002198411303015
     * sex : male
     * stationName : 汉口站
     * placeNo : 2
     * checkinTime : 2016-08-24 12:27:43
     * alarmLevel : 2
     * alarmType : system
     * status : 1
     * autoCheckResult : 5
     * manualCheckResult : 0
     * matchValue : 36.489
     * nation : 汉
     * address : 武汉市洪山区珞喻路1037号
     * videoname : 65摄像头
     * videoip : 192.168.1.65
     * videoport : 80
     * videouser : admin
     * videopass : admin123
     */

    private AlarmBean alarm;
    /**
     * alarm : {"id":"14a77f19f3f941ae8c95954f50612ec1","isNewRecord":false,"createDate":"2016-08-24 12:27:31","updateDate":"2016-12-22 11:41:47","checkLogId":"ab1e21126580401d9dcdb202baecbafb","userName":"王南杰","idcard":"431002198411303015","sex":"male","stationName":"汉口站","placeNo":"2","checkinTime":"2016-08-24 12:27:43","alarmLevel":"2","alarmType":"system","status":"1","autoCheckResult":5,"manualCheckResult":0,"matchValue":36.489,"nation":"汉","address":"武汉市洪山区珞喻路1037号","videoname":"65摄像头","videoip":"192.168.1.65","videoport":"80","videouser":"admin","videopass":"admin123"}
     * alarmretid : c54cd58160cc49f1b4a6793c203599a9
     * videofile : alarmret\videofile\20161222114147_VID_20161222_110018.mp4
     */

    private String alarmretid;
    private String videofile;

    public AlarmBean getAlarm() {
        return alarm;
    }

    public void setAlarm(AlarmBean alarm) {
        this.alarm = alarm;
    }

    public String getAlarmretid() {
        return alarmretid;
    }

    public void setAlarmretid(String alarmretid) {
        this.alarmretid = alarmretid;
    }

    public String getVideofile() {
        return videofile;
    }

    public void setVideofile(String videofile) {
        this.videofile = videofile;
    }

    public static class AlarmBean {
        private String id;
        private boolean isNewRecord;
        private String createDate;
        private String updateDate;
        private String checkLogId;
        private String userName;
        private String idcard;
        private String sex;
        private String stationName;
        private String placeNo;
        private String checkinTime;
        private String alarmLevel;
        private String alarmType;
        private String status;
        private int autoCheckResult;
        private int manualCheckResult;
        private double matchValue;
        private String nation;
        private String address;
        private String videoname;
        private String videoip;
        private String videoport;
        private String videouser;
        private String videopass;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public boolean isIsNewRecord() {
            return isNewRecord;
        }

        public void setIsNewRecord(boolean isNewRecord) {
            this.isNewRecord = isNewRecord;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public String getUpdateDate() {
            return updateDate;
        }

        public void setUpdateDate(String updateDate) {
            this.updateDate = updateDate;
        }

        public String getCheckLogId() {
            return checkLogId;
        }

        public void setCheckLogId(String checkLogId) {
            this.checkLogId = checkLogId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getIdcard() {
            return idcard;
        }

        public void setIdcard(String idcard) {
            this.idcard = idcard;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getStationName() {
            return stationName;
        }

        public void setStationName(String stationName) {
            this.stationName = stationName;
        }

        public String getPlaceNo() {
            return placeNo;
        }

        public void setPlaceNo(String placeNo) {
            this.placeNo = placeNo;
        }

        public String getCheckinTime() {
            return checkinTime;
        }

        public void setCheckinTime(String checkinTime) {
            this.checkinTime = checkinTime;
        }

        public String getAlarmLevel() {
            return alarmLevel;
        }

        public void setAlarmLevel(String alarmLevel) {
            this.alarmLevel = alarmLevel;
        }

        public String getAlarmType() {
            return alarmType;
        }

        public void setAlarmType(String alarmType) {
            this.alarmType = alarmType;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public int getAutoCheckResult() {
            return autoCheckResult;
        }

        public void setAutoCheckResult(int autoCheckResult) {
            this.autoCheckResult = autoCheckResult;
        }

        public int getManualCheckResult() {
            return manualCheckResult;
        }

        public void setManualCheckResult(int manualCheckResult) {
            this.manualCheckResult = manualCheckResult;
        }

        public double getMatchValue() {
            return matchValue;
        }

        public void setMatchValue(double matchValue) {
            this.matchValue = matchValue;
        }

        public String getNation() {
            return nation;
        }

        public void setNation(String nation) {
            this.nation = nation;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getVideoname() {
            return videoname;
        }

        public void setVideoname(String videoname) {
            this.videoname = videoname;
        }

        public String getVideoip() {
            return videoip;
        }

        public void setVideoip(String videoip) {
            this.videoip = videoip;
        }

        public String getVideoport() {
            return videoport;
        }

        public void setVideoport(String videoport) {
            this.videoport = videoport;
        }

        public String getVideouser() {
            return videouser;
        }

        public void setVideouser(String videouser) {
            this.videouser = videouser;
        }

        public String getVideopass() {
            return videopass;
        }

        public void setVideopass(String videopass) {
            this.videopass = videopass;
        }
    }
}
