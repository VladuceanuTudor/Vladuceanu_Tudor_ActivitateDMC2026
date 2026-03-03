package com.example.lab3;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate() - Activitatea este creată");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart() - Activitatea devine vizibilă");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.v(TAG, "onResume() - Activitatea este în prim-plan și utilizatorul interactionează");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.w(TAG, "onPause() - Activitatea pierde focusul");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(TAG, "onStop() - Activitatea nu mai este vizibilă");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "onRestart() - Activitatea este repornită");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy() - Activitatea este distrusă");
    }
}