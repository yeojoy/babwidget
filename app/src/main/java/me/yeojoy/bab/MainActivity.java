package me.yeojoy.bab;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.StyleSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.Set;

import me.yeojoy.bab.model.TodayMenu;
import me.yeojoy.bab.parsing.DataManager;


public class MainActivity extends ActionBarActivity 
        implements DataManager.OnFinishParsingListener {
    
    private static final String TAG = MainActivity.class.getSimpleName();
    
    private TextView mTvResult;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    
        mTvResult = (TextView) findViewById(R.id.tv_result);
        
        DataManager manager = DataManager.getInstance();
        manager.setOnFinishParsingListener(this);
        manager.updateMenu(this, true);
        
    }
    
    @Override
    public void onFinishParsingData(TodayMenu menus) {
        mTvResult.setText("");
        if (menus != null) {

            float d = getResources().getDisplayMetrics().density;
            SpannableStringBuilder ssb = new SpannableStringBuilder();
            SpannableString ss = new SpannableString(menus.getDate());
            
            int dateSize = (int) (20 * d);
            int titleSize = (int) (26 * d);
            int menuSize = (int) (16 * d);

            ss.setSpan(new AbsoluteSizeSpan(dateSize), 0, ss.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            ssb.append(ss).append("\n\n");
            
            
            Set<String> otherMenus = menus.getOtherMenus();
            
            if (otherMenus != null) {
                ss = new SpannableString("오늘의 점심 메뉴");
                ss.setSpan(new AbsoluteSizeSpan(titleSize), 0, ss.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                ss.setSpan(new StyleSpan(Typeface.BOLD), 0, ss.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                ssb.append(ss).append("\n");
                
                ss = new SpannableString(menus.getMainMenu());
                ss.setSpan(new AbsoluteSizeSpan(dateSize), 0, ss.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                ssb.append(ss).append("\n");

                ss = new SpannableString(menus.getSubMenuFirst());
                ss.setSpan(new AbsoluteSizeSpan(menuSize), 0, ss.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                ssb.append(ss).append("\n");
                ssb.append(menus.getSubMenuSecond()).append("\n");
                ssb.append(menus.getSubMenuThird()).append("\n");
                ssb.append(menus.getSubMenuFourth()).append("\n");
                
                for (String s : otherMenus) {
                    ssb.append(s).append("\n");
                }
            }

            Set<String> dieteticInfo = menus.getDieteticInfo();
            if (dieteticInfo != null) {
                ss = new SpannableString("영양정보");
                ss.setSpan(new AbsoluteSizeSpan(titleSize), 0, ss.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                ss.setSpan(new StyleSpan(Typeface.BOLD), 0, ss.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                ssb.append("\n").append(ss);

                ss = new SpannableString("");
                ss.setSpan(new AbsoluteSizeSpan(menuSize), 0, ss.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                ssb.append(ss).append("\n");
                
                for (String s : dieteticInfo) {
                    ssb.append(s).append("\n");
                }
            }

            mTvResult.setText(ssb);
        } else {
            mTvResult.setText("등록된 메뉴가 없습니다.");
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        
        DataManager manager = DataManager.getInstance();
        manager.setOnFinishParsingListener(this);
        manager.updateMenu(this, true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {
            Intent intent = new Intent(this, BabConfiguration.class);
            intent.putExtra("for_setting", true);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
