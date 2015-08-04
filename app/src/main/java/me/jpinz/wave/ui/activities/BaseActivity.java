package me.jpinz.wave.ui.activities;

import android.content.Context;
import android.content.ServiceConnection;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import me.jpinz.wave.utils.PreferenceUtils;
import me.jpinz.wave.utils.WaveUtils;


public abstract class BaseActivity extends AppCompatActivity {

    private static BaseActivity instance;

    public static BaseActivity getInstance() {
        return instance;
    }

    public static Context getContext(){
        return instance;
        // or return instance.getApplicationContext();
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        instance = this;
        super.onCreate(savedInstanceState);
        setContentView(setContentView());
    }

    /**
     * @return The resource ID to be inflated.
     */
    public abstract int setContentView();




}
