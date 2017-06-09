package com.huiyu.tech.zhongxing.models;

/**
 * Created by ml on 2016/12/19.
 */
public class UserModel {


    /**
     * alarmright : 2
     * user : {"id":"60a4ddb9b99c4e5ea0e2a1415757daf8","isNewRecord":false,"remarks":"","createDate":"2016-12-24 15:23:56","updateDate":"2016-12-28 11:44:42","loginName":"zhaoaizi","no":"543543","name":"赵矮子","email":"","phone":"","mobile":"","userType":"9","loginFlag":"1","photo":"userfiles\\head\\20161228114441_无标题1.png","admin":false,"roleNames":"前端设备"}
     */

    /**
     *  0  没有权限  1  查看权限   2  设置权限
      */
    private String alarmright;
    /**
     * id : 60a4ddb9b99c4e5ea0e2a1415757daf8
     * isNewRecord : false
     * remarks :
     * createDate : 2016-12-24 15:23:56
     * updateDate : 2016-12-28 11:44:42
     * loginName : zhaoaizi
     * no : 543543
     * name : 赵矮子
     * email :
     * phone :
     * mobile :
     * userType : 9
     * loginFlag : 1
     * photo : userfiles\head\20161228114441_无标题1.png
     * admin : false
     * roleNames : 前端设备
     */

    private UserBean user;

    public String getAlarmright() {
        return alarmright;
    }

    public void setAlarmright(String alarmright) {
        this.alarmright = alarmright;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public static class UserBean {
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
        private String loginFlag;
        private String photo;
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
