
package com.example.e_commerce.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_commerce.LanguageHandling;
import com.example.e_commerce.MyApplication;
import com.example.e_commerce.R;
import com.example.e_commerce.model.Address;

import java.util.List;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.ViewHolder> {


    private List<Address> myAddresses;
    private Context context;

    public AddressAdapter(Context context, List<Address> myAddresses ) {
        Log.d("DDDD","cccc");

        this.myAddresses = myAddresses;
        this.context = context;
        // this.mClickListener = clickListener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.address_item, parent, false);
        ViewHolder mViewHolder = new ViewHolder(view);

        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d("DDDD","dddd");
        Address mAddress = myAddresses.get(position);
        String address = "";
        String address_num = "";
        String lang = LanguageHandling.getLanguage(MyApplication.getInstance().getApplicationContext());

            if (lang == null || lang.equals("en"))
                address_num = "Address " + (position + 1);
            else if (lang != null && lang.equals("ar"))
                address_num = (position + 1) + " عنوان ";

            holder.addressNum.setText(address_num);

        address += mAddress.getBuilding() + "," + mAddress.getAtreet() + "," + mAddress.getCity();
        holder.address.setText(address);


    }


    @Override
    public int getItemCount() {
        return myAddresses.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView addressNum;
        TextView address;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            addressNum = itemView.findViewById(R.id.adress_number);
            address = itemView.findViewById(R.id.address);
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
