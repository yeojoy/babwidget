package me.yeojoy.bab.parsing;

import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

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
import java.util.List;

import me.yeojoy.bab.model.TodayMenu;
import me.yeojoy.bab.utils.DateUtil;

/**
 * Created by yeojoy on 15. 1. 27..
 */
public class DataManager {
    
    private static final String TAG = DataManager.class.getSimpleName();
    
    private static DataManager mManager;
    private Context mContext;
    
    private OnFinishParsingListener mListener;
    
    public DataManager(Context context) {
        mContext = context;
    }
    
    public static DataManager getInstance(Context context) {
        if (mManager == null)
            mManager = new DataManager(context);
        
        return mManager;
    }

    public interface OnFinishParsingListener {
        public void onFinishParsingData(TodayMenu menus);
    }
    
    public void setOnFinishParsingListener(OnFinishParsingListener l) {
        mListener = l;
    }
    
    public void getData(boolean isToday) {
        String date;
        if (isToday) {
            // 오늘
            date = new SimpleDateFormat("yyyyMMdd").format(new Date());
        } else {
            Calendar c = Calendar.getInstance();
            c.add(Calendar.DAY_OF_MONTH, 1);
            // 내일
            date = new SimpleDateFormat("yyyyMMdd")
                    .format(new Date(c.getTimeInMillis()));
        }

        new ParserAsyncTask().execute(date);
    } 
    
    class ParserAsyncTask extends AsyncTask<String, Void, TodayMenu> {
        // 첫번째 %s는 yyyyMMdd 형식의 날짜
        // 두번째 %d는 1~4 중 하나로 
        private static final String URL_FORMAT = "http://emenu.ourhome.co.kr/meal/list.action?tempcd=c58cddd991f708569abe8b21ba8c2f07&offerdt=%s&up_yn=N&up_busiplcd=c58cddd991f708569abe8b21ba8c2f07&busiord=&mealclass=%d&conner=A";
        private static final String TEST_URL = "http://emenu.ourhome.co.kr/meal/list.action?tempcd=c58cddd991f708569abe8b21ba8c2f07&offerdt=20150127&up_yn=N&up_busiplcd=c58cddd991f708569abe8b21ba8c2f07&busiord=&mealclass=2&conner=A";
        private static final int BREAKFAST = 1;
        private static final int LUNCH = 2;
        private static final int AFTER_WORK = 3;

        private String today;

        @Override
        protected TodayMenu doInBackground(String... params) {

            if (params.length < 0 || TextUtils.isEmpty(params[0])) {
                return null;
            }
            
            today = params[0];
            
            URL url;
            Source source;
            List<String> menu = new ArrayList<String>();
            try {
                url = new URL(String.format(URL_FORMAT, today, LUNCH));
                Log.d(TAG, url.toString());
                source = new Source(url);
                List<Element> list = source.getAllElements(HTMLElementName.TD);
                String str = null;
                for (Element e : list) {
                    str = e.getTextExtractor().toString();
                    if (str.indexOf("·") > -1) {
                        str = str.substring(1);
                    } else {

                    }

                    if (str != null && !TextUtils.isEmpty(str))
                        menu.add(str);
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (menu.size() > 0) {
                TodayMenu todayMenu = new TodayMenu();
                todayMenu.setDate(DateUtil.getTodayDate());
                
                return todayMenu;
            }
            return null;
        }

        @Override
        protected void onPostExecute(TodayMenu menu) {
            super.onPostExecute(menu);

            if (menu == null) return;
            
            mListener.onFinishParsingData(menu);
        }
    }
}
