package me.jpinz.wave.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.util.Log;

import me.jpinz.wave.ui.activities.BaseActivity;

/**
 * Created by Julian on 8/3/2015.
 */
public class PreferenceUtils {

    private static PreferenceUtils sInstance;

    private final SharedPreferences mPreferences;

    /**
     * Constructor for <code>PreferenceUtils</code>
     *
     * @param context The {@link Context} to use.
     */
    public PreferenceUtils(final Context context) {
        mPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    /**
     * @param context The {@link Context} to use.
     * @return A singelton of this class
     */
    public static final PreferenceUtils getInstace(final Context context) {
        if (sInstance == null) {
            sInstance = new PreferenceUtils(context.getApplicationContext());
        }
        return sInstance;
    }

}
