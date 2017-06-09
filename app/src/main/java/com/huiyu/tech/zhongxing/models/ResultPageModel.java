package com.huiyu.tech.zhongxing.models;

import java.util.List;

/**
 * Created by ml on 2016/12/22.
 */
public class ResultPageModel {

    /**
     * id : 098a399fedbb430e83e6bf88c08785eb
     * isNewRecord : false
     * createDate : 2016-12-22 14:40:13
     * updateDate : 2016-12-22 14:40:13
     * almid : c377d7996e06499dbabb9ffc30a63ff2
     * videofile : alarmret\videofile\20161222144013_VID_20161222_110018.mp4
     * comments : 4444
     * status : 1
     */

    private AlarmretBean alarmret;
    /**
     * id : 852f674a9f9a49749f7a6c418f9ba4a9
     * isNewRecord : false
     * createDate : 2016-12-22 14:40:32
     * updateDate : 2016-12-22 14:40:32
     * almchkid : 098a399fedbb430e83e6bf88c08785eb
     * imagefile : userfiles\head\20161222144031_img_1482389002223.jpg
     */

    private List<ImagesBean> images;
    /**
     * recuser : idcheck
     * recphone : 13597526179
     */

    private String recuser;
    private String recphone;

    public AlarmretBean getAlarmret() {
        return alarmret;
    }

    public void setAlarmret(AlarmretBean alarmret) {
        this.alarmret = alarmret;
    }

    public List<ImagesBean> getImages() {
        return images;
    }

    public void setImages(List<ImagesBean> images) {
        this.images = images;
    }

    public String getRecuser() {
        return recuser;
    }

    public void setRecuser(String recuser) {
        this.recuser = recuser;
    }

    public String getRecphone() {
        return recphone;
    }

    public void setRecphone(String recphone) {
        this.recphone = recphone;
    }

    public static class AlarmretBean {
        private String id;
        private boolean isNewRecord;
        private String createDate;
        private String updateDate;
        private String almid;
        private String videofile;
        private String comments;
        private String status;
        private String videoThumbnail;

        public String getVideoThumbnail() {
            return videoThumbnail;
        }

        public void setVideoThumbnail(String videoThumbnail) {
            this.videoThumbnail = videoThumbnail;
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

        public String getAlmid() {
            return almid;
        }

        public void setAlmid(String almid) {
            this.almid = almid;
        }

        public String getVideofile() {
            return videofile;
        }

        public void setVideofile(String videofile) {
            this.videofile = videofile;
        }

        public String getComments() {
            return comments;
        }

        public void setComments(String comments) {
            this.comments = comments;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }

    public static class ImagesBean {
        private String id;
        private boolean isNewRecord;
        private String createDate;
        private String updateDate;
        private String almchkid;
        private String imagefile;

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

        public String getUpdateDate() {
            return updateDate;
        }

        public void setUpdateDate(String updateDate) {
            this.updateDate = updateDate;
        }

        public String getAlmchkid() {
            return almchkid;
        }

        public void setAlmchkid(String almchkid) {
            this.almchkid = almchkid;
        }

        public String getImagefile() {
            return imagefile;
        }

        public void setImagefile(String imagefile) {
            this.imagefile = imagefile;
        }
    }
}
