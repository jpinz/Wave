package me.jpinz.wave.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.util.ArrayList;

import me.jpinz.wave.R;
import me.jpinz.wave.model.Album;

public class AlbumAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Album> albums;
    private LayoutInflater albumInf;
    private Album currAlbum;
    private final Context mContext;


    public AlbumAdapter(Context c, ArrayList<Album> contents) {
        mContext = c;
        this.albums = contents;
        albumInf = LayoutInflater.from(c);

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
        ImageView albumArt = (ImageView) albumView.findViewById(R.id.album_art);
        //get song using position
        currAlbum = albums.get(pos);
        //albums.get(pos) = currAlbum.setTag(pos);
        //get title and artist strings
        albumText.setText(currAlbum.getTitle());
        artistText.setText(currAlbum.getArtist() + " | " + numSongs(currAlbum));

        ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
        int color = generator.getColor(currAlbum.getTitle());
        final TextDrawable drawable = TextDrawable.builder()
                .buildRect(currAlbum.getTitle().substring(0,1).toUpperCase(), color);

        Glide.with(mContext)
                .load("file://" + currAlbum.getAlbumArt())
                .placeholder(drawable)
                .crossFade().into(albumArt);

        return new RecyclerView.ViewHolder(albumView) {

        };
    }

    private String numSongs(Album album) {
        if (album.getNumSongs() == 1) {
            return album.getNumSongs() + " song";
        }
        return album.getNumSongs() + " songs";
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }


}