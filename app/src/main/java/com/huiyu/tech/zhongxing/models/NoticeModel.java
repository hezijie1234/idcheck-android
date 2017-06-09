package com.huiyu.tech.zhongxing.models;

import java.util.List;

/**
 * Created by ml on 2016/12/8.
 */
public class NoticeModel {

    /**
     * max_page : 1
     * list : [{"id":"75be0baa2fbf4c73a31884895b8dc923","isNewRecord":false,"remarks":"的广告费和梵蒂冈和返回规范","createDate":"2016-12-01 17:14:55","updateDate":"2016-12-01 17:53:02","title":"OIJGFDSHFJDJ HJ","image":"/userfiles/1/files/mobile/idcheckNotice/2016/12/%E5%91%98%E5%B7%A52(2)%20-%20%E5%89%AF%E6%9C%AC.jpg","content":"<p>\r\n\tFGHDFG DFG HDFGHF HF HDFG H<\/p>","department":"是否"}]
     */

    private int max_page;
    /**
     * id : 75be0baa2fbf4c73a31884895b8dc923
     * isNewRecord : false
     * remarks : 的广告费和梵蒂冈和返回规范
     * createDate : 2016-12-01 17:14:55
     * updateDate : 2016-12-01 17:53:02
     * title : OIJGFDSHFJDJ HJ
     * image : /userfiles/1/files/mobile/idcheckNotice/2016/12/%E5%91%98%E5%B7%A52(2)%20-%20%E5%89%AF%E6%9C%AC.jpg
     * content : <p>
     FGHDFG DFG HDFGHF HF HDFG H</p>
     * department : 是否
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
        private String department;
        private String createName;
        private String pubeDate;

        public void setCreateName(String createName) {
            this.createName = createName;
        }

        public void setPubeDate(String pubeDate) {
            this.pubeDate = pubeDate;
        }

        public String getCreateName() {

            return createName;
        }

        public String getPubeDate() {
            return pubeDate;
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

        public String getDepartment() {
            return department;
        }

        public void setDepartment(String department) {
            this.department = department;
        }
    }
}
