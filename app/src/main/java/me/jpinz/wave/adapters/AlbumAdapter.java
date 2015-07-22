package me.jpinz.wave.adapters;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import me.jpinz.wave.R;
import me.jpinz.wave.models.Album;
import me.jpinz.wave.utils.WaveUtils;

public class AlbumAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Album> albums;
    private LayoutInflater albumInf;
    private Album currAlbum;
    private final Context mContext;

    private DisplayImageOptions options;

    public AlbumAdapter(Context c, ArrayList<Album> contents) {
        mContext = c;
        this.albums = contents;
        albumInf = LayoutInflater.from(c);

        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(mContext);
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.denyCacheImageMultipleSizesInMemory();
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCacheSize(175 * 1024 * 1024); // 175 MiB
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        config.writeDebugLogs(); // Remove for release app

        ImageLoader.getInstance().init(config.build());
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return albums.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent,final int pos) {
        //map to song layout
        final RelativeLayout albumView = (RelativeLayout) albumInf.inflate
                (R.layout.item_album, parent, false);
        //get title and artist views
        TextView albumText = (TextView) albumView.findViewById(R.id.album_title);
        TextView artistText = (TextView) albumView.findViewById(R.id.album_artist);
        final ImageView coverAlbum = (ImageView) albumView.findViewById(R.id.album_art);
        //get song using position
        currAlbum = albums.get(pos);
        //albums.get(pos) = currAlbum.setTag(pos);
        //get title and artist strings
        albumText.setText(currAlbum.getTitle());
        artistText.setText(currAlbum.getArtist() + " | " + currAlbum.getNumSongs() + numSongs());

        ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
        int color = generator.getColor(currAlbum.getTitle());
        final TextDrawable drawable = TextDrawable.builder()
                .buildRect(currAlbum.getTitle().substring(0,1).toUpperCase(), color);

        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(drawable)
                .showImageForEmptyUri(drawable)
                .showImageOnFail(drawable)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();

        ImageLoader.getInstance()
                    .displayImage("file://" + currAlbum.getAlbumArt(), coverAlbum, options, new ImageLoadingListener() {
                        @Override
                        public void onLoadingStarted(String imageUri, View view) {

                        }

                        @Override
                        public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                            coverAlbum.setImageDrawable(drawable);

                        }

                        @Override
                        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {

                        }

                        @Override
                        public void onLoadingCancelled(String imageUri, View view) {
                            coverAlbum.setImageDrawable(drawable);

                        }
                    }, new ImageLoadingProgressListener() {
                        @Override
                        public void onProgressUpdate(String imageUri, View view, int current, int total) {

                        }
                    });





        //set position as tag
        albumView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent i = new Intent(mContext, null);
                i.putExtra("ALBUM_NAME", albums.get(pos).getTitle());
                //int position= (Integer)v.getTag();
                i.putExtra("ALBUM_ART", albums.get(pos).getAlbumArt());
                WaveUtils.album = albums.get(pos);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(i);*/
                Toast.makeText(mContext,albums.get(pos).getTitle(),Toast.LENGTH_SHORT).show();

            }
        });

        return new RecyclerView.ViewHolder(albumView) {

        };
    }

    private String numSongs() {
        if (currAlbum.getNumSongs() == 1) {
            return " song";
        }
        return " songs";
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }
}
