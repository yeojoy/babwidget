package me.yeojoy.bab;

import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

import me.yeojoy.bab.model.TodayMenu;
import me.yeojoy.bab.parsing.DataManager;

/**
 * Created by yeojoy on 15. 3. 10..
 */
public class MainActivityTests
        extends ActivityInstrumentationTestCase2<MainActivity> {

    private static final String TAG = MainActivityTests.class.getSimpleName();

//    public MainActivityTests(Class<MainActivity> activityClass) {
//        super(activityClass);
//    }

    public MainActivityTests() {
        super("me.yeojoy.bab", MainActivity.class);
    }

    public void testMenuData() {
        Log.d(TAG, "testMenuData(), start");
        DataManager manager = DataManager.getInstance();

        assertNotNull(manager);

        manager.setOnFinishParsingListener(new DataManager.OnFinishParsingListener() {
            @Override
            public void onFinishParsingData(TodayMenu menus) {
                Log.d(TAG, "onFinishParsingData()");
                assertNotNull(menus);
                Log.d(TAG, menus.toString());
            }
        });

        manager.updateMenu(getActivity(), true);
        Log.d(TAG, "testMenuData(), end");
    }
}
