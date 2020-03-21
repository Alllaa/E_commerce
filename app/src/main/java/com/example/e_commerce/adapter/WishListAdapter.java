package com.example.e_commerce.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.e_commerce.LanguageHandling;
import com.example.e_commerce.MyApplication;
import com.example.e_commerce.R;
import com.example.e_commerce.model.Product;

import java.util.List;

import static com.example.e_commerce.rest.RestClient.BASE_URL;

public class WishListAdapter extends RecyclerView.Adapter<WishListAdapter.MyViewHolder>{

    private LayoutInflater inflater;
    private OnItemClickListener mListener;

    List<Product> productList;

    public interface OnItemClickListener {
        void onShowProduct(int position);
        void onDeleteFromWishList(int position);
        void onBuyNow(int position);
    }

    public void setOnBuyOrDeleteListener(OnItemClickListener onFavouriteClickListener) {
        mListener = onFavouriteClickListener;
    }
    public WishListAdapter(Context ctx, List<Product> newProduct)
    {
        inflater = LayoutInflater.from(ctx);
        productList = newProduct;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.custom_adapter_wish_list, parent, false);
        MyViewHolder holder = new MyViewHolder(view,mListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {

        String lang = LanguageHandling.getLanguage(MyApplication.getInstance().getApplicationContext());
        if (productList.get(position).getName_en().length() > 22){
            if(lang == null || lang.equals("en"))
                holder.name.setText(productList.get(position).getName_en().substring(0, 21) + "...");
            else if(lang != null && lang.equals("ar"))
                holder.name.setText(productList.get(position).getName_ar().substring(0, 21) + "...");
        } else{
            if(lang == null || lang.equals("en"))
                holder.name.setText(productList.get(position).getName_en());
            else if(lang != null && lang.equals("ar"))
                holder.name.setText(productList.get(position).getName_ar());
        }

        holder.price.setText("USD "+productList.get(position).getPrice());
        String url = BASE_URL+productList.get(position).getDefault_image();
        Glide.with(holder.imageView.getContext()).load(url).listener(new RequestListener<String, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                holder.progressBar.setVisibility(View.GONE);
                return false;
            }
        }).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        ImageView imageView;
        ProgressBar progressBar;
        TextView name;
        TextView price;
        ImageButton imageButton;
        Button button;
        public MyViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card_wish_list);
            imageView = itemView.findViewById(R.id.img_wish_list);
            progressBar = itemView.findViewById(R.id.progress_bar_wish_List);
            name = itemView.findViewById(R.id.name_product_wish_list);
            price = itemView.findViewById(R.id.price_wish_list);
            imageButton = itemView.findViewById(R.id.delete_wish_list);
            button = itemView.findViewById(R.id.buy_wish_list);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null)
                    {
                        int position = getAdapterPosition();
                        listener.onShowProduct(position);
                    }
                }
            });
            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null)
                    {
                        int position = getAdapterPosition();
                        listener.onDeleteFromWishList(position);
                        notifyItemChanged(position);
                    }
                }
            });
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null)
                    {
                        int position = getAdapterPosition();
                        listener.onBuyNow(position);

                    }
                }
            });
        }
    }
}
