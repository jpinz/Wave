package me.jpinz.wave.loaders;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

import me.jpinz.wave.model.Album;
import me.jpinz.wave.utils.Lists;

/**
 * Created by Julian on 11/13/2015.
 */
public class AlbumLoader extends WrappedAsyncTaskLoader<List<Album>> {

    /**
     * The result
     */
    private final ArrayList<Album> mAlbumList = Lists.newArrayList();

    /**
     * The {@link Cursor} used to run the query.
     */
    private Cursor mCursor;

    /**
     * Constructor of <code>PlaylistLoader</code>
     *
     * @param context The {@link Context} to use
     */
    public AlbumLoader(final Context context) {
        super(context);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArrayList<Album> loadInBackground() {
        // Add the deafult playlits to the adapter

        // Create the Cursor
        mCursor = makeAlbumCursor(getContext());
        // Gather the data
        if (mCursor != null && mCursor.moveToFirst()) {
                //get columns
                int trackNumColumn = mCursor.getColumnIndex
                        (MediaStore.Audio.AlbumColumns.NUMBER_OF_SONGS);
                int idColumn = mCursor.getColumnIndex
                        (MediaStore.Audio.AlbumColumns.ALBUM_KEY);
                int artistColumn = mCursor.getColumnIndex
                        (MediaStore.Audio.AlbumColumns.ARTIST);
                int albumColumn = mCursor.getColumnIndex
                        (MediaStore.Audio.AlbumColumns.ALBUM);
                int albumYear = mCursor.getColumnIndex
                        (MediaStore.Audio.AlbumColumns.FIRST_YEAR);
                int artColumn = mCursor.getColumnIndex
                        (MediaStore.Audio.Albums.ALBUM_ART);

                //add songs to list
                do {
                    String thisArtPath = mCursor.getString(artColumn);
                    long thisId = mCursor.getLong(idColumn);
                    int thisTrack = mCursor.getInt(trackNumColumn);
                    String thisArtist = mCursor.getString(artistColumn);
                    String thisAlbum = mCursor.getString(albumColumn);
                    String thisYear = mCursor.getString(albumYear);
                    mAlbumList.add(new Album(thisId, thisAlbum, thisArtist, thisTrack, thisYear, thisArtPath));
                } while (mCursor.moveToNext());
        }
        // Close the cursor
        if (mCursor != null) {
            mCursor.close();
            mCursor = null;
        }
        return mAlbumList;
    }

    /**
     * Creates the {@link Cursor} used to run the query.
     *
     * @param context The {@link Context} to use.
     * @return The {@link Cursor} used to run the playlist query.
     */
    public static final Cursor makeAlbumCursor(final Context context) {

        return context.getContentResolver().query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
                new String[] {
                        MediaStore.Audio.AlbumColumns.ARTIST, MediaStore.Audio.AlbumColumns.ALBUM,
                        MediaStore.Audio.AlbumColumns.ALBUM_ART, MediaStore.Audio.AlbumColumns.ALBUM_KEY,
                        MediaStore.Audio.AlbumColumns.FIRST_YEAR, MediaStore.Audio.AlbumColumns.NUMBER_OF_SONGS
                }, null, null, MediaStore.Audio.AlbumColumns.ALBUM);
    }

}
