package core.framework.dev.utils;

import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by lyh on 2016/11/21.
 */

public class ZhiHuDateUtils {

    /**
     * 根据page 获取日期
     * <p>
     * 拉取知乎数据时，查看过往消息需要的日期
     *
     * @param page
     * @return
     */
    public static String getCurrnetDate(int page) {
        page++;
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        day -= page;
        calendar.set(Calendar.DAY_OF_MONTH, day);
        if (day == 0) {
            //下面可以设置月份，注：月份设置要减1，所以设置1月就是1-1，设置2月就是2-1，如此类推
            calendar.set(Calendar.MONTH, month - 1);
            int MaxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
            calendar.set(Calendar.DAY_OF_MONTH, MaxDay);
            month--;
            if (month == 0) {
                calendar.set(Calendar.YEAR, year - 1);
                year--;
                if (year == 0) {
                    Log.e("ArrayUtils", "你真能刷");
                }
            }
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        return sdf.format(calendar.getTime());
    }

}
