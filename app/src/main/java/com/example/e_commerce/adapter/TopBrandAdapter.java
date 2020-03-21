package com.example.e_commerce.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.e_commerce.R;
import com.example.e_commerce.model.TopCategories;

import java.util.List;

import static android.view.View.GONE;
import static com.example.e_commerce.rest.RestClient.BASE_URL;

public class TopBrandAdapter extends RecyclerView.Adapter<TopBrandAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    List<TopCategories> list2;
    public TopBrandAdapter(Context ctx, List<TopCategories> objects) {
        inflater = LayoutInflater.from(ctx);
        list2 = objects;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.top_brand_adapter, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        String urlPhoto = BASE_URL + list2.get(position).getImage();
        Glide.with(holder.imageView.getContext()).load(urlPhoto).listener(new RequestListener<String, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                holder.progressBar.setVisibility(GONE);
                return false;
            }
        }).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return list2.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        ProgressBar progressBar;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img_deal);
            progressBar = itemView.findViewById(R.id.progress_bar_deal);
        }
    }
}
