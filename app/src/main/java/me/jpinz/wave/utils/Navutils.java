package me.jpinz.wave.utils;

import android.app.Activity;
import android.os.Bundle;
import android.app.Activity;
import android.app.SearchManager;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.media.audiofx.AudioEffect;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import me.jpinz.wave.Config;
import me.jpinz.wave.R;
import me.jpinz.wave.ui.activities.HomeActivity;
import me.jpinz.wave.ui.activities.ProfileActivity;
import me.jpinz.wave.ui.activities.SettingsActivity;

/**
 * Created by Julian on 8/4/2015.
 */
public class NavUtils {

    /**
     * Opens the profile of an artist.
     *
     * @param context The {@link FragmentActivity} to use.
     * @param artistName The name of the artist
     */
    public static void openArtistProfile(final Activity context,
                                         final String artistName) {

        // Create a new bundle to transfer the artist info
        final Bundle bundle = new Bundle();
        bundle.putLong(Config.ID, MusicUtils.getIdForArtist(context, artistName));
        bundle.putString(Config.MIME_TYPE, MediaStore.Audio.Artists.CONTENT_TYPE);
        bundle.putString(Config.ARTIST_NAME, artistName);

        // Create the intent to launch the profile activity
        final Intent intent = new Intent(context, ProfileActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    /**
     * Opens the profile of an album.
     *
     * @param context The {@link FragmentActivity} to use.
     * @param albumName The name of the album
     * @param artistName The name of the album artist
     */
    public static void openAlbumProfile(final FragmentActivity context,
                                        final String albumName, final String artistName) {

        // Create a new bundle to transfer the album info
        final Bundle bundle = new Bundle();
        bundle.putString(Config.ALBUM_YEAR, MusicUtils.getReleaseDateForAlbum(context, albumName));
        bundle.putString(Config.ARTIST_NAME, artistName);
        bundle.putString(Config.MIME_TYPE, MediaStore.Audio.Albums.CONTENT_TYPE);
        bundle.putLong(Config.ID, MusicUtils.getIdForAlbum(context, albumName));
        bundle.putString(Config.NAME, albumName);

        // Create the intent to launch the profile activity
        final Intent intent = new Intent(context, ProfileActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    /**
     * Opens the sound effects panel or DSP manager in CM
     *
     * @param context The {@link FragmentActivity} to use.
     */
    public static void openEffectsPanel(final FragmentActivity context) {
        try {
            final Intent effects = new Intent(AudioEffect.ACTION_DISPLAY_AUDIO_EFFECT_CONTROL_PANEL);
            effects.putExtra(AudioEffect.EXTRA_AUDIO_SESSION, MusicUtils.getCurrentAudioId());
            context.startActivityForResult(effects, 0);
            // Make sure the notification starts
            MusicUtils.startBackgroundService(context);
        } catch (final ActivityNotFoundException e) {
            Toast.makeText(context, context.getString(R.string.no_effects_for_you),
                    Toast.LENGTH_SHORT);
        }
    }

    /**
     * Opens to {@link SettingsActivity}.
     *
     * @param activity The {@link FragmentActivity} to use.
     */
    public static void openSettings(final FragmentActivity activity) {
        final Intent intent = new Intent(activity, SettingsActivity.class);
        activity.startActivity(intent);
    }

    /**
     * Opens to {@link HomeActivity}.
     *
     * @param activity The {@link Activity} to use.
     */
    public static void goHome(final Activity activity) {
        final Intent intent = new Intent(activity, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
        activity.finish();
    }
}
