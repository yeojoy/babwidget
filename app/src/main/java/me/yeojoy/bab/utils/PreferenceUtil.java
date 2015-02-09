package me.yeojoy.bab.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import me.yeojoy.bab.model.TodayMenu;

/**
 * Created by yeojoy on 15. 2. 5..
 */
public class PreferenceUtil {
    
    public static final String KEY_DATE             = "date";
    public static final String KEY_MAIN             = "main_menu";
    public static final String KEY_SUB_FIRST        = "sub_menu_first";
    public static final String KEY_SUB_SECOND       = "sub_menu_second";
    public static final String KEY_SUB_THIRD        = "sub_menu_third";
    public static final String KEY_SUB_FOURTH       = "sub_menu_fourth";
    public static final String KEY_ALL_MENUS        = "all_menus";
    public static final String KEY_DIETETIC_INFO    = "dietetic_info";
    public static final String KEY_AW_MAIN          = "aw_main_menu";
    public static final String KEY_AW_SUB_FIRST     = "aw_sub_menu_first";
    public static final String KEY_AW_SUB_SECOND    = "aw_sub_menu_second";
    public static final String KEY_AW_SUB_THIRD     = "aw_sub_menu_third";
    public static final String KEY_AW_SUB_FOURTH    = "aw_sub_menu_fourth";

    public static final String KEY_HAS_LIGH_BACKGROUND = "ligth_back";
    
    private SharedPreferences mSharedPreferences;
    
    private static PreferenceUtil mUtil;
    
    public PreferenceUtil(Context context) {
        mSharedPreferences
                = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static PreferenceUtil getInstance(Context context) {
        if (mUtil == null) {
            mUtil = new PreferenceUtil(context);
        }
        return mUtil;
    }

    public void putMenuInADay(TodayMenu menus) {
        if (menus == null) return;
        
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        
        editor.putString(KEY_DATE, menus.getDate());
        editor.putString(KEY_MAIN, menus.getMainMenu());
        editor.putString(KEY_SUB_FIRST, menus.getSubMenuFirst());
        editor.putString(KEY_SUB_SECOND, menus.getSubMenuSecond());
        editor.putString(KEY_SUB_THIRD, menus.getSubMenuThird());
        editor.putString(KEY_SUB_FOURTH, menus.getSubMenuFourth());

        editor.putStringSet(KEY_ALL_MENUS, menus.getOtherMenus());
        editor.putStringSet(KEY_DIETETIC_INFO, menus.getDieteticInfo());
        
        editor.apply();
    }
    
    public TodayMenu getMenuInADay(String date) {
        String savedDate = mSharedPreferences.getString(KEY_DATE, null);
        
        // 저장한 날짜가 없거나 날짜가 같이 않다면 null을 반환
        if (savedDate == null || !savedDate.equals(date))
            return null;

        return new TodayMenu(savedDate, mSharedPreferences.getString(KEY_MAIN, null),
                mSharedPreferences.getString(KEY_SUB_FIRST, null),
                mSharedPreferences.getString(KEY_SUB_SECOND, null),
                mSharedPreferences.getString(KEY_SUB_THIRD, null),
                mSharedPreferences.getString(KEY_SUB_FOURTH, null),
                mSharedPreferences.getStringSet(KEY_ALL_MENUS, null),
                mSharedPreferences.getStringSet(KEY_DIETETIC_INFO, null));
    }
    
    public void putBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }
    
    public boolean getBoolean(String key) {
        return mSharedPreferences.getBoolean(key, false);
    }
}
