package com.huiyu.tech.zhongxing.models;

/**
 * Created by Administrator on 2017-06-30.
 */

public class WarningNumModel {

    /**
     * m : 操作成功!
     * c : 0
     * d : {"count":17,"time":1498807204000}
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
        /**
         * count : 17
         * time : 1498807204000
         */

        private int count;
        private String time;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }
}
