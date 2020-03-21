package com.example.e_commerce.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.e_commerce.R;
import com.example.e_commerce.model.User;
import com.example.e_commerce.model.UserInteractor;
import com.example.e_commerce.presenter.MyAccountPresenter;
import com.example.e_commerce.presenter.interface_presnter.ImyAccountPresenter;
import com.example.e_commerce.view.view_interface.IHomeActivity;
import com.example.e_commerce.view.view_interface.ImyAccountView;


public class MyAccountFragment extends Fragment implements ImyAccountView {
    View view;
    RelativeLayout relative;
    SharedPreferences sharedPreferences;

    private ImageView profileImage;
    private TextView userName;
    private TextView Email;
    private TextView number_of_orders;

    private String name, email, profilrPath;

    private ImyAccountPresenter myAccountPresenter;
    IHomeActivity mListener;
    ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_my_account, container, false);
        ((HomeActivity) getActivity()).setBottomVisible(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();

        mListener.onChangeInMyAccount(getActivity().getString(R.string.my_account_title));
        mListener.setProgressBarVisible(false);

        progressBar = view.findViewById(R.id.progress_bar1);

        sharedPreferences = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);

        name = sharedPreferences.getString("name", "");
        Log.d("opop", sharedPreferences.getString("api_token", ""));
        email = sharedPreferences.getString("email", "");
        profilrPath = sharedPreferences.getString("profilePic", "");

        myAccountPresenter = new MyAccountPresenter(new UserInteractor(), this);
        myAccountPresenter.displayaccount(sharedPreferences.getString("api_token", ""));

        relative = view.findViewById(R.id.relative);

        profileImage = view.findViewById(R.id.profile_image);
        userName = view.findViewById(R.id.user_name);
        Email = view.findViewById(R.id.user_email);
        number_of_orders = view.findViewById(R.id.number_of_orders);

        diplayUserWithoutApi();

        view.findViewById(R.id.change_password).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_myAccountFragment_to_cangePasswordFromMyAccountFragment);
                mListener.onChangeInFragment(getActivity().getString(R.string.change_password_title));

            }
        });
        view.findViewById(R.id.contact).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_myAccountFragment_to_contactUsFragment);
                mListener.onChangeInFragment(getActivity().getString(R.string.contact_us_title));

            }
        });
        view.findViewById(R.id.relative).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_myAccountFragment_to_updateProfileFragment);
                mListener.onChangeInFragment(getActivity().getString(R.string.edit_title));

            }
        });
        view.findViewById(R.id.my_wishlist).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_myAccountFragment_to_wishListFragment);
            }
        });
        view.findViewById(R.id.my_cart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_myAccountFragment_to_cartFragment2);
            }
        });
        view.findViewById(R.id.my_addresses).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_myAccountFragment_to_myAddressesFragment);
            }
        });

        view.findViewById(R.id.my_orders).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_myAccountFragment_to_myOrderFragment);
            }
        });
        view.findViewById(R.id.language).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_myAccountFragment_to_changeLanguageFragment);
                mListener.onChangeInFragment(getActivity().getString(R.string.change_language_title));
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
    public void displayuser(User user) {

        if (user.getUser().getCartCount() > 0)
            number_of_orders.setBackgroundResource(R.drawable.circle_red_noimage);
        number_of_orders.setText(user.getUser().getCartCount() + "");

        if (!user.getUser().getEmail().equals(email) || !user.getUser().getName().equals(name) || !user.getUser().getProfilePic().equals(profilrPath)) {
            Log.d("TAGG", "updated");
            name = user.getUser().getName();
            email = user.getUser().getEmail();
            profilrPath = user.getUser().getProfilePic();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("name", name);
            editor.putString("email", email);
            editor.putString("profilePic", profilrPath);
            editor.commit();
            diplayUserWithoutApi();
        }

    }

    private void diplayUserWithoutApi() {

        progressBar.setVisibility(View.VISIBLE);
        userName.setText(name);
        Email.setText(email);
        Glide.with(getActivity()).load("https://e-commerce-dev.intcore.net/" + profilrPath)
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
                .into(profileImage);
    }
}
