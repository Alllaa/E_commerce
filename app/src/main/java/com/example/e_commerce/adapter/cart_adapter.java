package com.example.e_commerce.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.e_commerce.LanguageHandling;
import com.example.e_commerce.MyApplication;
import com.example.e_commerce.R;
import com.example.e_commerce.model.Cart_items;

import java.util.List;

import static android.view.View.GONE;

public class cart_adapter extends RecyclerView.Adapter<cart_adapter.ViewHolder> {

    private List<Cart_items.Cart_object> myItmes;
    private Context context;
    private int check;
    int none = 2;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onAddOneQuantity(int position);
        void onDecreaseOneQuantity(int position);
        void onDeleteProduct(int position);
    }

    public void setOnImageBut(OnItemClickListener onFavouriteClickListener) {
        mListener = onFavouriteClickListener;
    }

    public cart_adapter(Context context, List<Cart_items.Cart_object> myItmes, int check) {
        this.myItmes = myItmes;
        this.context = context;
        this.check = check;
        // this.mClickListener = clickListener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_items, parent, false);
        ViewHolder mViewHolder = new ViewHolder(view, mListener);

        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        holder.progressBar.setVisibility(View.VISIBLE);

        String lang = LanguageHandling.getLanguage(MyApplication.getInstance().getApplicationContext());
        if (myItmes.get(position).getProduct().getName_en().length() > 22) {
            if(lang == null || lang.equals("en"))
                holder.item_title.setText(myItmes.get(position).getProduct().getName_en().substring(0, 21) + "...");
            else if(lang != null && lang.equals("ar"))
                holder.item_title.setText(myItmes.get(position).getProduct().getName_ar().substring(0, 21) + "...");
        } else{
            if(lang == null || lang.equals("en"))
                holder.item_title.setText(myItmes.get(position).getProduct().getName_en());
            else if(lang != null && lang.equals("ar"))
                holder.item_title.setText(myItmes.get(position).getProduct().getName_ar());
        }

        holder.item_price.setText("USD " + myItmes.get(position).getProduct().getPrice());


        Glide.with(holder.item_image.getContext()).load("https://e-commerce-dev.intcore.net/" + myItmes.get(position).getProduct().getDefault_image())
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        holder.progressBar.setVisibility(GONE);
                        return false;
                    }
                })
                .into(holder.item_image);

           holder.itemQuantity.setText(myItmes.get(position).getQuantity());

        if (check == 1) {
            holder.text.setText(myItmes.get(0).getQuantity());
            if (position == myItmes.size() - 1)
                holder.v.setVisibility(GONE);
        }
    }


    @Override
    public int getItemCount() {
        return myItmes.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView item_image;
        TextView item_title;
        TextView item_price;
        TextView itemQuantity;

        ImageButton increase_quantity;
        ImageButton decrease_quantity;
        ImageButton delete_product;

        ProgressBar progressBar;

        LinearLayout quantity_layout;
        LinearLayout inpayment;
        TextView text;
        View v;

        public ViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);

            item_image = itemView.findViewById(R.id.item_photo);
            item_title = itemView.findViewById(R.id.item_title);
            item_price = itemView.findViewById(R.id.item_price);
            itemQuantity = itemView.findViewById(R.id.quantity_ordered);
            increase_quantity = itemView.findViewById(R.id.increase_items);
            decrease_quantity = itemView.findViewById(R.id.decrease_items);
            delete_product = itemView.findViewById(R.id.delete_item);

            increase_quantity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (listener != null) {
                        int position = getAdapterPosition();
                        listener.onAddOneQuantity(position);
                    }
                }
            });
            decrease_quantity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if ((Integer.parseInt(itemQuantity.getText().toString()) - 1 == 0)) {
                    } else {
                        if (listener != null) {
                            int position = getAdapterPosition();
                            listener.onDecreaseOneQuantity(position);
                        }
                    }

                }
            });
            delete_product.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        listener.onDeleteProduct(position);
                    }
                }
            });


            quantity_layout = itemView.findViewById(R.id.layout_quantity);
            inpayment = itemView.findViewById(R.id.inPayment);
            text = itemView.findViewById(R.id.text_quantity);
            v = itemView.findViewById(R.id.line_view);
            progressBar = itemView.findViewById(R.id.progress_bar111);
            if (check == 1) {
                delete_product.setVisibility(GONE);
                quantity_layout.setVisibility(GONE);
                inpayment.setVisibility(View.VISIBLE);
                v.setVisibility(View.VISIBLE);
            }

        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

}
