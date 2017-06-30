package com.huiyu.tech.zhongxing.models;

import java.util.List;

/**
 * Created by Administrator on 2017-06-27.
 */

public class RecongRecordModel {


    /**
     * m : 操作成功!
     * c : 0
     * d : {"max_page":2,"list":[{"id":"2091360","isNewRecord":false,"operation_user_id":"d7e96defb93d4f6b9a932c5a8a2c854e","operation_time":"2017-6-28 04:23:56","person_id":"420113198010300034","idcrad_img_src":"http://192.168.1.252:8080/idcheck\\faceBank\\420113198010300034\\420113198010300034.jpg","face_img_src":"http://192.168.1.252:8080/idcheck\\faceBank\\420113198010300034\\1498638251516.jpg","is_marry":"1","detect_type":"1","return_score":"82.84511168751949","operation_name":"杨磊","person_name":"宰波","selfIncrease":true}]}
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
         * list : [{"id":"2091360","isNewRecord":false,"operation_user_id":"d7e96defb93d4f6b9a932c5a8a2c854e","operation_time":"2017-6-28 04:23:56","person_id":"420113198010300034","idcrad_img_src":"http://192.168.1.252:8080/idcheck\\faceBank\\420113198010300034\\420113198010300034.jpg","face_img_src":"http://192.168.1.252:8080/idcheck\\faceBank\\420113198010300034\\1498638251516.jpg","is_marry":"1","detect_type":"1","return_score":"82.84511168751949","operation_name":"杨磊","person_name":"宰波","selfIncrease":true}]
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
             * id : 2091360
             * isNewRecord : false
             * operation_user_id : d7e96defb93d4f6b9a932c5a8a2c854e
             * operation_time : 2017-6-28 04:23:56
             * person_id : 420113198010300034
             * idcrad_img_src : http://192.168.1.252:8080/idcheck\faceBank\420113198010300034\420113198010300034.jpg
             * face_img_src : http://192.168.1.252:8080/idcheck\faceBank\420113198010300034\1498638251516.jpg
             * is_marry : 1
             * detect_type : 1
             * return_score : 82.84511168751949
             * operation_name : 杨磊
             * person_name : 宰波
             * selfIncrease : true
             */

            private String id;
            private boolean isNewRecord;
            private String operation_user_id;
            private String operation_time;
            private String person_id;
            private String idcrad_img_src;
            private String face_img_src;
            private String is_marry;
            private String detect_type;
            private String return_score;
            private String operation_name;
            private String person_name;
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

            public String getOperation_user_id() {
                return operation_user_id;
            }

            public void setOperation_user_id(String operation_user_id) {
                this.operation_user_id = operation_user_id;
            }

            public String getOperation_time() {
                return operation_time;
            }

            public void setOperation_time(String operation_time) {
                this.operation_time = operation_time;
            }

            public String getPerson_id() {
                return person_id;
            }

            public void setPerson_id(String person_id) {
                this.person_id = person_id;
            }

            public String getIdcrad_img_src() {
                return idcrad_img_src;
            }

            public void setIdcrad_img_src(String idcrad_img_src) {
                this.idcrad_img_src = idcrad_img_src;
            }

            public String getFace_img_src() {
                return face_img_src;
            }

            public void setFace_img_src(String face_img_src) {
                this.face_img_src = face_img_src;
            }

            public String getIs_marry() {
                return is_marry;
            }

            public void setIs_marry(String is_marry) {
                this.is_marry = is_marry;
            }

            public String getDetect_type() {
                return detect_type;
            }

            public void setDetect_type(String detect_type) {
                this.detect_type = detect_type;
            }

            public String getReturn_score() {
                return return_score;
            }

            public void setReturn_score(String return_score) {
                this.return_score = return_score;
            }

            public String getOperation_name() {
                return operation_name;
            }

            public void setOperation_name(String operation_name) {
                this.operation_name = operation_name;
            }

            public String getPerson_name() {
                return person_name;
            }

            public void setPerson_name(String person_name) {
                this.person_name = person_name;
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
