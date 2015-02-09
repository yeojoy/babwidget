package me.yeojoy.bab;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;

import java.util.List;
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

            StringBuilder sb = new StringBuilder();
            sb.append(menus.getDate()).append("\n");

            
            Set<String> otherMenus = menus.getOtherMenus();
            if (otherMenus != null) {
                sb.append("오늘의 점심 메뉴").append("\n");
                sb.append("Main : ").append(menus.getMainMenu()).append("\n");
                sb.append(menus.getSubMenuFirst()).append("\n");
                sb.append(menus.getSubMenuSecond()).append("\n");
                sb.append(menus.getSubMenuThird()).append("\n");
                sb.append(menus.getSubMenuFourth()).append("\n");
                
                for (String s : otherMenus) {
                    sb.append(s).append("\n");
                }
            }

            Set<String> dieteticInfo = menus.getDieteticInfo();
            if (dieteticInfo != null) {
                sb.append("\n").append("영양정보").append("\n");

                for (String s : dieteticInfo) {
                    sb.append(s).append("\n");
                }
            }

            mTvResult.setText(sb);
        } else {
            mTvResult.setText("등록된 메뉴가 없습니다.");
        }
    }
}
