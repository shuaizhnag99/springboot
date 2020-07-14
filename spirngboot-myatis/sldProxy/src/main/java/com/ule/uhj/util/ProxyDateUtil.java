package com.ule.uhj.util;

import java.util.Calendar;

public class ProxyDateUtil {

    /**
     * 返回距离明天还有多少秒
     * @return
     */
    public static int getSecondsTomorrow() {
        Calendar curDate = Calendar.getInstance();
        Calendar tomorrowZeroDate = Calendar.getInstance();
        tomorrowZeroDate.set(curDate.get(Calendar.YEAR), curDate.get(Calendar.MONTH), curDate.get(Calendar.DATE) + 1, 0, 0, 0);
        return (int) ((tomorrowZeroDate.getTimeInMillis() - curDate.getTimeInMillis()) / 1000);
    }
}
