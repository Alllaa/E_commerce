package com.example.e_commerce.view;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.example.e_commerce.R;
import com.example.e_commerce.RealPathUtil;
import com.example.e_commerce.model.UserInteractor;
import com.example.e_commerce.presenter.UpdateProfilePresenter;
import com.example.e_commerce.view.view_interface.IHomeActivity;
import com.example.e_commerce.view.view_interface.IUpdateProfileView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import static android.app.Activity.RESULT_OK;


public class UpdateProfileFragment extends Fragment implements IUpdateProfileView {

    public static final int REQUEST_CODE = 1;
    public static final int OPEN_DIAKOGUE_CODE = 2;

    SharedPreferences sharedPreferences;

    FloatingActionButton floatingActionButton;
    View view;

    private ImageView profileImage;
    private TextView userName;
    private TextView Email;
    private ProgressBar progressBar;
    private MaterialButton button ;
    private String name, email, profilrPath;

    private UpdateProfilePresenter presenter;
    Uri imageUri;

    private IHomeActivity mListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_update_profile, container, false);

        presenter = new UpdateProfilePresenter(new UserInteractor(), this);

        sharedPreferences = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
        name = sharedPreferences.getString("name", "");
        Log.d("opop", sharedPreferences.getString("api_token", ""));
        email = sharedPreferences.getString("email", "");
        profilrPath = sharedPreferences.getString("profilePic", "");

        profileImage = view.findViewById(R.id.profile_image_update);
        userName = view.findViewById(R.id.update_name);
        Email = view.findViewById(R.id.update_email);

        diplayUser();
        floatingActionButton = view.findViewById(R.id.floating_action_button);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (getActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                        ActivityCompat.requestPermissions(getActivity(), new String[]
                                {Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE);

                        return;
                    }
                }
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                startActivityForResult(intent, OPEN_DIAKOGUE_CODE);
            }
        });

        button = view.findViewById(R.id.save);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (name.equals(userName.getText().toString()) && email.equals(Email.getText().toString()) && profilrPath.equals(sharedPreferences.getString("profilePic", ""))) {
                    Snackbar.make(HomeActivity.coordinatorLayout, "No thing changed", BaseTransientBottomBar.LENGTH_SHORT)
                            .show();
                } else {

                    profilrPath = profilrPath.equals(sharedPreferences.getString("profilePic", "")) ? null : profilrPath;

                    presenter.updateProfile(sharedPreferences.getString("api_token", ""),
                            userName.getText().toString(),
                            Email.getText().toString(),
                            profilrPath
                    );
                    button.setEnabled(false);
                    mListener.setProgressBarVisible(true);
                }
            }
        });
        return view;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {
        if (requestCode == OPEN_DIAKOGUE_CODE && resultCode == RESULT_OK) {
            if (resultData != null) {
                // this is the image selected by the user
                imageUri = resultData.getData();
                //profilrPath = imageUri.getLastPathSegment();
                profileImage.setImageURI(imageUri);

                profilrPath = RealPathUtil.getRealPath(getActivity(), imageUri);
                Log.d("TAG", profilrPath/*+"    "+f.getAbsolutePath()+" -   "+f.getName()+" ,,   "+f.getPath()*/);


            }
        }
    }

    private void diplayUser() {
        userName.setText(name);
        Email.setText(email);
        Glide.with(getActivity()).load("https://e-commerce-dev.intcore.net/" + profilrPath)
                .into(profileImage);
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
    public void navigate() {
        Navigation.findNavController(view).navigate(R.id.action_updateProfileFragment_to_myAccountFragment);
        mListener.setProgressBarVisible(false);
    }
}