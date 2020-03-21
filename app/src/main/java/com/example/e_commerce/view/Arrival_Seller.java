package com.example.e_commerce.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_commerce.R;
import com.example.e_commerce.adapter.BestSellerAdapter;
import com.example.e_commerce.adapter.NewArrivalAdapter;
import com.example.e_commerce.model.BestSeller;
import com.example.e_commerce.model.NewArrivals;
import com.example.e_commerce.presenter.ArrivalSellerPresenter;
import com.example.e_commerce.view.view_interface.IArrival_ISeller;
import com.example.e_commerce.view.view_interface.IHomeActivity;

import java.util.ArrayList;
import java.util.List;

public class Arrival_Seller extends Fragment implements IArrival_ISeller {
    ArrivalSellerPresenter arrivalSellerPresenter;
    View view;
    int id;
    RecyclerView recyclerView;
    private GridLayoutManager mGridLayoutManager;
    List<NewArrivals> newArrivalsList = new ArrayList<>();
    List<BestSeller> bestSellerList = new ArrayList<>();
    BestSellerAdapter bestSellerAdapter;
    NewArrivalAdapter newArrivalAdapter;
    TextView textView;
    Toolbar toolbar;
    ImageView imageView;
    int ID;
    SharedPreferences sharedPreferences;
    IHomeActivity mListener;
    String api_token;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_arrival__seller, container, false);
        Bundle bundle = getArguments();
        id = bundle.getInt("id");
        ((HomeActivity) getActivity()).setBottomVisible(false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        /*((HomeActivity) getActivity())*/mListener.setProgressBarVisible(true);
        recyclerView = view.findViewById(R.id.recycler_arrival_best);
        sharedPreferences = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
        api_token = sharedPreferences.getString("api_token","");
        toolbar = view.findViewById(R.id.my_toolbar);
        textView = toolbar.findViewById(R.id.tool_title);
        imageView = toolbar.findViewById(R.id.back_arrow);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((HomeActivity) getActivity()).onBackPressed();

            }
        });
        if (id == 1) {
            textView.setText("New Arrival");
        } else {
            textView.setText("Best Seller");
        }
        mGridLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(mGridLayoutManager);

        arrivalSellerPresenter = new ArrivalSellerPresenter(this);
        arrivalSellerPresenter.onSendDoubleData(api_token);
        if (id == 1) {
            newArrivalAdapter = new NewArrivalAdapter(getContext(), newArrivalsList);
            recyclerView.setAdapter(newArrivalAdapter);

            newArrivalAdapter.setOnFavouriteClickListener(new NewArrivalAdapter.OnItemClickListener() {
                @Override
                public void onFavouriteClick(int position) {
                    Bundle bundle = new Bundle();
                    ID = newArrivalsList.get(position).getId();
                    bundle.putInt("id", ID);
                    bundle.putString("title", newArrivalsList.get(position).getName_en());
                    Navigation.findNavController(view).navigate(R.id.action_arrival_Seller_to_showProduct, bundle);

                }

                @Override
                public void onAddToWishListClick(int position) {
                    int id = newArrivalsList.get(position).getId();
                    arrivalSellerPresenter.onAddToFavourite(api_token, id);

                    if(newArrivalsList.get(position).isIs_fav())
                    {
                        newArrivalsList.get(position).setIs_fav(false);
                    }else{
                        newArrivalsList.get(position).setIs_fav(true);
                    }
                }
            });
        } else {
            bestSellerAdapter = new BestSellerAdapter(getContext(), bestSellerList);
            recyclerView.setAdapter(bestSellerAdapter);
            bestSellerAdapter.setOnFavouriteClickListener(new BestSellerAdapter.OnItemClickListener() {
                @Override
                public void onFavouriteClick(int position) {
                    Bundle bundle = new Bundle();
                    ID = bestSellerList.get(position).getId();
                    bundle.putInt("id", ID);
                    bundle.putString("title", bestSellerList.get(position).getName_en());
                    Navigation.findNavController(view).navigate(R.id.action_arrival_Seller_to_showProduct, bundle);

                }

                @Override
                public void addToWishListBest(int position) {
                    int id = bestSellerList.get(position).getId();
                    arrivalSellerPresenter.onAddToFavourite(api_token, id);

                    if(bestSellerList.get(position).isIs_fav())
                    {
                        bestSellerList.get(position).setIs_fav(false);
                    }else{
                        bestSellerList.get(position).setIs_fav(true);
                    }
                }
            });
        }

        return view;
    }

    @Override
    public void onNewArrivalRecieved(List<NewArrivals> newArrivals) {
        newArrivalsList.clear();
        newArrivalsList.addAll(newArrivals);
        if (newArrivalsList != null)
        {
            /*((HomeActivity) getActivity())*/mListener.setProgressBarVisible(false);
        }
        if (id == 1)
            newArrivalAdapter.notifyDataSetChanged();
    }

    @Override
    public void onBestSellerRecieved(List<BestSeller> bestSellers) {
        bestSellerList.clear();
        bestSellerList.addAll(bestSellers);
        if (bestSellerList != null)
        {
            /*((HomeActivity) getActivity())*/mListener.setProgressBarVisible(false);
        }

        if (id == 2)
            bestSellerAdapter.notifyDataSetChanged();
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
}
