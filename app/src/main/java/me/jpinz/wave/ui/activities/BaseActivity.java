package me.jpinz.wave.ui.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import me.jpinz.wave.utils.WaveUtils;


public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(WaveUtils.WV, "BaseActivity");
        super.onCreate(savedInstanceState);
    }


}
