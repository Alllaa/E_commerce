package com.example.e_commerce.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.e_commerce.R;
import com.example.e_commerce.model.AddressInteractor;
import com.example.e_commerce.presenter.AddAddressPresenter;
import com.example.e_commerce.view.view_interface.IAddAddressView;
import com.example.e_commerce.view.view_interface.IHomeActivity;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;


public class AddAddressFragment extends Fragment implements IAddAddressView {


    private TextInputEditText city;
    private TextInputEditText street;
    private TextInputEditText building;
    private TextInputEditText floor;
    private TextInputEditText apartment;
    private TextInputEditText landmark;
    private TextInputEditText phone;
    private TextInputEditText notes;

    String name, email, profilrPath, api_token;
    SharedPreferences sharedPreferences;
    IHomeActivity mListener;

    private AddAddressPresenter mAddAddressPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_address, container, false);

        mListener.onChangeInFragment(getString(R.string.my_address_title));

        mAddAddressPresenter = new AddAddressPresenter(new AddressInteractor(), this);

        sharedPreferences = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
        name = sharedPreferences.getString("name", "");
        api_token = sharedPreferences.getString("api_token", "");
        email = sharedPreferences.getString("email", "");
        profilrPath = sharedPreferences.getString("profilePic", "");

        city = view.findViewById(R.id.city);
        street = view.findViewById(R.id.street);
        building = view.findViewById(R.id.building);
        floor = view.findViewById(R.id.floor);
        apartment = view.findViewById(R.id.apartment);
        landmark = view.findViewById(R.id.landmark);
        phone = view.findViewById(R.id.phone);
        notes = view.findViewById(R.id.notes);

        view.findViewById(R.id.create).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (city.getText().length() == 0 || street.getText().length() == 0 || building.getText().length() == 0 ||
                        floor.getText().length() == 0 || apartment.getText().length() == 0 || landmark.getText().length() == 0 || phone.getText().length() == 0) {
                    Snackbar.make(getView(), "Please enter all requirement fields", BaseTransientBottomBar.LENGTH_SHORT)
                            .setTextColor(getActivity().getResources().getColor(R.color.BackViewColorRed))
                            .show();
                } else {
                    mAddAddressPresenter.createAddress(city.getText().toString(),
                            street.getText().toString(),
                            building.getText().toString(),
                            floor.getText().toString(),
                            apartment.getText().toString(),
                            landmark.getText().toString(),
                            phone.getText().toString(),
                            notes.getText().toString(),
                            api_token
                    );
                    getActivity().onBackPressed();
                }
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
}