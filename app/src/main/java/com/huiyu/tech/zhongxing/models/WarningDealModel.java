package com.huiyu.tech.zhongxing.models;

import java.util.List;

/**
 * Created by ml on 2016/11/9.
 */
public class WarningDealModel {


    /**
     * m : 操作成功!
     * c : 0
     * d : {"max_page":1,"list":[{"id":"16","isNewRecord":false,"createDate":"2017-06-29 20:32:48","alarmTime":"2017-06-29 20:31:40","alarmPlaceMain":"1","alarmPlaceSub":"1","alarmPlaceDesc":"大厅正门","alarmScore":"89","alarmName":"jiangchun","alarmSex":"1","alarmIdcard":"420103198604153710","alarmRemark":"TF","modelImage":"http://192.168.1.252:8080/idcheck/alarm/6.jpg","faceImage":"http://192.168.1.252:8080/idcheck/alarm/01_01_201706292031405913891.jpg","fullviewImage":"/alarm/bk_01_01_201706292031405913891.jpg","status":"0","selfIncrease":false},{"id":"15","isNewRecord":false,"createDate":"2017-06-29 20:32:14","alarmTime":"2017-06-29 20:31:06","alarmPlaceMain":"1","alarmPlaceSub":"1","alarmPlaceDesc":"大厅正门","alarmScore":"87","alarmName":"46914258486297676","alarmSex":"-1","alarmIdcard":"未知","alarmRemark":"","modelImage":"http://192.168.1.252:8080/idcheck/alarm/4.jpg","faceImage":"http://192.168.1.252:8080/idcheck/alarm/01_01_201706292031065819523.jpg","fullviewImage":"/alarm/bk_01_01_201706292031065819523.jpg","status":"0","selfIncrease":false},{"id":"14","isNewRecord":false,"createDate":"2017-06-29 20:19:07","alarmTime":"2017-06-29 20:17:58","alarmPlaceMain":"1","alarmPlaceSub":"1","alarmPlaceDesc":"大厅正门","alarmScore":"88","alarmName":"46914258486297676","alarmSex":"-1","alarmIdcard":"未知","alarmRemark":"","modelImage":"http://192.168.1.252:8080/idcheck/alarm/4.jpg","faceImage":"http://192.168.1.252:8080/idcheck/alarm/01_01_201706292017585121059.jpg","fullviewImage":"/alarm/bk_01_01_201706292017585121059.jpg","status":"0","selfIncrease":false},{"id":"13","isNewRecord":false,"createDate":"2017-06-29 20:18:53","alarmTime":"2017-06-29 20:17:45","alarmPlaceMain":"1","alarmPlaceSub":"1","alarmPlaceDesc":"大厅正门","alarmScore":"86","alarmName":"46914258486297676","alarmSex":"-1","alarmIdcard":"未知","alarmRemark":"","modelImage":"http://192.168.1.252:8080/idcheck/alarm/4.jpg","faceImage":"http://192.168.1.252:8080/idcheck/alarm/01_01_201706292017455014915.jpg","fullviewImage":"/alarm/bk_01_01_201706292017455014915.jpg","status":"0","selfIncrease":false},{"id":"12","isNewRecord":false,"createDate":"2017-06-29 20:16:35","alarmTime":"2017-06-29 20:15:27","alarmPlaceMain":"1","alarmPlaceSub":"1","alarmPlaceDesc":"大厅正门","alarmScore":"89","alarmName":"jiangchun","alarmSex":"1","alarmIdcard":"420103198604153710","alarmRemark":"TF","modelImage":"http://192.168.1.252:8080/idcheck/alarm/6.jpg","faceImage":"http://192.168.1.252:8080/idcheck/alarm/01_01_201706292015274912359.jpg","fullviewImage":"/alarm/bk_01_01_201706292015274912359.jpg","status":"0","selfIncrease":false},{"id":"11","isNewRecord":false,"createDate":"2017-06-29 20:16:30","alarmTime":"2017-06-29 20:15:21","alarmPlaceMain":"1","alarmPlaceSub":"1","alarmPlaceDesc":"大厅正门","alarmScore":"88","alarmName":"jiangchun","alarmSex":"1","alarmIdcard":"420103198604153710","alarmRemark":"TF","modelImage":"http://192.168.1.252:8080/idcheck/alarm/6.jpg","faceImage":"http://192.168.1.252:8080/idcheck/alarm/01_01_201706292015214815431.jpg","fullviewImage":"/alarm/bk_01_01_201706292015214815431.jpg","status":"0","selfIncrease":false}]}
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
         * max_page : 1
         * list : [{"id":"16","isNewRecord":false,"createDate":"2017-06-29 20:32:48","alarmTime":"2017-06-29 20:31:40","alarmPlaceMain":"1","alarmPlaceSub":"1","alarmPlaceDesc":"大厅正门","alarmScore":"89","alarmName":"jiangchun","alarmSex":"1","alarmIdcard":"420103198604153710","alarmRemark":"TF","modelImage":"http://192.168.1.252:8080/idcheck/alarm/6.jpg","faceImage":"http://192.168.1.252:8080/idcheck/alarm/01_01_201706292031405913891.jpg","fullviewImage":"/alarm/bk_01_01_201706292031405913891.jpg","status":"0","selfIncrease":false},{"id":"15","isNewRecord":false,"createDate":"2017-06-29 20:32:14","alarmTime":"2017-06-29 20:31:06","alarmPlaceMain":"1","alarmPlaceSub":"1","alarmPlaceDesc":"大厅正门","alarmScore":"87","alarmName":"46914258486297676","alarmSex":"-1","alarmIdcard":"未知","alarmRemark":"","modelImage":"http://192.168.1.252:8080/idcheck/alarm/4.jpg","faceImage":"http://192.168.1.252:8080/idcheck/alarm/01_01_201706292031065819523.jpg","fullviewImage":"/alarm/bk_01_01_201706292031065819523.jpg","status":"0","selfIncrease":false},{"id":"14","isNewRecord":false,"createDate":"2017-06-29 20:19:07","alarmTime":"2017-06-29 20:17:58","alarmPlaceMain":"1","alarmPlaceSub":"1","alarmPlaceDesc":"大厅正门","alarmScore":"88","alarmName":"46914258486297676","alarmSex":"-1","alarmIdcard":"未知","alarmRemark":"","modelImage":"http://192.168.1.252:8080/idcheck/alarm/4.jpg","faceImage":"http://192.168.1.252:8080/idcheck/alarm/01_01_201706292017585121059.jpg","fullviewImage":"/alarm/bk_01_01_201706292017585121059.jpg","status":"0","selfIncrease":false},{"id":"13","isNewRecord":false,"createDate":"2017-06-29 20:18:53","alarmTime":"2017-06-29 20:17:45","alarmPlaceMain":"1","alarmPlaceSub":"1","alarmPlaceDesc":"大厅正门","alarmScore":"86","alarmName":"46914258486297676","alarmSex":"-1","alarmIdcard":"未知","alarmRemark":"","modelImage":"http://192.168.1.252:8080/idcheck/alarm/4.jpg","faceImage":"http://192.168.1.252:8080/idcheck/alarm/01_01_201706292017455014915.jpg","fullviewImage":"/alarm/bk_01_01_201706292017455014915.jpg","status":"0","selfIncrease":false},{"id":"12","isNewRecord":false,"createDate":"2017-06-29 20:16:35","alarmTime":"2017-06-29 20:15:27","alarmPlaceMain":"1","alarmPlaceSub":"1","alarmPlaceDesc":"大厅正门","alarmScore":"89","alarmName":"jiangchun","alarmSex":"1","alarmIdcard":"420103198604153710","alarmRemark":"TF","modelImage":"http://192.168.1.252:8080/idcheck/alarm/6.jpg","faceImage":"http://192.168.1.252:8080/idcheck/alarm/01_01_201706292015274912359.jpg","fullviewImage":"/alarm/bk_01_01_201706292015274912359.jpg","status":"0","selfIncrease":false},{"id":"11","isNewRecord":false,"createDate":"2017-06-29 20:16:30","alarmTime":"2017-06-29 20:15:21","alarmPlaceMain":"1","alarmPlaceSub":"1","alarmPlaceDesc":"大厅正门","alarmScore":"88","alarmName":"jiangchun","alarmSex":"1","alarmIdcard":"420103198604153710","alarmRemark":"TF","modelImage":"http://192.168.1.252:8080/idcheck/alarm/6.jpg","faceImage":"http://192.168.1.252:8080/idcheck/alarm/01_01_201706292015214815431.jpg","fullviewImage":"/alarm/bk_01_01_201706292015214815431.jpg","status":"0","selfIncrease":false}]
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
             * id : 16
             * isNewRecord : false
             * createDate : 2017-06-29 20:32:48
             * alarmTime : 2017-06-29 20:31:40
             * alarmPlaceMain : 1
             * alarmPlaceSub : 1
             * alarmPlaceDesc : 大厅正门
             * alarmScore : 89
             * alarmName : jiangchun
             * alarmSex : 1
             * alarmIdcard : 420103198604153710
             * alarmRemark : TF
             * modelImage : http://192.168.1.252:8080/idcheck/alarm/6.jpg
             * faceImage : http://192.168.1.252:8080/idcheck/alarm/01_01_201706292031405913891.jpg
             * fullviewImage : /alarm/bk_01_01_201706292031405913891.jpg
             * status : 0
             * selfIncrease : false
             */

