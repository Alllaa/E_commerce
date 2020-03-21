package com.example.e_commerce.view;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.e_commerce.R;
import com.example.e_commerce.adapter.TopBrandAdapter;
import com.example.e_commerce.adapter.TopCategoriesAdapter;
import com.example.e_commerce.view.view_interface.IHomeActivity;
import com.example.e_commerce.model.BestSeller;
import com.example.e_commerce.model.TopCategories;
import com.example.e_commerce.presenter.DealsPresenter;
import com.example.e_commerce.view.view_interface.IDeals;

import java.util.ArrayList;
import java.util.List;


public class DealsFragment extends Fragment implements IDeals {
    View view;
    ImageView imageView;
    String url;
    List<TopCategories> topCategoriesList = new ArrayList<>();
    RecyclerView recyclerView;
    RecyclerView recyclerView2;

    TopCategoriesAdapter topCategoriesAdapter;
    ProgressBar progressBar;
   List <TopCategories> topBrand = new ArrayList<>();
    TopBrandAdapter topBrandAdapter;

   IHomeActivity mListener;


    DealsPresenter dealsPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_deals, container, false);
        ((HomeActivity)getActivity()).setBottomVisible(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
       mListener.setProgressBarVisible(true);

        imageView = view.findViewById(R.id.ads);
        progressBar = view.findViewById(R.id.progress_bar_deal);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        final LinearLayoutManager layoutManager2 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);

        recyclerView = view.findViewById(R.id.recycler_deals);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView2 = view.findViewById(R.id.recycler_deals2);
        recyclerView2.setLayoutManager(layoutManager2);

        dealsPresenter = new DealsPresenter(this);
        dealsPresenter.onSendData();

        topCategoriesAdapter = new TopCategoriesAdapter(getContext(), topCategoriesList);
        recyclerView.setAdapter(topCategoriesAdapter);

        topBrandAdapter = new TopBrandAdapter(getContext(),topBrand);
        recyclerView2.setAdapter(topBrandAdapter);


        return view;
    }

    @Override
    public void onDataRecieved(List<TopCategories> ads, List<TopCategories> topCategories, List<TopCategories> topBrandList) {
       if (ads != null && topCategories != null&& topBrandList != null)
       {
           /*((HomeActivity) getActivity())*/mListener.setProgressBarVisible(false);
       }
        url = "https://e-commerce-dev.intcore.net/" + ads.get(0).getImage();
        topCategoriesList.addAll(topCategories);
        topBrand.addAll(topBrandList);
        Glide.with(getActivity()).load(url).listener(new RequestListener<String, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                progressBar.setVisibility(View.GONE);
                return false;
            }
        }).into(imageView);
        topCategoriesAdapter.notifyDataSetChanged();
        topBrandAdapter.notifyDataSetChanged();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof IHomeActivity) {
            mListener = (IHomeActivity) context;
            mListener.onChangeToolbarTitle(getActivity().getString(R.string.deal_title));
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
    public void onDestroyView() {
        super.onDestroyView();
        dealsPresenter.cancelCall();
    }
}
