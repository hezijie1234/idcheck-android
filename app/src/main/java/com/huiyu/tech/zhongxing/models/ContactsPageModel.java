package com.huiyu.tech.zhongxing.models;

import java.util.List;

/**
 * Created by ml on 2016/12/21.
 */
public class ContactsPageModel {

    /**
     * max_page : 7
     * list : [{"id":"c0f816ec505b41649484f5b7b08bef8d","isNewRecord":false,"remarks":"","createDate":"2016-08-18 13:54:25","updateDate":"2016-08-18 13:57:10","loginName":"192_168_1_123","no":"192.168.1.123","name":"192.168.1.123","email":"","phone":"","mobile":"","userType":"","loginIp":"192.168.1.123","loginDate":"2016-08-25 14:42:31","loginFlag":"1","photo":"","oldLoginIp":"192.168.1.123","oldLoginDate":"2016-08-25 14:42:31","admin":false,"roleNames":""},{"id":"3f2d2b02d80745bcbb5f4d32ffee06ac","isNewRecord":false,"remarks":"","createDate":"2016-08-18 13:53:24","updateDate":"2016-08-18 13:57:10","loginName":"192_168_1_77","no":"192.168.1.77","name":"192.168.1.77","email":"","phone":"","mobile":"","userType":"3","loginFlag":"1","photo":"","admin":false,"roleNames":""}]
     */

    private int max_page;
    /**
     * id : c0f816ec505b41649484f5b7b08bef8d
     * isNewRecord : false
     * remarks :
     * createDate : 2016-08-18 13:54:25
     * updateDate : 2016-08-18 13:57:10
     * loginName : 192_168_1_123
     * no : 192.168.1.123
     * name : 192.168.1.123
     * email :
     * phone :
     * mobile :
     * userType :
     * loginIp : 192.168.1.123
     * loginDate : 2016-08-25 14:42:31
     * loginFlag : 1
     * photo :
     * oldLoginIp : 192.168.1.123
     * oldLoginDate : 2016-08-25 14:42:31
     * admin : false
     * roleNames :
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
        private String loginName;
        private String no;
        private String name;
        private String email;
        private String phone;
        private String mobile;
        private String userType;
        private String loginIp;
        private String loginDate;
        private String loginFlag;
        private String photo;
        private String oldLoginIp;
        private String oldLoginDate;
        private boolean admin;
        private String roleNames;

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

        public String getLoginName() {
            return loginName;
        }

        public void setLoginName(String loginName) {
            this.loginName = loginName;
        }

        public String getNo() {
            return no;
        }

        public void setNo(String no) {
            this.no = no;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getUserType() {
            return userType;
        }

        public void setUserType(String userType) {
            this.userType = userType;
        }

        public String getLoginIp() {
            return loginIp;
        }

        public void setLoginIp(String loginIp) {
            this.loginIp = loginIp;
        }

        public String getLoginDate() {
            return loginDate;
        }

        public void setLoginDate(String loginDate) {
            this.loginDate = loginDate;
        }

        public String getLoginFlag() {
            return loginFlag;
        }

        public void setLoginFlag(String loginFlag) {
            this.loginFlag = loginFlag;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public String getOldLoginIp() {
            return oldLoginIp;
        }

        public void setOldLoginIp(String oldLoginIp) {
            this.oldLoginIp = oldLoginIp;
        }

        public String getOldLoginDate() {
            return oldLoginDate;
        }

        public void setOldLoginDate(String oldLoginDate) {
            this.oldLoginDate = oldLoginDate;
        }

        public boolean isAdmin() {
            return admin;
        }

        public void setAdmin(boolean admin) {
            this.admin = admin;
        }

        public String getRoleNames() {
            return roleNames;
        }

        public void setRoleNames(String roleNames) {
            this.roleNames = roleNames;
        }
    }
}
