package srb.homemanagement.utils;

import android.content.Context;
import android.content.SharedPreferences;

import srb.homemanagement.BaseApplication;

public class PreferenceAppHelper {


    private static final String PREFS_NAME = "Home_app";
    private static final String SUGU_BALANCE = "sugu_balance";

    private static SharedPreferences mSharedPreferences = null;

    private static SharedPreferences getSharedPreference() {
        if (mSharedPreferences == null) {
            mSharedPreferences = BaseApplication.getAppContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        }
        return mSharedPreferences;
    }

    private static void setStringInPrefs(String key, String value) {
        SharedPreferences.Editor editor = getSharedPreference().edit();
        editor.putString(key, value);
        editor.apply();
    }
}
