package me.yeojoy.bab.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by yeojoy on 15. 2. 5..
 */
public class PreferenceUtil {
    
    public static final String KEY_DATE         = "date";
    public static final String KEY_MAIN         = "main_menu";
    public static final String KEY_SUB_FIRST    = "sub_menu_first";
    public static final String KEY_SUB_SECOND   = "sub_menu_second";
    public static final String KEY_SUB_THIRD    = "sub_menu_third";
    public static final String KEY_SUB_FOURTH   = "sub_menu_fourth";

    public static SharedPreferences getSharedPreference(Context context) {
        
        return PreferenceManager.getDefaultSharedPreferences(context);
    }
}
