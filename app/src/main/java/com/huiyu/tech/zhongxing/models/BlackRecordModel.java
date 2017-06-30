package com.huiyu.tech.zhongxing.models;

import java.util.List;

/**
 * Created by Administrator on 2017-06-27.
 */

public class BlackRecordModel {


    /**
     * m : 操作成功!
     * c : 0
     * d : {"max_page":2,"list":[{"id":"42","isNewRecord":false,"createDate":"2017-06-28 21:14:47","updateDate":"2017-06-28 21:14:47","userName":"江纯","idcard":"420103198604153710","suspectType":"crime","userImage":"http://192.168.1.19:8080/idcheck\\faceBank\\420103198604153710\\\\1498655686580.jpg|http://192.168.1.19:8080/idcheck\\faceBank\\420103198604153710\\\\1498655686580.jpg|","userImageUrl":"|/idcheck\\\\faceBank\\420103198604153710\\\\1498655686580.jpg|/idcheck\\\\faceBank\\420103198604153710\\\\1498655686580.jpg","selfIncrease":true}]}
     */

    private String m;
    private String c;
    private DBean d;

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

    public DBean getD() {
        return d;
    }

    public void setD(DBean d) {
        this.d = d;
    }

    public static class DBean {
        /**
         * max_page : 2
         * list : [{"id":"42","isNewRecord":false,"createDate":"2017-06-28 21:14:47","updateDate":"2017-06-28 21:14:47","userName":"江纯","idcard":"420103198604153710","suspectType":"crime","userImage":"http://192.168.1.19:8080/idcheck\\faceBank\\420103198604153710\\\\1498655686580.jpg|http://192.168.1.19:8080/idcheck\\faceBank\\420103198604153710\\\\1498655686580.jpg|","userImageUrl":"|/idcheck\\\\faceBank\\420103198604153710\\\\1498655686580.jpg|/idcheck\\\\faceBank\\420103198604153710\\\\1498655686580.jpg","selfIncrease":true}]
         */

        private int max_page;
        private List<ListBean> list;

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

        public static class ListBean {
            /**
             * id : 42
             * isNewRecord : false
             * createDate : 2017-06-28 21:14:47
             * updateDate : 2017-06-28 21:14:47
             * userName : 江纯
             * idcard : 420103198604153710
             * suspectType : crime
             * userImage : http://192.168.1.19:8080/idcheck\faceBank\420103198604153710\\1498655686580.jpg|http://192.168.1.19:8080/idcheck\faceBank\420103198604153710\\1498655686580.jpg|
             * userImageUrl : |/idcheck\\faceBank\420103198604153710\\1498655686580.jpg|/idcheck\\faceBank\420103198604153710\\1498655686580.jpg
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
}
