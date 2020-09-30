package com.nicklaus.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    /**
     * 将传入的日期格式化
     * @param date
     * @return
     */
    public static String format(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }
}
