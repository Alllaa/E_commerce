package com.example.e_commerce.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.e_commerce.R;
import com.example.e_commerce.adapter.AddressAdapter;
import com.example.e_commerce.presenter.interface_presnter.IMyAddressPresenter;
import com.example.e_commerce.view.view_interface.IHomeActivity;
import com.example.e_commerce.view.view_interface.IMyAddressView;
import com.example.e_commerce.model.Address;
import com.example.e_commerce.model.AddressInteractor;
import com.example.e_commerce.presenter.MyAddresspresenter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


public class MyAddressesFragment extends Fragment implements IMyAddressView {

    SharedPreferences sharedPreferences;


    FloatingActionButton floatingActionButton;

    private List<Address> myAdresses = new ArrayList<>();
    private RecyclerView addressesRecycler;
    private AddressAdapter mAddressAdapter = new AddressAdapter(getActivity(),myAdresses);

    private IMyAddressPresenter myAddressPresenter;

    String name,email,profilrPath,api_token;
    IHomeActivity mListener;

    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_my_addresses, container, false);

        mListener.setProgressBarVisible(true);

        mListener.onChangeInFragment(getString(R.string.my_address_title));


        sharedPreferences = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
        name = sharedPreferences.getString("name","");
        api_token = sharedPreferences.getString("api_token","");
        email = sharedPreferences.getString("email","");
        profilrPath = sharedPreferences.getString("profilePic","");

        addressesRecycler = view.findViewById(R.id.my_addresses_recycler);
        addressesRecycler.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));

            myAddressPresenter = new MyAddresspresenter(new AddressInteractor(), this);
            myAddressPresenter.getAddresses(api_token);

        floatingActionButton = view.findViewById(R.id.floating_action_button);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_myAddressesFragment_to_addAddressFragment);
            }
        });


        return view;
    }

    @Override
    public void displayAddresses(List<Address> list) {

        if(list != null) {
            mListener.setProgressBarVisible(false);
            myAdresses.clear();
            myAdresses.addAll(list);
            addressesRecycler.setAdapter(mAddressAdapter);
        }
        if(list.size() == 0){
            view.findViewById(R.id.no_addresses_found).setVisibility(View.VISIBLE);
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
}