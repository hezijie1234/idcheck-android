package com.huiyu.tech.zhongxing.models;

/**
 * =============================================================
 * Copyright (c) 2015-2017 huiyu, Inc.
 * All rights reserved.
 * <p>
 * 文件名：     RecognitionModel
 * 作者：       songwei
 * 创建日期：   2017-02-24
 * 版本：       V1.0
 * 描述：       认证识别的时候，用来上传的数据
 * *============================================================
 */

public class RecognitionModel {


    /**
     * idCardImage : {"imgName":"身份证","imgStr":"************","imgType":"1"}
     * phoneImage : {"imgName":"时间戳","imgStr":"****************","imgType":"2"}
     * idcardBean : {"idCard":"420103198604153710","idName":"江纯","idSex":"1","idEntityType":"1","idBirthday":"1986-04-15","idNation":"汉","idAddress":"湖北省武汉市江汉区万松园小区","idValidateDate":"2015-12-28 - 2035-12-28","idLicenceAuthority":"武汉市公安局江汉分局"}
     */

    private IdCardImageBean idCardImage;
    private PhoneImageBean phoneImage;
    private IdcardBeanBean idcardBean;

    public IdCardImageBean getIdCardImage() {
        return idCardImage;
    }

    public void setIdCardImage(IdCardImageBean idCardImage) {
        this.idCardImage = idCardImage;
    }

    public PhoneImageBean getPhoneImage() {
        return phoneImage;
    }

    public void setPhoneImage(PhoneImageBean phoneImage) {
        this.phoneImage = phoneImage;
    }

    public IdcardBeanBean getIdcardBean() {
        return idcardBean;
    }

    public void setIdcardBean(IdcardBeanBean idcardBean) {
        this.idcardBean = idcardBean;
    }

    public static class IdCardImageBean {
        /**
         * imgName : 身份证
         * imgStr : ************
         * imgType : 1
         */

        private String imgName;
        private String imgStr;
        private String imgType;

        public String getImgName() {
            return imgName;
        }

        public void setImgName(String imgName) {
            this.imgName = imgName;
        }

        public String getImgStr() {
            return imgStr;
        }

        public void setImgStr(String imgStr) {
            this.imgStr = imgStr;
        }

        public String getImgType() {
            return imgType;
        }

        public void setImgType(String imgType) {
            this.imgType = imgType;
        }

        @Override
        public String toString() {
            return "IdCardImageBean{" +
                    "imgName='" + imgName + '\'' +
                    ", imgStr='" + imgStr + '\'' +
                    ", imgType='" + imgType + '\'' +
                    '}';
        }
    }

    public static class PhoneImageBean {
        /**
         * imgName : 时间戳
         * imgStr : ****************
         * imgType : 2
         */

        private String imgName;
        private String imgStr;
        private String imgType;

        public String getImgName() {
            return imgName;
        }

        public void setImgName(String imgName) {
            this.imgName = imgName;
        }

        public String getImgStr() {
            return imgStr;
        }

        public void setImgStr(String imgStr) {
            this.imgStr = imgStr;
        }

        public String getImgType() {
            return imgType;
        }

        public void setImgType(String imgType) {
            this.imgType = imgType;
        }

        @Override
        public String toString() {
            return "PhoneImageBean{" +
                    "imgName='" + imgName + '\'' +
                    ", imgStr='" + imgStr + '\'' +
                    ", imgType='" + imgType + '\'' +
                    '}';
        }
    }

    public static class IdcardBeanBean {
        /**
         * idCard : 420103198604153710
         * idName : 江纯
         * idSex : 1
         * idEntityType : 1
         * idBirthday : 1986-04-15
         * idNation : 汉
         * idAddress : 湖北省武汉市江汉区万松园小区
         * idValidateDate : 2015-12-28 - 2035-12-28
         * idLicenceAuthority : 武汉市公安局江汉分局
         */

        private String idCard;
        private String idName;
        private String idSex;
        private String idEntityType;
        private String idBirthday;
        private String idNation;
        private String idAddress;
        private String idValidateDate;
        private String idLicenceAuthority;

        public String getIdCard() {
            return idCard;
        }

        public void setIdCard(String idCard) {
            this.idCard = idCard;
        }

        public String getIdName() {
            return idName;
        }

        public void setIdName(String idName) {
            this.idName = idName;
        }

        public String getIdSex() {
            return idSex;
        }

        public void setIdSex(String idSex) {
            this.idSex = idSex;
        }

        public String getIdEntityType() {
            return idEntityType;
        }

        public void setIdEntityType(String idEntityType) {
            this.idEntityType = idEntityType;
        }

        public String getIdBirthday() {
            return idBirthday;
        }

        public void setIdBirthday(String idBirthday) {
            this.idBirthday = idBirthday;
        }

        public String getIdNation() {
            return idNation;
        }

        public void setIdNation(String idNation) {
            this.idNation = idNation;
        }

        public String getIdAddress() {
            return idAddress;
        }

        public void setIdAddress(String idAddress) {
            this.idAddress = idAddress;
        }

        public String getIdValidateDate() {
            return idValidateDate;
        }

        public void setIdValidateDate(String idValidateDate) {
            this.idValidateDate = idValidateDate;
        }

        public String getIdLicenceAuthority() {
            return idLicenceAuthority;
        }

        public void setIdLicenceAuthority(String idLicenceAuthority) {
            this.idLicenceAuthority = idLicenceAuthority;
        }

        @Override
        public String toString() {
            return "IdcardBeanBean{" +
                    "idCard='" + idCard + '\'' +
                    ", idName='" + idName + '\'' +
                    ", idSex='" + idSex + '\'' +
                    ", idEntityType='" + idEntityType + '\'' +
                    ", idBirthday='" + idBirthday + '\'' +
                    ", idNation='" + idNation + '\'' +
                    ", idAddress='" + idAddress + '\'' +
                    ", idValidateDate='" + idValidateDate + '\'' +
                    ", idLicenceAuthority='" + idLicenceAuthority + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "RecognitionModel{" +
                "idCardImage=" + idCardImage +
                ", phoneImage=" + phoneImage +
                ", idcardBean=" + idcardBean +
                '}';
    }
}
