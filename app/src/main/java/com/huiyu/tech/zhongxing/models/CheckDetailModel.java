package com.huiyu.tech.zhongxing.models;

import java.util.List;

/**
 * Created by ml on 2016/12/20.
 */
public class CheckDetailModel {

    /**
     * id : 107f886e605c11e6880b00cfe033fba6
     * isNewRecord : false
     * remarks : from python
     * createDate : 2016-08-12 15:12:01
     * updateDate : 2016-08-25 12:09:40
     * userName : 谭佳庆
     * sex : male
     * idcard : 450721197706035893
     * birthday : 1977-11-15
     * nation : 汉
     * address : 湖北武汉
     * validateDate : 2015.02.02-2020.05.06
     * licenceAuthority : 武汉公安局
     */

    private PersonBean person;
    /**
     * id : 49d5277130964defa694094e404fd9c6
     * isNewRecord : false
     * createDate : 2016-08-25 12:09:40
     * updateDate : 2016-08-25 12:09:40
     * checkLogId : 778a299f41fa4d82816595347f4c0ae9
     * userName : 谭佳庆
     * idcard : 450721197706035893
     * sex : male
     * stationName : 汉口站
     * placeNo : 7
     * checkinTime : 2016-08-25 16:02:12
     * alarmLevel : 1
     * alarmType : system
     * status : 0
     * autoCheckResult : 1
     * manualCheckResult : 0
     * matchValue : 60.2
     * nation : 汉
     * address : 湖北武汉
     */

    private AlarmBean alarm;
    /**
     * ticket : [{"id":"d22bdd615bba441da97c07db65b6d869","isNewRecord":false,"createDate":"2016-08-25 12:09:40","updateDate":"2016-08-25 12:09:40","checkLogId":"778a299f41fa4d82816595347f4c0ae9","ticketNo":"64598655449889 G032097","sellStation":"北京西","startAddress":"北京西","endAddress":"上海","trainNo":"G17次","trainRunTime":"2016-08-25 11:02:00","trainSeat":"7车15A号","ticketMoney":"92.0元","ticketType":"二等座","userName":"谭佳庆","idcard":"450721197706035893"},{"id":"8b6bd49776cb4b83a421d283befea42a","isNewRecord":false,"createDate":"2016-08-25 12:00:15","updateDate":"2016-08-25 12:00:15","checkLogId":"ad079306f2714eed8a437d3ee77aaad7","ticketNo":"64598655449889 G032097","sellStation":"武昌","startAddress":"武昌","endAddress":"上海","trainNo":"K72次","trainRunTime":"2016-08-25 11:02:00","trainSeat":"10车15A号","ticketMoney":"92.0元","ticketType":"硬座","userName":"谭佳庆","idcard":"450721197706035893"}]
     * person : {"id":"107f886e605c11e6880b00cfe033fba6","isNewRecord":false,"remarks":"from python","createDate":"2016-08-12 15:12:01","updateDate":"2016-08-25 12:09:40","userName":"谭佳庆","sex":"male","idcard":"450721197706035893","birthday":"1977-11-15","nation":"汉","address":"湖北武汉","validateDate":"2015.02.02-2020.05.06","licenceAuthority":"武汉公安局"}
     * alarm : {"id":"49d5277130964defa694094e404fd9c6","isNewRecord":false,"createDate":"2016-08-25 12:09:40","updateDate":"2016-08-25 12:09:40","checkLogId":"778a299f41fa4d82816595347f4c0ae9","userName":"谭佳庆","idcard":"450721197706035893","sex":"male","stationName":"汉口站","placeNo":"7","checkinTime":"2016-08-25 16:02:12","alarmLevel":"1","alarmType":"system","status":"0","autoCheckResult":1,"manualCheckResult":0,"matchValue":60.2,"nation":"汉","address":"湖北武汉"}
     * suspecttype : 2
     */

    private String suspecttype;
    /**
     * id : d22bdd615bba441da97c07db65b6d869
     * isNewRecord : false
     * createDate : 2016-08-25 12:09:40
     * updateDate : 2016-08-25 12:09:40
     * checkLogId : 778a299f41fa4d82816595347f4c0ae9
     * ticketNo : 64598655449889 G032097
     * sellStation : 北京西
     * startAddress : 北京西
     * endAddress : 上海
     * trainNo : G17次
     * trainRunTime : 2016-08-25 11:02:00
     * trainSeat : 7车15A号
     * ticketMoney : 92.0元
     * ticketType : 二等座
     * userName : 谭佳庆
     * idcard : 450721197706035893
     */

