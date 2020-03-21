package com.example.e_commerce.view;

import android.Manifest;
import android.app.SearchManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.e_commerce.LanguageHandling;
import com.example.e_commerce.MyApplication;
import com.example.e_commerce.R;
import com.example.e_commerce.view.view_interface.IHomeActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;
import java.net.InetAddress;

import static android.view.View.GONE;

public class HomeActivity extends AppCompatActivity implements IHomeActivity {
    TextView mTitle;
    ImageButton imageButton;
    BottomNavigationView bottomNavigationView;
    Toolbar toolbar;
    SharedPreferences sharedPreferences;
    Menu menu;
    ImageButton img;
    ProgressBar progressBar;
    public static CoordinatorLayout coordinatorLayout;
    NavHostFragment navHostFragment;
    private EditText search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        coordinatorLayout = findViewById(R.id.snack_bar);

        sharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE);

        toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        ActionBar ab = getSupportActionBar();


        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(false);
        }
        ab.setDisplayShowTitleEnabled(false);
        mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        imageButton = toolbar.findViewById(R.id.img_btn);
        progressBar = findViewById(R.id.progress_bar);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Navigation.findNavController(HomeActivity.this, R.id.fragment).navigate(R.id.action_homeFragment_to_headLine);
                } catch (Exception e) {
                }
                try {
                    Navigation.findNavController(HomeActivity.this, R.id.fragment).navigate(R.id.action_cartFragment_to_headLine);
                } catch (Exception e) {
                }
                try {
                    Navigation.findNavController(HomeActivity.this, R.id.fragment).navigate(R.id.action_dealsFragment_to_headLine);
                } catch (Exception e) {
                }
                try {
                    Navigation.findNavController(HomeActivity.this, R.id.fragment).navigate(R.id.action_wishListFragment_to_headLine);
                } catch (Exception e) {
                }
                try {
                    Navigation.findNavController(HomeActivity.this, R.id.fragment).navigate(R.id.action_myAccountFragment_to_headLine);
                } catch (Exception e) {
                }

            }
        });


        bottomNavigationView = findViewById(R.id.bottom_nav);
        navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment);
        NavigationUI.setupWithNavController(bottomNavigationView,
                navHostFragment.getNavController());
        setBottomVisible(true);


        img = toolbar.findViewById(R.id.back_arrow);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Navigation.findNavController(HomeActivity.this, R.id.fragment).navigate(R.id.action_showProduct_to_homeFragment);
                } catch (Exception e) {
                    onBackPressed();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {

        getMenuInflater().inflate(R.menu.app_bar, menu);
        this.menu = menu;

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView = (SearchView) menu.findItem(R.id.search)
                .getActionView();

        if (null != searchView) {
            searchView.setSearchableInfo(searchManager
                    .getSearchableInfo(getComponentName()));
            searchView.setIconifiedByDefault(false);

            int magId = getResources().getIdentifier("android:id/search_mag_icon", null, null);
            ImageView magImage = (ImageView) searchView.findViewById(magId);
            magImage.setLayoutParams(new LinearLayout.LayoutParams(0, 0));
            magImage.setVisibility(GONE);
        }

        SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
            public boolean onQueryTextChange(String newText) {
                // this is your adapter that will be filtered
                return true;
            }

            public boolean onQueryTextSubmit(String query) {
                navigateToSearch(query);
                searchView.setIconified(true);
                menu.findItem(R.id.search).collapseActionView();


                //Here u can get the value "query" which is entered in the search box.
                return true;
            }
        };
        searchView.setOnQueryTextListener(queryTextListener);


        return super.onCreateOptionsMenu(menu);
    }

    private void navigateToSearch(String key) {
        Bundle bundle = new Bundle();
        bundle.putString("key_word", key);
        try {
            Navigation.findNavController(HomeActivity.this, R.id.fragment).navigate(R.id.action_homeFragment_to_showItemInCategories2, bundle);
        } catch (Exception e) {
        }
        try {
            Navigation.findNavController(HomeActivity.this, R.id.fragment).navigate(R.id.action_dealsFragment_to_showItemInCategories, bundle);
        } catch (Exception e) {
        }
        try {
            Navigation.findNavController(HomeActivity.this, R.id.fragment).navigate(R.id.action_wishListFragment_to_showItemInCategories, bundle);
        } catch (Exception e) {
        }
        try {
            Navigation.findNavController(HomeActivity.this, R.id.fragment).navigate(R.id.action_cartFragment_to_showItemInCategories, bundle);
        } catch (Exception e) {
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.cart:
                try {
                    Navigation.findNavController(HomeActivity.this, R.id.fragment).navigate(R.id.action_homeFragment_to_cartFragment);
                } catch (Exception e) {
                }
                try {
                    Navigation.findNavController(HomeActivity.this, R.id.fragment).navigate(R.id.action_dealsFragment_to_cartFragment);
                } catch (Exception e) {
                }
                try {
                    Navigation.findNavController(HomeActivity.this, R.id.fragment).navigate(R.id.action_wishListFragment_to_cartFragment);
                } catch (Exception e) {
                }
                try {
                    Navigation.findNavController(HomeActivity.this, R.id.fragment).navigate(R.id.action_myAccountFragment_to_cartFragment);
                } catch (Exception e) {
                }
                try {
                    Navigation.findNavController(HomeActivity.this, R.id.fragment).navigate(R.id.action_showProduct_to_cartFragment);
                } catch (Exception e) {
                    Log.d("Exception", e.getLocalizedMessage());
                }
                return true;
            case R.id.signOut:
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.commit();
                Intent i = new Intent(this, MainActivity.class);
                startActivity(i);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }

    }

    public void setBottomVisible(boolean bl) {
        if (!bl) {
            bottomNavigationView.setVisibility(GONE);
        } else {
            bottomNavigationView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onChangeToolbarTitle(String title) {
        mTitle.setText(title);
        if (menu != null) {
            menu.findItem(R.id.signOut).setVisible(false);
            menu.findItem(R.id.cart).setVisible(true);
            menu.findItem(R.id.search).setVisible(true);
        }
        img.setVisibility(GONE);
        imageButton.setVisibility(View.VISIBLE);

        setBottomVisible(true);
    }

    @Override
    public void onChangeInMyAccount(String title) {

        menu.findItem(R.id.signOut).setVisible(true);
        menu.findItem(R.id.cart).setVisible(false);
        menu.findItem(R.id.search).setVisible(false);
        mTitle.setText(title);

        img.setVisibility(GONE);
        imageButton.setVisibility(View.VISIBLE);

        setBottomVisible(true);
    }

    @Override
    public void onChangeInFragment(String title) {

        img.setVisibility(View.VISIBLE);
        /*img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        */
        imageButton.setVisibility(GONE);

        menu.findItem(R.id.signOut).setVisible(false);
        menu.findItem(R.id.cart).setVisible(false);
        menu.findItem(R.id.search).setVisible(false);
        mTitle.setText(title);

        setBottomVisible(false);
    }


    @Override
    public void onCahngeShowProduct(String title) {
        img.setVisibility(View.VISIBLE);
/*
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
*/
        imageButton.setVisibility(GONE);

        menu.findItem(R.id.signOut).setVisible(false);
        menu.findItem(R.id.cart).setVisible(true);
        menu.findItem(R.id.search).setVisible(true);
        mTitle.setText(title);

        setBottomVisible(false);
    }

    @Override
    public void setProgressBarVisible(boolean b2) {
        if (!b2) {
            progressBar.setVisibility(GONE);
        } else {
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case UpdateProfileFragment.REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        }
                    }
                }
                break;
        }
    }

    @Override
    public void changeLanguageOMenue(String lang) {
        if (lang.equals("ar"))
            menu.findItem(R.id.signOut).setTitle("تسجيل الخروج");
        if (lang.equals("en"))
            menu.findItem(R.id.signOut).setTitle("Sign out");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}