package me.jpinz.wave.loaders;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import me.jpinz.wave.models.Song;
import me.jpinz.wave.provider.FavoritesStore;
import me.jpinz.wave.provider.FavoritesStore.FavoriteColumns;
import me.jpinz.wave.utils.Lists;

/**
 * Used to query the {@link FavoritesStore} for the tracks marked as favorites.
 *
 * @author Andrew Neal (andrewdneal@gmail.com)
 */
public class FavoritesLoader extends WrappedAsyncTaskLoader<List<Song>> {

    /**
     * The result
     */
    private final ArrayList<Song> mSongList = Lists.newArrayList();

    /**
     * The {@link Cursor} used to run the query.
     */
    private Cursor mCursor;

    /**
     * Constructor of <code>FavoritesHandler</code>
     *
     * @param context The {@link Context} to use.
     */
    public FavoritesLoader(final Context context) {
        super(context);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Song> loadInBackground() {
        // Create the Cursor
        mCursor = makeFavoritesCursor(getContext());
        // Gather the data
        if (mCursor != null && mCursor.moveToFirst()) {
            do {

                // Copy the song Id
                final String id = mCursor.getString(mCursor
                        .getColumnIndexOrThrow(FavoriteColumns.ID));

                // Copy the song name
                final String songName = mCursor.getString(mCursor
                        .getColumnIndexOrThrow(FavoriteColumns.SONGNAME));

                // Copy the artist name
                final String artist = mCursor.getString(mCursor
                        .getColumnIndexOrThrow(FavoriteColumns.ARTISTNAME));

                // Copy the album name
                final String album = mCursor.getString(mCursor
                        .getColumnIndexOrThrow(FavoriteColumns.ALBUMNAME));

                // Create a new song
                final Song song = new Song(id, songName, artist, album, null);

                // Add everything up
                mSongList.add(song);
            } while (mCursor.moveToNext());
        }
        // Close the cursor
        if (mCursor != null) {
            mCursor.close();
            mCursor = null;
        }
        return mSongList;
    }

    /**
     * @param context The {@link Context} to use.
     * @return The {@link Cursor} used to run the favorites query.
     */
    public static final Cursor makeFavoritesCursor(final Context context) {
        return FavoritesStore
                .getInstance(context)
                .getReadableDatabase()
                .query(FavoriteColumns.NAME,
                        new String[] {
                                FavoriteColumns.ID + " as _id", FavoriteColumns.ID,
                                FavoriteColumns.SONGNAME, FavoriteColumns.ALBUMNAME,
                                FavoriteColumns.ARTISTNAME, FavoriteColumns.PLAYCOUNT
                        }, null, null, null, null, FavoriteColumns.PLAYCOUNT + " DESC");
    }
}