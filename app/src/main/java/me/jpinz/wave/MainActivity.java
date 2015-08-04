package me.jpinz.wave;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.GridView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.mikepenz.aboutlibraries.LibsBuilder;

import me.jpinz.wave.ui.activities.BaseActivity;
import me.jpinz.wave.ui.activities.SettingsActivity;
import me.jpinz.wave.utils.WaveUtils;

public class MainActivity extends AppCompatActivity {

    private static final String DEBUG_TAG = "Gestures";

    public static final String PREFS_NAME = "WVPrefs";
    static final String CURRENT_TAB = "currentTab";

    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;

    final static AccelerateInterpolator ACCELERATE = new AccelerateInterpolator();
    final static AccelerateDecelerateInterpolator ACCELERATE_DECELERATE = new AccelerateDecelerateInterpolator();
    final static DecelerateInterpolator DECELERATE = new DecelerateInterpolator();

    private GestureDetector mDetector;

    private int albumViewType = 0;
    private int albumCols = GridView.AUTO_FIT;
    private int restartTab = 0;
    private SharedPreferences views;

    private Menu mOptionsMenu;

    private RecyclerView gridView;
    private FloatingActionButton fab;


    private MaterialDialog.Builder materialDialog;
    private LibsBuilder libs;

    private DrawerLayout mDrawerLayout;

    private ViewPager viewPager;

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(WaveUtils.WV, "MainActivity");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(getResources().getColor(R.color.primary_dark));
        }

        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {

        }
        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);

        views = getSharedPreferences(PREFS_NAME, 0);
        albumViewType = views.getInt("albumView", 1);
        albumCols = views.getInt("albumCols", 2);
        restartTab = views.getInt("restartTab", 0);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);



        viewPager.setCurrentItem(restartTab,false);

        if(restartTab != 0) {
            final SharedPreferences.Editor editor = views.edit();
            editor.putInt("restartTab",0);
            editor.apply();
        }

        fab = (FloatingActionButton) findViewById(R.id.fab);


        fab.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(final View view, final MotionEvent event) {
                mDetector.onTouchEvent(event);
                return true;
            }
        });

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

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
            Intent s = new Intent(this, SettingsActivity.class);
            startActivity(s);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
