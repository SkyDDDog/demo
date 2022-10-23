package com.lczyfz.demo.sys.utils;

import java.util.Date;

public class DateUtil {

    public static Integer getMinutesFromNow(Date startTime) {
        Date endTime = new Date();
        long time = endTime.getTime() - startTime.getTime();
        time /= (60*1000);
        return Math.toIntExact(time);
    }

}
