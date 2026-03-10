package com.example.lab4;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    TextView tvMarca, tvDiagonala, tvSmartTV, tvPret, tvTipPanel;
    Button btnAdauga;

    ActivityResultLauncher<Intent> launcher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    TV tv = (TV) result.getData().getSerializableExtra("tv");
                    afiseazaTV(tv);
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvMarca = findViewById(R.id.tvMarca);
        tvDiagonala = findViewById(R.id.tvDiagonala);
        tvSmartTV = findViewById(R.id.tvSmartTV);
        tvPret = findViewById(R.id.tvPret);
        tvTipPanel = findViewById(R.id.tvTipPanel);
        btnAdauga = findViewById(R.id.btnAdauga);

        btnAdauga.setOnClickListener(v -> {
            Intent intent = new Intent(this, AdaugaActivity.class);
            launcher.launch(intent);
        });
    }

    private void afiseazaTV(TV tv) {
        tvMarca.setText("Marcă: " + tv.getMarca());
        tvDiagonala.setText("Diagonală: " + tv.getDiagonala() + " inch");
        tvSmartTV.setText("Smart TV: " + (tv.isEsteSmartTV() ? "Da" : "Nu"));
        tvPret.setText("Preț estimat: " + tv.getPret() + " RON");
        tvTipPanel.setText("Tip panou: " + tv.getTipPanel());
    }
}