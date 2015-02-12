package me.yeojoy.bab.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import me.yeojoy.bab.config.Consts;
import me.yeojoy.bab.parsing.DataManager;

/**
 * Created by yeojoy on 15. 1. 27..
 */
public class BabHomeWidgetProvider extends AppWidgetProvider implements Consts {

    private static final String TAG = BabHomeWidgetProvider.class.getSimpleName();
    
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "onReceive()");
        super.onReceive(context, intent);
        if (intent == null) return;

        final String action = intent.getAction();
        Log.d(TAG, "onReceive(), action is " + action);

        if (action.equals(TODAY_MENU_ACTION)) {
            DataManager.getInstance().updateMenu(context, true);
            WidgetLayoutManager.updateWidgetViewForLoading(context, null, null, -1, null);
        } else if (action.equals(TOMORROW_MENU_ACTION)) {
            DataManager.getInstance().updateMenu(context, false);
            WidgetLayoutManager.updateWidgetViewForLoading(context, null, null, -1, null);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        Log.i(TAG, "onUpdate()");
        final int widgetCount = appWidgetIds.length;

        for (int i = 0; i < widgetCount; i++) {
            Log.d(TAG, "onUpdate(), AppWidget ID : " + appWidgetIds[i]);

            DataManager.getInstance().updateMenu(context, true);
        }
    }

}
