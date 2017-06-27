package com.huiyu.tech.zhongxing.models;

import java.util.List;

/**
 * Created by Administrator on 2017-06-21.
 */

public class BlackTypeModel {

    @Override
    public String toString() {
        return "BlackTypeModel{" +
                "m='" + m + '\'' +
                ", c='" + c + '\'' +
                ", d=" + d +
                '}';
    }

    /**
     * m : 操作成功!
     * c : 0
     * d : [{"id":"a39e5462acec4395a01045ae0e71a1ad","isNewRecord":false,"remarks":"","createDate":"2017-06-20 16:59:23","updateDate":"2017-06-20 16:59:23","value":"drug","label":"吸毒","type":"blacklist_type","description":"黑名单类型","sort":10,"parentId":"0","selfIncrease":false},{"id":"c2c4bf9c0a344da8a361e553e5dfaad1","isNewRecord":false,"remarks":"","createDate":"2017-06-20 16:59:48","updateDate":"2017-06-20 16:59:48","value":"crime","label":"犯罪","type":"blacklist_type","description":"黑名单类型","sort":20,"parentId":"0","selfIncrease":false}]
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
        @Override
        public String toString() {
            return "DBean{" +
                    "id='" + id + '\'' +
                    ", isNewRecord=" + isNewRecord +
                    ", remarks='" + remarks + '\'' +
                    ", createDate='" + createDate + '\'' +
                    ", updateDate='" + updateDate + '\'' +
                    ", value='" + value + '\'' +
                    ", label='" + label + '\'' +
                    ", type='" + type + '\'' +
                    ", description='" + description + '\'' +
                    ", sort=" + sort +
                    ", parentId='" + parentId + '\'' +
                    ", selfIncrease=" + selfIncrease +
                    '}';
        }

        /**
         * id : a39e5462acec4395a01045ae0e71a1ad
         * isNewRecord : false
         * remarks :
         * createDate : 2017-06-20 16:59:23
         * updateDate : 2017-06-20 16:59:23
         * value : drug
         * label : 吸毒
         * type : blacklist_type
         * description : 黑名单类型
         * sort : 10
         * parentId : 0
         * selfIncrease : false
         */

        private String id;
        private boolean isNewRecord;
        private String remarks;
        private String createDate;
        private String updateDate;
        private String value;
        private String label;
        private String type;
        private String description;
        private int sort;
        private String parentId;
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

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public String getParentId() {
            return parentId;
        }

        public void setParentId(String parentId) {
            this.parentId = parentId;
        }

        public boolean isSelfIncrease() {
            return selfIncrease;
        }

        public void setSelfIncrease(boolean selfIncrease) {
            this.selfIncrease = selfIncrease;
        }
    }
}
