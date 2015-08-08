package me.jpinz.wave.menu;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import me.jpinz.wave.Config;
import me.jpinz.wave.R;
import me.jpinz.wave.utils.MusicUtils;

/**
 * Alert dialog used to delete tracks.
 * <p/>
 * TODO: Remove albums from the recents list upon deletion.
 *
 * @author Andrew Neal (andrewdneal@gmail.com)
 */
public class DeleteDialog extends DialogFragment {

    /**
     * The item(s) to delete
     */
    private long[] mItemList;

    /**
     * The image cache
     */
    //private ImageFetcher mFetcher;

    /**
     * Empty constructor as per the {@link Fragment} documentation
     */
    public DeleteDialog() {
    }

    /**
     * @param title The title of the artist, album, or song to delete
     * @param items The item(s) to delete
     * @param key   The key used to remove items from the cache.
     * @return A new instance of the dialog
     */
    public static DeleteDialog newInstance(final String title, final long[] items, final String key) {
        final DeleteDialog frag = new DeleteDialog();
        final Bundle args = new Bundle();
        args.putString(Config.NAME, title);
        args.putLongArray("items", items);
        args.putString("cachekey", key);
        frag.setArguments(args);
        return frag;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        final String delete = getString(R.string.context_menu_delete);
        final Bundle arguments = getArguments();
        // Get the image cache key
        final String key = arguments.getString("cachekey");
        // Get the track(s) to delete
        mItemList = arguments.getLongArray("items");
        // Get the dialog title
        final String title = arguments.getString(Config.NAME);
        // Initialize the image cache
        //mFetcher = WaveUtils.getImageFetcher(getActivity());
        // Build the dialog
        return new AlertDialog.Builder(getActivity()).setTitle(delete + " " + title)
                .setMessage(R.string.cannot_be_undone)
                .setPositiveButton(delete, new OnClickListener() {

                    @Override
                    public void onClick(final DialogInterface dialog, final int which) {
                        // Remove the items from the image cache
                        //mFetcher.removeFromCache(key);
                        // Delete the selected item(s)
                        MusicUtils.deleteTracks(getActivity(), mItemList);
                        dialog.dismiss();
                    }
                }).setNegativeButton(R.string.cancel, new OnClickListener() {

                    @Override
                    public void onClick(final DialogInterface dialog, final int which) {
                        dialog.dismiss();
                    }
                }).create();
    }
}