package com.example.team98;


import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.team98.info.postinfo;

import java.io.BufferedInputStream;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;

public class postadapter extends RecyclerView.Adapter<postadapter.GalleryViewHolder> {
    private ArrayList<postinfo> mDataset;
    private Activity activity;
    static class GalleryViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        GalleryViewHolder(CardView v) {
            super(v);
            cardView = v;
        }
    }

    public postadapter(ArrayList<postinfo> postinfo) {
        this.mDataset = postinfo;
        this.activity = activity;
    }

    @NonNull
    @Override
    public postadapter.GalleryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView cardView = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, parent, false);
        final GalleryViewHolder galleryViewHolder = new GalleryViewHolder(cardView);



        return galleryViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final GalleryViewHolder holder, int position) {
        Log.e("123","1234");
        CardView cardView = holder.cardView;
        //ImageView imageView = holder.cardView.findViewById(R.id.imageView);
        TextView titleTextView = cardView.findViewById(R.id.titleTextview);
        TextView contentsTextView = cardView.findViewById(R.id.contentstext);
        TextView dateTextView = cardView.findViewById(R.id.createdate);
        TextView idTextView = cardView.findViewById(R.id.idtext);
        ImageView postimageview =cardView.findViewById(R.id.post_imageview);


        titleTextView.setText(mDataset.get(position).getTitle());
        contentsTextView.setText(mDataset.get(position).getContents()+"\n");
        dateTextView.setText(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(mDataset.get(position).getCreatedate()));
        idTextView.setText("작성자 : " +mDataset.get(position).getPublisher());
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);


        if(Patterns.WEB_URL.matcher(mDataset.get(position).getUri()).matches())
        {
            Log.e("11",mDataset.get(position).getUri());
            //ImageView img = new ImageView(activity);
            //img.setLayoutParams(layoutParams);
            Glide.with(postimageview).load(mDataset.get(position).getUri()).override(1000).into(postimageview);

            //postimageview.setImageURI(i);
        }

    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
    public static final Bitmap getBitmap(ContentResolver cr, Uri url)
            throws FileNotFoundException, IOException {
        InputStream input = cr.openInputStream(url);
        Bitmap bitmap = BitmapFactory.decodeStream(input);
        input.close();
        return bitmap;
    }

}