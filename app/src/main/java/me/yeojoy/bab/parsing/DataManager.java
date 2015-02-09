package me.yeojoy.bab.parsing;

import android.content.Context;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import net.htmlparser.jericho.Source;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import me.yeojoy.bab.R;
import me.yeojoy.bab.model.TodayMenu;
import me.yeojoy.bab.utils.DateUtil;
import me.yeojoy.bab.utils.PreferenceUtil;
import me.yeojoy.bab.widget.WidgetLayoutManager;

/**
 * Created by yeojoy on 15. 1. 27..
 */
public class DataManager {
    
    private static final String TAG = DataManager.class.getSimpleName();

    // 첫번째 %s는 yyyyMMdd 형식의 날짜
    // 두번째 %d는 1~4 중 하나로
    private static final String URL_FORMAT 
            = "http://emenu.ourhome.co.kr/meal/list.action" +
            "?tempcd=c58cddd991f708569abe8b21ba8c2f07" +
            "&offerdt=" + "%s" +    // 날짜(yyyyMMdd)
            "&up_yn=N" +
            "&up_busiplcd=c58cddd991f708569abe8b21ba8c2f07" +
            "&busiord=" +
            "&mealclass=" + "%d" +  // 시간(2: 점심, 3: 저녁)
            "&conner=A";
    private static final int LUNCH = 2;
    private static final int AFTER_WORK = 3;
    
    private static DataManager mManager;

    private OnFinishParsingListener mListener;
    
    public enum Time {
        TODAY, TOMORROW
    }
    
    public static DataManager getInstance() {
        if (mManager == null)
            mManager = new DataManager();
        
        return mManager;
    }

    public interface OnFinishParsingListener {
        public void onFinishParsingData(TodayMenu menus);
    }
    
    public void setOnFinishParsingListener(OnFinishParsingListener l) {
        mListener = l;
    }

    public void updateMenu(Context context, boolean isToday) {

        if (isToday) {
            getMenuData(context, DataManager.Time.TODAY);
        } else {
            getMenuData(context, Time.TOMORROW);
        }

    }
    
    private void getMenuData(Context context, Time time) {
        Log.d(TAG, "getMenuData()");

        String date;
        
        switch (time) {
            case TODAY:
            default:
                date = DateUtil.getTodayDate();
                break;
            case TOMORROW:
                date = DateUtil.getTomorrowDate();
                break;
            
        }
        
        TodayMenu menu = PreferenceUtil.getInstance(context).getMenuInADay(date);
        
        if (menu != null) {
            if (mListener != null)
                mListener.onFinishParsingData(menu);

            RemoteViews views = new RemoteViews(context.getPackageName(),
                    R.layout.bab_widget);

            WidgetLayoutManager.setWidgetViews(context, views, null, -1, menu);
            
            return;
        }
        
        Log.d(TAG, "date : " + date);
        new ParserAsyncTask(context, date).execute();
    } 
    
    class ParserAsyncTask extends AsyncTask<Void, Void, TodayMenu> {
        private Context mContext;
        
        private String date;

        private List<String> menusLunch;
        
        public ParserAsyncTask(Context context, String date) {
            mContext = context;
            this.date = date;
        }
        
        @Override
        protected TodayMenu doInBackground(Void... params) {
            menusLunch = getCafeteriaMenu(date, LUNCH);

            TodayMenu todayMenu = new TodayMenu();
            
            if (menusLunch.size() > 0) {
                Log.d(TAG, "===========================================");
                Set<String> otherMenus = new HashSet<String>();
                Set<String> dieteticInfo = new HashSet<String>();
                int index = 0;
                for (String s : menusLunch) {
                    Log.d(TAG, s);
                    if (s.startsWith("menu_") && index > 4) {
                        otherMenus.add(s.substring(5));
                    } else if (s.startsWith("info_")) {
                        dieteticInfo.add(s.substring(5));
                    }
                    index++;
                }
                Log.d(TAG, "===========================================");
                
                todayMenu.setDate(date);
                todayMenu.setMainMenu(menusLunch.get(0).substring(5));
                todayMenu.setSubMenuFirst(menusLunch.get(1).substring(5));
                todayMenu.setSubMenuSecond(menusLunch.get(2).substring(5));
                todayMenu.setSubMenuThird(menusLunch.get(3).substring(5));
                todayMenu.setSubMenuFourth(menusLunch.get(4).substring(5));
                todayMenu.setOtherMenus(otherMenus);
                todayMenu.setDieteticInfo(dieteticInfo);

            }

            PreferenceUtil.getInstance(mContext).putMenuInADay(todayMenu);
            
            return todayMenu;
        }

        @Override
        protected void onPostExecute(TodayMenu menu) {
            super.onPostExecute(menu);

            if (menu == null) return;
            if (mListener != null) {
                mListener.onFinishParsingData(menu);
            }

            RemoteViews views = new RemoteViews(mContext.getPackageName(),
                    R.layout.bab_widget); 
            
            WidgetLayoutManager.setWidgetViews(mContext, views, null, -1, menu);
        }
    }
    
    private List<String> getCafeteriaMenu(String date, int time) {
        URL url;
        Source source;
        List<String> menu = new ArrayList<String>();

        date = date.replaceAll("-", "");
        
        try {
            url = new URL(String.format(URL_FORMAT, date, time));
            Log.d(TAG, url.toString());
            source = new Source(url);
            List<Element> list = source.getAllElements(HTMLElementName.TD);
            String str = null;

            for (Element e : list) {

                str = e.getTextExtractor().toString();

                if (str != null && !TextUtils.isEmpty(str)) {
                    if (str.indexOf("·") > -1) {
                        str = str.substring(1);
                        menu.add("menu_" + str);
                    } else {
                        // 열량정보
                        menu.add("info_" + str);
                    }
                }
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return menu;
    }
}
