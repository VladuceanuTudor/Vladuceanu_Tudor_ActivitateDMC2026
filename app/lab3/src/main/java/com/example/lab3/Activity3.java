package com.example.lab3;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Activity3 extends AppCompatActivity {

    private static final String TAG = "ThirdActivity";
    private String mesajPrimit;
    private int valoare1;
    private int valoare2;
    private int suma;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3);
        Log.d(TAG, "onCreate()");

        TextView tvMesaj = findViewById(R.id.tvMesajPrimit);
        Button btnRaspuns = findViewById(R.id.btnTrimiteRaspuns);

        // Preluăm Bundle-ul din Intent
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mesajPrimit = bundle.getString("mesaj", "Niciun mesaj");
            valoare1 = bundle.getInt("valoare1", 0);
            valoare2 = bundle.getInt("valoare2", 0);
            suma = valoare1 + valoare2;

            // Afișăm într-un Toast
            String toastText = mesajPrimit + "\nValoare 1: " + valoare1 + "\nValoare 2: " + valoare2;
            Toast.makeText(this, toastText, Toast.LENGTH_LONG).show();

            // Afișăm și în TextView
            tvMesaj.setText("Mesaj: " + mesajPrimit + "\nValoare 1: " + valoare1 +
                    "\nValoare 2: " + valoare2 + "\nSuma: " + suma);

            Log.i(TAG, "Primit: " + mesajPrimit + ", val1=" + valoare1 + ", val2=" + valoare2);
        }

        // Buton pentru trimiterea răspunsului înapoi
        btnRaspuns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Creăm Intent pentru rezultat
                Intent intentRaspuns = new Intent();
                intentRaspuns.putExtra("mesaj_raspuns", "Răspuns din Activitatea 3!");
                intentRaspuns.putExtra("suma", suma);

                // Setăm rezultatul
                setResult(RESULT_OK, intentRaspuns);
                Log.i(TAG, "Trimitem înapoi suma: " + suma);

                // Închidem activitatea
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.v(TAG, "onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.w(TAG, "onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(TAG, "onStop()");
    }
}