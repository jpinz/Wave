package me.jpinz.wave.ui.fragments;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pluscubed.recyclerfastscroll.RecyclerFastScroller;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.jpinz.wave.Config;
import me.jpinz.wave.R;
import me.jpinz.wave.adapters.AlbumAdapter;
import me.jpinz.wave.loaders.AlbumLoader;
import me.jpinz.wave.model.Album;

public class AlbumFragment extends Fragment {

    private RecyclerView mRecyclerview;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<Album> myDataset = new ArrayList<Album>();
    private AlbumLoader loader;
    private RecyclerFastScroller fastScroller;


    //@Bind(R.id.temp_recyclerview) RecyclerView mRecyclerview;
    //@Bind(R.id.recyclerviewScroller) RecyclerFastScroller fastScroller;

    public static AlbumFragment newInstance() {
        return new AlbumFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_album, container, false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Log.d("AFRAG", "Intialized!");
        setHasOptionsMenu(true);
        ButterKnife.bind(view);
        mRecyclerview = (RecyclerView) view.findViewById(R.id.albumRecylerView);
        fastScroller = (RecyclerFastScroller) view.findViewById(R.id.recyclerviewScroller);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerview.setLayoutManager(mLayoutManager);
        loader = new AlbumLoader(getActivity());
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    Config.CAN_READ_EXTERNAL);


        }
        myDataset = loader.loadInBackground();

        mAdapter = new AlbumAdapter(getActivity(), myDataset);
        mRecyclerview.setAdapter(mAdapter);

        fastScroller.setRecyclerView(mRecyclerview);
        fastScroller.setHandlePressedColor(getResources().getColor(R.color.colorAccent));



    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        menu.clear();
        inflater.inflate(R.menu.menu_fragment,menu
        );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case Config.CAN_READ_EXTERNAL: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.


                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

}