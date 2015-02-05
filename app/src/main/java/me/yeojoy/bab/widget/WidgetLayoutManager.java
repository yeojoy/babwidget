package me.yeojoy.bab.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

/**
 * Created by yeojoy on 15. 2. 5..
 */
public class WidgetLayoutManager {
    
    private static final String TAG = WidgetLayoutManager.class.getSimpleName();
    
    public static void setWidgetViews(Context context, RemoteViews views,
                                      AppWidgetManager manager, int widgetId) {

        Log.i(TAG, "setWidgetViews()");
        // View가 업데이트 됐음을 알린다.
        if (manager == null) {
            manager = AppWidgetManager.getInstance(context);
        }

//        Log.d(TAG, "setWidgetViews(), COUNT is " + COUNT);
//
//        // PendingIntent의 requestCode가 같아지면 바로 가장 마지막 PendingIntent의
//        // Intent로 강제 update되는 사태가 발생함.
//        Intent plusIntent = new Intent(PLUS_ACTION);
//        PendingIntent plusPIntent = PendingIntent.getBroadcast(context,
//                PLUS_ID, plusIntent, PendingIntent.FLAG_ONE_SHOT);
//
//        Intent minusIntent = new Intent(MINUS_ACTION);
//        PendingIntent minusPIntent = PendingIntent.getBroadcast(context,
//                MINUS_ID, minusIntent, PendingIntent.FLAG_ONE_SHOT);
//        views.setOnClickPendingIntent(R.id.btn_plus, plusPIntent);
//        views.setOnClickPendingIntent(R.id.btn_minus, minusPIntent);
//
//        views.setImageViewResource(R.id.iv_cup, colorList[COUNT]);
//
//        if (COUNT == 0) {
//            views.setTextViewText(R.id.tv_percentage, "0");
//            views.setProgressBar(R.id.pb_percentage, 100, 0, false);
//        } else {
//            int percentage = (int) (COUNT * ONE_SHOT);
//            views.setTextViewText(R.id.tv_percentage, String.valueOf(percentage));
//            views.setProgressBar(R.id.pb_percentage, 100, percentage, false);
//        }
//        // Tell the AppWidgetManager to perform an update on the current app widget
//        if (widgetId != -1) {
//            manager.updateAppWidget(widgetId, views);
//        } else {
//            ComponentName myWidget = new ComponentName(context, WaterWidgetProvider.class);
//            manager.updateAppWidget(myWidget, views);
//        }
        
    }
}
