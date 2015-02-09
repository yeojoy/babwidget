package me.yeojoy.bab.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import me.yeojoy.bab.BabApplication;
import me.yeojoy.bab.MainActivity;
import me.yeojoy.bab.R;
import me.yeojoy.bab.config.Consts;
import me.yeojoy.bab.model.TodayMenu;
import me.yeojoy.bab.utils.DateUtil;
import me.yeojoy.bab.utils.PreferenceUtil;

/**
 * Created by yeojoy on 15. 2. 5..
 */
public class WidgetLayoutManager implements Consts {
    
    private static final String TAG = WidgetLayoutManager.class.getSimpleName();
    
    private static final String BTN_TODAY = "오늘 메뉴";
    private static final String BTN_TOMORROW = "내일 메뉴";

    public static void setWidgetViews(Context context, RemoteViews views,
                                      AppWidgetManager manager, int widgetId) {

        Log.i(TAG, "setWidgetViews()");
        // View가 업데이트 됐음을 알린다.
        if (manager == null) {
            manager = AppWidgetManager.getInstance(context);
        }

        TodayMenu menus = PreferenceUtil.getInstance(context).getMenuInADay(DateUtil.getTodayDate());
        
        if (menus != null) {
            setWidgetViews(context, views, manager, widgetId, menus);
            return;
        }

        views.setTextViewText(R.id.tv_date, DateUtil.getTodayDate());
        
        bindEvents(context, views);
        
        if (widgetId != -1) {
            manager.updateAppWidget(widgetId, views);
        } else {
            ComponentName myWidget = new ComponentName(context, BabHomeWidgetProvider.class);
            manager.updateAppWidget(myWidget, views);
        }

    }

    public static void setWidgetViews(Context context, RemoteViews views,
                                      AppWidgetManager manager, int widgetId,
                                      TodayMenu todayMenu) {

        Log.i(TAG, "setWidgetViews()");
        // View가 업데이트 됐음을 알린다.
        if (manager == null) {
            manager = AppWidgetManager.getInstance(context);
        }

        StringBuilder sb = new StringBuilder();
        
        if ((todayMenu.getSubMenuFirst().length() 
                + todayMenu.getSubMenuSecond().length() 
                + todayMenu.getSubMenuThird().length() 
                + todayMenu.getSubMenuFourth().length()) < 16) {
            sb.append(todayMenu.getSubMenuFirst()).append("  ");
            sb.append(todayMenu.getSubMenuSecond()).append("  ");
            sb.append(todayMenu.getSubMenuThird()).append("  ");
            sb.append(todayMenu.getSubMenuFourth());
        } else {
            sb.append(todayMenu.getSubMenuFirst()).append("  ");
            sb.append(todayMenu.getSubMenuSecond()).append("  ");
            sb.append(todayMenu.getSubMenuThird());
        }

        views.setTextViewText(R.id.tv_main_menu, todayMenu.getMainMenu());
        views.setTextViewText(R.id.tv_sub_menu, sb);
        views.setTextViewText(R.id.tv_date, todayMenu.getDate());

        bindEvents(context, views);
        
        if (widgetId != -1) {
            manager.updateAppWidget(widgetId, views);
        } else {
            ComponentName myWidget = new ComponentName(context, BabHomeWidgetProvider.class);
            manager.updateAppWidget(myWidget, views);
        }
    }
    
    private static void bindEvents(Context context, RemoteViews views) {
        
        if (BabApplication.isToday) {
            views.setTextViewText(R.id.btn_today, BTN_TOMORROW);
            Intent tomorrowMenu = new Intent(TOMORROW_MENU_ACTION);
            PendingIntent tomorrowPending = PendingIntent.getBroadcast(context,
                    0x00010002, tomorrowMenu, PendingIntent.FLAG_ONE_SHOT);
            views.setOnClickPendingIntent(R.id.btn_today, tomorrowPending);
        } else {
            views.setTextViewText(R.id.btn_today, BTN_TODAY);
            Intent todayMenu = new Intent(TODAY_MENU_ACTION);
            PendingIntent todayPending = PendingIntent.getBroadcast(context,
                    0x00010001, todayMenu, PendingIntent.FLAG_ONE_SHOT);
            views.setOnClickPendingIntent(R.id.btn_today, todayPending);
        }

        Intent launchApp = new Intent(context, MainActivity.class);
        PendingIntent launchAppPending = PendingIntent.getActivity(context,
                0x00020001, launchApp, PendingIntent.FLAG_ONE_SHOT);

        views.setOnClickPendingIntent(R.id.iv_icon, launchAppPending);
    }
}
