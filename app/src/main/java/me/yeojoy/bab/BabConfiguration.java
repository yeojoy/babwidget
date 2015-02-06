package me.yeojoy.bab;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;

import java.util.List;

import me.yeojoy.bab.parsing.DataManager;
import me.yeojoy.bab.widget.WidgetLayoutManager;


public class BabConfiguration extends ActionBarActivity implements View.OnClickListener {
    
    private static final String TAG = BabConfiguration.class.getSimpleName();
    private int mAppWidgetId;
    private Context mContext;

    private TextView mTvDesc;

    private Button mBtnFinish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        mContext = this;
        
        mTvDesc = (TextView) findViewById(R.id.tv_desc);

        mBtnFinish = (Button) findViewById(R.id.btn_finish);
        mBtnFinish.setOnClickListener(this);

        Intent intent = getIntent();

        // First, get the App Widget ID from the Intent that launched the Activity:
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mAppWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
            // AppWidgetManager.INVALID_APPWIDGET_ID is 0.
            Log.i(TAG, "onCreate(), App Widget ID is " + mAppWidgetId);
        }

        if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
        }

        if (mAppWidgetId != AppWidgetManager.INVALID_APPWIDGET_ID) {
            RemoteViews views = new RemoteViews(getPackageName(),
                    R.layout.bab_widget);

            DataManager.getInstance().updateMenu(this, true);
            Log.i(TAG, "onCreate(), App Widget ID is valid.");
        }

        // Finally, create the return Intent, set it with the Activity
        // result, and finish the Activity
        Intent resultIntent = new Intent();
        resultIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                mAppWidgetId);
        resultIntent.putExtra("aaa", "hello world!");
        setResult(RESULT_OK, resultIntent);
        finish();
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_finish) {
            // Update the App Widget with a RemoteViews layout by calling
            // updateAppWidget();
//            if (mAppWidgetId != AppWidgetManager.INVALID_APPWIDGET_ID) {
//                RemoteViews views = new RemoteViews(getPackageName(),
//                        R.layout.bab_widget);
//
//                DataManager.getInstance(this).updateMenu(true);
//                Log.i(TAG, "onCreate(), App Widget ID is valid.");
//            }
//
//            // Finally, create the return Intent, set it with the Activity
//            // result, and finish the Activity
//            Intent resultIntent = new Intent();
//            resultIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
//                    mAppWidgetId);
//            resultIntent.putExtra("aaa", "hello world!");
//            setResult(RESULT_OK, resultIntent);
//            finish();

        }
    }
}
