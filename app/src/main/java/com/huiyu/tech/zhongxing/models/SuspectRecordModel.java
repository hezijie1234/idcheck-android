package com.huiyu.tech.zhongxing.models;

import java.util.List;

/**
 * Created by Administrator on 2017-06-27.
 */

public class SuspectRecordModel {
    /**
     * m : 操作成功!
     * c : 0
     * d : {"max_page":1,"list":[{"id":"2091332","isNewRecord":false,"operation_user_id":"d7e96defb93d4f6b9a932c5a8a2c854e","operation_time":"2017-6-23 11:39:07","person_id":"crime","idcrad_img_src":"http://192.168.1.252:8080/idcheck\\faceBank\\4664466465\\\\IMG_-2109262558.jpg","face_img_src":"http://192.168.1.252:8080/idcheck\\faceBank/temp\\\\1498189149379.jpg","is_marry":"1","detect_type":"2","return_score":"89.2675519508563","operation_name":"杨磊","suspectTypeName":"犯罪","selfIncrease":true},{"id":"2091333","isNewRecord":false,"operation_user_id":"d7e96defb93d4f6b9a932c5a8a2c854e","operation_time":"2017-6-23 11:39:07","person_id":"crime","idcrad_img_src":"http://192.168.1.252:8080/idcheck\\faceBank\\457878787\\\\IMG_-2109262558.jpg","face_img_src":"http://192.168.1.252:8080/idcheck\\faceBank/temp\\\\1498189149379.jpg","is_marry":"1","detect_type":"2","return_score":"89.2675519508563","operation_name":"杨磊","suspectTypeName":"犯罪","selfIncrease":true},{"id":"2091330","isNewRecord":false,"operation_user_id":"d7e96defb93d4f6b9a932c5a8a2c854e","operation_time":"2017-6-22 03:45:12","person_id":"crime","idcrad_img_src":"http://192.168.1.252:8080/idcheck\\faceBank\\4664466465\\\\IMG_-2109262558.jpg","face_img_src":"http://192.168.1.252:8080/idcheck\\faceBank/temp\\\\1498117512337.jpg","is_marry":"1","detect_type":"2","return_score":"75.21077357284514","operation_name":"杨磊","suspectTypeName":"犯罪","selfIncrease":true},{"id":"2091331","isNewRecord":false,"operation_user_id":"d7e96defb93d4f6b9a932c5a8a2c854e","operation_time":"2017-6-22 03:45:12","person_id":"crime","idcrad_img_src":"http://192.168.1.252:8080/idcheck\\faceBank\\457878787\\\\IMG_-2109262558.jpg","face_img_src":"http://192.168.1.252:8080/idcheck\\faceBank/temp\\\\1498117512337.jpg","is_marry":"1","detect_type":"2","return_score":"75.21077357284514","operation_name":"杨磊","suspectTypeName":"犯罪","selfIncrease":true},{"id":"2091328","isNewRecord":false,"operation_user_id":"d7e96defb93d4f6b9a932c5a8a2c854e","operation_time":"2017-6-22 03:44:54","person_id":"crime","idcrad_img_src":"http://192.168.1.252:8080/idcheck\\faceBank\\4664466465\\\\IMG_-2109262558.jpg","face_img_src":"http://192.168.1.252:8080/idcheck\\faceBank/temp\\\\1498117494817.jpg","is_marry":"1","detect_type":"2","return_score":"83.78414539767971","operation_name":"杨磊","suspectTypeName":"犯罪","selfIncrease":true},{"id":"2091329","isNewRecord":false,"operation_user_id":"d7e96defb93d4f6b9a932c5a8a2c854e","operation_time":"2017-6-22 03:44:54","person_id":"crime","idcrad_img_src":"http://192.168.1.252:8080/idcheck\\faceBank\\457878787\\\\IMG_-2109262558.jpg","face_img_src":"http://192.168.1.252:8080/idcheck\\faceBank/temp\\\\1498117494817.jpg","is_marry":"1","detect_type":"2","return_score":"83.78414539767971","operation_name":"杨磊","suspectTypeName":"犯罪","selfIncrease":true},{"id":"2091326","isNewRecord":false,"operation_user_id":"d7e96defb93d4f6b9a932c5a8a2c854e","operation_time":"2017-6-22 03:44:33","person_id":"crime","idcrad_img_src":"http://192.168.1.252:8080/idcheck\\faceBank\\4664466465\\\\IMG_-2109262558.jpg","face_img_src":"http://192.168.1.252:8080/idcheck\\faceBank/temp\\\\1498117473539.jpg","is_marry":"1","detect_type":"2","return_score":"84.49417852629838","operation_name":"杨磊","suspectTypeName":"犯罪","selfIncrease":true},{"id":"2091327","isNewRecord":false,"operation_user_id":"d7e96defb93d4f6b9a932c5a8a2c854e","operation_time":"2017-6-22 03:44:33","person_id":"crime","idcrad_img_src":"http://192.168.1.252:8080/idcheck\\faceBank\\457878787\\\\IMG_-2109262558.jpg","face_img_src":"http://192.168.1.252:8080/idcheck\\faceBank/temp\\\\1498117473539.jpg","is_marry":"1","detect_type":"2","return_score":"84.49417852629838","operation_name":"杨磊","suspectTypeName":"犯罪","selfIncrease":true}]}
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
         * list : [{"id":"2091332","isNewRecord":false,"operation_user_id":"d7e96defb93d4f6b9a932c5a8a2c854e","operation_time":"2017-6-23 11:39:07","person_id":"crime","idcrad_img_src":"http://192.168.1.252:8080/idcheck\\faceBank\\4664466465\\\\IMG_-2109262558.jpg","face_img_src":"http://192.168.1.252:8080/idcheck\\faceBank/temp\\\\1498189149379.jpg","is_marry":"1","detect_type":"2","return_score":"89.2675519508563","operation_name":"杨磊","suspectTypeName":"犯罪","selfIncrease":true},{"id":"2091333","isNewRecord":false,"operation_user_id":"d7e96defb93d4f6b9a932c5a8a2c854e","operation_time":"2017-6-23 11:39:07","person_id":"crime","idcrad_img_src":"http://192.168.1.252:8080/idcheck\\faceBank\\457878787\\\\IMG_-2109262558.jpg","face_img_src":"http://192.168.1.252:8080/idcheck\\faceBank/temp\\\\1498189149379.jpg","is_marry":"1","detect_type":"2","return_score":"89.2675519508563","operation_name":"杨磊","suspectTypeName":"犯罪","selfIncrease":true},{"id":"2091330","isNewRecord":false,"operation_user_id":"d7e96defb93d4f6b9a932c5a8a2c854e","operation_time":"2017-6-22 03:45:12","person_id":"crime","idcrad_img_src":"http://192.168.1.252:8080/idcheck\\faceBank\\4664466465\\\\IMG_-2109262558.jpg","face_img_src":"http://192.168.1.252:8080/idcheck\\faceBank/temp\\\\1498117512337.jpg","is_marry":"1","detect_type":"2","return_score":"75.21077357284514","operation_name":"杨磊","suspectTypeName":"犯罪","selfIncrease":true},{"id":"2091331","isNewRecord":false,"operation_user_id":"d7e96defb93d4f6b9a932c5a8a2c854e","operation_time":"2017-6-22 03:45:12","person_id":"crime","idcrad_img_src":"http://192.168.1.252:8080/idcheck\\faceBank\\457878787\\\\IMG_-2109262558.jpg","face_img_src":"http://192.168.1.252:8080/idcheck\\faceBank/temp\\\\1498117512337.jpg","is_marry":"1","detect_type":"2","return_score":"75.21077357284514","operation_name":"杨磊","suspectTypeName":"犯罪","selfIncrease":true},{"id":"2091328","isNewRecord":false,"operation_user_id":"d7e96defb93d4f6b9a932c5a8a2c854e","operation_time":"2017-6-22 03:44:54","person_id":"crime","idcrad_img_src":"http://192.168.1.252:8080/idcheck\\faceBank\\4664466465\\\\IMG_-2109262558.jpg","face_img_src":"http://192.168.1.252:8080/idcheck\\faceBank/temp\\\\1498117494817.jpg","is_marry":"1","detect_type":"2","return_score":"83.78414539767971","operation_name":"杨磊","suspectTypeName":"犯罪","selfIncrease":true},{"id":"2091329","isNewRecord":false,"operation_user_id":"d7e96defb93d4f6b9a932c5a8a2c854e","operation_time":"2017-6-22 03:44:54","person_id":"crime","idcrad_img_src":"http://192.168.1.252:8080/idcheck\\faceBank\\457878787\\\\IMG_-2109262558.jpg","face_img_src":"http://192.168.1.252:8080/idcheck\\faceBank/temp\\\\1498117494817.jpg","is_marry":"1","detect_type":"2","return_score":"83.78414539767971","operation_name":"杨磊","suspectTypeName":"犯罪","selfIncrease":true},{"id":"2091326","isNewRecord":false,"operation_user_id":"d7e96defb93d4f6b9a932c5a8a2c854e","operation_time":"2017-6-22 03:44:33","person_id":"crime","idcrad_img_src":"http://192.168.1.252:8080/idcheck\\faceBank\\4664466465\\\\IMG_-2109262558.jpg","face_img_src":"http://192.168.1.252:8080/idcheck\\faceBank/temp\\\\1498117473539.jpg","is_marry":"1","detect_type":"2","return_score":"84.49417852629838","operation_name":"杨磊","suspectTypeName":"犯罪","selfIncrease":true},{"id":"2091327","isNewRecord":false,"operation_user_id":"d7e96defb93d4f6b9a932c5a8a2c854e","operation_time":"2017-6-22 03:44:33","person_id":"crime","idcrad_img_src":"http://192.168.1.252:8080/idcheck\\faceBank\\457878787\\\\IMG_-2109262558.jpg","face_img_src":"http://192.168.1.252:8080/idcheck\\faceBank/temp\\\\1498117473539.jpg","is_marry":"1","detect_type":"2","return_score":"84.49417852629838","operation_name":"杨磊","suspectTypeName":"犯罪","selfIncrease":true}]
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
             * id : 2091332
             * isNewRecord : false
             * operation_user_id : d7e96defb93d4f6b9a932c5a8a2c854e
             * operation_time : 2017-6-23 11:39:07
             * person_id : crime
             * idcrad_img_src : http://192.168.1.252:8080/idcheck\faceBank\4664466465\\IMG_-2109262558.jpg
             * face_img_src : http://192.168.1.252:8080/idcheck\faceBank/temp\\1498189149379.jpg
             * is_marry : 1
             * detect_type : 2
             * return_score : 89.2675519508563
             * operation_name : 杨磊
             * suspectTypeName : 犯罪
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
            private String suspectTypeName;
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

            public String getSuspectTypeName() {
                return suspectTypeName;
            }

            public void setSuspectTypeName(String suspectTypeName) {
                this.suspectTypeName = suspectTypeName;
            }

            public boolean isSelfIncrease() {
                return selfIncrease;
            }

            public void setSelfIncrease(boolean selfIncrease) {
                this.selfIncrease = selfIncrease;
            }
        }
    }


    //E/111: onAPISuccess: {"m":"操作成功!","c":"0","d":{"max_page":1,"list":[{"id":"2091332","isNewRecord":false,"operation_user_id":"d7e96defb93d4f6b9a932c5a8a2c854e","operation_time":"2017-6-23 11:39:07","person_id":"crime","idcrad_img_src":"http:\/\/192.168.1.252:8080\/idcheck\\faceBank\\4664466465\\\\IMG_-2109262558.jpg","face_img_src":"http:\/\/192.168.1.252:8080\/idcheck\\faceBank\/temp\\\\1498189149379.jpg","is_marry":"1","detect_type":"2","return_score":"89.2675519508563","operation_name":"杨磊","suspectTypeName":"犯罪","selfIncrease":true},{"id":"2091333","isNewRecord":false,"operation_user_id":"d7e96defb93d4f6b9a932c5a8a2c854e","operation_time":"2017-6-23 11:39:07","person_id":"crime","idcrad_img_src":"http:\/\/192.168.1.252:8080\/idcheck\\faceBank\\457878787\\\\IMG_-2109262558.jpg","face_img_src":"http:\/\/192.168.1.252:8080\/idcheck\\faceBank\/temp\\\\1498189149379.jpg","is_marry":"1","detect_type":"2","return_score":"89.2675519508563","operation_name":"杨磊","suspectTypeName":"犯罪","selfIncrease":true},{"id":"2091330","isNewRecord":false,"operation_user_id":"d7e96defb93d4f6b9a932c5a8a2c854e","operation_time":"2017-6-22 03:45:12","person_id":"crime","idcrad_img_src":"http:\/\/192.168.1.252:8080\/idcheck\\faceBank\\4664466465\\\\IMG_-2109262558.jpg","face_img_src":"http:\/\/192.168.1.252:8080\/idcheck\\faceBank\/temp\\\\1498117512337.jpg","is_marry":"1","detect_type":"2","return_score":"75.21077357284514","operation_name":"杨磊","suspectTypeName":"犯罪","selfIncrease":true},{"id":"2091331","isNewRecord":false,"operation_user_id":"d7e96defb93d4f6b9a932c5a8a2c854e","operation_time":"2017-6-22 03:45:12","person_id":"crime","idcrad_img_src":"http:\/\/192.168.1.252:8080\/idcheck\\faceBank\\457878787\\\\IMG_-2109262558.jpg","face_img_src":"http:\/\/192.168.1.252:8080\/idcheck\\faceBank\/temp\\\\1498117512337.jpg","is_marry":"1","detect_type":"2","return_score":"75.21077357284514","operation_name":"杨磊","suspectTypeName":"犯罪","selfIncrease":true},{"id":"2091328","isNewRecord":false,"operation_user_id":"d7e96defb93d4f6b9a932c5a8a2c854e","operation_time":"2017-6-22 03:44:54","person_id":"crime","idcrad_img_src":"http:\/\/192.168.1.252:8080\/idcheck\\faceBank\\4664466465\\\\IMG_-2109262558.jpg","face_img_src":"http:\/\/192.168.1.252:8080\/idcheck\\faceBank\/temp\\\\1498117494817.jpg","is_marry":"1","detect_type":"2","return_score":"83.78414539767971","operation_name":"杨磊","suspectTypeName":"犯罪","selfIncrease":true},{"id":"2091329","isNewRecord":false,"operation_user_id":"d7e96defb93d4f6b9a932c5a8a2c854e","operation_time":"2017-6-22 03:44:54","person_id":"crime","idcrad_img_src":"http:\/\/192.168.1.252:8080\/idcheck\\faceBank\\457878787\\\\IMG_-2109262558.jpg","face_img_src":"http:\/\/192.168.1.252:8080\/idcheck\\faceBank\/temp\\\\1498117494817.jpg","is_marry":"1","detect_type":"2","return_score":"83.78414539767971","operation_name":"杨磊","suspectTypeName":"犯罪","selfIncrease":true},{"id":"2091326","isNewRecord":false,"operation_user_id":"d7e96defb93d4f6b9a932c5a8a2c854e","operation_time":"2017-6-22 03:44:33","person_id":"crime","idcrad_img_src":"http:\/\/192.168.1.252:8080\/idcheck\\faceBank\\4664466465\\\\IMG_-2109262558.jpg","face_img_src":"http:\/\/192.168.1.252:8080\/idcheck\\faceBank\/temp\\\\1498117473539.jpg","is_marry":"1","detect_type":"2","return_score":"84.49417852629838","operation_name":"杨磊","suspectTypeName":"犯罪","selfIncrease":true},{"id":"2091327","isNewRecord":false,"operation_user_id":"d7e96defb93d4f6b9a932c5a8a2c854e","operation_time":"2017-6-22 03:44:33","person_id":"crime","idcrad_img_src":"http:\/\/192.168.1.252:8080\/idcheck\\faceBank\\457878787\\\\IMG_-2109262558.jpg","face_img_src":"http:\/\/192.168.1.252:8080\/idcheck\\faceBank\/temp\\\\1498117473539.jpg","is_marry":"1","detect_type":"2","return_score":"84.49417852629838","operation_name":"杨磊","suspectTypeName":"犯罪","selfIncrease":true}]}}

}
