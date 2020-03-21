package com.example.e_commerce.view;


import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;

import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.e_commerce.R;
import com.example.e_commerce.model.TopCategories;
import com.example.e_commerce.presenter.HeadLinePresenter;
import com.example.e_commerce.view.view_interface.IHeadLine;
import com.example.e_commerce.view.view_interface.IHomeActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class HeadLine extends Fragment implements IHeadLine {
    View view;
    Toolbar toolbar;
    ImageButton img;
    ListView listView;
    HeadLinePresenter headLinePresenter;
    List<TopCategories> topCategoriesList = new ArrayList<>();
    ArrayList<String> values = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;
    IHomeActivity mListener;
    RelativeLayout relative;
    ImageView profile;
    TextView Name ;
    TextView Email;
    SharedPreferences sharedPreferences;
    ProgressBar progressBar;

    private String name, email, profilrPath;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_head_line, container, false);

        mListener.setProgressBarVisible(true);
        progressBar = view.findViewById(R.id.progress_bar1);
        progressBar.setVisibility(View.VISIBLE);


        profile = view.findViewById(R.id.profile);
        Name = view.findViewById(R.id.user_name);
        Email = view.findViewById(R.id.email);

        sharedPreferences = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);

        name = sharedPreferences.getString("name","");
        Log.d("opop",sharedPreferences.getString("api_token",""));
        email = sharedPreferences.getString("email","");
        profilrPath = sharedPreferences.getString("profilePic","");
        displayUserInfo();

        relative = view.findViewById(R.id.relative);
        relative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_headLine_to_myAccountFragment);
            }
        });

        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        ((HomeActivity) getActivity()).setBottomVisible(false);


        listView = view.findViewById(R.id.list_item);
        toolbar = view.findViewById(R.id.tool_bar_head_line);
        img = toolbar.findViewById(R.id.exit);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((HomeActivity)getActivity()).onBackPressed();
            }
        });


        headLinePresenter = new HeadLinePresenter(this);
        headLinePresenter.onSendData();

        arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, android.R.id.text1, values);
        listView.setAdapter(arrayAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //   Toast.makeText(getContext(), "Hello "+ topCategoriesList.get(i).getId(), Toast.LENGTH_SHORT).show();
                int id = topCategoriesList.get(i).getId();
                Bundle bundle = new Bundle();
                bundle.putInt("id", id);
                bundle.putString("name", topCategoriesList.get(i).getName_en());
                bundle.putString("url",topCategoriesList.get(i).getImage());
                Navigation.findNavController(view).navigate(R.id.showItemInCategories, bundle);
            }
        });
        return view;
    }

    @Override
    public void onHeadLineRecieved(List<TopCategories> topCategories) {
        if (mListener != null && topCategories!= null )
        {


              mListener.setProgressBarVisible(false);

        }
        topCategoriesList.addAll(topCategories);
        Log.d("HeadLineRecieved", topCategoriesList.get(0).getName_en());

        if (values.size() < 8) {
            for (int i = 0; i < topCategoriesList.size(); i++) {
                values.add(topCategoriesList.get(i).getName_en());
                Log.d("values", values.get(i));
            }
        }
        arrayAdapter.notifyDataSetChanged();



    }



    private void displayUserInfo(){
        Name.setText(name);
        Email.setText(email);
        Glide.with(getActivity()).load("https://e-commerce-dev.intcore.net/"+profilrPath)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
               .into(profile);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof IHomeActivity) {
            mListener = (IHomeActivity) context;
//            mListener.onChangeToolbarTitle(title);
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
}
