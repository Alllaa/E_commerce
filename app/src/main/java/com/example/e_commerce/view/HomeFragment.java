package com.example.e_commerce.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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
import com.example.e_commerce.adapter.BestSellerAdapter;
import com.example.e_commerce.adapter.NewArrivalAdapter;
import com.example.e_commerce.adapter.TopCategoriesAdapter;
import com.example.e_commerce.model.BestSeller;
import com.example.e_commerce.model.NewArrivals;
import com.example.e_commerce.model.TopCategories;
import com.example.e_commerce.presenter.HomePresenter;
import com.example.e_commerce.view.view_interface.IHome;
import com.example.e_commerce.view.view_interface.IHomeActivity;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment implements IHome {

    private View view;
    private RecyclerView recyclerView;
    private RecyclerView recyclerView2;
    private RecyclerView recyclerView3;
    List<NewArrivals> newLest = new ArrayList<>();
    List<TopCategories> newLest2 = new ArrayList<>();
    List<BestSeller> newLest3 = new ArrayList<>();
    NewArrivalAdapter newArrivalAdapter;
    TopCategoriesAdapter topCategoriesAdapter;
    BestSellerAdapter bestSellerAdapter;
    IHomeActivity mListener;
    HomePresenter homePresenter;
    TextView seeMore1;
    TextView seeMore2;
    TextView seeMore3;
    private String api_token;
    SharedPreferences sharedPreferences;
    LinearLayout lin1,lin2,lin3;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        ((HomeActivity) getActivity()).setBottomVisible(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();

        mListener.onChangeToolbarTitle("Home");


        if (newLest.size() == 0 || newLest2.size() == 0  || newLest3.size() == 0)
            mListener.setProgressBarVisible(true);
        mListener.onChangeToolbarTitle(getActivity().getString(R.string.home_title));


        sharedPreferences = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
        api_token = sharedPreferences.getString("api_token", "");


        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        final LinearLayoutManager layoutManager2 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        final LinearLayoutManager layoutManager3 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);

        Log.d("api_tokenn", getActivity().getIntent().getStringExtra("api_token"));

        seeMore1 = view.findViewById(R.id.see_more1);
        seeMore2 = view.findViewById(R.id.see_more2);
        seeMore3 = view.findViewById(R.id.see_more3);

        lin1 = view.findViewById(R.id.line_home1);
        lin2 = view.findViewById(R.id.line_home2);
        lin3 = view.findViewById(R.id.line_home3);
        lin1.setVisibility(View.GONE);
        lin2.setVisibility(View.GONE);
        lin3.setVisibility(View.GONE);


        recyclerView = view.findViewById(R.id.recycler_home);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView2 = view.findViewById(R.id.recycler_home2);
        recyclerView2.setLayoutManager(layoutManager2);

        recyclerView3 = view.findViewById(R.id.recycler_home3);
        recyclerView3.setLayoutManager(layoutManager3);


        homePresenter = new HomePresenter(this);
        homePresenter.onSendData(api_token);


        newArrivalAdapter = new NewArrivalAdapter(getContext(), newLest);
        recyclerView.setAdapter(newArrivalAdapter);

        topCategoriesAdapter = new TopCategoriesAdapter(getContext(), newLest2);
        recyclerView3.setAdapter(topCategoriesAdapter);

        bestSellerAdapter = new BestSellerAdapter(getContext(), newLest3);
        recyclerView2.setAdapter(bestSellerAdapter);

        newArrivalAdapter.setOnFavouriteClickListener(new NewArrivalAdapter.OnItemClickListener() {
            @Override
            public void onFavouriteClick(int position) {
                int id = newLest.get(position).getId();
                String title = newLest.get(position).getName_en();

                Bundle bundle = new Bundle();
                bundle.putInt("id", id);
                bundle.putString("title", title);
                Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_showProduct, bundle);

            }

            @Override
            public void onAddToWishListClick(int position) {
                Toast.makeText(MyApplication.getInstance().getApplicationContext(), "Hello" + position, Toast.LENGTH_SHORT).show();
                homePresenter.onAddToWish_List(api_token, newLest.get(position).getId());

                if (newLest.get(position).isIs_fav()) {
                    newLest.get(position).setIs_fav(false);
                } else {
                    newLest.get(position).setIs_fav(true);
                }
                for (int i = 0; i < newLest.size(); i++) {
                    if (newLest3.get(i).getId() == newLest.get(position).getId()) {
                        newLest3.get(i).setIs_fav(newLest.get(position).isIs_fav());
                    }
                }
                newArrivalAdapter.notifyDataSetChanged();
                bestSellerAdapter.notifyDataSetChanged();
            }
        });

        topCategoriesAdapter.setOnFavouriteClickListener(new TopCategoriesAdapter.OnItemClickListener() {
            @Override
            public void onFavouriteClick(int position) {
//                Toast.makeText(getContext(), "Clickedd" + position, Toast.LENGTH_SHORT).show();
                int id = newLest2.get(position).getId();
                Bundle bundle = new Bundle();
                bundle.putInt("id", id);
                bundle.putString("name", newLest2.get(position).getName_en());
                bundle.putString("url", newLest2.get(position).getImage());
                Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_showItemInCategories, bundle);

            }
        });

        bestSellerAdapter.setOnFavouriteClickListener(new BestSellerAdapter.OnItemClickListener() {
            @Override
            public void onFavouriteClick(int position) {
                int id = newLest3.get(position).getId();
                String title = newLest3.get(position).getName_en();

                Bundle bundle = new Bundle();
                bundle.putInt("id", id);
                bundle.putString("title", title);
                Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_showProduct, bundle);


            }

            @Override
            public void addToWishListBest(int position) {

                homePresenter.onAddToWish_List(api_token, newLest3.get(position).getId());

                if (newLest3.get(position).isIs_fav()) {
                    newLest3.get(position).setIs_fav(false);
                } else {
                    newLest3.get(position).setIs_fav(true);
                }
                for (int i = 0; i < newLest3.size(); i++) {
                    if (newLest.get(i).getId() == newLest3.get(position).getId()) {
                        newLest.get(i).setIs_fav(newLest3.get(position).isIs_fav());
                    }
                }
                newArrivalAdapter.notifyDataSetChanged();
                bestSellerAdapter.notifyDataSetChanged();

            }
        });
        seeMore1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("id", 1);
                Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_arrival_Seller, bundle);

            }
        });
        seeMore2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("id", 2);
                Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_arrival_Seller, bundle);

            }
        });
        seeMore3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "See more", Toast.LENGTH_SHORT).show();

                Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_headLine2);
            }
        });
        return view;
    }

    @Override
    public void onDataRecieved(List<NewArrivals> newArrivals, List<TopCategories> topCategories, List<BestSeller> bestSellers) {

        if (newLest.size() < 12) {
            newLest.addAll(newArrivals);
            newLest3.addAll(bestSellers);
        }
        if (newLest2.size() < 8)
            newLest2.addAll(topCategories);


        if (newLest != null || newLest2 != null || newLest3 != null) {
            mListener.setProgressBarVisible(false);
            lin1.setVisibility(View.VISIBLE);
            lin2.setVisibility(View.VISIBLE);
            lin3.setVisibility(View.VISIBLE);

        }
        newArrivalAdapter.notifyDataSetChanged();
        topCategoriesAdapter.notifyDataSetChanged();
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

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION);
        getActivity().registerReceiver(checkInternet, intentFilter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //getActivity().unregisterReceiver(checkInternet);
        homePresenter.cancelCall();
        view = null;
        lin1 = null;

    }

    private BroadcastReceiver checkInternet = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int wifi = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, WifiManager.WIFI_STATE_UNKNOWN);

            switch (wifi) {
                case WifiManager.WIFI_STATE_ENABLING:
                    /*Snackbar.make(getActivity().findViewById(R.id.snack_bar), "you are online reload for data", BaseTransientBottomBar.LENGTH_LONG)
                            .show();*/
                    try {
                        Thread.sleep(4000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    homePresenter.onSendData(api_token);
                    break;
                case WifiManager.WIFI_STATE_DISABLED:
                    Snackbar.make(getActivity().findViewById(R.id.snack_bar), "Your are offline...check your connection", BaseTransientBottomBar.LENGTH_LONG)
                            .setTextColor(getActivity().getResources().getColor(R.color.BackViewColorRed))
                            .show();
                    break;
            }
        }
    };
}
