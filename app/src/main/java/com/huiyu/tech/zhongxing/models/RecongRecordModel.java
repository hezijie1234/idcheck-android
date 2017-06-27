package com.huiyu.tech.zhongxing.models;

import java.util.List;

/**
 * Created by Administrator on 2017-06-27.
 */

public class RecongRecordModel {

    /**
     * m : 操作成功!
     * c : 0
     * d : [{"id":"2091348","isNewRecord":false,"operation_user_id":"d7e96defb93d4f6b9a932c5a8a2c854e","operation_time":"2017-6-27\n        05:09:22","person_id":"429004199212030352","idcrad_img_src":"http://192.168.1.252:8080/idcheck\\faceBank\\429004199212030352\\429004199212030352.jpg","face_img_src":"http://192.168.1.252:8080/idcheck\\faceBank\\429004199212030352\\1498554574261.jpg","is_marry":"1","detect_type":"1","return_score":"93.43054704089732","operation_name":"杨磊","person_name":"何子杰","selfIncrease":true},{"id":"2091347","isNewRecord":false,"operation_user_id":"d7e96defb93d4f6b9a932c5a8a2c854e","operation_time":"2017-6-27\n        05:07:08","person_id":"429004199212030352","idcrad_img_src":"http://192.168.1.252:8080/idcheck\\faceBank\\429004199212030352\\429004199212030352.jpg","face_img_src":"http://192.168.1.252:8080/idcheck\\faceBank\\429004199212030352\\1498554439838.jpg","is_marry":"1","detect_type":"1","return_score":"95.00692643463759","operation_name":"杨磊","person_name":"何子杰","selfIncrease":true},{"id":"2091346","isNewRecord":false,"operation_user_id":"d7e96defb93d4f6b9a932c5a8a2c854e","operation_time":"2017-6-27\n        05:05:52","person_id":"429004199212030352","idcrad_img_src":"http://192.168.1.252:8080/idcheck\\faceBank\\429004199212030352\\429004199212030352.jpg","face_img_src":"http://192.168.1.252:8080/idcheck\\faceBank\\429004199212030352\\1498554362909.jpg","is_marry":"1","detect_type":"1","return_score":"90.74517220930751","operation_name":"杨磊","person_name":"何子杰","selfIncrease":true},{"id":"2091341","isNewRecord":false,"operation_user_id":"d7e96defb93d4f6b9a932c5a8a2c854e","operation_time":"2017-6-26\n        11:19:19","person_id":"429004199212030352","idcrad_img_src":"http://192.168.1.252:8080/idcheck\\faceBank\\429004199212030352\\429004199212030352.jpg","face_img_src":"http://192.168.1.252:8080/idcheck\\faceBank\\429004199212030352\\1498447166841.jpg","is_marry":"1","detect_type":"1","return_score":"77.24295041407905","operation_name":"杨磊","person_name":"何子杰","selfIncrease":true},{"id":"2091340","isNewRecord":false,"operation_user_id":"d7e96defb93d4f6b9a932c5a8a2c854e","operation_time":"2017-6-26\n        11:18:24","person_id":"429004199212030352","idcrad_img_src":"http://192.168.1.252:8080/idcheck\\faceBank\\429004199212030352\\429004199212030352.jpg","face_img_src":"http://192.168.1.252:8080/idcheck\\faceBank\\429004199212030352\\1498447112693.jpg","is_marry":"1","detect_type":"1","return_score":"75.14133614695189","operation_name":"杨磊","person_name":"何子杰","selfIncrease":true},{"id":"2091339","isNewRecord":false,"operation_user_id":"d7e96defb93d4f6b9a932c5a8a2c854e","operation_time":"2017-6-26\n        11:18:23","person_id":"429004199212030352","idcrad_img_src":"http://192.168.1.252:8080/idcheck\\faceBank\\429004199212030352\\429004199212030352.jpg","face_img_src":"http://192.168.1.252:8080/idcheck\\faceBank\\429004199212030352\\1498447111333.jpg","is_marry":"0","detect_type":"1","return_score":"67.23327496812462","operation_name":"杨磊","person_name":"何子杰","selfIncrease":true},{"id":"2091338","isNewRecord":false,"operation_user_id":"d7e96defb93d4f6b9a932c5a8a2c854e","operation_time":"2017-6-26\n        11:18:21","person_id":"429004199212030352","idcrad_img_src":"http://192.168.1.252:8080/idcheck\\faceBank\\429004199212030352\\429004199212030352.jpg","face_img_src":"http://192.168.1.252:8080/idcheck\\faceBank\\429004199212030352\\1498447109200.jpg","is_marry":"0","detect_type":"1","return_score":"69.09915003384911","operation_name":"杨磊","person_name":"何子杰","selfIncrease":true}]
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
         * id : 2091348
         * isNewRecord : false
         * operation_user_id : d7e96defb93d4f6b9a932c5a8a2c854e
         * operation_time : 2017-6-27
         05:09:22
         * person_id : 429004199212030352
         * idcrad_img_src : http://192.168.1.252:8080/idcheck\faceBank\429004199212030352\429004199212030352.jpg
         * face_img_src : http://192.168.1.252:8080/idcheck\faceBank\429004199212030352\1498554574261.jpg
         * is_marry : 1
         * detect_type : 1
         * return_score : 93.43054704089732
         * operation_name : 杨磊
         * person_name : 何子杰
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
