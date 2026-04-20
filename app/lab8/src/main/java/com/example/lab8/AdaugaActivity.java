package com.example.lab8;

import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Date;

public class AdaugaActivity extends AppCompatActivity {

    private EditText etMarca, etDiagonala, etPret;
    private Spinner spinnerTipPanel;
    private CheckBox cbSmartTV;
    private Button btnSalveaza;

    private TVDatabase database;
    private TVDao tvDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adauga);

        // Initializare baza de date
        database = TVDatabase.getInstance(this);
        tvDao = database.tvDao();

        // Initializare view-uri
        etMarca = findViewById(R.id.etMarca);
        etDiagonala = findViewById(R.id.etDiagonala);
        etPret = findViewById(R.id.etPret);
        spinnerTipPanel = findViewById(R.id.spinnerTipPanel);
        cbSmartTV = findViewById(R.id.cbSmartTV);
        btnSalveaza = findViewById(R.id.btnSalveaza);

        // Setup Spinner cu valorile din enum TipPanel
        ArrayAdapter<TipPanel> adapterSpinner = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, TipPanel.values());
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTipPanel.setAdapter(adapterSpinner);

        // Buton salvare
        btnSalveaza.setOnClickListener(v -> salveazaTV());
    }

    private void salveazaTV() {
        String marca = etMarca.getText().toString().trim();
        String diagonalaText = etDiagonala.getText().toString().trim();
        String pretText = etPret.getText().toString().trim();

        // Validare
        if (marca.isEmpty()) {
            etMarca.setError("Introduceti marca!");
            return;
        }
        if (diagonalaText.isEmpty()) {
            etDiagonala.setError("Introduceti diagonala!");
            return;
        }
        if (pretText.isEmpty()) {
            etPret.setError("Introduceti pretul!");
            return;
        }

        int diagonala = Integer.parseInt(diagonalaText);
        double pret = Double.parseDouble(pretText);
        boolean esteSmart = cbSmartTV.isChecked();
        TipPanel tipPanel = (TipPanel) spinnerTipPanel.getSelectedItem();
        Date dataAdaugarii = new Date(); // Data curenta

        // Creare obiect TV
        TV tv = new TV(marca, diagonala, esteSmart, pret, tipPanel, dataAdaugarii);

        // Salvare in baza de date ROOM
        tvDao.insert(tv);

        Toast.makeText(this, "TV salvat in baza de date!", Toast.LENGTH_SHORT).show();

        // Inchide activity-ul si revino la MainActivity
        setResult(RESULT_OK);
        finish();
    }
}