    private List<TicketBean> ticket;

    public PersonBean getPerson() {
        return person;
    }

    public void setPerson(PersonBean person) {
        this.person = person;
    }

    public AlarmBean getAlarm() {
        return alarm;
    }

    public void setAlarm(AlarmBean alarm) {
        this.alarm = alarm;
    }

    public String getSuspecttype() {
        return suspecttype;
    }

    public void setSuspecttype(String suspecttype) {
        this.suspecttype = suspecttype;
    }

    public List<TicketBean> getTicket() {
        return ticket;
    }

    public void setTicket(List<TicketBean> ticket) {
        this.ticket = ticket;
    }

    public static class PersonBean {
        private String id;
        private boolean isNewRecord;
        private String remarks;
        private String createDate;
        private String updateDate;
        private String userName;
        private String sex;
        private String idcard;
        private String birthday;
        private String nation;
        private String address;
        private String validateDate;
        private String licenceAuthority;

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

        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
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

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getIdcard() {
            return idcard;
        }

        public void setIdcard(String idcard) {
            this.idcard = idcard;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
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

        public String getValidateDate() {
            return validateDate;
        }

        public void setValidateDate(String validateDate) {
            this.validateDate = validateDate;
        }

        public String getLicenceAuthority() {
            return licenceAuthority;
        }

        public void setLicenceAuthority(String licenceAuthority) {
            this.licenceAuthority = licenceAuthority;
        }
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
        /**
         * idcardImg : {"path":"person_imgs/500239198701120015/idcard/idcard_20160823205000.jpg","title":"身份证照片","des":"身份证：500239198701120015，检票时间：2016-08-23 20:50:00","hidden":false}
         * frontImg : {"path":"person_imgs/500239198701120015/front_img/frontimg_20160823205000.png","title":"正面照片","des":"身份证：500239198701120015，检票时间：2016-08-23 20:50:00","hidden":false}
         * videoname : camera1
         * videoip : 192.168.1.252
         * videoport : 80
         * videouser : admin
         * videopass : admin123456
         * serialno : 235
         */

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

        public static class IdcardImgBean {
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

        public static class FrontImgBean {
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

    public static class TicketBean {
        private String id;
        private boolean isNewRecord;
        private String createDate;
        private String updateDate;
        private String checkLogId;
        private String ticketNo;
        private String sellStation;
        private String startAddress;
        private String endAddress;
        private String trainNo;
        private String trainRunTime;
        private String trainSeat;
        private String ticketMoney;
        private String ticketType;
        private String userName;
        private String idcard;

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

        public String getTicketNo() {
            return ticketNo;
        }

        public void setTicketNo(String ticketNo) {
            this.ticketNo = ticketNo;
        }

        public String getSellStation() {
            return sellStation;
        }

        public void setSellStation(String sellStation) {
            this.sellStation = sellStation;
        }

        public String getStartAddress() {
            return startAddress;
        }

        public void setStartAddress(String startAddress) {
            this.startAddress = startAddress;
        }

        public String getEndAddress() {
            return endAddress;
        }

        public void setEndAddress(String endAddress) {
            this.endAddress = endAddress;
        }

        public String getTrainNo() {
            return trainNo;
        }

        public void setTrainNo(String trainNo) {
            this.trainNo = trainNo;
        }

        public String getTrainRunTime() {
            return trainRunTime;
        }

        public void setTrainRunTime(String trainRunTime) {
            this.trainRunTime = trainRunTime;
        }

        public String getTrainSeat() {
            return trainSeat;
        }

        public void setTrainSeat(String trainSeat) {
            this.trainSeat = trainSeat;
        }

        public String getTicketMoney() {
            return ticketMoney;
        }

        public void setTicketMoney(String ticketMoney) {
            this.ticketMoney = ticketMoney;
        }

        public String getTicketType() {
            return ticketType;
        }

        public void setTicketType(String ticketType) {
            this.ticketType = ticketType;
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
    }
}
