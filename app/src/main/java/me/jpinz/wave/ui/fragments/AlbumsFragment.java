package me.jpinz.wave.ui.fragments;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import me.jpinz.wave.R;
import me.jpinz.wave.adapters.AlbumAdapter;
import me.jpinz.wave.models.Album;

/**
 * Created by Julian on 7/18/2015.
 */
public class AlbumsFragment extends Fragment {

    private RecyclerView albumView;

    private ArrayList<Album> mAlbumItems = new ArrayList<>();
    private RecyclerView.Adapter mAdapter;

    public static AlbumsFragment newInstance() {
        return new AlbumsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_albums, container, false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Log.d("AFRAG", "Intialized!");
        setHasOptionsMenu(true);

        mAlbumItems = new ArrayList<Album>();
        albumView = (RecyclerView) view.findViewById(R.id.album_list);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        albumView.setLayoutManager(layoutManager);
        albumView.setHasFixedSize(true);

        getAlbumList();

        mAdapter = new AlbumAdapter(getActivity().getApplicationContext(), mAlbumItems);
        albumView.setAdapter(mAdapter);

    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        menu.clear();
        inflater.inflate(R.menu.menu_fragment,menu
        );
    }

    public void getAlbumList() {
        //retrieve song info
        ContentResolver musicResolver = getActivity().getContentResolver();
        final String[] cursor_cols = {
                MediaStore.Audio.AlbumColumns.ARTIST, MediaStore.Audio.AlbumColumns.ALBUM,
                MediaStore.Audio.AlbumColumns.ALBUM_ART, MediaStore.Audio.AlbumColumns.ALBUM_KEY,
                MediaStore.Audio.AlbumColumns.FIRST_YEAR, MediaStore.Audio.AlbumColumns.NUMBER_OF_SONGS};
        //final String where = MediaStore.Audio.Media.IS_MUSIC + "=1";

        Uri musicUri = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;
        Cursor musicCursor = musicResolver.query(musicUri,cursor_cols,null,null, MediaStore.Audio.AlbumColumns.ALBUM);
        if (musicCursor != null && musicCursor.moveToFirst()) {
            //get columns
            int trackNumColumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.AlbumColumns.NUMBER_OF_SONGS);
            int idColumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.AlbumColumns.ALBUM_KEY);
            int artistColumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.AlbumColumns.ARTIST);
            int albumColumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.AlbumColumns.ALBUM);
            int albumYear = musicCursor.getColumnIndex
                    (MediaStore.Audio.AlbumColumns.FIRST_YEAR);
            int artColumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.Albums.ALBUM_ART);

            //add songs to list
            do {
                String thisArtPath = musicCursor.getString(artColumn);
                long thisId = musicCursor.getLong(idColumn);
                int thisTrack = musicCursor.getInt(trackNumColumn);
                String thisArtist = musicCursor.getString(artistColumn);
                String thisAlbum = musicCursor.getString(albumColumn);
                String thisYear = musicCursor.getString(albumYear);
                //Log.d("ART", "Album art: " + thisArtPath);
                mAlbumItems.add(new Album(thisId, thisAlbum, thisArtist, thisTrack, thisYear, thisArtPath));
            }

            while (musicCursor.moveToNext());
        }

    }
}
