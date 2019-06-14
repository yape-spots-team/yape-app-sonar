package com.software.yapespots.ui.detailplace;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.software.yapespots.R;

import java.util.ArrayList;

public class PhotosAdapter extends RecyclerView.Adapter<PhotosAdapter.PhotosViewHolder> {
    private Context context;
    private ArrayList<PhotosClass> photos;

    public PhotosAdapter(Context context, ArrayList<PhotosClass> photos) {
        this.context = context;
        this.photos = photos;
    }

    @NonNull
    @Override
    public PhotosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PhotosViewHolder(LayoutInflater.from(context).inflate(R.layout.detailplace_bottom_sheet_photo_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PhotosViewHolder holder, int position) {
        holder.photoImage.setImageBitmap(photos.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

    public class PhotosViewHolder extends RecyclerView.ViewHolder {
        private ImageView photoImage;

        public PhotosViewHolder(View view) {
            super(view);
            photoImage = view.findViewById(R.id.photo_image);
        }
    }
}
