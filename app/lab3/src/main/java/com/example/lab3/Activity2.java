package com.example.lab3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Activity2 extends AppCompatActivity {

    private static final String TAG = "SecondActivity";
    private ActivityResultLauncher<Intent> activityResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
        Log.d(TAG, "onCreate()");

        // Înregistrăm launcher-ul pentru a primi rezultate de la ThirdActivity
        activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Intent data = result.getData();
                        String mesajPrimit = data.getStringExtra("mesaj_raspuns");
                        int sumaCalculata = data.getIntExtra("suma", 0);

                        // Afișăm într-un Toast
                        String mesajToast = mesajPrimit + "\nSuma: " + sumaCalculata;
                        Toast.makeText(Activity2.this, mesajToast, Toast.LENGTH_LONG).show();
                        Log.i(TAG, "Primit răspuns: " + mesajToast);
                    }
                }
        );

        Button btnDeschide = findViewById(R.id.btnDeschideActivitatea3);
        btnDeschide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Creăm Intent pentru ThirdActivity
                Intent intent = new Intent(Activity2.this, Activity3.class);

                // Creăm Bundle cu datele
                Bundle bundle = new Bundle();
                bundle.putString("mesaj", "Salut din Activitatea 2!");
                bundle.putInt("valoare1", 15);
                bundle.putInt("valoare2", 27);

                // Adăugăm Bundle la Intent
                intent.putExtras(bundle);

                // Lansăm activitatea și așteptăm rezultat
                activityResultLauncher.launch(intent);
                Log.i(TAG, "Am trimis mesaj și valori către Activity 3");
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