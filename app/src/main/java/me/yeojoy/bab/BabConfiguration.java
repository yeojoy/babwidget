package me.yeojoy.bab;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import me.yeojoy.bab.parsing.DataManager;
import me.yeojoy.bab.utils.PreferenceUtil;


public class BabConfiguration extends ActionBarActivity implements View.OnClickListener {
    
    private static final String TAG = BabConfiguration.class.getSimpleName();
    private int mAppWidgetId;
    private Context mContext;

    private TextView mTvDesc;

    private Button mBtnFinish;
    
    private CheckBox mCbHasLightBackground;
    
    private boolean isForSetting = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        mContext = this;
        
        mTvDesc = (TextView) findViewById(R.id.tv_desc);

        mBtnFinish = (Button) findViewById(R.id.btn_finish);
        mBtnFinish.setOnClickListener(this);

        mCbHasLightBackground 
                = (CheckBox) findViewById(R.id.cb_has_light_background);
        
        mCbHasLightBackground.setChecked(PreferenceUtil.getInstance(mContext)
                .getBoolean(PreferenceUtil.KEY_HAS_LIGH_BACKGROUND));
        
        mCbHasLightBackground.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView
                    , boolean isChecked) {
                PreferenceUtil.getInstance(mContext)
                        .putBoolean(PreferenceUtil.KEY_HAS_LIGH_BACKGROUND,
                                isChecked);
                BabApplication.hasLightBackground = isChecked;
            }
        });
                

        Intent intent = getIntent();
        isForSetting = intent.getBooleanExtra("for_setting", false);

        // First, get the App Widget ID from the Intent that launched the Activity:
        Bundle extras = intent.getExtras();
        if (extras != null && !isForSetting) {
            mAppWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
            // AppWidgetManager.INVALID_APPWIDGET_ID is 0.
            Log.i(TAG, "onCreate(), App Widget ID is " + mAppWidgetId);
            
            if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
                finish();
            }
        }
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_finish) {
            
            if (isForSetting) {
                // MainActivity에서 설정만을 위해 옴.
                DataManager.getInstance().updateMenu(this, true);
                finish();
                
                return;
            }
            
            // Update the App Widget with a RemoteViews layout by calling
            // updateAppWidget();
            if (mAppWidgetId != AppWidgetManager.INVALID_APPWIDGET_ID) {
                DataManager.getInstance().updateMenu(this, true);
                Log.i(TAG, "onCreate(), App Widget ID is valid.");
            }
            
            // Finally, create the return Intent, set it with the Activity
            // result, and finish the Activity
            Intent resultIntent = new Intent();
            resultIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                    mAppWidgetId);
            setResult(RESULT_OK, resultIntent);
            finish();

        }
    }
}
