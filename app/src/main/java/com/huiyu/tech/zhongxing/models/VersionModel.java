package com.huiyu.tech.zhongxing.models;

/**
 * Created by ml on 2016/12/21.
 */
public class VersionModel {

    /**
     * id : 813870f4f374497a91f5fa1d51d53ca6
     * isNewRecord : false
     * remarks : 反倒是
     * createDate : 2016-12-01 17:41:59
     * updateDate : 2016-12-01 17:42:07
     * versionName : 我的奋斗
     * versionCode : 102
     * file : /userfiles/1/files/mobile/idcheckApk/2016/12/Shadowsocks_v2_9_6_android.apk
     */

    private String id;
    private boolean isNewRecord;
    private String remarks;
    private String createDate;
    private String updateDate;
    private String versionName;
    private int versionCode;
    private String file;

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

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }
}
