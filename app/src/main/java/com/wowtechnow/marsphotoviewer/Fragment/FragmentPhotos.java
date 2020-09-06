package com.wowtechnow.marsphotoviewer.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.wowtechnow.marsphotoviewer.Adapter.RecyclerViewAdapter;
import com.wowtechnow.marsphotoviewer.Model.Common;
import com.wowtechnow.marsphotoviewer.Model.MarsPhotos;
import com.wowtechnow.marsphotoviewer.R;
import com.wowtechnow.marsphotoviewer.Retrofit.MarsNetworkCall;
import com.wowtechnow.marsphotoviewer.Retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class FragmentPhotos extends Fragment {
    private String rover;
    private int sol;
    private RecyclerView recyclerView;
    private FloatingActionButton Fab;
    private RecyclerViewAdapter adapter;
    private CompositeDisposable disposable;
    private MarsNetworkCall marsNetworkCall;
    private ProgressBar progressBar;
    private List<MarsPhotos> marsPhoto;

    public FragmentPhotos(int sol , String rover) {
        this.sol = sol ;
        this.rover = rover;
        disposable = new CompositeDisposable();
        Retrofit retrofit = RetrofitClient.getInstance();
        marsNetworkCall = retrofit.create(MarsNetworkCall.class);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_photos, container, false);
        recyclerView = view.findViewById(R.id.rvPhotos);
        Fab = view.findViewById(R.id.fabSetting);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        progressBar = view.findViewById(R.id.progressBar);
        progressBar.setVisibility(VISIBLE);
        adapter = new RecyclerViewAdapter(getContext());
        marsPhoto = new ArrayList<>();
        getData();
        Fab.setOnClickListener(v -> {
            BottomSheet bottomSheet = new BottomSheet(rover);
            String TAG = bottomSheet.getTag();
            assert getFragmentManager() != null;
            bottomSheet.show(getFragmentManager(),TAG);
        });
        return view;
    }

    private void getData(){
        disposable.add(marsNetworkCall.getAllMarsPhotosBySol(rover,sol,1,Common.APP_ID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(marsPhotos -> {
                    adapter.setUpMarsList(marsPhotos.getPhotos());
                    recyclerView.setAdapter(adapter);
                    progressBar.setVisibility(GONE);
                    recyclerView.setVisibility(VISIBLE);
                    Fab.setVisibility(VISIBLE);
                }, throwable -> {
                    Toast.makeText(getContext(), throwable.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                })
        );
    }


}