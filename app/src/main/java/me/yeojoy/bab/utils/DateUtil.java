package me.yeojoy.bab.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by yeojoy on 15. 2. 5..
 */
public class DateUtil {
    
    public static String getTodayDate() {
        Date date = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        
        return f.format(date);
    }
}
