package me.yeojoy.bab;

import android.app.Application;

import me.yeojoy.bab.utils.PreferenceUtil;

/**
 * Created by yeojoy on 15. 2. 9..
 */
public class BabApplication extends Application {

    public static boolean isToday = true;
    
    public static boolean mHasLightBackground = false;
    
    @Override
    public void onCreate() {
        super.onCreate();

        mHasLightBackground = PreferenceUtil.getInstance(this)
                .getBoolean(PreferenceUtil.KEY_HAS_LIGH_BACKGROUND);
    }
}
