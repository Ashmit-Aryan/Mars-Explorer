package com.wowtechnow.marsphotoviewer.Fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.wowtechnow.marsphotoviewer.Model.Common;
import com.wowtechnow.marsphotoviewer.Model.MarsManifest;
import com.wowtechnow.marsphotoviewer.R;
import com.wowtechnow.marsphotoviewer.Retrofit.MarsNetworkCall;
import com.wowtechnow.marsphotoviewer.Retrofit.RetrofitClient;


import java.util.Objects;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;


public class BottomSheet extends BottomSheetDialogFragment {
    private BottomSheetBehavior bottomSheetBehavior;
    private CompositeDisposable compositeDisposable;
    private Retrofit retrofit;
    private String rover;
    private MarsNetworkCall marsNetworkCall;
    private LinearLayout linearLayout ;
    private ProgressBar progressBar;
    public BottomSheet(String rover) {
        this.rover = rover;
        compositeDisposable = new CompositeDisposable();
        retrofit = RetrofitClient.getInstance();
        marsNetworkCall = retrofit.create(MarsNetworkCall.class);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        BottomSheetDialog dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.bottom_sheet, null,false);
        dialog.setContentView(view);
        progressBar = dialog.findViewById(R.id.Bottom_Sheet_progressBar);
        AppBarLayout appBarLayout = dialog.findViewById(R.id.appBarLayout);
        TextView name , launch_date , max_sol, max_date , total_Photos , landing_Date,status;
        name = dialog.findViewById(R.id.tvRoverName );
        max_date = dialog.findViewById(R.id.tvMaxDate);
        launch_date = dialog.findViewById(R.id.tvLaunchDate);
        landing_Date = dialog.findViewById(R.id.tvLandingDate);
        max_sol = dialog.findViewById(R.id.tvMaxSol);
        total_Photos = dialog.findViewById(R.id.tvTotalPhotos);
        status = dialog.findViewById(R.id.tvStatus);
        linearLayout = dialog.findViewById(R.id.LinearLayoutView);
        ImageView ivPhoto  = dialog.findViewById(R.id.roverImage);
        bottomSheetBehavior = BottomSheetBehavior.from((View) (view.getParent()));
        bottomSheetBehavior.setPeekHeight(BottomSheetBehavior.PEEK_HEIGHT_AUTO);
        view.setMinimumHeight((Resources.getSystem().getDisplayMetrics().heightPixels) / 2);
        assert appBarLayout != null;
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int i) {
                if (BottomSheetBehavior.STATE_EXPANDED == i) {

                    showView(appBarLayout, getActionBarSize());
                }
                if (BottomSheetBehavior.STATE_COLLAPSED == i) {
                    hideAppBar(appBarLayout);
                }

                if (BottomSheetBehavior.STATE_HIDDEN == i) {
                    dismiss();
                }

            }

            @Override
            public void onSlide(@NonNull View view, float v) {

            }
        });

        ImageButton imageButton =
        dialog.findViewById(R.id.cancelBtn);
        assert imageButton != null;
        imageButton.setOnClickListener(v-> dismiss());
        assert ivPhoto != null;
        switch(rover){
            case "curiosity":
                Glide.with(Objects.requireNonNull(getContext()))
                        .load(getResources().getDrawable(R.mipmap.curiosity))
                        .into(ivPhoto);
                break;
            case "opportunity":
                Glide.with(Objects.requireNonNull(getContext()))
                        .load(getResources().getDrawable(R.mipmap.opportunity))
                        .into(ivPhoto);
                break;
            case "spirit":
                Glide.with(Objects.requireNonNull(getContext()))
                        .load(getResources().getDrawable(R.mipmap.spirit))
                        .into(ivPhoto);
                break;
        }
        getData(name,landing_Date,launch_date,status,max_sol,max_date,total_Photos);
        return dialog;
    }

    @Override
    public void onStart() {
        super.onStart();
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
    }

    private void hideAppBar(View view) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.height = 0;
        view.setLayoutParams(params);

    }

    private void showView(View view, int size) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.height = size;
        view.setLayoutParams(params);
    }

    private int getActionBarSize() {
        final TypedArray array = Objects.requireNonNull(getContext()).getTheme().obtainStyledAttributes(new int[]{android.R.attr.actionBarSize});
        int size = (int) array.getDimension(0, 0);
        return size;
    }

    @SuppressLint("SetTextI18n")
    private void getData(TextView RoverName, TextView landing_Date, TextView launch_Date, TextView Status, TextView max_sol, TextView max_Date, TextView total_Photos){
        compositeDisposable.add(marsNetworkCall.getMarsRoverManifest(rover,Common.APP_ID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<MarsManifest>() {
                               @Override
                               public void accept(MarsManifest marsManifest) throws Exception {
                                   progressBar.setVisibility(GONE);
                                   linearLayout.setVisibility(VISIBLE);
                                   RoverName.setText(getResources().getString(R.string.rover_name) + marsManifest.getManifest().getName());
                                   landing_Date.setText(getResources().getString(R.string.landing_date) + marsManifest.getManifest().getLanding_date());
                                   launch_Date.setText(getResources().getString(R.string.launch_date) + marsManifest.getManifest().getLaunch_date());
                                   Status.setText(getResources().getString(R.string.status) + marsManifest.getManifest().getStatus());
                                   max_sol.setText(getResources().getString(R.string.max_sol) + (marsManifest.getManifest().getMax_sol()));
                                   max_Date.setText(getResources().getString(R.string.max_earth_date) + marsManifest.getManifest().getMax_date());
                                   total_Photos.setText(getResources().getString(R.string.total_photos) + marsManifest.getManifest().getTotal_photos());

                               }
                           }, new Consumer<Throwable>() {
                               @Override
                               public void accept(Throwable throwable) throws Exception {
                                   Toast.makeText(getContext(), throwable.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                               }
                           }
                )
        );
    }
}

