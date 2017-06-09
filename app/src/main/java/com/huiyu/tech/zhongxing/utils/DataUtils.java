package com.huiyu.tech.zhongxing.utils;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2017/3/30.
 */

public class DataUtils {

    /**created by hezijie at 2017/3/30
     * 将格式化的日期装换成Date
     * @param dateString
     * @return
     */
    public static Date string2Data(String dateString){
        //从第一个字符开始解析
        ParsePosition position = new ParsePosition(0);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date dateValue = sdf.parse(dateString,position);
        return dateValue;
    }

}
