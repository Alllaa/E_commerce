package com.example.e_commerce.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_commerce.MyApplication;
import com.example.e_commerce.R;
import com.example.e_commerce.adapter.OrderAdapter;
import com.example.e_commerce.adapter.cart_adapter;
import com.example.e_commerce.model.CartInteractor;
import com.example.e_commerce.model.Cart_items;
import com.example.e_commerce.model.Order;
import com.example.e_commerce.model.OrderInteractor;
import com.example.e_commerce.presenter.CartPresenter;
import com.example.e_commerce.presenter.MyOrderPresenter;
import com.example.e_commerce.presenter.interface_presnter.ICartPresenter;
import com.example.e_commerce.presenter.interface_presnter.IMyOrderPresenter;
import com.example.e_commerce.view.view_interface.ICartView;
import com.example.e_commerce.view.view_interface.IHomeActivity;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;


public class CartFragment extends Fragment implements ICartView {
    View view;
    IHomeActivity mListener;

    private List<Cart_items.Cart_object> list = new ArrayList<>();
    private ICartPresenter cartPresenter;
    private SharedPreferences sharedPreferences;

    private RecyclerView cartRecycler;
    private cart_adapter cartAdapter = new cart_adapter(getActivity(),list,0);

    private TextView totlaPrice;
    private TextView shipping;
    private TextView Total;
    private TextView itemsNum;
    private TextView noItemToShow;
    String id;

    FrameLayout frameLayout;
    Bundle bundle ;
    Cart_items cart;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_cart, container, false);

        ((HomeActivity) getActivity()).setBottomVisible(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        mListener.onChangeToolbarTitle(getActivity().getString(R.string.cart_title_text));
        mListener.setProgressBarVisible(true);


        bundle = new Bundle();

        totlaPrice = view.findViewById(R.id.items_price);
        shipping = view.findViewById(R.id.shipping_price);
        Total = view.findViewById(R.id.total_price);
        itemsNum = view.findViewById(R.id.items_quantity);
        frameLayout = view.findViewById(R.id.frame_layout);
        noItemToShow = view.findViewById(R.id.noproduct);

        sharedPreferences = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
        cartPresenter = new CartPresenter(this,new CartInteractor());
        cartPresenter.getCart(sharedPreferences.getString("api_token",""));

        cartRecycler = view.findViewById(R.id.cart_recycler);
        cartRecycler.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));


        if (list.size() == 0)
        {
            frameLayout.setVisibility(View.GONE);
        }
        view.findViewById(R.id.checkout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (list.size() == 0)
                {
                    //Toast.makeText(MyApplication.getInstance().getApplicationContext(), "Your cart is empty!🙂", Toast.LENGTH_SHORT).show();
                    Snackbar.make(getActivity().findViewById(R.id.snack_bar),"Your cart is empty!🙂", BaseTransientBottomBar.LENGTH_SHORT).show();
                }
                else {

                    cart.getCarts().clear();
                    cart.getCarts().addAll(list);
                    bundle.putParcelable("cart",cart);
                    Navigation.findNavController(view).navigate(R.id.action_cartFragment_to_checkOutFragment,bundle);
                }
            }
        });
        cartAdapter.setOnImageBut(new cart_adapter.OnItemClickListener() {
            @Override
            public void onAddOneQuantity(int position) {
                id = list.get(position).getId();
                cartPresenter.updateCart(sharedPreferences.getString("api_token",""), Integer.parseInt(id),1);
                list.get(position).setQuantity((Integer.parseInt(list.get(position).getQuantity())+1)+"");

                String oldTotal = totlaPrice.getText().toString().substring(4);
                int price = list.get(position).getProduct().getPrice();
                int old_total = Integer.parseInt(oldTotal);
                Log.d("ddd",oldTotal);
                int new_total = old_total + price;
                totlaPrice.setText("USD "+new_total);
                Total.setText("USD "+(new_total+Integer.parseInt(shipping.getText().toString().substring(4))));

                cart.setTotal_items(list.size());
                cart.setTotal_price(new_total);
                cartAdapter.notifyItemChanged(position);
            }

            @Override
            public void onDecreaseOneQuantity(int position) {
                id = list.get(position).getId();
                cartPresenter.updateCart(sharedPreferences.getString("api_token",""), Integer.parseInt(id),0);
                list.get(position).setQuantity((Integer.parseInt(list.get(position).getQuantity())-1)+"");

                String oldTotal = totlaPrice.getText().toString().substring(4);
                int price = list.get(position).getProduct().getPrice();
                int old_total = Integer.parseInt(oldTotal);
                Log.d("ddd",oldTotal);
                int new_total = old_total - price;
                totlaPrice.setText("USD "+new_total);
                Total.setText("USD "+(new_total+Integer.parseInt(shipping.getText().toString().substring(4))));
                cart.setTotal_items(list.size());
                cart.setTotal_price(new_total);
                cartAdapter.notifyItemChanged(position);


            }

            @Override
            public void onDeleteProduct(int position) {
                //Toast.makeText(MyApplication.getInstance().getApplicationContext(), "Has Deleted", Toast.LENGTH_SHORT).show();
                Snackbar.make(getActivity().findViewById(R.id.snack_bar),"Item deleted successfully", BaseTransientBottomBar.LENGTH_SHORT)
                        .setTextColor(getActivity().getResources().getColor(R.color.BackViewColorGreen)).show();

                id = list.get(position).getId();
                cartPresenter.deleteproduct(sharedPreferences.getString("api_token",""),Integer.parseInt(id));
                if (list.size() == 1)
                {
                    shipping.setText("USD 0");
                    Total.setText("USD 0");
                    itemsNum.setText(0+" Item");
                    totlaPrice.setText("USD 0");
                    noItemToShow.setVisibility(View.VISIBLE);
                    frameLayout.setVisibility(View.GONE);
                }

                else {
                    String oldTotal = totlaPrice.getText().toString().substring(4);
                    int price = list.get(position).getProduct().getPrice();
                    int quantity = Integer.parseInt(list.get(position).getQuantity());
                    int total = price * quantity;
                    int old_total = Integer.parseInt(oldTotal);
                    int new_total = old_total - total;
                    totlaPrice.setText("USD " + new_total);
                    Total.setText("USD " + (new_total + Integer.parseInt(shipping.getText().toString().substring(4))));
                    cart.setTotal_items(list.size());
                    cart.setTotal_price(new_total);
                }
                list.remove(position);

                cartAdapter.notifyItemChanged(position);
            }
        });



        return view;
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof IHomeActivity) {
            mListener = (IHomeActivity) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void displayCarts(Cart_items cart) {
        this.cart = cart;
        list.clear();
        list.addAll(cart.getCarts());
        mListener.setProgressBarVisible(false);
        cartRecycler.setAdapter(cartAdapter);
        totlaPrice.setText("USD "+cart.getTotal_price());
        shipping.setText("USD "+cart.getShiping());
        Total.setText("USD "+(cart.getTotal_price()+cart.getShiping()));

        if(cart.getTotal_items() == 1) itemsNum.setText(cart.getTotal_items()+" item");
        else if(cart.getTotal_items() > 1) itemsNum.setText(cart.getTotal_items()+" items");

        if (list.size() == 0)
        {
            totlaPrice.setText("USD 0");
            shipping.setText("USD 0");
            Total.setText("USD 0");
            noItemToShow.setVisibility(View.VISIBLE);
            frameLayout.setVisibility(View.GONE);

        }
        else
        {
            frameLayout.setVisibility(View.VISIBLE);
        }


        bundle.putParcelable("cart",cart);
    }
}
