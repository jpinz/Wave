package me.jpinz.wave.model;

public class Album {

    public long mAlbumId; //Album Id

    public String mAlbumName; //The Album Name

    public String mArtistName;

    public int mSongNumber;

    public String mYear;

    public String mAlbumArt;

    /**
     * Constructer of Album
     * @param albumId The Album's Id
     * @param albumName The name of the Album
     * @param artistName The album Artist
     * @param songNumber The num of songs in the album
     * @param albumYear The year the album was released
     * @param albumArt The URI of the album art
     */

    public Album(final long albumId, final String albumName, final String artistName,
                 final int songNumber, final String albumYear, final String albumArt) {
        super();
        mAlbumId = albumId;
        mAlbumName = albumName;
        mArtistName = artistName;
        mSongNumber = songNumber;
        mYear = albumYear;
        mAlbumArt = albumArt;

    }

    @Override
    public String toString() {
        return mAlbumName;
    }

    public long getID() {
        return mAlbumId;
    }

    public String getTitle() {
        return mAlbumName;
    }

    public String getArtist() {
        if (mArtistName.equals("<unknown>")) {
            return "unknown";
        }
        if(mArtistName.length() > 26) {
            return mArtistName.substring(0,27) + "...";
        }
        return mArtistName;
    }

    public int getNumSongs() {
        return mSongNumber;
    }

    public String getAlbumArt() {
        return mAlbumArt;
    }

}