            private String id;
            private boolean isNewRecord;
            private String createDate;
            private String alarmTime;
            private String alarmPlaceMain;
            private String alarmPlaceSub;
            private String alarmPlaceDesc;
            private String alarmScore;
            private String alarmName;
            private String alarmSex;
            private String alarmIdcard;
            private String alarmRemark;
            private String modelImage;
            private String faceImage;
            private String fullviewImage;
            private String status;
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

            public String getAlarmTime() {
                return alarmTime;
            }

            public void setAlarmTime(String alarmTime) {
                this.alarmTime = alarmTime;
            }

            public String getAlarmPlaceMain() {
                return alarmPlaceMain;
            }

            public void setAlarmPlaceMain(String alarmPlaceMain) {
                this.alarmPlaceMain = alarmPlaceMain;
            }

            public String getAlarmPlaceSub() {
                return alarmPlaceSub;
            }

            public void setAlarmPlaceSub(String alarmPlaceSub) {
                this.alarmPlaceSub = alarmPlaceSub;
            }

            public String getAlarmPlaceDesc() {
                return alarmPlaceDesc;
            }

            public void setAlarmPlaceDesc(String alarmPlaceDesc) {
                this.alarmPlaceDesc = alarmPlaceDesc;
            }

            public String getAlarmScore() {
                return alarmScore;
            }

            public void setAlarmScore(String alarmScore) {
                this.alarmScore = alarmScore;
            }

            public String getAlarmName() {
                return alarmName;
            }

            public void setAlarmName(String alarmName) {
                this.alarmName = alarmName;
            }

            public String getAlarmSex() {
                return alarmSex;
            }

            public void setAlarmSex(String alarmSex) {
                this.alarmSex = alarmSex;
            }

            public String getAlarmIdcard() {
                return alarmIdcard;
            }

            public void setAlarmIdcard(String alarmIdcard) {
                this.alarmIdcard = alarmIdcard;
            }

            public String getAlarmRemark() {
                return alarmRemark;
            }

            public void setAlarmRemark(String alarmRemark) {
                this.alarmRemark = alarmRemark;
            }

            public String getModelImage() {
                return modelImage;
            }

            public void setModelImage(String modelImage) {
                this.modelImage = modelImage;
            }

            public String getFaceImage() {
                return faceImage;
            }

            public void setFaceImage(String faceImage) {
                this.faceImage = faceImage;
            }

            public String getFullviewImage() {
                return fullviewImage;
            }

            public void setFullviewImage(String fullviewImage) {
                this.fullviewImage = fullviewImage;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
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
