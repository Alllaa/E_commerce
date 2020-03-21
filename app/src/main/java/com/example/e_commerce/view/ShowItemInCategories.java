package com.example.e_commerce.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.e_commerce.R;
import com.example.e_commerce.adapter.NewArrivalAdapter;
import com.example.e_commerce.model.NewArrivals;
import com.example.e_commerce.presenter.CategoriesPresenter;
import com.example.e_commerce.view.view_interface.ICategories;
import com.example.e_commerce.view.view_interface.IHomeActivity;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;

public class ShowItemInCategories extends Fragment implements ICategories {
    View view;
    int id;
    String title;
    TextView textView;
    RecyclerView recyclerView;
    private GridLayoutManager mGridLayoutManager;
    CategoriesPresenter categoriesPresenter;
    List<NewArrivals> products = new ArrayList<>();
    NewArrivalAdapter productAdapter;
    String url;
    ImageView imageView;
    ImageView imageView2;
    IHomeActivity mListener;
    SharedPreferences sharedPreferences;
    ProgressBar progressBar;
    String api_token;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_show_item_in_categories, container, false);
        ((HomeActivity) getActivity()).setBottomVisible(false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        /*((HomeActivity) getActivity())*/mListener.setProgressBarVisible(true);

        imageView = view.findViewById(R.id.img_category);
        progressBar = view.findViewById(R.id.progress_bar_categ);
        categoriesPresenter = new CategoriesPresenter(this);

        Bundle bundle = getArguments();
        if(bundle.containsKey("name")) {
            progressBar.setVisibility(View.VISIBLE);
            id = bundle.getInt("id");
            title = bundle.getString("name");
            url = "https://e-commerce-dev.intcore.net/" + bundle.getString("url");
            Glide.with(getActivity()).load(url).listener(new RequestListener<String, GlideDrawable>() {
                @Override
                public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                    return false;
                }

                @Override
                public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                    progressBar.setVisibility(GONE);
                    return false;
                }
            }).into(imageView);
            categoriesPresenter.onSendData(id,api_token);
        }
        else if(bundle.containsKey("key_word")){
            String key = bundle.getString("key_word");
            title = "result: "+key;
            categoriesPresenter.getProductForSearch(api_token,key);
            imageView.setVisibility(GONE);
            view.findViewById(R.id.linear_ca).setVisibility(GONE);
        }

        Toolbar toolbar = view.findViewById(R.id.my_toolbar);
        textView = toolbar.findViewById(R.id.tool_title);
        textView.setText(title);
        imageView2 = toolbar.findViewById(R.id.back_arrow);

        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((HomeActivity) getActivity()).onBackPressed();

            }
        });

        sharedPreferences = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
        api_token = sharedPreferences.getString("api_token","");

        recyclerView = view.findViewById(R.id.item_categories_recycler);
        mGridLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(mGridLayoutManager);

        productAdapter = new NewArrivalAdapter(getContext(), products);
        recyclerView.setAdapter(productAdapter);

        productAdapter.setOnFavouriteClickListener(new NewArrivalAdapter.OnItemClickListener() {
            @Override
            public void onFavouriteClick(int position) {
                int id = products.get(position).getId();
                Bundle bundle = new Bundle();
                bundle.putInt("id", id);
                bundle.putString("title", products.get(position).getName_en());
                Navigation.findNavController(view).navigate(R.id.action_showItemInCategories_to_showProduct, bundle);

            }

            @Override
            public void onAddToWishListClick(int position) {
                int id = products.get(position).getId();
                categoriesPresenter.addToDavourite(api_token,id);
                if(products.get(position).isIs_fav())
                {
                    products.get(position).setIs_fav(false);
                }else{
                    products.get(position).setIs_fav(true);
                }
            }
        });


        return view;
    }


    @Override
    public void onDataRecieved(List<NewArrivals> newArrivals) {
        products.clear();
        products.addAll(newArrivals);
        if (products != null)
        {
            mListener.setProgressBarVisible(false);
        }
        if(products.size() == 0){
            view.findViewById(R.id.linear_ca).setVisibility(GONE);
            view.findViewById(R.id.no_result).setVisibility(View.VISIBLE);
        }
        productAdapter.notifyDataSetChanged();
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
