package com.huiyu.tech.zhongxing.models;

/**
 * Created by ml on 2016/12/21.
 */
public class EmergencyNoticeDetailModel {

    /**
     * id : 7e9be8366d114027aa473756d95225f1
     * isNewRecord : false
     * remarks : 1313123
     * createDate : 2016-12-15 11:22:55
     * updateDate : 2016-12-15 11:22:55
     * title : 刘兴旺
     * image : /userfiles/1/images/mobile/idcheckEmergencyDeploy/2016/12/173656pzhxc1cmbw0rhxrh_jpg_thumb.jpg
     * content : <p>
     刘兴旺，抢劫犯，二号通缉犯</p>
     * pubeDate : 2016-12-14 11:22:44
     * department : 武汉公安局
     */

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
