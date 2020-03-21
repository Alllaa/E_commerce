package com.example.e_commerce.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_commerce.R;
import com.example.e_commerce.adapter.WishListAdapter;
import com.example.e_commerce.model.Product;
import com.example.e_commerce.presenter.WishListPresenter;
import com.example.e_commerce.view.view_interface.IHomeActivity;
import com.example.e_commerce.view.view_interface.IWishListView;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;


public class WishListFragment extends Fragment implements IWishListView {
    View view;
    IHomeActivity mListener;
    SharedPreferences sharedPreferences;
    String api_token;
    RecyclerView recyclerView;
    WishListAdapter wishListAdapter;
    GridLayoutManager mGridLayoutManager;
    WishListPresenter wishListPresenter;
    List<Product> productList = new ArrayList<>();
    @SuppressLint("NewApi")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_wish_list, container, false);
        ((HomeActivity)getActivity()).setBottomVisible(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        mListener.setProgressBarVisible(true);
        mListener.onChangeToolbarTitle(getActivity().getString(R.string.wish_list_title));

        if(productList.size() == 0){
            view.findViewById(R.id.noitems).setVisibility(View.VISIBLE);
        }

        sharedPreferences = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
        api_token = sharedPreferences.getString("api_token","");
        recyclerView = view.findViewById(R.id.wish_list_recycler);

        mGridLayoutManager = new GridLayoutManager(getContext(), 1);
        recyclerView.setLayoutManager(mGridLayoutManager);

        wishListPresenter = new WishListPresenter(this);
        wishListPresenter.onSendRequestForData(api_token);

        wishListAdapter = new WishListAdapter(getContext(),productList);
        recyclerView.setAdapter(wishListAdapter);

        wishListAdapter.setOnBuyOrDeleteListener(new WishListAdapter.OnItemClickListener() {
            @Override
            public void onShowProduct(int position) {
                String title = productList.get(position).getName_en() ;
                int id = productList.get(position).getId();
                Bundle bundle = new Bundle();
                bundle.putInt("id", id);
                bundle.putString("title", title);
                Navigation.findNavController(view).navigate(R.id.action_wishListFragment_to_showProduct, bundle);
            }

            @Override
            public void onDeleteFromWishList(int position) {
                int id = productList.get(position).getId();
                wishListPresenter.onDeleteFavourite(api_token,id);
                productList.remove(position);
                wishListAdapter.notifyDataSetChanged();
                if(productList.size() == 0){
                    view.findViewById(R.id.noitems).setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onBuyNow(int position) {
                int id = productList.get(position).getId();
                wishListPresenter.onAddToCart(api_token,id);
                Snackbar.make(HomeActivity.coordinatorLayout,"the product added to cart", BaseTransientBottomBar.LENGTH_SHORT).setTextColor(Color.rgb(0, 255, 0)).show();

            }
        });

        return view;
    }

    @Override
    public void onFavouritesRevieved(List<Product> products) {
        productList.clear();
        productList.addAll(products);
        if(productList != null)
        {
            view.findViewById(R.id.noitems).setVisibility(View.GONE);
            mListener.setProgressBarVisible(false);
        }
        wishListAdapter.notifyDataSetChanged();
        if(productList.size() == 0){
            view.findViewById(R.id.noitems).setVisibility(View.VISIBLE);
        }

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
    public void onResume() {
        super.onResume();
        wishListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        wishListPresenter.cancelCall();
    }
}
