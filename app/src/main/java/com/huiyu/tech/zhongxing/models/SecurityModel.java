package com.huiyu.tech.zhongxing.models;

import java.util.List;

/**
 * Created by Administrator on 2017-08-09.
 */

public class SecurityModel {

    /**
     * m : 操作成功!
     * c : 0
     * d : {"stationList":[{"value":"station_a","label":"A站点","type":"security_station","description":"安全考核站点"},{"value":"station_b","label":"B站点","type":"security_station","description":"安全考核站点"}],"companyList":[{"value":"1","label":"A公司","type":"security_company","description":"安全考核公司"},{"value":"2","label":"B公司","type":"security_company","description":"安全考核公司"}],"wayList":[{"value":"1","label":"液体","type":"security_way","description":"过关方式"},{"value":"2","label":"管制刀具","type":"security_way","description":"过关方式"}]}
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
        private List<StationListBean> stationList;
        private List<CompanyListBean> companyList;
        private List<WayListBean> wayList;

        public List<StationListBean> getStationList() {
            return stationList;
        }

        public void setStationList(List<StationListBean> stationList) {
            this.stationList = stationList;
        }

        public List<CompanyListBean> getCompanyList() {
            return companyList;
        }

        public void setCompanyList(List<CompanyListBean> companyList) {
            this.companyList = companyList;
        }

        public List<WayListBean> getWayList() {
            return wayList;
        }

        public void setWayList(List<WayListBean> wayList) {
            this.wayList = wayList;
        }

        public static class StationListBean {
            /**
             * value : station_a
             * label : A站点
             * type : security_station
             * description : 安全考核站点
             */

            private String value;
            private String label;
            private String type;
            private String description;

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
        }

        public static class CompanyListBean {
            /**
             * value : 1
             * label : A公司
             * type : security_company
             * description : 安全考核公司
             */

            private String value;
            private String label;
            private String type;
            private String description;

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
        }

        public static class WayListBean {
            /**
             * value : 1
             * label : 液体
             * type : security_way
             * description : 过关方式
             */

            private String value;
            private String label;
            private String type;
            private String description;

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
        }
    }
}
