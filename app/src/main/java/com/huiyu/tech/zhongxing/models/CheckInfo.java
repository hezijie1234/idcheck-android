package com.huiyu.tech.zhongxing.models;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ml on 2016/11/9.
 */
public class CheckInfo {


    /**
     * listcount : 0
     * max_page : 228
     * list : [{"id":"fcd679fd821e455f9599cf96c66b8891","isNewRecord":false,"createDate":"2016-08-23 20:49:53","updateDate":"2016-08-23 20:49:53","checkLogId":"717c4ae8cce0449182f5e4f54dff9ce4","userName":"杨洁","idcard":"500239198701120015","sex":"male","stationName":"汉口站","placeNo":"7","checkinTime":"2016-08-23 20:50:00","alarmLevel":"2","alarmType":"system","status":"0","autoCheckResult":5,"manualCheckResult":0,"matchValue":49.473,"nation":"土家","address":"重庆市黔江区城东街道联合街280号3幢3单元3-5","idcardImg":{"path":"person_imgs/500239198701120015/idcard/idcard_20160823205000.jpg","title":"身份证照片","des":"身份证：500239198701120015，检票时间：2016-08-23 20:50:00","hidden":false},"frontImg":{"path":"person_imgs/500239198701120015/front_img/frontimg_20160823205000.png","title":"正面照片","des":"身份证：500239198701120015，检票时间：2016-08-23 20:50:00","hidden":false},"videoname":"camera1","videoip":"192.168.1.252","videoport":"80","videouser":"admin","videopass":"admin123456","serialno":"235"}]
     */

    private String listcount;
    private int max_page;
    /**
     * id : fcd679fd821e455f9599cf96c66b8891
     * isNewRecord : false
     * createDate : 2016-08-23 20:49:53
     * updateDate : 2016-08-23 20:49:53
     * checkLogId : 717c4ae8cce0449182f5e4f54dff9ce4
     * userName : 杨洁
     * idcard : 500239198701120015
     * sex : male
     * stationName : 汉口站
     * placeNo : 7
     * checkinTime : 2016-08-23 20:50:00
     * alarmLevel : 2
     * alarmType : system
     * status : 0
     * autoCheckResult : 5
     * manualCheckResult : 0
     * matchValue : 49.473
     * nation : 土家
     * address : 重庆市黔江区城东街道联合街280号3幢3单元3-5
     * idcardImg : {"path":"person_imgs/500239198701120015/idcard/idcard_20160823205000.jpg","title":"身份证照片","des":"身份证：500239198701120015，检票时间：2016-08-23 20:50:00","hidden":false}
     * frontImg : {"path":"person_imgs/500239198701120015/front_img/frontimg_20160823205000.png","title":"正面照片","des":"身份证：500239198701120015，检票时间：2016-08-23 20:50:00","hidden":false}
     * videoname : camera1
     * videoip : 192.168.1.252
     * videoport : 80
     * videouser : admin
     * videopass : admin123456
     * serialno : 235
     */

    private List<ListBean> list;

    public String getListcount() {
        return listcount;
    }

    public void setListcount(String listcount) {
        this.listcount = listcount;
    }

    public int getMax_page() {
        return max_page;
    }

    public void setMax_page(int max_page) {
        this.max_page = max_page;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean implements Serializable {
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
        /**
         * path : person_imgs/500239198701120015/idcard/idcard_20160823205000.jpg
         * title : 身份证照片
         * des : 身份证：500239198701120015，检票时间：2016-08-23 20:50:00
         * hidden : false
         */

        private IdcardImgBean idcardImg;
        /**
         * path : person_imgs/500239198701120015/front_img/frontimg_20160823205000.png
         * title : 正面照片
         * des : 身份证：500239198701120015，检票时间：2016-08-23 20:50:00
         * hidden : false
         */

        private FrontImgBean frontImg;
        private String videoname;
        private String videoip;
        private String videoport;
        private String videouser;
        private String videopass;
        private String serialno;

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

        public IdcardImgBean getIdcardImg() {
            return idcardImg;
        }

        public void setIdcardImg(IdcardImgBean idcardImg) {
            this.idcardImg = idcardImg;
        }

        public FrontImgBean getFrontImg() {
            return frontImg;
        }

        public void setFrontImg(FrontImgBean frontImg) {
            this.frontImg = frontImg;
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

        public String getSerialno() {
            return serialno;
        }

        public void setSerialno(String serialno) {
            this.serialno = serialno;
        }

        public static class IdcardImgBean  implements Serializable {
            private String path;
            private String title;
            private String des;
            private boolean hidden;

            public String getPath() {
                return path;
            }

            public void setPath(String path) {
                this.path = path;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getDes() {
                return des;
            }

            public void setDes(String des) {
                this.des = des;
            }

            public boolean isHidden() {
                return hidden;
            }

            public void setHidden(boolean hidden) {
                this.hidden = hidden;
            }
        }

        public static class FrontImgBean  implements Serializable {
            private String path;
            private String title;
            private String des;
            private boolean hidden;

            public String getPath() {
                return path;
            }

            public void setPath(String path) {
                this.path = path;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getDes() {
                return des;
            }

            public void setDes(String des) {
                this.des = des;
            }

            public boolean isHidden() {
                return hidden;
            }

            public void setHidden(boolean hidden) {
                this.hidden = hidden;
            }
        }
    }
}
