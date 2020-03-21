package com.example.e_commerce.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.e_commerce.model.Order;

import java.text.DateFormatSymbols;
import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {
    private List<Order> myOrders;
    private Context context;
    // private final ClickListener mClickListener;

    public OrderAdapter(Context context , List<Order> myOrders /*,ClickListener clickListener*/) {

        this.myOrders = myOrders;
        this.context = context;
        // this.mClickListener = clickListener;
    }


    @NonNull
    @Override
    public OrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item, parent,false);
        OrderAdapter.ViewHolder mViewHolder = new OrderAdapter.ViewHolder(view);

        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        holder.progressBar.setVisibility(View.VISIBLE);
        Order o = myOrders.get(position);

        holder.shippingNum.setText(o.getShipping());

        String lang = LanguageHandling.getLanguage(MyApplication.getInstance().getApplicationContext());

        if(lang == null || lang.equals("en"))
            holder.Title.setText(o.getProduct().get(0).getProduct().getName_en());
        else if(lang != null && lang.equals("ar"))
            holder.Title.setText(o.getProduct().get(0).getProduct().getName_ar());

        holder.price.setText("USD "+o.getProduct().get(0).getProduct().getPrice());

        int x = Integer.parseInt(o.getUpdated().substring(5,7));
        String month = new DateFormatSymbols().getMonths()[x-1];
        String[] Date = o.getUpdated().split(" ");
        String day = Date[0].substring(8);
        String year = Date[0].substring(0,4);
        month = month.substring(0,3);

        holder.orderDate.setText(day+"-"+month+"-"+year);

        Glide.with(holder.productImage.getContext()).load("https://e-commerce-dev.intcore.net/"+o.getProduct().get(0).getProduct().getDefault_image())
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }
                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        holder.progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(holder.productImage);


        String status = o.getHuman_status();
        if(status.equals("Preparing")){
            holder.firstCircle.setBackgroundResource(R.drawable.circle_green);

            holder.process.setVisibility(View.VISIBLE);
            holder.shipping.setVisibility(View.GONE);
            holder.delivered.setVisibility(View.GONE);
        }
        else if(status.equals("Shipping")){
            holder.firstCircle.setBackgroundResource(R.drawable.circle_green);
            holder.firstLine.setBackgroundColor(MyApplication.getInstance()
                    .getResources().getColor(R.color.BackViewColorGreen));
            holder.secondCircle.setBackgroundResource(R.drawable.circle_green);

            holder.process.setVisibility(View.VISIBLE);
            holder.shipping.setVisibility(View.VISIBLE);
            holder.delivered.setVisibility(View.GONE);
        }
        else if(status.equals("Delivered")){
            holder.firstCircle.setBackgroundResource(R.drawable.circle_green);
            holder.firstLine.setBackgroundColor(MyApplication.getInstance()
                    .getResources().getColor(R.color.BackViewColorGreen));
            holder.secondCircle.setBackgroundResource(R.drawable.circle_green);
            holder.secondLine.setBackgroundColor(MyApplication.getInstance()
                    .getResources().getColor(R.color.BackViewColorGreen));
            holder.thirdCircle.setBackgroundResource(R.drawable.circle_green);

            holder.process.setVisibility(View.VISIBLE);
            holder.shipping.setVisibility(View.VISIBLE);
            holder.delivered.setVisibility(View.VISIBLE);
        }

        else if(status.equals("Canceled")){
            holder.firstCircle.setBackgroundResource(R.drawable.circle_red);

            holder.process.setText("Canceled");
            holder.process.setVisibility(View.VISIBLE);
            holder.shipping.setVisibility(View.GONE);
            holder.delivered.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return myOrders.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder  {

        TextView shippingNum;
        TextView orderDate;
        TextView Title;
        TextView price;
        ImageView productImage;

        ProgressBar progressBar;

        View firstCircle;
        View firstLine;
        View secondCircle;
        View secondLine;
        View thirdCircle;

        TextView process;
        TextView shipping;
        TextView delivered;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            shippingNum = itemView.findViewById(R.id.shipment_number);
            orderDate = itemView.findViewById(R.id.orderDate);
            Title = itemView.findViewById(R.id.product_title);
            price = itemView.findViewById(R.id.product_price);
            productImage = itemView.findViewById(R.id.product_image);

            firstCircle = itemView.findViewById(R.id.firstCircle);
            firstLine = itemView.findViewById(R.id.firstLine);
            secondCircle = itemView.findViewById(R.id.secondCircle);
            secondLine = itemView.findViewById(R.id.secondLine);
            thirdCircle = itemView.findViewById(R.id.thirdCircle);

            process = itemView.findViewById(R.id.process);
            shipping = itemView.findViewById(R.id.shipping);
            delivered = itemView.findViewById(R.id.delivered);

            progressBar = itemView.findViewById(R.id.progress_bar11);
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
