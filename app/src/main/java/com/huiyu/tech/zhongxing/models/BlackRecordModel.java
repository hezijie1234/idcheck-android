package com.huiyu.tech.zhongxing.models;

import java.util.List;

/**
 * Created by Administrator on 2017-06-27.
 */

public class BlackRecordModel {


    /**
     * m : 操作成功!
     * c : 0
     * d : [{"id":"38","isNewRecord":false,"createDate":"2017-06-27 10:17:24","updateDate":"2017-06-27 10:17:24","userName":"hzj","idcard":"5965","suspectType":"drug","userImage":"http://192.168.1.252:8080/idcheck\\faceBank\\5965\\\\1498529844262.jpg|http://192.168.1.252:8080/idcheck\\faceBank\\5965\\\\1498529844263.jpg|","userImageUrl":"|/idcheck\\\\faceBank\\5965\\\\1498529844262.jpg|/idcheck\\\\faceBank\\5965\\\\1498529844263.jpg","selfIncrease":true},{"id":"37","isNewRecord":false,"createDate":"2017-06-27 10:15:33","updateDate":"2017-06-27 10:15:33","userName":"hzj","idcard":"571686","suspectType":"drug","userImage":"http://192.168.1.252:8080/idcheck\\faceBank\\571686\\\\1498529733228.jpg|http://192.168.1.252:8080/idcheck\\faceBank\\571686\\\\1498529733230.jpg|","userImageUrl":"|/idcheck\\\\faceBank\\571686\\\\1498529733228.jpg|/idcheck\\\\faceBank\\571686\\\\1498529733230.jpg","selfIncrease":true},{"id":"36","isNewRecord":false,"createDate":"2017-06-27 10:14:36","updateDate":"2017-06-27 10:14:36","userName":"hzj","idcard":"2565","suspectType":"crime","userImage":"http://192.168.1.252:8080/idcheck\\faceBank\\2565\\\\1498529676306.jpg|http://192.168.1.252:8080/idcheck\\faceBank\\2565\\\\1498529676309.jpg|","userImageUrl":"|/idcheck\\\\faceBank\\2565\\\\1498529676306.jpg|/idcheck\\\\faceBank\\2565\\\\1498529676309.jpg","selfIncrease":true},{"id":"35","isNewRecord":false,"createDate":"2017-06-27 10:11:55","updateDate":"2017-06-27 10:11:55","userName":"hzj","idcard":"5875399","suspectType":"crime","userImage":"http://192.168.1.252:8080/idcheck\\faceBank\\5875399\\\\1498529515167.jpg|http://192.168.1.252:8080/idcheck\\faceBank\\5875399\\\\1498529515197.jpg|","userImageUrl":"|/idcheck\\\\faceBank\\5875399\\\\1498529515167.jpg|/idcheck\\\\faceBank\\5875399\\\\1498529515197.jpg","selfIncrease":true},{"id":"34","isNewRecord":false,"createDate":"2017-06-26 11:26:46","updateDate":"2017-06-26 11:26:46","userName":"hzj","idcard":"5533","suspectType":"drug","userImage":"http://192.168.1.252:8080/idcheck\\faceBank\\5533\\\\1498447546496.jpg|","userImageUrl":"|/idcheck\\\\faceBank\\5533\\\\1498447546496.jpg","selfIncrease":true},{"id":"33","isNewRecord":false,"createDate":"2017-06-26 11:06:07","updateDate":"2017-06-26 11:06:07","userName":"hzj","idcard":"2548885","suspectType":"drug","userImage":"http://192.168.1.252:8080/idcheck\\faceBank\\2548885\\\\1498446366528.jpg|http://192.168.1.252:8080/idcheck\\faceBank\\2548885\\\\1498446366532.jpg|","userImageUrl":"|/idcheck\\\\faceBank\\2548885\\\\1498446366528.jpg|/idcheck\\\\faceBank\\2548885\\\\1498446366532.jpg","selfIncrease":true},{"id":"32","isNewRecord":false,"createDate":"2017-06-26 11:02:08","updateDate":"2017-06-26 11:02:08","userName":"tfjgh","idcard":"5555","suspectType":"drug","userImage":"http://192.168.1.252:8080/idcheck\\faceBank\\5555\\\\1498446128118.jpg|","userImageUrl":"|/idcheck\\\\faceBank\\5555\\\\1498446128118.jpg","selfIncrease":true},{"id":"31","isNewRecord":false,"createDate":"2017-06-26 11:00:09","updateDate":"2017-06-26 11:00:09","userName":"ugdh","idcard":"555555","suspectType":"drug","userImage":"http://192.168.1.252:8080/idcheck\\faceBank\\555555\\\\1498446008769.jpg|","userImageUrl":"|/idcheck\\\\faceBank\\555555\\\\1498446008769.jpg","selfIncrease":true},{"id":"30","isNewRecord":false,"createDate":"2017-06-26 10:40:58","updateDate":"2017-06-26 10:40:58","userName":"fhfjhh","idcard":"25542866","suspectType":"drug","userImage":"http://192.168.1.252:8080/idcheck\\faceBank\\25542866\\\\1498444858115.jpg|","userImageUrl":"|/idcheck\\\\faceBank\\25542866\\\\1498444858115.jpg","selfIncrease":true},{"id":"28","isNewRecord":false,"createDate":"2017-06-23 18:09:12","updateDate":"2017-06-23 18:09:12","userName":"哥几个","idcard":"2545","suspectType":"drug","userImage":"http://192.168.1.252:8080/idcheck\\faceBank\\2545\\\\1498212552418.jpg|","userImageUrl":"/faceBank\\2545\\\\1498212552418.jpg","selfIncrease":true}]
     */

    private String m;
    private String c;
    private List<DBean> d;

    public String getM() {
        return m;
    }

    public void setM(String m) {
        this.m = m;
    }

    public String getC() {
        return c;
    }

    public void setC(String c) {
        this.c = c;
    }

    public List<DBean> getD() {
        return d;
    }

    public void setD(List<DBean> d) {
        this.d = d;
    }

    public static class DBean {
        /**
         * id : 38
         * isNewRecord : false
         * createDate : 2017-06-27 10:17:24
         * updateDate : 2017-06-27 10:17:24
         * userName : hzj
         * idcard : 5965
         * suspectType : drug
         * userImage : http://192.168.1.252:8080/idcheck\faceBank\5965\\1498529844262.jpg|http://192.168.1.252:8080/idcheck\faceBank\5965\\1498529844263.jpg|
         * userImageUrl : |/idcheck\\faceBank\5965\\1498529844262.jpg|/idcheck\\faceBank\5965\\1498529844263.jpg
         * selfIncrease : true
         */

        private String id;
        private boolean isNewRecord;
        private String createDate;
        private String updateDate;
        private String userName;
        private String idcard;
        private String suspectType;
        private String userImage;
        private String userImageUrl;
        private boolean selfIncrease;

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

        public String getSuspectType() {
            return suspectType;
        }

        public void setSuspectType(String suspectType) {
            this.suspectType = suspectType;
        }

        public String getUserImage() {
            return userImage;
        }

        public void setUserImage(String userImage) {
            this.userImage = userImage;
        }

        public String getUserImageUrl() {
            return userImageUrl;
        }

        public void setUserImageUrl(String userImageUrl) {
            this.userImageUrl = userImageUrl;
        }

        public boolean isSelfIncrease() {
            return selfIncrease;
        }

        public void setSelfIncrease(boolean selfIncrease) {
            this.selfIncrease = selfIncrease;
        }
    }
}
