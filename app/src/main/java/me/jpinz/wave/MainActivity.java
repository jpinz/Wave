package me.jpinz.wave;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
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
import android.widget.Adapter;
import android.widget.GridView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.mikepenz.aboutlibraries.LibsBuilder;

import me.jpinz.wave.ui.activities.BaseActivity;
import me.jpinz.wave.ui.activities.SettingsActivity;
import me.jpinz.wave.utils.WaveUtils;

public class MainActivity extends BaseActivity {

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
            setTranslucentStatus(true);

        }
        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);

        views = getSharedPreferences(PREFS_NAME, 0);
        albumViewType = views.getInt("albumView", 1);
        albumCols = views.getInt("albumCols", 2);
        restartTab = views.getInt("restartTab", 0);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        if (viewPager != null) {
            setupViewPager(viewPager);
        }

        viewPager.setCurrentItem(restartTab,false);

        if(restartTab != 0) {
            final SharedPreferences.Editor editor = views.edit();
            editor.putInt("restartTab",0);
            editor.apply();
        }

        fab = (FloatingActionButton) findViewById(R.id.fab);

        mDetector = new GestureDetector(new GestureListener());

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

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    private void setupViewPager(ViewPager viewPager) {
        Adapter adapter = new Adapter(getSupportFragmentManager());
        adapter.addFragment(new ArtistFragment(), "Artists");
        if (albumViewType == 1) {
            adapter.addFragment(new AlbumFragment(), "Albums");
        } else {
            adapter.addFragment(new AlbumGridFragment(), "Albums");
        }
        adapter.addFragment(new SongsFragment(), "Songs");
        adapter.addFragment(new PlaylistFragment(), "Playlists");

        viewPager.setAdapter(adapter);
    }

    private void setupDrawerContent(NavigationView navigationView) {

        //TODO: add highlight to the current tab in the navDrawer.


        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        if (menuItem.getItemId() == R.id.nav_changelog) {
                            DialogFragment dialogFragment = new ChangelogDialogFragment();
                            dialogFragment.show(getFragmentManager(), "dialog");
                        } else if (menuItem.getItemId() == R.id.nav_settings) {
                            Intent i = new Intent(getApplicationContext(), SettingsActivity.class);
                            startActivity(i);
                        } else if (menuItem.getItemId() == R.id.nav_about) {

                            //((TextView) findViewById(R.id.about_body)).setText(Html.fromHtml(getResources().getString(R.string.about)));
                            //((TextView) findViewById(R.id.about_links)).setMovementMethod(LinkMovementMethod.getInstance());
                            //((TextView) findViewById(R.id.about_links)).setText(Html.fromHtml(getResources().getString(R.string.about_links)));

                            /*new MaterialDialog.Builder(getApplicationContext())
                                    .title(R.string.action_about)
                                    .customView(R.layout.about_view, true)
                                    .neutralText(R.string.ok)
                                    .show();*/
                            materialDialog.icon(getResources().getDrawable(R.drawable.ic_launcher));

                            materialDialog.callback(new MaterialDialog.ButtonCallback() {
                                @Override
                                public void onPositive(MaterialDialog dialog) {
                                    dialog.cancel();
                                }

                                @Override
                                public void onNeutral(MaterialDialog dialog) {
                                    libs.start(dialog.getContext());
                                }
                            });

                            materialDialog.show();

                        } else if (menuItem.getItemId() == R.id.nav_artists) {
                            viewPager.setCurrentItem(0, true);
                        } else if (menuItem.getItemId() == R.id.nav_albums) {
                            viewPager.setCurrentItem(1, true);
                        } else if (menuItem.getItemId() == R.id.nav_songs) {
                            viewPager.setCurrentItem(2, true);
                        }
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });
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
