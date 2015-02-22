package me.yeojoy.bab.utils;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by yeojoy on 15. 2. 5..
 */
public class DateUtil {
    
    private static final String TAG = DateUtil.class.getSimpleName();
    
    private static final String SAT = "토";
    private static final String SUN = "일";
    private static final String MON = "월";
    
    public static String getTodayDate() {
        Calendar c = Calendar.getInstance();
        Date date = c.getTime();
        
        String e = new SimpleDateFormat("E", Locale.KOREA).format(date);
        
        if (e.equals(SAT)) {
            c.add(Calendar.DAY_OF_MONTH, 2);
        } else if (e.equals(SUN)) {
            c.add(Calendar.DAY_OF_MONTH, 1);
        }
        
        date = c.getTime();
        
        return new SimpleDateFormat("yyyy-MM-dd").format(date);
    }
    
    public static String getTomorrowDate() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, 1);
        Date date = c.getTime();

        String e = new SimpleDateFormat("E", Locale.KOREA).format(date);

        if (e.equals(SAT)) {
            c.add(Calendar.DAY_OF_MONTH, 2);
        } else if (e.equals(SUN)) {
            c.add(Calendar.DAY_OF_MONTH, 2);
        } else if (e.equals(MON)) {
            c.add(Calendar.DAY_OF_MONTH, 1);
        }

        date = c.getTime();

        return new SimpleDateFormat("yyyy-MM-dd").format(date);
    }
    
    public static boolean isWeekend(Date date) {
        
        String d = new SimpleDateFormat("E").format(date);
        
        Log.d(TAG, d);
        
        if (d.equals("일") || d.equals("토") || d.equals("Sun") || d.equals("Sat"))
            return true;
        
        return false;
    }
            
}
