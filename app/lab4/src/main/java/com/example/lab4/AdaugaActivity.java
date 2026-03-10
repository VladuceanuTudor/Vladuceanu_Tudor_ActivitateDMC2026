package com.example.lab4;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class AdaugaActivity extends AppCompatActivity {

    EditText etMarca, etDiagonala;
    Spinner spinnerTipPanel;
    CheckBox cbSmartTV;
    RadioGroup rgConditie;
    RatingBar ratingBar;
    Switch switchDisponibil;
    ToggleButton togglePromotie;
    Button btnSalveaza;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adauga);

        etMarca = findViewById(R.id.etMarca);
        etDiagonala = findViewById(R.id.etDiagonala);
        spinnerTipPanel = findViewById(R.id.spinnerTipPanel);
        cbSmartTV = findViewById(R.id.cbSmartTV);
        rgConditie = findViewById(R.id.rgConditie);
        ratingBar = findViewById(R.id.ratingBar);
        switchDisponibil = findViewById(R.id.switchDisponibil);
        togglePromotie = findViewById(R.id.togglePromotie);
        btnSalveaza = findViewById(R.id.btnSalveaza);

        // Setup Spinner cu valorile enum
        ArrayAdapter<TipPanel> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                TipPanel.values()
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTipPanel.setAdapter(adapter);

        btnSalveaza.setOnClickListener(v -> {
            String marca = etMarca.getText().toString();
            int diagonala = Integer.parseInt(etDiagonala.getText().toString());
            boolean esteSmart = cbSmartTV.isChecked();
            double pret = ratingBar.getRating() * 100; // folosim rating ca aproximare pret
            TipPanel tipPanel = (TipPanel) spinnerTipPanel.getSelectedItem();

            TV tv = new TV(marca, diagonala, esteSmart, pret, tipPanel);

            Intent rezultat = new Intent();
            rezultat.putExtra("tv", tv);
            setResult(RESULT_OK, rezultat);
            finish();
        });
    }
}