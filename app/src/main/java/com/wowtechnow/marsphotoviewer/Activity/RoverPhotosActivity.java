package com.wowtechnow.marsphotoviewer.Activity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.wowtechnow.marsphotoviewer.Fragment.FragmentPhotos;
import com.wowtechnow.marsphotoviewer.R;

import java.util.Objects;

public class RoverPhotosActivity extends AppCompatActivity {
    private String rover ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rover_photos);
        rover = getIntent().getStringExtra("ROVER_NAME");
        EditText etsol = findViewById(R.id.etSol);

        findViewById(R.id.ivSearch).setOnClickListener(v -> {
            String sol = etsol.getText().toString();
            if(TextUtils.isEmpty(sol)){
             etsol.setError("Please Enter Sol",getResources().getDrawable(R.drawable.ic_round_error_outline_24));
             etsol.requestFocus();
             return;
            }
            int Sol =  Integer.parseInt(sol);

            getSupportFragmentManager()
                    .beginTransaction().
                    replace(R.id.fragment_container, new FragmentPhotos(Sol,rover))
                    .commit();
        });
        findViewById(R.id.tvMartinSolInfo).setOnClickListener(v ->{
            AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
            builder.setTitle("About Martina Sol");
            builder.setMessage("Martina sol is 1 day on Mars \n " +
                    "668 sol = 1 year on Mars " +
                    "\n You need To enter the sol on which the rover has send photos \n" +
                    " Example:- If you enter 1 sol then the Photos clicked by rover's first day on mars will be displayed");

            builder.setPositiveButton("OK!!", (dialog, which) -> {
                builder.create().dismiss();
            });
            builder.create().show();
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        View view = getCurrentFocus();
        if (view != null && (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() ==
                MotionEvent.ACTION_MOVE) && view instanceof EditText &&
                !view.getClass().getName().startsWith("android.webkit.")) {
            int[] scrcoords = new int[2];
            view.getLocationOnScreen(scrcoords);
            float x = ev.getRawX() + view.getLeft() - scrcoords[0];
            float y = ev.getRawY() + view.getTop() - scrcoords[1];
            if (x < view.getLeft() || x > view.getRight() || y < view.getTop() || y > view.getBottom())
                ((InputMethodManager) Objects.requireNonNull(this.getSystemService(Context.INPUT_METHOD_SERVICE))).hideSoftInputFromWindow((
                        this.getWindow().getDecorView().getApplicationWindowToken()), 0);
        }
        return super.dispatchTouchEvent(ev);
    }
}