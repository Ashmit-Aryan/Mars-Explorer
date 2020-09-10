package com.wowtechnow.marsphotoviewer.Activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.wowtechnow.marsphotoviewer.R;

public class MainActivity extends AppCompatActivity {
    Intent rover;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rover = new Intent(getApplicationContext(), RoverPhotosActivity.class);

        if (!isNetworkAvailable(getApplicationContext())){
            Toast.makeText(this, "Please Make Sure Of Internet", Toast.LENGTH_SHORT).show();
            return;
        }
        findViewById(R.id.tvCuriosity).setOnClickListener(v -> {
            rover.putExtra("ROVER_NAME", "curiosity");
            startActivity(rover);
        });

        findViewById(R.id.tvOpportunity).setOnClickListener(v -> {
            rover.putExtra("ROVER_NAME", "opportunity");
            startActivity(rover);
        });

        findViewById(R.id.tvSpirit).setOnClickListener(v -> {
            rover.putExtra("ROVER_NAME", "spirit");
            startActivity(rover);
        });

    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
            Log.d("NetworkCheck", "isNetworkAvailable: No");
            return false;
        }
                // get network info for all of the data interfaces (e.g. WiFi, 3G, LTE, etc.)
        NetworkInfo[] info = connectivity.getAllNetworkInfo();
                 // make sure that there is at least one interface to test against
        if (info != null) {
                 // iterate through the interfaces
            for (NetworkInfo networkInfo : info) {
                 // check this interface for a connected state
                if (networkInfo.getState() == NetworkInfo.State.CONNECTED) {
                    Log.d("NetworkCheck", "isNetworkAvailable: Yes");
                    return true;
                }
            }
        }
        return false;
    }
}