package me.jpinz.wave.ui.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {

    private static BaseActivity instance;

    public static BaseActivity getInstance() {
        return instance;
    }

    public static Context getContext() {
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
