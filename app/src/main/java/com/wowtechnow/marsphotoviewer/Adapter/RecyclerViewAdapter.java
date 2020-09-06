package com.wowtechnow.marsphotoviewer.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.wowtechnow.marsphotoviewer.Model.MarsPhotos;
import com.wowtechnow.marsphotoviewer.Model.Photos;
import com.wowtechnow.marsphotoviewer.R;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{
    private List<Photos> Photos = new ArrayList<>();
    private Context mCtx;
    public RecyclerViewAdapter(Context mCtx) {
        this.mCtx = mCtx;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.rv_photos,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(mCtx)
                .load(Photos.get(position).getImg_src())
                .into(holder.imageView);

        String PhotoId = holder.itemView.getResources().getString(R.string.photo_id) + Photos.get(position).getId();
        holder.photoId.setText(PhotoId);
        String MartinSol = holder.itemView.getResources().getString(R.string.martin_sol) + Photos.get(position).getSol();
        holder.martinSol.setText(MartinSol);
        String earthDate = holder.itemView.getResources().getString(R.string.earth_date) + Photos.get(position).getEarth_date();
        holder.earthDate.setText(earthDate);
        String Camera = holder.itemView.getResources().getString(R.string.camera) + Photos.get(position).getCamera().getFull_name() + "(" + Photos.get(position).getCamera().getName()+")";
        holder.camera.setText(Camera);
    }

    @Override
    public int getItemCount() {
        return Photos.size();
    }

    public void setUpMarsList(List<Photos> marsList){
        Photos = marsList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView martinSol , earthDate , camera , photoId;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.ivPhotos);
            earthDate = itemView.findViewById(R.id.tvEarthDate);
            martinSol = itemView.findViewById(R.id.tvPhotoSol);
            camera = itemView.findViewById(R.id.tvCameraName);
            photoId = itemView.findViewById(R.id.tvPhotoId);
        }
    }

}