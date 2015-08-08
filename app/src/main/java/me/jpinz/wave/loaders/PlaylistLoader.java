package me.jpinz.wave.loaders;


import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.provider.BaseColumns;
import android.provider.MediaStore;
import android.provider.MediaStore.Audio.PlaylistsColumns;

import java.util.ArrayList;
import java.util.List;

import me.jpinz.wave.R;
import me.jpinz.wave.models.Playlist;
import me.jpinz.wave.utils.Lists;

/**
 * Used to query {@link MediaStore.Audio.Playlists.EXTERNAL_CONTENT_URI} and
 * return the playlists on a user's device.
 *
 * @author Andrew Neal (andrewdneal@gmail.com)
 */
public class PlaylistLoader extends WrappedAsyncTaskLoader<List<Playlist>> {

    /**
     * The result
     */
    private final ArrayList<Playlist> mPlaylistList = Lists.newArrayList();

    /**
     * The {@link Cursor} used to run the query.
     */
    private Cursor mCursor;

    /**
     * Constructor of <code>PlaylistLoader</code>
     *
     * @param context The {@link Context} to use
     */
    public PlaylistLoader(final Context context) {
        super(context);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Playlist> loadInBackground() {
        // Add the deafult playlits to the adapter
        makeDefaultPlaylists();

        // Create the Cursor
        mCursor = makePlaylistCursor(getContext());
        // Gather the data
        if (mCursor != null && mCursor.moveToFirst()) {
            do {
                // Copy the playlist id
                final String id = mCursor.getString(0);

                // Copy the playlist name
                final String name = mCursor.getString(1);

                // Create a new playlist
                final Playlist playlist = new Playlist(id, name);

                // Add everything up
                mPlaylistList.add(playlist);
            } while (mCursor.moveToNext());
        }
        // Close the cursor
        if (mCursor != null) {
            mCursor.close();
            mCursor = null;
        }
        return mPlaylistList;
    }

    /* Adds the favorites and last added playlists */
    private void makeDefaultPlaylists() {
        final Resources resources = getContext().getResources();

        /* Favorites list */
        final Playlist favorites = new Playlist("-1",
                resources.getString(R.string.playlist_favorites));
        mPlaylistList.add(favorites);

        /* Last added list */
        final Playlist lastAdded = new Playlist("-2",
                resources.getString(R.string.playlist_last_added));
        mPlaylistList.add(lastAdded);
    }

    /**
     * Creates the {@link Cursor} used to run the query.
     *
     * @param context The {@link Context} to use.
     * @return The {@link Cursor} used to run the playlist query.
     */
    public static final Cursor makePlaylistCursor(final Context context) {
        return context.getContentResolver().query(MediaStore.Audio.Playlists.EXTERNAL_CONTENT_URI,
                new String[] {
                        /* 0 */
                        BaseColumns._ID,
                        /* 1 */
                        PlaylistsColumns.NAME
                }, null, null, MediaStore.Audio.Playlists.DEFAULT_SORT_ORDER);
    }
}