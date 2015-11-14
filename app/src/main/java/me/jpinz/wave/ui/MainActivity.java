package me.jpinz.wave.ui;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;


import com.pluscubed.recyclerfastscroll.RecyclerFastScroller;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.jpinz.wave.Config;
import me.jpinz.wave.R;
import me.jpinz.wave.adapters.AlbumAdapter;
import me.jpinz.wave.adapters.FragmentAdapter;
import me.jpinz.wave.loaders.AlbumLoader;
import me.jpinz.wave.model.Album;
import me.jpinz.wave.ui.fragments.AlbumFragment;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }

        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }

        final ActionBar ab = getSupportActionBar();
        if (ab != null) {
            //ab.setHomeAsUpIndicator(R.drawable.menu);
            ab.setDisplayHomeAsUpEnabled(true);
        }
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        if (viewPager != null) {
            setupViewPager(viewPager);
        }

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        if (viewPager != null) {
            tabLayout.setupWithViewPager(viewPager);
        }

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
        switch (item.getItemId()) {

            case R.id.view_as_list:
                finish();
                startActivity(getIntent());
                return true;
            case R.id.view_as_grid:
                return true;
            case R.id.view_as_grid_palette:
                return true;
            case R.id.view_as_grid_card:
                return true;
        /*
            case R.id.col_1:
                return true;
            case R.id.col_2:
                return true;
            case R.id.col_3:
                return true;
            case R.id.col_4:
                return true;
            case R.id.col_5:
                return true;
            case R.id.col_6:
                return true;
         */
            case android.R.id.home:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupViewPager(ViewPager viewPager) {
        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager());
        adapter.addFragment(new AlbumFragment(), "Artists");
        adapter.addFragment(new AlbumFragment(), "Albums");
        adapter.addFragment(new AlbumFragment(), "Songs");
        adapter.addFragment(new AlbumFragment(), "Playlists");

        viewPager.setAdapter(adapter);
    }

}

