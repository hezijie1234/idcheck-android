package com.huiyu.tech.zhongxing.models;

import java.util.List;

/**
 * Created by ml on 2016/12/10.
 */
public class EmergencyNoticeModel {

    /**
     * max_page : 1
     * list : [{"id":"a23300c8813a4c8bad00eb13f56aea72","isNewRecord":false,"remarks":"个地方官","createDate":"2016-12-20 14:57:42","updateDate":"2016-12-20 14:57:42","title":"紧急布控","image":"/userfiles/1/images/mobile/idcheckEmergencyDeploy/2016/12/%E5%91%98%E5%B7%A52(2)%20-%20%E5%89%AF%E6%9C%AC.jpg","content":"<p>\r\n\t第三方第三方大是大非规范<\/p>","pubeDate":"2016-12-20 14:57:37","department":"发发发"},{"id":"7e9be8366d114027aa473756d95225f1","isNewRecord":false,"remarks":"1313123","createDate":"2016-12-15 11:22:55","updateDate":"2016-12-15 11:22:55","title":"刘兴旺","image":"/userfiles/1/images/mobile/idcheckEmergencyDeploy/2016/12/173656pzhxc1cmbw0rhxrh_jpg_thumb.jpg","content":"<p>\r\n\t刘兴旺，抢劫犯，二号通缉犯<\/p>","pubeDate":"2016-12-14 11:22:44","department":"武汉公安局"},{"id":"1032b3613f464b83ba08032aa3801473","isNewRecord":false,"remarks":"123123","createDate":"2016-12-15 11:22:05","updateDate":"2016-12-15 11:22:05","title":"魏晓菲","image":"/userfiles/1/images/mobile/idcheckEmergencyDeploy/2016/12/173653nz4wwmuxvymdmxvo_jpg_thumb.jpg","content":"<p>\r\n\t魏晓菲，一号通缉犯<\/p>","pubeDate":"2016-12-15 11:21:49","department":"湖北公安厅"}]
     */

    private int max_page;
    /**
     * id : a23300c8813a4c8bad00eb13f56aea72
     * isNewRecord : false
     * remarks : 个地方官
     * createDate : 2016-12-20 14:57:42
     * updateDate : 2016-12-20 14:57:42
     * title : 紧急布控
     * image : /userfiles/1/images/mobile/idcheckEmergencyDeploy/2016/12/%E5%91%98%E5%B7%A52(2)%20-%20%E5%89%AF%E6%9C%AC.jpg
     * content : <p>
     第三方第三方大是大非规范</p>
     * pubeDate : 2016-12-20 14:57:37
     * department : 发发发
     */

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
        private String id;
        private boolean isNewRecord;
        private String remarks;
        private String createDate;
        private String updateDate;
        private String title;
        private String image;
        private String content;
        private String pubeDate;
        private String department;
        private String createName;
        private String desc;

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getDesc() {
            return desc;
        }

        public void setCreateName(String createName) {
            this.createName = createName;
        }

        public String getCreateName() {

            return createName;
        }

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

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getPubeDate() {
            return pubeDate;
        }

        public void setPubeDate(String pubeDate) {
            this.pubeDate = pubeDate;
        }

        public String getDepartment() {
            return department;
        }

        public void setDepartment(String department) {
            this.department = department;
        }
    }
}
