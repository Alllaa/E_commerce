package com.example.e_commerce.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.e_commerce.LanguageHandling;
import com.example.e_commerce.MyApplication;
import com.example.e_commerce.R;
import com.example.e_commerce.model.BestSeller;

import java.util.List;

import static com.example.e_commerce.rest.RestClient.BASE_URL;

public class BestSellerAdapter extends RecyclerView.Adapter<BestSellerAdapter.MyViewHolder> {
    private LayoutInflater inflater;
    List<BestSeller> list2;


    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onFavouriteClick(int position);
        void addToWishListBest(int position);
    }

    public void setOnFavouriteClickListener(OnItemClickListener onFavouriteClickListener) {
        mListener = onFavouriteClickListener;
    }

    public BestSellerAdapter(Context ctx, List<BestSeller> objects) {
        inflater = LayoutInflater.from(ctx);
        list2 = objects;
    }

    @NonNull
    @Override
    public BestSellerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.custom_adapter, parent, false);
        BestSellerAdapter.MyViewHolder holder = new BestSellerAdapter.MyViewHolder(view,mListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final BestSellerAdapter.MyViewHolder holder, int position) {

        String lang = LanguageHandling.getLanguage(MyApplication.getInstance().getApplicationContext());
        if(lang != null) {
            if (lang.equals("en"))
                holder.name.setText((list2.get(position).getName_en()));
            else if (lang.equals("ar"))
                holder.name.setText((list2.get(position).getName_ar()));
        } else holder.name.setText((list2.get(position).getName_en()));


        holder.price.setText("$" + (list2.get(position).getPrice()));
        String urlPhoto = BASE_URL + list2.get(position).getDefault_image();
        Glide.with(holder.photo.getContext()).load(urlPhoto).listener(new RequestListener<String, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                holder.progressBar.setVisibility(View.GONE);
                return false;
            }
        }).into(holder.photo);
        if (list2.get(position).isIs_fav())
        {
            holder.imageButton.setImageResource(R.drawable.favorite_red);
        }
        else
        {
            holder.imageButton.setImageResource(R.drawable.ic_favorite_border_black_24dp);
        }
    }

    @Override
    public int getItemCount() {
        return list2.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView price;
        ImageView photo;
        ImageView imageButton;
        ProgressBar progressBar;

        public MyViewHolder(@NonNull View itemView,final OnItemClickListener listener) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            price = itemView.findViewById(R.id.price);
            photo = itemView.findViewById(R.id.img);
            imageButton = itemView.findViewById(R.id.img_btn_adapter);
            progressBar = itemView.findViewById(R.id.progress_bar_adapter);

            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        listener.addToWishListBest(position);
                        notifyItemChanged(position);
                    }
                }
            });
            photo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        listener.onFavouriteClick(position);
                    }
                }
            });


        }
    }
}
