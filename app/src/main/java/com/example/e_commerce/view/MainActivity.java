package com.example.e_commerce.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.e_commerce.R;
import com.example.e_commerce.view.view_interface.ImainActivity;

import static android.view.View.GONE;

public class MainActivity extends AppCompatActivity implements ImainActivity {

    private ImageButton imgB;
    private TextView titleBar;
    public static CoordinatorLayout coordinatorLayout;

    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.my_toolbar);

        toolbar.setBackgroundColor(Color.TRANSPARENT);

        coordinatorLayout = findViewById(R.id.snack_bar);

        imgB = findViewById(R.id.back_arrow);
        titleBar = findViewById(R.id.toolbar_title);
        progressBar = findViewById(R.id.progress_bar_main);
        imgB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();
            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void setAppBarTitle(String title) {
        titleBar.setText(title);
    }

    @Override
    public void setBrogressBarVisible(Boolean state) {
        if (!state) {
            progressBar.setVisibility(GONE);
        } else {
            progressBar.setVisibility(View.VISIBLE);
        }
    }
}
