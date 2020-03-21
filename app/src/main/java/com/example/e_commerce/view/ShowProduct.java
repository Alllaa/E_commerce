package com.example.e_commerce.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.e_commerce.LanguageHandling;
import com.example.e_commerce.MyApplication;
import com.example.e_commerce.R;
import com.example.e_commerce.model.Product;
import com.example.e_commerce.presenter.ShowProductPresenter;
import com.example.e_commerce.view.view_interface.IHomeActivity;
import com.example.e_commerce.view.view_interface.IShowProduct;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class ShowProduct extends Fragment implements IShowProduct {
    View view;
    View VIEW;
    TextView name_en, price, offerPrice, category, rateProduct;
    ImageView imageView, imageView2, img1, img2, img3;
    RatingBar ratingBar;
    Product oProduct;
    String title;
    ShowProductPresenter showProductPresenter;
    Button btn;
    int id;
    IHomeActivity mListener;
    int num;
    ProgressBar progressBar;
    //     static int click = 0;
    SharedPreferences sharedPreferences;
    String api_token;
    RelativeLayout relativeLayout;
    String url1, url2, url3;
    int[][] states = new int[][]{
            new int[]{android.R.attr.state_enabled}
    };
    int[][] states0 = new int[][]{
            new int[]{0}
    };
    String color = null;
    RelativeLayout rel1, rel2, rel3, rel4, rell1, rell2, rell3, rell4, rell5, rell6;
    LinearLayout rel;
    TextView selectColor;
     int stars;
    String size = null;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_show_product, container, false);
        VIEW = view;
        ((HomeActivity) getActivity()).setBottomVisible(false);

        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        mListener.setProgressBarVisible(true);
        mListener.onChangeInFragment("");



        sharedPreferences = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
        api_token = sharedPreferences.getString("api_token", "");

        progressBar = view.findViewById(R.id.progress_bar_product);
        name_en = view.findViewById(R.id.name_en);
        price = view.findViewById(R.id.price_after_offer);
        offerPrice = view.findViewById(R.id.real_price);
        category = view.findViewById(R.id.category);
        imageView = view.findViewById(R.id.img_product);
        btn = view.findViewById(R.id.to_cart);
        imageView2 = view.findViewById(R.id.heart);
        img1 = view.findViewById(R.id.img1_small);
        img2 = view.findViewById(R.id.img2_small);
        img3 = view.findViewById(R.id.img3_small);
        relativeLayout = view.findViewById(R.id.images_relative);
        ratingBar = view.findViewById(R.id.rating);
        Bundle bundle = getArguments();
        id = bundle.getInt("id");
        title = bundle.getString("title");
        rateProduct = view.findViewById(R.id.rate_product);
        rateProduct.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);

        //mListener.onCahngeShowProduct(title);


        showProductPresenter = new ShowProductPresenter(this);
        showProductPresenter.onSendData(api_token, id);
        Log.d("IDDDD", id + "");
        btn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {

                final View dialogView = getLayoutInflater().inflate(R.layout.bottom_sheet, null);
                final BottomSheetDialog dialog = new BottomSheetDialog(getContext());
                dialog.setContentView(dialogView);
                dialog.show();
                final View view1, view2, view3, view4;
                final TextView textView, xs, s, m, l, xl, xll;
                final LinearLayout linearLayout2;
                Button button;

                linearLayout2 = dialogView.findViewById(R.id.linear_bottom_sheet);
                textView = dialogView.findViewById(R.id.select_size);
                view1 = dialogView.findViewById(R.id.view1);
                view2 = dialogView.findViewById(R.id.view2);
                view3 = dialogView.findViewById(R.id.view3);
                view4 = dialogView.findViewById(R.id.view4);
                rel = dialogView.findViewById(R.id.Relative_colors);
                rel3 = dialogView.findViewById(R.id.rel3);
                rel2 = dialogView.findViewById(R.id.rel2);
                rel1 = dialogView.findViewById(R.id.rel1);
                rel4 = dialogView.findViewById(R.id.rel4);
                xs = dialogView.findViewById(R.id.xs1);
                s = dialogView.findViewById(R.id.s1);
                m = dialogView.findViewById(R.id.m1);
                l = dialogView.findViewById(R.id.l1);
                xl = dialogView.findViewById(R.id.xl1);
                xll = dialogView.findViewById(R.id.xll1);
                rell1 = dialogView.findViewById(R.id.xs);
                rell2 = dialogView.findViewById(R.id.s);
                rell3 = dialogView.findViewById(R.id.m);
                rell4 = dialogView.findViewById(R.id.l);
                rell5 = dialogView.findViewById(R.id.xl);
                rell6 = dialogView.findViewById(R.id.xll);

                selectColor = dialogView.findViewById(R.id.select_color);
                button = dialogView.findViewById(R.id.to_cart_bottom_sheet);
                if (oProduct.getSizes().size() == 0) {
                    textView.setText("No sizes to choose");
                    linearLayout2.setVisibility(View.GONE);
                }

                xs.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        rell1.setBackgroundResource(R.drawable.border_circle2);
                        rell2.setBackgroundResource(R.drawable.border_circle);
                        rell3.setBackgroundResource(R.drawable.border_circle);
                        rell4.setBackgroundResource(R.drawable.border_circle);
                        rell5.setBackgroundResource(R.drawable.border_circle);
                        rell6.setBackgroundResource(R.drawable.border_circle);
                        size = "xs";
                    }
                });
                s.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        rell1.setBackgroundResource(R.drawable.border_circle);
                        rell2.setBackgroundResource(R.drawable.border_circle2);
                        rell3.setBackgroundResource(R.drawable.border_circle);
                        rell4.setBackgroundResource(R.drawable.border_circle);
                        rell5.setBackgroundResource(R.drawable.border_circle);
                        rell6.setBackgroundResource(R.drawable.border_circle);
                        size = "s";
                    }
                });
                m.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        size = "m";
                        rell1.setBackgroundResource(R.drawable.border_circle);
                        rell2.setBackgroundResource(R.drawable.border_circle);
                        rell3.setBackgroundResource(R.drawable.border_circle2);
                        rell4.setBackgroundResource(R.drawable.border_circle);
                        rell5.setBackgroundResource(R.drawable.border_circle);
                        rell6.setBackgroundResource(R.drawable.border_circle);
                    }
                });
                l.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        size = "l";
                        rell1.setBackgroundResource(R.drawable.border_circle);
                        rell2.setBackgroundResource(R.drawable.border_circle);
                        rell3.setBackgroundResource(R.drawable.border_circle);
                        rell4.setBackgroundResource(R.drawable.border_circle2);
                        rell5.setBackgroundResource(R.drawable.border_circle);
                        rell6.setBackgroundResource(R.drawable.border_circle);
                    }
                });
                xl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        size = "xl";
                        rell1.setBackgroundResource(R.drawable.border_circle);
                        rell2.setBackgroundResource(R.drawable.border_circle);
                        rell3.setBackgroundResource(R.drawable.border_circle);
                        rell4.setBackgroundResource(R.drawable.border_circle);
                        rell5.setBackgroundResource(R.drawable.border_circle2);
                        rell6.setBackgroundResource(R.drawable.border_circle);
                    }
                });
                xll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        size = "xxl";
                        rell1.setBackgroundResource(R.drawable.border_circle);
                        rell2.setBackgroundResource(R.drawable.border_circle);
                        rell3.setBackgroundResource(R.drawable.border_circle);
                        rell4.setBackgroundResource(R.drawable.border_circle);
                        rell5.setBackgroundResource(R.drawable.border_circle);
                        rell6.setBackgroundResource(R.drawable.border_circle2);
                    }
                });
                if (oProduct.getColors().size() >= 2) {
                    rel3.setVisibility(View.VISIBLE);
                    rel2.setVisibility(View.VISIBLE);
                    view2.setBackgroundTintList(new ColorStateList(states, new int[]{Color.parseColor(oProduct.getColors().get(0).getColor())}));
                    view3.setBackgroundTintList(new ColorStateList(states, new int[]{Color.parseColor(oProduct.getColors().get(1).getColor())}));
                    rel2.setBackgroundResource(R.drawable.border_circle2);
                    color = oProduct.getColors().get(0).getColor();
                    if (oProduct.getColors().size() >= 3) {
                        rel1.setVisibility(View.VISIBLE);
                        view1.setBackgroundTintList(new ColorStateList(states, new int[]{Color.parseColor(oProduct.getColors().get(2).getColor())}));
                        if (oProduct.getColors().size() >= 4) {
                            rel4.setVisibility(View.VISIBLE);
                            view4.setBackgroundTintList(new ColorStateList(states, new int[]{Color.parseColor(oProduct.getColors().get(3).getColor())}));
                        }
                    }

                } else {
                    selectColor.setText("No colors to choose");
                    rel.setVisibility(View.GONE);
                }
                view1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        rel1.setBackgroundResource(R.drawable.border_circle2);
                        if (rel2.getVisibility() == View.VISIBLE)
                            rel2.setBackgroundResource(R.drawable.border_circle);
                        if (rel3.getVisibility() == View.VISIBLE)
                            rel3.setBackgroundResource(R.drawable.border_circle);
                        if (rel4.getVisibility() == View.VISIBLE)
                            rel4.setBackgroundResource(R.drawable.border_circle);
                        color = oProduct.getColors().get(2).getColor();
                    }
                });
                view2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        rel2.setBackgroundResource(R.drawable.border_circle2);
                        if (rel1.getVisibility() == View.VISIBLE)
                            rel1.setBackgroundResource(R.drawable.border_circle);
                        if (rel3.getVisibility() == View.VISIBLE)
                            rel3.setBackgroundResource(R.drawable.border_circle);
                        if (rel4.getVisibility() == View.VISIBLE)
                            rel4.setBackgroundResource(R.drawable.border_circle);
                        color = oProduct.getColors().get(0).getColor();
                    }
                });
                view3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        rel3.setBackgroundResource(R.drawable.border_circle2);
                        if (rel1.getVisibility() == View.VISIBLE)
                            rel1.setBackgroundResource(R.drawable.border_circle);
                        if (rel2.getVisibility() == View.VISIBLE)
                            rel2.setBackgroundResource(R.drawable.border_circle);
                        if (rel4.getVisibility() == View.VISIBLE)
                            rel4.setBackgroundResource(R.drawable.border_circle);
                        color = oProduct.getColors().get(1).getColor();
                    }
                });
                view4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        rel4.setBackgroundResource(R.drawable.border_circle2);
                        if (rel1.getVisibility() == View.VISIBLE)
                            rel1.setBackgroundResource(R.drawable.border_circle);
                        if (rel2.getVisibility() == View.VISIBLE)
                            rel2.setBackgroundResource(R.drawable.border_circle);
                        if (rel3.getVisibility() == View.VISIBLE)
                            rel3.setBackgroundResource(R.drawable.border_circle);
                        color = oProduct.getColors().get(3).getColor();
                    }
                });


                button.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @SuppressLint("ResourceAsColor")
                    @Override
                    public void onClick(View view) {
                        if (oProduct != null) {
                            showProductPresenter.onAddToCart(api_token, oProduct.getId(), size, color, 1);
                            Navigation.findNavController(VIEW).navigate(R.id.action_showProduct_to_cartFragment);
                            dialog.hide();
                        }
                    }
                });

            }
        });
        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Glide.with(getActivity()).load(url1).into(imageView);
                img1.setBackgroundResource(R.drawable.border);
                img2.setBackgroundResource(R.drawable.border2);
                img3.setBackgroundResource(R.drawable.border2);
            }
        });
        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Glide.with(getActivity()).load(url2).into(imageView);
                img2.setBackgroundResource(R.drawable.border);
                img1.setBackgroundResource(R.drawable.border2);
                img3.setBackgroundResource(R.drawable.border2);
            }
        });
        img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Glide.with(getActivity()).load(url3).into(imageView);
                img3.setBackgroundResource(R.drawable.border);
                img1.setBackgroundResource(R.drawable.border2);
                img2.setBackgroundResource(R.drawable.border2);
            }
        });
        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showProductPresenter.onAddToWish_List(api_token, oProduct.getId());
                if (oProduct.isIs_fav()) {
                    oProduct.setIs_fav(false);
                    imageView2.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                } else {
                    oProduct.setIs_fav(true);
                    imageView2.setImageResource(R.drawable.favorite_red);

                }
            }
        });

        rateProduct.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                ShowDialog();
            }
        });

        return view;
    }

    @Override
    public void onDataRecieved(final Product product) {
        oProduct = product;
        if (oProduct != null) {
            mListener.setProgressBarVisible(false);
        }

        if (oProduct.getImages().size() == 0) {
            relativeLayout.setVisibility(View.GONE);
        } else {
            if (oProduct.getImages().size() >= 1) {

                url1 = "https://e-commerce-dev.intcore.net/" + oProduct.getImages().get(0).getImage();
                Glide.with(getActivity()).load(url1).into(img1);
                if (oProduct.getImages().size() >= 2) {
                    img2.setVisibility(View.VISIBLE);
                    url2 = "https://e-commerce-dev.intcore.net/" + oProduct.getImages().get(1).getImage();
                    Glide.with(getActivity()).load(url2).into(img2);
                    if (oProduct.getImages().size() >= 3) {
                        img3.setVisibility(View.VISIBLE);
                        url3 = "https://e-commerce-dev.intcore.net/" + oProduct.getImages().get(2).getImage();
                        Glide.with(getActivity()).load(url3).into(img3);
                    }
                }
            }
        }
        String url = "https://e-commerce-dev.intcore.net/" + product.getDefault_image();
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
        String lang = LanguageHandling.getLanguage(MyApplication.getInstance().getApplicationContext());
        if (lang != null) {
            if (lang.equals("en")) {
                mListener.onChangeInFragment("Product");
                name_en.setText(product.getName_en());
            } else if (lang.equals("ar")) {
                name_en.setText(product.getName_ar());
                mListener.onChangeInFragment("المنتج");
            }
        } else {
            name_en.setText(product.getName_en());
            mListener.onChangeInFragment("Product");
        }

        price.setText(product.getPrice() + "$");

        if (product.getOffer().size() > 0) {
            num = product.getPrice() - (product.getOffer().get(0).getPercentage() / 100 * product.getPrice());
            offerPrice.setText(num + "$");
        } else {
            offerPrice.setText(R.string.no_offer);
        }

        if (product.getSubcategory().getCategory().getName_en() != null)
            category.append(" " + product.getSubcategory().getCategory().getName_en());
        else
            category.append("");

        if (oProduct.isIs_fav()) {
            imageView2.setImageResource(R.drawable.favorite_red);
        } else {
            imageView2.setImageResource(R.drawable.ic_favorite_border_black_24dp);
        }

        ratingBar.setRating(oProduct.getTotal_rate());

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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        showProductPresenter.cancelCall();
    }

    @SuppressLint("NewApi")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void ShowDialog()
    {

        final AlertDialog.Builder popDialog = new AlertDialog.Builder(getContext());
        final RatingBar rating = new RatingBar(getContext());
        LinearLayout linearLayout = new LinearLayout(getContext());

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );

        TextView text = new TextView(getContext());


        text.setLayoutParams(lp);
        rating.setLayoutParams(lp2);

        linearLayout.addView(text);
        linearLayout.addView(rating);
        text.setText(R.string.review);
        text.append("\n");
        text.setPadding(10, 10, 10, 10);
        text.setGravity(Gravity.CENTER);
        text.setTextSize(20);
        rating.setMax(5);
        rating.setNumStars(5);

        rating.setForegroundGravity(Gravity.CENTER);
        rating.setProgressTintList(new ColorStateList(states0, new int[]{Color.parseColor("#E3C841")}));
        rating.setStepSize((float) 1.0);



       // popDialog.setIcon(android.R.drawable.btn_star_big_on);

        //(popDialog.setTitle("Rating: ")
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setGravity(Gravity.CENTER);
        popDialog.setView(linearLayout);

        rating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                stars = (int) v;
            }
        });
        // Button OK
        popDialog.setPositiveButton(android.R.string.ok,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        showProductPresenter.onRateTheProduct(api_token,id,stars);
                        dialog.dismiss();
                    }

                })

                // Button Cancel
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        popDialog.create();
        popDialog.show();

    }

}
