package com.example.team98;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder> {
    private ArrayList<String> mDataset;
    private Activity activity;

    static class GalleryViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        GalleryViewHolder(CardView v) {
            super(v);
            cardView = v;
        }
    }

    public GalleryAdapter(Activity activity, ArrayList<String> myDataset) {
        mDataset = myDataset;
        this.activity = activity;
    }

    @NonNull
    @Override
    public GalleryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView cardView = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gallery, parent, false);
        final GalleryViewHolder galleryViewHolder = new GalleryViewHolder(cardView);


        /*
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra(INTENT_PATH, mDataset.get(galleryViewHolder.getAdapterPosition()));
                activity.setResult(Activity.RESULT_OK, resultIntent);
                activity.finish();
            }
        });

         */

        return galleryViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final GalleryViewHolder holder, int position) {
        Log.e("123","1234");
        //CardView cardView = holder.cardView;
        ImageView imageView = holder.cardView.findViewById(R.id.imageView);
        //Bitmap bmp = BitmapFactory.decodeFile(mDataset.get(position));
        //imageView.setImageBitmap(bmp);
        Glide.with(activity).load(mDataset.get(position)).centerCrop().override(300).into(imageView);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}