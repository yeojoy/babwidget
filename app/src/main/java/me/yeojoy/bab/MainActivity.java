package me.yeojoy.bab;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.List;

import me.yeojoy.bab.model.TodayMenu;
import me.yeojoy.bab.parsing.DataManager;


public class MainActivity extends ActionBarActivity 
        implements DataManager.OnFinishParsingListener {
    
    private static final String TAG = MainActivity.class.getSimpleName();
    private Context mContext;
    
    private TextView mTvResult;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        mTvResult = (TextView) findViewById(R.id.tv_result);
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(mContext, BabConfiguration.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    
    @Override
    public void onFinishParsingData(TodayMenu menus) {
                    mTvResult.setText("");
        if (menus != null) {
            mTvResult.setText(menus.toString());
        } else {
            mTvResult.setText("등록된 메뉴가 없습니다.");
        }
    }
}
