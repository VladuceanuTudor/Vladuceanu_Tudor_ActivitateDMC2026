package com.example.lab4;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.Date;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class AdaugaActivity extends AppCompatActivity {

    EditText etMarca, etDiagonala;
    Spinner spinnerTipPanel;
    CheckBox cbSmartTV;
    RadioGroup rgConditie;
    RatingBar ratingBar;
    Switch switchDisponibil;
    ToggleButton togglePromotie;
    Button btnSalveaza;
    CalendarView calendarView;
    Date dataSelectata = new Date();

    private void salveazaInFisier(TV tv) {
        try {
            FileOutputStream fos = openFileOutput("televizoare.txt", MODE_APPEND);
            OutputStreamWriter writer = new OutputStreamWriter(fos);
            writer.write(
                    tv.getMarca() + "|" +
                            tv.getDiagonala() + "|" +
                            tv.isEsteSmartTV() + "|" +
                            tv.getPret() + "|" +
                            tv.getTipPanel().name() + "|" +
                            tv.getDataAdaugarii().getTime() + "\n"
            );
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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

        SharedPreferences prefs = getSharedPreferences(SetariActivity.PREFS_NAME, MODE_PRIVATE);
        int dimensiune = prefs.getInt(SetariActivity.KEY_DIMENSIUNE, 16);
        String culoare = prefs.getString(SetariActivity.KEY_CULOARE, "#1A237E");
        int culoareInt = android.graphics.Color.parseColor(culoare);


        int[] iduriTextView = {
                R.id.tvTitlu,
                R.id.tvInfoGenerale,
                R.id.tvLabelMarca,
                R.id.tvLabelDiagonala,
                R.id.tvLabelTipPanel,
                R.id.tvCaracteristici,
                R.id.tvLabelConditie,
                R.id.tvRating,
                R.id.tvDataAchizitie
        };

        for (int id : iduriTextView) {
            TextView tv = findViewById(id);
            if (tv != null) {
                tv.setTextSize(dimensiune);
                tv.setTextColor(culoareInt);
            }
        }


        TextView tvTitlu = findViewById(R.id.tvTitlu);
        tvTitlu.setTextSize(dimensiune + 4);
        // Setup Spinner
        ArrayAdapter<TipPanel> adapterSpinner = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, TipPanel.values());
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTipPanel.setAdapter(adapterSpinner);

        // Setup Calendar
        calendarView.setOnDateChangeListener((view, an, luna, zi) -> {
            Calendar cal = Calendar.getInstance();
            cal.set(an, luna, zi);
            dataSelectata = cal.getTime();
        });

        // Pre-completare câmpuri dacă e modificare
        TV tvExistent = getIntent().getParcelableExtra("tv");
        if (tvExistent != null) {
            etMarca.setText(tvExistent.getMarca());
            etDiagonala.setText(String.valueOf(tvExistent.getDiagonala()));
            cbSmartTV.setChecked(tvExistent.isEsteSmartTV());

            TipPanel[] tipuri = TipPanel.values();
            for (int i = 0; i < tipuri.length; i++) {
                if (tipuri[i] == tvExistent.getTipPanel()) {
                    spinnerTipPanel.setSelection(i);
                    break;
                }
            }

            dataSelectata = tvExistent.getDataAdaugarii();
            calendarView.setDate(dataSelectata.getTime());
        }

        btnSalveaza.setOnClickListener(v -> {
            String marca = etMarca.getText().toString();
            String diagonalaText = etDiagonala.getText().toString();

            if (marca.isEmpty() || diagonalaText.isEmpty()) {
                Toast.makeText(this, "Completați toate câmpurile!", Toast.LENGTH_SHORT).show();
                return;
            }

            int diagonala = Integer.parseInt(diagonalaText);
            boolean esteSmart = cbSmartTV.isChecked();
            double pret = ratingBar.getRating() * 100;
            TipPanel tipPanel = (TipPanel) spinnerTipPanel.getSelectedItem();

            TV tv = new TV(marca, diagonala, esteSmart, pret, tipPanel, dataSelectata);

            salveazaInFisier(tv);

            Intent rezultat = new Intent();
            rezultat.putExtra("tv", tv);
            setResult(RESULT_OK, rezultat);
            finish();
        });
    }
}