package me.yeojoy.bab.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import me.yeojoy.bab.MainActivity;
import me.yeojoy.bab.R;
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
        } else if (action.equals(TOMORROW_MENU_ACTION)) {
            DataManager.getInstance().updateMenu(context, false);
//            Toast.makeText(context, "준비 중 입니다.", Toast.LENGTH_SHORT).show();
        } else if (action.equals(LAUNCH_APP_ACTION)) {
            Intent i = new Intent(context, MainActivity.class);
            context.startActivity(i);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        Log.i(TAG, "onUpdate()");
        final int widgetCount = appWidgetIds.length;

        for (int i = 0; i < widgetCount; i++) {
            Log.d(TAG, "onUpdate(), AppWidget ID : " + appWidgetIds[i]);
            RemoteViews views = new RemoteViews(context.getPackageName(), 
                    R.layout.bab_widget);
            
            WidgetLayoutManager.setWidgetViews(context, views, appWidgetManager,
                    appWidgetIds[i]);
        }
    }

}
