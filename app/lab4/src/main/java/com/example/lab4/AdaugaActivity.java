package com.example.lab4;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.Date;

public class AdaugaActivity extends AppCompatActivity {

    EditText etMarca, etDiagonala;
    Spinner spinnerTipPanel;
    CheckBox cbSmartTV;
    RadioGroup rgConditie;
    RatingBar ratingBar;
    Switch switchDisponibil;
    ToggleButton togglePromotie;
    Button btnSalveaza;
    CalendarView calendarView;          // <- aici, nu în onCreate
    Date dataSelectata = new Date();    // <- aici, nu în onCreate

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
        calendarView = findViewById(R.id.calendarView);

        ArrayAdapter<TipPanel> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                TipPanel.values()
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTipPanel.setAdapter(adapter);

        calendarView.setOnDateChangeListener((view, an, luna, zi) -> {
            Calendar cal = Calendar.getInstance();
            cal.set(an, luna, zi);
            dataSelectata = cal.getTime();
        });

        btnSalveaza.setOnClickListener(v -> {
            String marca = etMarca.getText().toString();
            int diagonala = Integer.parseInt(etDiagonala.getText().toString());
            boolean esteSmart = cbSmartTV.isChecked();
            double pret = ratingBar.getRating() * 100;
            TipPanel tipPanel = (TipPanel) spinnerTipPanel.getSelectedItem();

            TV tv = new TV(marca, diagonala, esteSmart, pret, tipPanel, dataSelectata);

            Intent rezultat = new Intent();
            rezultat.putExtra("tv", tv);
            setResult(RESULT_OK, rezultat);
            finish();
        });
    }
}